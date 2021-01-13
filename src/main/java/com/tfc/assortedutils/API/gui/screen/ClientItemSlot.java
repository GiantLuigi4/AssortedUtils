package com.tfc.assortedutils.API.gui.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.tfc.assortedutils.API.gui.container.ItemSlot;
import com.tfc.assortedutils.AssortedUtils;
import com.tfc.assortedutils.packets.container.MoveItemPacket;
import com.tfc.assortedutils.utils.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class ClientItemSlot extends ItemSlot {
	public boolean renderSlot = false;
	public Color color = new Color(255, 255, 255);
	
	public SimpleScreen screen;
	
	public ItemStack stack;
	
	public ClientItemSlot(ItemStack stack, int index, int x, int y, SimpleScreen screen) {
		super(null, index, x, y);
		this.stack = stack;
		this.screen = screen;
	}
	
	@Override
	public void set(ItemStack stack) {
//		super.set(stack);
		this.stack = stack;
	}
	
	@Override
	public ItemStack get() {
//		return super.get();
		return stack;
	}
	
	public void moveStack(int from) {
		AssortedUtils.NETWORK_INSTANCE.sendToServer(new MoveItemPacket(from, index));
		ClientItemSlot i = screen.slots.get(from);
		ItemStack stack1 = i.stack;
		ItemStack stack2 = stack;
		set(stack1);
		i.set(stack2);
	}
	
	public void render(MatrixStack matrixStack, int mouseX, int mouseY, int guiLeft, int guiTop, Screen screen) {
		ItemStack stack = screen.getMinecraft().player.inventory.getStackInSlot(index);
		
		if (renderSlot) {
			screen.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("textures/gui/container/inventory.png"));
			RenderSystem.enableTexture();
			RenderSystem.color3f(color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f);
			screen.blit(
					matrixStack, guiLeft + x - 1, guiTop + y - 1,
					7, 119, 18, 18
			);
		}
		
		Minecraft.getInstance().getItemRenderer().renderItemIntoGUI(
				stack, guiLeft + x, guiTop + y
		);
		Minecraft.getInstance().getItemRenderer().renderItemOverlayIntoGUI(Minecraft.getInstance().fontRenderer, stack,
				guiLeft + x, guiTop + y, null
		);
		
		if (
				mouseX >= guiLeft + x - 1 &&
						mouseX < guiLeft + x + 17
		) {
			if (
					mouseY >= guiTop + y - 1 &&
							mouseY < guiTop + y + 17
			) {
				RenderSystem.disableTexture();
				RenderSystem.color4f(
						1, 1, 1, 0.5f
				);
				matrixStack.push();
				matrixStack.translate(0, 0, 128);
				RenderSystem.enableAlphaTest();
				RenderSystem.enableBlend();
				screen.blit(
						matrixStack,
						guiLeft + x,
						guiTop + y,
						0, 0, 16, 16
				);
				if (!stack.isEmpty()) {
					renderTooltip(
							matrixStack, stack, mouseX, mouseY, screen
					);
				}
				matrixStack.pop();
			}
		}
	}
	
	protected void renderTooltip(MatrixStack matrixStack, ItemStack itemStack, int mouseX, int mouseY, Screen screen) {
		FontRenderer font = itemStack.getItem().getFontRenderer(itemStack);
		net.minecraftforge.fml.client.gui.GuiUtils.preItemToolTip(itemStack);
		screen.renderWrappedToolTip(matrixStack, screen.getTooltipFromItem(itemStack), mouseX, mouseY, (font == null ? Minecraft.getInstance().fontRenderer : font));
		net.minecraftforge.fml.client.gui.GuiUtils.postItemToolTip();
	}
	
	public boolean click(int mouseX, int mouseY, int guiLeft, int guiTop, Screen screen) {
		return
				mouseX >= guiLeft + x - 1 &&
						mouseX < guiLeft + x + 17 &&
						mouseY >= guiTop + y - 1 &&
						mouseY < guiTop + y + 17;
	}
}
