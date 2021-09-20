package tfc.assortedutils.API.debug.vanilla;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Hand;
import tfc.assortedutils.API.debug.CustomDebugRenderer;
import tfc.assortedutils.AssortedUtils;
import tfc.assortedutils.registry.ItemRegistry;

public class HeightmapDebugRenderer extends CustomDebugRenderer {
	@Override
	public void render(MatrixStack stack, double playerX, double playerY, double playerZ) {
		AssortedUtils.createBetterFPSGraphSection("assortedutils:drawHeightmap");
		RenderSystem.translated(playerX, playerY, playerZ);
		RenderSystem.enableDepthTest();
		Minecraft.getInstance().debugRenderer.heightMap.render(
				new MatrixStack(), Minecraft.getInstance().getRenderTypeBuffers().getBufferSource(),
				playerX, playerY, playerZ
		);
		AssortedUtils.endBetterFPSGraphSection();
	}
	
	@Override
	public boolean shouldRender() {
		return ItemRegistry.isCorrect(
				Minecraft.getInstance().player.getHeldItem(Hand.OFF_HAND),
				ItemRegistry.DEBUG_TOOL_HEIGHTMAP
		);
	}
}
