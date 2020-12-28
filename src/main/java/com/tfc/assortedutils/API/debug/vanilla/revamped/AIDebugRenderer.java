package com.tfc.assortedutils.API.debug.vanilla.revamped;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.tfc.assortedutils.API.debug.CustomDebugRenderer;
import com.tfc.assortedutils.AssortedUtils;
import com.tfc.assortedutils.registry.ItemRegistry;
import com.tfc.assortedutils.utils.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.pathfinding.Path;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.pathfinding.PathPoint;
import net.minecraft.util.Hand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;

import java.util.Map;

/**
 * If you want and example custom renderer, then check out {@link com.tfc.assortedutils.API.debug.vanilla.HeightmapDebugRenderer}
 */
public class AIDebugRenderer extends CustomDebugRenderer {
	public static void drawLine(Path path, int camX, int camY, int camZ) {
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder bufferbuilder = tessellator.getBuffer();
		bufferbuilder.begin(3, DefaultVertexFormats.POSITION_COLOR);
		
		for (int i = 0; i < path.getCurrentPathLength(); ++i) {
			PathPoint point = path.getPathPointFromIndex(i);
			Color c;
			if (point.nodeType.equals(PathNodeType.BLOCKED)) {
				c = new Color(255, 0, 0);
			} else {
				if (i < path.getCurrentPathIndex()) {
					c = new Color(0, 255, 0);
				} else if (i == path.getCurrentPathIndex()) {
					c = new Color(0, 255, 255);
				} else if (
						point.nodeType.equals(PathNodeType.DANGER_FIRE) ||
								point.nodeType.equals(PathNodeType.DANGER_OTHER) ||
								point.nodeType.equals(PathNodeType.DAMAGE_CACTUS) ||
								point.nodeType.equals(PathNodeType.DAMAGE_FIRE) ||
								point.nodeType.equals(PathNodeType.DANGER_CACTUS)
				) {
					c = new Color(255, 0, 255);
				} else if (
						point.nodeType.equals(PathNodeType.WATER) ||
								point.nodeType.equals(PathNodeType.WATER_BORDER)
				) {
					c = new Color(0, 128, 255);
				} else {
					c = new Color(0, 0, 255);
				}
			}
			double dist = Minecraft.getInstance().player.getDistanceSq(point.x, point.y, point.z);
			if (dist >= (360 * 3)) {
				RenderSystem.lineWidth(1f);
			} else {
				float distI = (float) Math.max(1, Math.abs(((360f * 3) - dist) / ((360f * 3) / 6f)));
				RenderSystem.lineWidth(distI);
			}
			bufferbuilder.pos((double) point.x + 0.5D, (double) point.y + 0.5D, (double) point.z + 0.5D).color(c.getRed(), c.getGreen(), c.getBlue(), 255).endVertex();
//			for (int x = -1; x <= 1; x++) {
//				for (int y = -1; y <= 1; y++) {
//					for (int z = -1; z <= 1; z++) {
//						for (int x1 = -1; x1 <= 1; x1++) {
//							for (int y1 = -1; y1 <= 1; y1++) {
//								for (int z1 = -1; z1 <= 1; z1++) {
//									float scl = 20;
//									bufferbuilder.pos((double) point.x - camX + 0.5D + x / scl, (double) point.y - camY + 0.5D + y / scl, (double) point.z - camZ + 0.5D + z / scl).color(red, blue, green, 255).endVertex();
//									bufferbuilder.pos((double) point.x - camX + 0.5D + x1 / scl, (double) point.y - camY + 0.5D + y1 / scl, (double) point.z - camZ + 0.5D + z1 / scl).color(red, blue, green, 255).endVertex();
//								}
//							}
//						}
//					}
//				}
//			}
//			bufferbuilder.pos((double) point.x - camX + 0.5D, (double) point.y - camY + 0.5D, (double) point.z - camZ + 0.5D).color(red, blue, green, 255).endVertex();
		}
		
		tessellator.draw();
	}
	
	@Override
	public boolean shouldRender() {
		return ItemRegistry.isCorrect(
				Minecraft.getInstance().player.getHeldItem(Hand.OFF_HAND),
				ItemRegistry.DEBUG_TOOL_AI
		);
	}
	
