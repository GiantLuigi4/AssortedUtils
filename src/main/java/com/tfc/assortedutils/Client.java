package com.tfc.assortedutils;

import com.mojang.blaze3d.systems.RenderSystem;
import com.tfc.assortedutils.API.debug.DebugRendererRegistry;
import com.tfc.assortedutils.API.debug.ICustomDebugRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraftforge.client.event.RenderWorldLastEvent;

public class Client {
	public static void onRenderWorldLast(RenderWorldLastEvent event) {
//		if (ItemRegistry.isCorrect(Minecraft.getInstance().player.getHeldItem(Hand.OFF_HAND),ItemRegistry.DEBUG_TOOL_AI)) {
//
//		}
//		if (ItemRegistry.isCorrect(Minecraft.getInstance().player.getHeldItem(Hand.OFF_HAND),ItemRegistry.DEBUG_TOOL_HEIGHTMAP)) {
		for (ICustomDebugRenderer debugRenderer : DebugRendererRegistry.getAllValues()) {
			RenderSystem.pushMatrix();
			RenderSystem.rotatef(Minecraft.getInstance().getRenderManager().info.getPitch(), 1, 0, 0);
			RenderSystem.rotatef(Minecraft.getInstance().getRenderManager().info.getYaw() + 180, 0, 1, 0);
			event.getMatrixStack().push();
			event.getMatrixStack().translate(
					-Minecraft.getInstance().getRenderManager().info.getProjectedView().x,
					-Minecraft.getInstance().getRenderManager().info.getProjectedView().y,
					-Minecraft.getInstance().getRenderManager().info.getProjectedView().z
			);
			Quaternion rotation = Minecraft.getInstance().getRenderManager().info.getRotation().copy();
			rotation.multiply(-1);
			event.getMatrixStack().rotate(rotation);
			RenderSystem.translated(
					-Minecraft.getInstance().getRenderManager().info.getProjectedView().x,
					-Minecraft.getInstance().getRenderManager().info.getProjectedView().y,
					-Minecraft.getInstance().getRenderManager().info.getProjectedView().z
			);
			debugRenderer.render(event.getMatrixStack(),
					Minecraft.getInstance().getRenderManager().info.getProjectedView().x,
					Minecraft.getInstance().getRenderManager().info.getProjectedView().y,
					Minecraft.getInstance().getRenderManager().info.getProjectedView().z
			);
			event.getMatrixStack().pop();
			RenderSystem.popMatrix();
		}
//		}
	}
}
