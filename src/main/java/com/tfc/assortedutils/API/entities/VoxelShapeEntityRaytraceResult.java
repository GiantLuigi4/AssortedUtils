package com.tfc.assortedutils.API.entities;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.vector.Vector3d;

public class VoxelShapeEntityRaytraceResult extends EntityRayTraceResult {
	private final VoxelShape shape;
	
	public VoxelShapeEntityRaytraceResult(Entity entityIn, VoxelShape shape) {
		super(entityIn);
		this.shape = shape;
	}
	
	public VoxelShapeEntityRaytraceResult(Entity entityIn, Vector3d hitVec, VoxelShape shape) {
		super(entityIn, hitVec);
		this.shape = shape;
	}
	
	public VoxelShape getShape() {
		return shape;
	}
}
