package com.tfc.assortedutils.API.rendering;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.util.math.vector.Matrix4f;
import org.lwjgl.opengl.GL11;

public class RenderHelper {
	private static final MatrixStack blankStack = new MatrixStack();
	
	public static void drawBox(MatrixStack stack, AxisAlignedBB box, float r, float g, float b, float a) {
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder bufferbuilder = tessellator.getBuffer();
		bufferbuilder.begin(GL11.GL_TRIANGLE_STRIP, DefaultVertexFormats.POSITION_COLOR);
		if (stack != null)
			doDrawBox(stack.getLast().getMatrix(), bufferbuilder, box.minX, box.minY, box.minZ, box.maxX, box.maxY, box.maxZ, r, g, b, a);
		else doDrawBox(null, bufferbuilder, box.minX, box.minY, box.minZ, box.maxX, box.maxY, box.maxZ, r, g, b, a);
		tessellator.draw();
	}
	
	private static void doDrawBox(Matrix4f matrix4f, BufferBuilder builder, double x1, double y1, double z1, double x2, double y2, double z2, float red, float green, float blue, float alpha) {
		if (matrix4f == null) {
			WorldRenderer.addChainedFilledBoxVertices(builder, x1, y1, z1, x2, y2, z2, red, green, blue, alpha);
			return;
		}
		builder.pos(matrix4f, (float) x1, (float) y1, (float) z1).color(red, green, blue, alpha).endVertex();
		builder.pos(matrix4f, (float) x1, (float) y1, (float) z1).color(red, green, blue, alpha).endVertex();
		builder.pos(matrix4f, (float) x1, (float) y1, (float) z1).color(red, green, blue, alpha).endVertex();
		builder.pos(matrix4f, (float) x1, (float) y1, (float) z2).color(red, green, blue, alpha).endVertex();
		builder.pos(matrix4f, (float) x1, (float) y2, (float) z1).color(red, green, blue, alpha).endVertex();
		builder.pos(matrix4f, (float) x1, (float) y2, (float) z2).color(red, green, blue, alpha).endVertex();
		builder.pos(matrix4f, (float) x1, (float) y2, (float) z2).color(red, green, blue, alpha).endVertex();
		builder.pos(matrix4f, (float) x1, (float) y1, (float) z2).color(red, green, blue, alpha).endVertex();
		builder.pos(matrix4f, (float) x2, (float) y2, (float) z2).color(red, green, blue, alpha).endVertex();
		builder.pos(matrix4f, (float) x2, (float) y1, (float) z2).color(red, green, blue, alpha).endVertex();
		builder.pos(matrix4f, (float) x2, (float) y1, (float) z2).color(red, green, blue, alpha).endVertex();
		builder.pos(matrix4f, (float) x2, (float) y1, (float) z1).color(red, green, blue, alpha).endVertex();
		builder.pos(matrix4f, (float) x2, (float) y2, (float) z2).color(red, green, blue, alpha).endVertex();
		builder.pos(matrix4f, (float) x2, (float) y2, (float) z1).color(red, green, blue, alpha).endVertex();
		builder.pos(matrix4f, (float) x2, (float) y2, (float) z1).color(red, green, blue, alpha).endVertex();
		builder.pos(matrix4f, (float) x2, (float) y1, (float) z1).color(red, green, blue, alpha).endVertex();
		builder.pos(matrix4f, (float) x1, (float) y2, (float) z1).color(red, green, blue, alpha).endVertex();
		builder.pos(matrix4f, (float) x1, (float) y1, (float) z1).color(red, green, blue, alpha).endVertex();
		builder.pos(matrix4f, (float) x1, (float) y1, (float) z1).color(red, green, blue, alpha).endVertex();
		builder.pos(matrix4f, (float) x2, (float) y1, (float) z1).color(red, green, blue, alpha).endVertex();
		builder.pos(matrix4f, (float) x1, (float) y1, (float) z2).color(red, green, blue, alpha).endVertex();
		builder.pos(matrix4f, (float) x2, (float) y1, (float) z2).color(red, green, blue, alpha).endVertex();
		builder.pos(matrix4f, (float) x2, (float) y1, (float) z2).color(red, green, blue, alpha).endVertex();
		builder.pos(matrix4f, (float) x1, (float) y2, (float) z1).color(red, green, blue, alpha).endVertex();
		builder.pos(matrix4f, (float) x1, (float) y2, (float) z1).color(red, green, blue, alpha).endVertex();
		builder.pos(matrix4f, (float) x1, (float) y2, (float) z2).color(red, green, blue, alpha).endVertex();
		builder.pos(matrix4f, (float) x2, (float) y2, (float) z1).color(red, green, blue, alpha).endVertex();
		builder.pos(matrix4f, (float) x2, (float) y2, (float) z2).color(red, green, blue, alpha).endVertex();
		builder.pos(matrix4f, (float) x2, (float) y2, (float) z2).color(red, green, blue, alpha).endVertex();
		builder.pos(matrix4f, (float) x2, (float) y2, (float) z2).color(red, green, blue, alpha).endVertex();
	}
	