	@Override
	public void render(MatrixStack stack, double playerX, double playerY, double playerZ) {
		AssortedUtils.createBetterFPSGraphSection("assortedutils:drawPathfindingOverlay");
		RenderSystem.pushMatrix();
//		RenderSystem.rotatef(Minecraft.getInstance().getRenderManager().info.getPitch(), 1, 0, 0);
//		RenderSystem.rotatef(Minecraft.getInstance().getRenderManager().info.getYaw() + 180, 0, 1, 0);
		try {
//					Minecraft.getInstance().debugRenderer.pathfinding.render(
//							new MatrixStack(),
//							Minecraft.getInstance().getRenderTypeBuffers().getBufferSource(),
//							Minecraft.getInstance().getRenderManager().info.getProjectedView().x,
//							Minecraft.getInstance().getRenderManager().info.getProjectedView().y,
//							Minecraft.getInstance().getRenderManager().info.getProjectedView().z
//					);
//			Field map = Minecraft.getInstance().debugRenderer.pathfinding.getClass().getDeclaredField("pathMap");
//			Field map = ObfuscationReflectionHelper.findField(PathfindingDebugRenderer.class, "field_188291_b");
//			map.setAccessible(true);
			Map<Integer, Path> pathMap = Minecraft.getInstance().debugRenderer.pathfinding.pathMap;
			RenderSystem.pushMatrix();
			RenderSystem.enableBlend();
			RenderSystem.defaultAlphaFunc();
			RenderSystem.defaultBlendFunc();
			RenderSystem.color4f(0.0F, 1.0F, 0.0F, 0.75F);
			RenderSystem.disableTexture();
			pathMap.forEach((id, path) -> {
				Entity e = Minecraft.getInstance().world.getEntityByID(id);
				if (e != null) {
					for (int i = 0; i < path.getCurrentPathLength(); i++) {
						RenderSystem.pushMatrix();
						RenderSystem.enableBlend();
						RenderSystem.defaultAlphaFunc();
						RenderSystem.defaultBlendFunc();
						RenderSystem.color4f(0.0F, 1.0F, 0.0F, 0.75F);
						RenderSystem.disableTexture();
						try {
							Vector3d pos = path.getVectorFromIndex(e, i);
							PathPoint point = path.getPathPointFromIndex(i);
//									Color c;
//									if (point.nodeType.equals(PathNodeType.BLOCKED)) {
//										c = new Color(255,0,0);
//									} else {
//										if (point.index < path.getCurrentPathIndex()) {
//											c = new Color(0,255,0);
//										} else if (point.index == path.getCurrentPathIndex()) {
//											c = new Color(0,255,255);
//										} else if (
//												point.nodeType.equals(PathNodeType.DANGER_FIRE) ||
//														point.nodeType.equals(PathNodeType.DANGER_OTHER) ||
//														point.nodeType.equals(PathNodeType.DAMAGE_CACTUS) ||
//														point.nodeType.equals(PathNodeType.DAMAGE_FIRE) ||
//														point.nodeType.equals(PathNodeType.DANGER_CACTUS)
//										) {
//											c = new Color(255,0,255);
//										} else {
//											c = new Color(0,0,255);
//										}
//									}
//								Vector3d posMinusEPos = pos.subtract(e.getPositionVec());
//								AxisAlignedBB boundingBox = e.getBoundingBox().offset(posMinusEPos);
//								AxisAlignedBB boundingBox = new AxisAlignedBB(pos.x-0.5,pos.y+(1f/16),pos.z-0.5,pos.x+0.5,pos.y,pos.z+0.5);
							
							float hue = (float) i / (float) path.getCurrentPathLength() * 0.33F;
							int color = i == 0 ? 0 : MathHelper.hsvToRGB(hue, 0.9F, 0.9F);
							int red = color >> 16 & 255;
							int blue = color >> 8 & 255;
							int green = color & 255;
							
							RenderSystem.enableDepthTest();
							AxisAlignedBB boundingBox = new AxisAlignedBB(pos.x - 0.1, pos.y + 0.6, pos.z - 0.1, pos.x + 0.1, pos.y + 0.4, pos.z + 0.1);
							net.minecraft.client.renderer.debug.DebugRenderer.renderBox(boundingBox, red / 255f, green / 255f, blue / 255f, 1);
							net.minecraft.client.renderer.debug.DebugRenderer.renderText("" + i, pos.x * 2, pos.y * 2f + 1.25, pos.z * 2, -1, 0.02F, true, 0.0F, true);
							net.minecraft.client.renderer.debug.DebugRenderer.renderText(point.nodeType.name(), pos.x * 2, pos.y * 2f + 1.5, pos.z * 2, -1, 0.02F, true, 0.0F, true);
							net.minecraft.client.renderer.debug.DebugRenderer.renderText("" + point.costMalus, pos.x * 2, pos.y * 2f + 0.75, pos.z * 2, -1, 0.02F, true, 0.0F, true);
						} catch (Throwable ignored) {
							ignored.printStackTrace();
						}
						
						RenderSystem.enableBlend();
						RenderSystem.defaultAlphaFunc();
						RenderSystem.defaultBlendFunc();
						RenderSystem.color4f(0.0F, 1.0F, 0.0F, 0.75F);
						RenderSystem.disableTexture();
						Color c = new Color(0, 255, 0);
						AxisAlignedBB boundingBox = new AxisAlignedBB(
								path.getTarget().getX() + 0.25, path.getTarget().getY() + 0.25, path.getTarget().getZ() + 0.25,
								path.getTarget().getX() + 0.75, path.getTarget().getY() + 0.75, path.getTarget().getZ() + 0.75
						);
						
						drawLine(path, 0, 0, 0);
						
						net.minecraft.client.renderer.debug.DebugRenderer.renderBox(boundingBox, c.getRed() / 255f, c.getGreen() / 255f, c.getBlue() / 255f, 0.5f);
						RenderSystem.popMatrix();
					}
				}
			});
			RenderSystem.enableTexture();
			RenderSystem.disableBlend();
			RenderSystem.popMatrix();
		} catch (Throwable ignored) {
			ignored.printStackTrace();
		}
		RenderSystem.popMatrix();
		AssortedUtils.endBetterFPSGraphSection();
	}
}
