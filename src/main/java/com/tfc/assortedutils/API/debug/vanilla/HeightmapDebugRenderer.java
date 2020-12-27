package com.tfc.assortedutils.API.debug.vanilla;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.tfc.assortedutils.API.debug.CustomDebugRenderer;
import com.tfc.assortedutils.registry.ItemRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Hand;

public class HeightmapDebugRenderer extends CustomDebugRenderer {
	@Override
	public void render(MatrixStack stack, double playerX, double playerY, double playerZ) {
		RenderSystem.translated(playerX, playerY, playerZ);
		RenderSystem.enableDepthTest();
		Minecraft.getInstance().debugRenderer.heightMap.render(
				new MatrixStack(), Minecraft.getInstance().getRenderTypeBuffers().getBufferSource(),
				playerX, playerY, playerZ
		);
	}
	
	@Override
	public boolean shouldRender() {
		return ItemRegistry.isCorrect(
				Minecraft.getInstance().player.getHeldItem(Hand.OFF_HAND),
				ItemRegistry.DEBUG_TOOL_HEIGHTMAP
		);
	}
}
