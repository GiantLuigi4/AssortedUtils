package com.tfc.assortedutils.mixin_code;

import com.tfc.assortedutils.API.entities.IVoxelShapeEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemFrameEntity;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;

public class VoxelshapeEntity {
	public static void pickPost(Minecraft mc, float partialTicks) {
		Entity entity = mc.getRenderViewEntity();
		if (mc.objectMouseOver.getType().equals(RayTraceResult.Type.ENTITY)) {
			EntityRayTraceResult result = (EntityRayTraceResult) mc.objectMouseOver;
			if (result.getEntity() instanceof IVoxelShapeEntity) {
				Vector3d playerPos = Minecraft.getInstance().player.getEyePosition(partialTicks);
				double distance = 0;
				double reach = 8;
				if (mc.playerController.extendedReach()) {
					distance = 6.0D;
				}
				
				Vector3d lookVec = entity.getLook(1.0F);
				Vector3d reachVec = playerPos.add(lookVec.x * reach, lookVec.y * reach, lookVec.z * reach);
				RayTraceResult result1 = ((IVoxelShapeEntity) result.getEntity()).getRaytraceShape().rayTrace(
						playerPos.subtract(entity.getPositionVec()), reachVec.subtract(entity.getPositionVec()), new BlockPos(0, 0, 0)
				);
				AxisAlignedBB axisalignedbb = entity.getBoundingBox().expand(lookVec.scale(reach)).grow(1.0D, 1.0D, 1.0D);
				
				if (result1 != null)
					mc.objectMouseOver = new EntityRayTraceResult(entity, result1.getHitVec());
				else {
					mc.objectMouseOver = entity.pick(reach, partialTicks, false);
					
					EntityRayTraceResult entityraytraceresult = ProjectileHelper.rayTraceEntities(entity, playerPos, reachVec, axisalignedbb, (p_215312_0_) -> {
						boolean voxelShapeResult = true;
						if (p_215312_0_ instanceof IVoxelShapeEntity) {
							RayTraceResult result2 = ((IVoxelShapeEntity) result.getEntity()).getRaytraceShape().rayTrace(
									playerPos.subtract(entity.getPositionVec()), reachVec.subtract(entity.getPositionVec()), new BlockPos(0, 0, 0)
							);
							voxelShapeResult = result2 != null;
						}
						return !p_215312_0_.isSpectator() && p_215312_0_.canBeCollidedWith() && voxelShapeResult;
					}, distance);
					
					mc.objectMouseOver = entity.pick(reach, partialTicks, false);
					
					double distEntity = playerPos.squareDistanceTo(entityraytraceresult.getHitVec());
					double distBlock = playerPos.squareDistanceTo(mc.objectMouseOver.getHitVec());
					if (distEntity <= distBlock) {
						if (entityraytraceresult.getEntity() instanceof IVoxelShapeEntity) {
							RayTraceResult result2 = ((IVoxelShapeEntity) entityraytraceresult.getEntity()).getRaytraceShape().rayTrace(
									playerPos.subtract(entity.getPositionVec()), reachVec.subtract(entity.getPositionVec()), new BlockPos(0, 0, 0)
							);
							
							mc.objectMouseOver = new EntityRayTraceResult(entity, result1.getHitVec());
							mc.pointedEntity = entityraytraceresult.getEntity();
						} else {
							Entity entity1 = entityraytraceresult.getEntity();
							mc.objectMouseOver = entityraytraceresult;
							if (entity1 instanceof LivingEntity || entity1 instanceof ItemFrameEntity)
								mc.pointedEntity = entity1;
						}
					}
				}
			}
		}
	}
}
