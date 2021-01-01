package com.tfc.assortedutils.mixin_code;

import com.tfc.assortedutils.API.entities.IVoxelShapeEntity;
import com.tfc.assortedutils.API.entities.VoxelShapeEntityRaytraceResult;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemFrameEntity;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.vector.Vector3d;

public class VoxelshapeEntity {
	public static void pickPost(Minecraft mc, float partialTicks) {
		Entity entity = mc.getRenderViewEntity();
		
		if (mc.objectMouseOver == null)
			return;
		if (!(mc.objectMouseOver.getType().equals(RayTraceResult.Type.ENTITY)))
			return;
		
		EntityRayTraceResult result = (EntityRayTraceResult) mc.objectMouseOver;
		if (!(result.getEntity() instanceof IVoxelShapeEntity))
			return;
		if (((IVoxelShapeEntity) result.getEntity()).getRaytraceShape() == null)
			return;
		
		
		double distance = 0;
		double reach = 8;
		if (mc.playerController.extendedReach())
			distance = 6.0D;
		
		Vector3d playerPos = Minecraft.getInstance().player.getEyePosition(partialTicks);
		Vector3d lookVec = entity.getLook(1.0F);
		Vector3d reachVec = playerPos.add(lookVec.x * reach, lookVec.y * reach, lookVec.z * reach);
		
		AxisAlignedBB axisalignedbb = entity.getBoundingBox().expand(lookVec.scale(reach)).grow(1.0D, 1.0D, 1.0D);
		EntityRayTraceResult result1 = ProjectileHelper.rayTraceEntities(entity, playerPos, reachVec, axisalignedbb, (p_215312_0_) -> {
			if (p_215312_0_ == Minecraft.getInstance().player) return false;
			boolean voxelShapeResult = true;
			if (p_215312_0_ instanceof IVoxelShapeEntity) {
				if (((IVoxelShapeEntity) p_215312_0_).getRaytraceShape() != null) {
					VoxelShape shape = ((IVoxelShapeEntity) p_215312_0_.getEntity()).getRaytraceShape();

//					voxelShapeResult = false;
//					for (AxisAlignedBB box : shape.toBoundingBoxList()) {
////						if (box.offset(entity.getPositionVec()).rayTrace(playerPos,reachVec).isPresent()) {
////							voxelShapeResult = true;
////							break;
////						}
////						if (box.offset(entity.getPositionVec()).rayTrace(reachVec,playerPos).isPresent()) {
////							voxelShapeResult = true;
////							break;
////						}
//						if (new AxisAlignedBB(playerPos.x,playerPos.y,playerPos.z,reachVec.x,reachVec.y,reachVec.z).intersects(box))
//							voxelShapeResult = true;
//					}
//					if (!voxelShapeResult) {
					RayTraceResult result2 = shape.withOffset(
							-(entity.getBoundingBox().maxX - entity.getBoundingBox().minX) / 2f,
							0,
							-(entity.getBoundingBox().maxZ - entity.getBoundingBox().minZ) / 2f
					).withOffset(
							-(entity.getBoundingBox().maxX - entity.getBoundingBox().minX) / 3f,
							0,
							-(entity.getBoundingBox().maxZ - entity.getBoundingBox().minZ) / 3f
					).withOffset(p_215312_0_.getPosX(), p_215312_0_.getPosY(), p_215312_0_.getPosZ()).rayTrace(
							playerPos, reachVec, new BlockPos(0, 0, 0)
					);
					voxelShapeResult = result2 != null;
//					}
				}
			}
			return !p_215312_0_.isSpectator() && p_215312_0_.canBeCollidedWith() && voxelShapeResult;
		}, Integer.MAX_VALUE);
		
		if (result1 != null) {
			mc.objectMouseOver = new VoxelShapeEntityRaytraceResult(result1.getEntity(), result1.getHitVec(), ((IVoxelShapeEntity) result.getEntity()).getRaytraceShape());
			mc.pointedEntity = result1.getEntity();
			return;
		} else {
			mc.objectMouseOver = entity.pick(reach, partialTicks, false);
			
			EntityRayTraceResult entityraytraceresult = ProjectileHelper.rayTraceEntities(entity, playerPos, reachVec, axisalignedbb, (p_215312_0_) -> {
				if (p_215312_0_ == Minecraft.getInstance().player) return false;
				boolean voxelShapeResult = true;
				if (p_215312_0_ instanceof IVoxelShapeEntity) {
					if (((IVoxelShapeEntity) p_215312_0_).getRaytraceShape() != null) {
						VoxelShape shape = ((IVoxelShapeEntity) p_215312_0_).getRaytraceShape();
						RayTraceResult result2 = shape.withOffset(
								-(entity.getBoundingBox().maxX - entity.getBoundingBox().minX) / 2f,
								0,
								-(entity.getBoundingBox().maxZ - entity.getBoundingBox().minZ) / 2f
						).withOffset(
								-(entity.getBoundingBox().maxX - entity.getBoundingBox().minX) / 3f,
								0,
								-(entity.getBoundingBox().maxZ - entity.getBoundingBox().minZ) / 3f
						).withOffset(p_215312_0_.getPosX(), p_215312_0_.getPosY(), p_215312_0_.getPosZ()).rayTrace(
								playerPos, reachVec, new BlockPos(0, 0, 0)
						);
						voxelShapeResult = result2 != null;
					}
				}
				return !p_215312_0_.isSpectator() && p_215312_0_.canBeCollidedWith() && voxelShapeResult;
			}, Integer.MAX_VALUE);
			
			mc.objectMouseOver = entity.pick(reach, partialTicks, false);
			
			double distEntity = Double.POSITIVE_INFINITY;
			if (entityraytraceresult != null)
				distEntity = Math.abs(playerPos.squareDistanceTo(entityraytraceresult.getHitVec()));
			
			double distBlock = Double.POSITIVE_INFINITY;
			if (mc.objectMouseOver != null)
				distBlock = Math.abs(playerPos.squareDistanceTo(mc.objectMouseOver.getHitVec()));
			
			if (distEntity < distBlock && entityraytraceresult != null) {
				if (entityraytraceresult.getEntity() instanceof IVoxelShapeEntity) {
					RayTraceResult result2 = ((IVoxelShapeEntity) entityraytraceresult.getEntity()).getRaytraceShape().rayTrace(
							playerPos.subtract(entity.getPositionVec()), reachVec.subtract(entity.getPositionVec()), new BlockPos(0, 0, 0)
					);
					
					if (result2 == null) {
						Entity entity1 = entityraytraceresult.getEntity();
						mc.objectMouseOver = entityraytraceresult;
						if (entity1 instanceof LivingEntity || entity1 instanceof ItemFrameEntity)
							mc.pointedEntity = entity1;
						return;
					}
					
					mc.objectMouseOver = new VoxelShapeEntityRaytraceResult(entity, result2.getHitVec(), ((IVoxelShapeEntity) entityraytraceresult.getEntity()).getRaytraceShape());
					mc.pointedEntity = entityraytraceresult.getEntity();
				} else {
					Entity entity1 = entityraytraceresult.getEntity();
					mc.objectMouseOver = entityraytraceresult;
					if (entity1 instanceof LivingEntity || entity1 instanceof ItemFrameEntity)
						mc.pointedEntity = entity1;
				}
				return;
			}
		}
		mc.pointedEntity = null;
	}
}
