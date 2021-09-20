package tfc.assortedutils;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import tfc.assortedutils.API.debug.ICustomDebugRenderer;
import tfc.assortedutils.custom_registries.debug_renderer.DebugRendererRegistry;

public class Client {
	public static void onRenderWorldLast(RenderWorldLastEvent event) {
		for (ICustomDebugRenderer debugRenderer : DebugRendererRegistry.getAllValues()) {
			if (debugRenderer.shouldRender()) {
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
		}
	}
}
