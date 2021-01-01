package com.tfc.assortedutils.API.entities;

import net.minecraft.entity.Entity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.vector.Vector3d;

public class VoxelShapeEntityRaytraceResult extends EntityRayTraceResult {
	private final VoxelShape shape;
	private final Direction dir;
	
	public VoxelShapeEntityRaytraceResult(Entity entityIn, Vector3d hitVec, Direction dir) {
		super(entityIn, hitVec);
		if (entityIn instanceof IVoxelShapeEntity) this.shape = ((IVoxelShapeEntity) entityIn).getRaytraceShape();
		else this.shape = null;
		this.dir = dir;
	}
	
	public VoxelShapeEntityRaytraceResult(Entity entityIn, Vector3d hitVec, VoxelShape shape, Direction dir) {
		super(entityIn, hitVec);
		this.shape = shape;
		this.dir = dir;
	}
	
	public Direction getDir() {
		return dir;
	}
	
	public VoxelShape getShape() {
		return shape;
	}
}
