package com.tfc.assortedutils.API.raytracing;

import com.tfc.assortedutils.API.transformations.matrix.Matrix4fDistSafe;
import com.tfc.assortedutils.API.transformations.quaternion.QuaternionHelper;
import com.tfc.assortedutils.mixins.VoxelShapeAccessor;
import it.unimi.dsi.fastutil.doubles.DoubleList;
import net.minecraft.util.Direction;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3d;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;

public class RotatedVoxelShape extends VoxelShape {
	public Quaternion rotation;
	private final VoxelShape shape;
	
	public RotatedVoxelShape(VoxelShape shape) {
		super(((VoxelShapeAccessor) shape).getPart());
		this.shape = shape;
		this.rotation = QuaternionHelper.fromAngles(0, 0, 0, false);
	}
	
	public RotatedVoxelShape(VoxelShape shape, Quaternion quaternion) {
		super(((VoxelShapeAccessor) shape).getPart());
		this.shape = shape;
		this.rotation = quaternion;
	}
	
	@Override
	protected DoubleList getValues(Direction.Axis axis) {
		return ((VoxelShapeAccessor) shape).doGetValues(axis);
	}
	
	public static AxisAlignedBB rotateBoundingBox(AxisAlignedBB axisAlignedBB, Matrix4fDistSafe matrix) {
		AxisAlignedBB bb = axisAlignedBB;
		
		double[] x = new double[]{bb.minX, bb.maxX};
		double[] y = new double[]{bb.minY, bb.maxY};
		double[] z = new double[]{bb.minZ, bb.maxZ};
		Vector3d min = new Vector3d(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY);
		Vector3d max = new Vector3d(Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY);
		
		for (double x1 : x) {
			for (double y1 : y) {
				for (double z1 : z) {
					Vector3d point = new Vector3d(x1, y1, z1);
					point = matrix.transformS(point.subtract(bb.getCenter())).add(bb.getCenter());
					min = new Vector3d(
							Math.min(min.x, point.x),
							Math.min(min.y, point.y),
							Math.min(min.z, point.z)
					);
					max = new Vector3d(
							Math.max(max.x, point.x),
							Math.max(max.y, point.y),
							Math.max(max.z, point.z)
					);
				}
			}
		}
		
		bb = new AxisAlignedBB(
				Math.min(min.x, max.x),
				Math.min(min.y, max.y),
				Math.min(min.z, max.z),
				Math.max(min.x, max.x),
				Math.max(min.y, max.y),
				Math.max(min.z, max.z)
		);
		
		return bb;
	}
	
	@Nullable
	@Override
	public BlockRayTraceResult rayTrace(Vector3d startVec, Vector3d endVec, BlockPos pos) {
		double distBest = Double.POSITIVE_INFINITY;
		Vector3d bestVec = null;
		
		Matrix4fDistSafe matrix = new Matrix4fDistSafe(rotation);
		
		for (AxisAlignedBB axisAlignedBB : shape.toBoundingBoxList()) {
			AxisAlignedBB bb = rotateBoundingBox(axisAlignedBB, matrix);
			
			Optional<Vector3d> vector3dOptional = bb.rayTrace(startVec, endVec);
			if (vector3dOptional.isPresent()) {
				double dist = vector3dOptional.get().distanceTo(startVec);
				
				if (dist < distBest) {
					distBest = dist;
					bestVec = vector3dOptional.get();
				}
			}
		}
		
		Vector3d vector3d = endVec.subtract(startVec);
		Vector3d vector3d1 = startVec.add(vector3d.scale(0.001D));
		matrix.transformS(vector3d1);
		if (bestVec != null) {
			return new BlockRayTraceResult(
					bestVec, Direction.getFacingFromVector(vector3d.x, vector3d.y, vector3d.z).getOpposite(), pos, true
			);
		} else {
			return null;
		}

//		if (shape != null) return shape.rayTrace(startVec, endVec, pos);
//		return super.rayTrace(startVec, endVec, pos);
	}
	
	public AxisAlignedBB rotateBoundingBox(AxisAlignedBB axisAlignedBB) {
		return rotateBoundingBox(axisAlignedBB, new Matrix4fDistSafe(rotation));
	}
	
	@Override
	public AxisAlignedBB getBoundingBox() {
		AxisAlignedBB bb;
		if (shape != null) bb = shape.getBoundingBox();
		else bb = super.getBoundingBox();
		
		double[] x = new double[]{bb.minX, bb.maxX};
		double[] y = new double[]{bb.minY, bb.maxY};
		double[] z = new double[]{bb.minZ, bb.maxZ};
		Vector3d min = bb.getCenter();
		Vector3d max = bb.getCenter();
		
		Matrix4fDistSafe matrix = new Matrix4fDistSafe(rotation);
		for (double x1 : x) {
			for (double y1 : y) {
				for (double z1 : z) {
					Vector3d point = new Vector3d(x1, y1, z1);
					point = matrix.transformS(point);
					min = new Vector3d(
							Math.min(min.x, point.x),
							Math.min(min.y, point.y),
							Math.min(min.z, point.z)
					);
					max = new Vector3d(
							Math.max(max.x, point.x),
							Math.max(max.y, point.y),
							Math.max(max.z, point.z)
					);
				}
			}
		}
		
		bb = new AxisAlignedBB(
				Math.min(min.x, max.x),
				Math.min(min.y, max.y),
				Math.min(min.z, max.z),
				Math.max(min.x, max.x),
				Math.max(min.y, max.y),
				Math.max(min.z, max.z)
		);
		
		return bb;
	}
	
	@Override
	public VoxelShape withOffset(double xOffset, double yOffset, double zOffset) {
		if (shape == null) return new RotatedVoxelShape(super.withOffset(xOffset, yOffset, zOffset), rotation);
		else return new RotatedVoxelShape(this.shape.withOffset(xOffset, yOffset, zOffset), rotation);
	}
	
	@Override
	public void forEachBox(VoxelShapes.ILineConsumer action) {
		if (shape != null) shape.forEachBox(action);
		else super.forEachBox(action);
	}
	
	@Override
	public void forEachEdge(VoxelShapes.ILineConsumer action) {
		if (shape != null) shape.forEachEdge(action);
		else super.forEachEdge(action);
	}
	
	@Override
	public List<AxisAlignedBB> toBoundingBoxList() {
		if (shape != null) return shape.toBoundingBoxList();
		return super.toBoundingBoxList();
	}
}
