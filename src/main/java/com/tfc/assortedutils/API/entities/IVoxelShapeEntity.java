package com.tfc.assortedutils.API.entities;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.math.shapes.VoxelShape;

public interface IVoxelShapeEntity {
	VoxelShape getRaytraceShape();
	
	/**
	 * so the normal interact method doesn't provide the raytrace result, and VoxelShapeEntityRaytraceResult isn't all that useful unless you have it
	 *
	 * @param player the player interacting
	 * @param result the voxel shape raytrace result context, which contains the hit direction, vector, and entity (which can also be accessed using "this")
	 * @return PASS to not cancel the vanilla interaction method, CONSUME or FAIL to cancel it
	 */
	default ActionResultType onInteract(PlayerEntity player, VoxelShapeEntityRaytraceResult result) {
		return ActionResultType.PASS;
	}
}
