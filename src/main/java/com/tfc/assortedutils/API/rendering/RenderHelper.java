package com.tfc.assortedutils.API.rendering;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MutableBoundingBox;
import org.lwjgl.opengl.GL11;

public class RenderHelper {
	public static void drawBox(MatrixStack stack, AxisAlignedBB box, float r, float g, float b, float a) {
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder bufferbuilder = tessellator.getBuffer();
		bufferbuilder.begin(GL11.GL_TRIANGLE_STRIP, DefaultVertexFormats.POSITION_COLOR);
		if (stack != null) {
			RenderSystem.pushMatrix();
			RenderSystem.multMatrix(stack.getLast().getMatrix());
		}
		WorldRenderer.addChainedFilledBoxVertices(bufferbuilder, box.minX, box.minY, box.minZ, box.maxX, box.maxY, box.maxZ, r, g, b, a);
		tessellator.draw();
		if (stack != null)
			RenderSystem.popMatrix();
	}
	
	public static void drawBox(MatrixStack stack, MutableBoundingBox box, float r, float g, float b, float a) {
		drawBox(stack, new AxisAlignedBB(box.minX, box.minY, box.minZ, box.maxX, box.maxY, box.maxZ), r, g, b, a);
	}
	
	public static void drawBoxOutline(MatrixStack stack, MutableBoundingBox box, float r, float g, float b, float a) {
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder bufferbuilder = tessellator.getBuffer();
		bufferbuilder.begin(GL11.GL_LINES, DefaultVertexFormats.POSITION_COLOR);
		RenderSystem.lineWidth(2);
		if (stack != null) {
			RenderSystem.pushMatrix();
			RenderSystem.multMatrix(stack.getLast().getMatrix());
		}
		int x1 = box.minX;
		int y1 = box.minY;
		int z1 = box.minZ;
		int x2 = box.maxX;
		int y2 = box.maxY;
		int z2 = box.maxZ;
		bufferbuilder.pos(x1, y1, z1).color(r, g, b, a).endVertex();
		bufferbuilder.pos(x2, y1, z1).color(r, g, b, a).endVertex();
		bufferbuilder.pos(x2, y1, z1).color(r, g, b, a).endVertex();
		bufferbuilder.pos(x2, y1, z2).color(r, g, b, a).endVertex();
		bufferbuilder.pos(x2, y1, z2).color(r, g, b, a).endVertex();
		bufferbuilder.pos(x1, y1, z2).color(r, g, b, a).endVertex();
		bufferbuilder.pos(x1, y1, z2).color(r, g, b, a).endVertex();
		bufferbuilder.pos(x1, y1, z1).color(r, g, b, a).endVertex();
		bufferbuilder.pos(x1, y1, z1).color(r, g, b, a).endVertex();
		bufferbuilder.pos(x1, y2, z1).color(r, g, b, a).endVertex();
		bufferbuilder.pos(x1, y2, z1).color(r, g, b, a).endVertex();
		bufferbuilder.pos(x2, y2, z1).color(r, g, b, a).endVertex();
		bufferbuilder.pos(x2, y2, z1).color(r, g, b, a).endVertex();
		bufferbuilder.pos(x2, y1, z1).color(r, g, b, a).endVertex();
		bufferbuilder.pos(x2, y2, z1).color(r, g, b, a).endVertex();
		bufferbuilder.pos(x2, y2, z2).color(r, g, b, a).endVertex();
		bufferbuilder.pos(x2, y2, z2).color(r, g, b, a).endVertex();
		bufferbuilder.pos(x2, y1, z2).color(r, g, b, a).endVertex();
		bufferbuilder.pos(x2, y2, z2).color(r, g, b, a).endVertex();
		bufferbuilder.pos(x1, y2, z2).color(r, g, b, a).endVertex();
		bufferbuilder.pos(x1, y2, z2).color(r, g, b, a).endVertex();
		bufferbuilder.pos(x1, y1, z2).color(r, g, b, a).endVertex();
		bufferbuilder.pos(x1, y2, z2).color(r, g, b, a).endVertex();
		bufferbuilder.pos(x1, y2, z1).color(r, g, b, a).endVertex();
		tessellator.draw();
		if (stack != null)
			RenderSystem.popMatrix();
	}
}