	public static void drawBox(MatrixStack stack, MutableBoundingBox box, float r, float g, float b, float a) {
		drawBox(stack, new AxisAlignedBB(box.minX, box.minY, box.minZ, box.maxX, box.maxY, box.maxZ), r, g, b, a);
	}
	
	public static void drawBoxOutline(MatrixStack stack, AxisAlignedBB box, float r, float g, float b, float a) {
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder bufferbuilder = tessellator.getBuffer();
		bufferbuilder.begin(GL11.GL_LINES, DefaultVertexFormats.POSITION_COLOR);
		RenderSystem.lineWidth(2);
		float x1 = (float) box.minX;
		float y1 = (float) box.minY;
		float z1 = (float) box.minZ;
		float x2 = (float) box.maxX;
		float y2 = (float) box.maxY;
		float z2 = (float) box.maxZ;
		Matrix4f matrix4f = blankStack.getLast().getMatrix();
		if (stack != null)
			matrix4f = stack.getLast().getMatrix();
		bufferbuilder.pos(matrix4f, x1, y1, z1).color(r, g, b, a).endVertex();
		bufferbuilder.pos(matrix4f, x2, y1, z1).color(r, g, b, a).endVertex();
		bufferbuilder.pos(matrix4f, x2, y1, z1).color(r, g, b, a).endVertex();
		bufferbuilder.pos(matrix4f, x2, y1, z2).color(r, g, b, a).endVertex();
		bufferbuilder.pos(matrix4f, x2, y1, z2).color(r, g, b, a).endVertex();
		bufferbuilder.pos(matrix4f, x1, y1, z2).color(r, g, b, a).endVertex();
		bufferbuilder.pos(matrix4f, x1, y1, z2).color(r, g, b, a).endVertex();
		bufferbuilder.pos(matrix4f, x1, y1, z1).color(r, g, b, a).endVertex();
		bufferbuilder.pos(matrix4f, x1, y1, z1).color(r, g, b, a).endVertex();
		bufferbuilder.pos(matrix4f, x1, y2, z1).color(r, g, b, a).endVertex();
		bufferbuilder.pos(matrix4f, x1, y2, z1).color(r, g, b, a).endVertex();
		bufferbuilder.pos(matrix4f, x2, y2, z1).color(r, g, b, a).endVertex();
		bufferbuilder.pos(matrix4f, x2, y2, z1).color(r, g, b, a).endVertex();
		bufferbuilder.pos(matrix4f, x2, y1, z1).color(r, g, b, a).endVertex();
		bufferbuilder.pos(matrix4f, x2, y2, z1).color(r, g, b, a).endVertex();
		bufferbuilder.pos(matrix4f, x2, y2, z2).color(r, g, b, a).endVertex();
		bufferbuilder.pos(matrix4f, x2, y2, z2).color(r, g, b, a).endVertex();
		bufferbuilder.pos(matrix4f, x2, y1, z2).color(r, g, b, a).endVertex();
		bufferbuilder.pos(matrix4f, x2, y2, z2).color(r, g, b, a).endVertex();
		bufferbuilder.pos(matrix4f, x1, y2, z2).color(r, g, b, a).endVertex();
		bufferbuilder.pos(matrix4f, x1, y2, z2).color(r, g, b, a).endVertex();
		bufferbuilder.pos(matrix4f, x1, y1, z2).color(r, g, b, a).endVertex();
		bufferbuilder.pos(matrix4f, x1, y2, z2).color(r, g, b, a).endVertex();
		bufferbuilder.pos(matrix4f, x1, y2, z1).color(r, g, b, a).endVertex();
		tessellator.draw();
	}
	
	public static void drawBoxOutline(MatrixStack stack, MutableBoundingBox box, float r, float g, float b, float a) {
		drawBox(stack, new AxisAlignedBB(box.minX, box.minY, box.minZ, box.maxX, box.maxY, box.maxZ), r, g, b, a);
	}
	
	public static void drawLine(MatrixStack stack,
								double minX, double minY, double minZ,
								double maxX, double maxY, double maxZ,
								float r, float g, float b, float a
	) {
		drawLine(
				stack,
				minX, minY, minZ,
				maxX, maxY, maxZ,
				r, g, b, a, r, g, b, a
		);
	}
	
	public static void drawLine(MatrixStack stack,
								double minX, double minY, double minZ,
								double maxX, double maxY, double maxZ,
								float r, float g, float b, float a,
								float r2, float g2, float b2, float a2
	) {
		Matrix4f matrix4f = blankStack.getLast().getMatrix();
		if (stack != null)
			matrix4f = stack.getLast().getMatrix();
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder bufferbuilder = tessellator.getBuffer();
		bufferbuilder.begin(GL11.GL_LINES, DefaultVertexFormats.POSITION_COLOR);
		RenderSystem.lineWidth(2);
		bufferbuilder.pos(matrix4f, (float) minX, (float) minY, (float) minZ).color(r, g, b, a).endVertex();
		bufferbuilder.pos(matrix4f, (float) maxX, (float) maxY, (float) maxZ).color(r2, g2, b2, a2).endVertex();
		tessellator.draw();
	}
}
