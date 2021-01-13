package com.tfc.assortedutils.API.gui.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;

public class SimpleScreen extends Screen {
	private static final ResourceLocation DEMO_BACKGROUND_LOCATION = new ResourceLocation("textures/gui/demo_background.png");
	public int sizeX = 0;
	public int sizeY = 0;
	public ArrayList<ClientItemSlot> slots = new ArrayList<>();
	private boolean focusAlternator = false;
	
	public SimpleScreen(ITextComponent titleIn, Minecraft minecraft) {
		super(titleIn);
		this.minecraft = minecraft;
	}
	
	public SimpleScreen(ITextComponent titleIn) {
		super(titleIn);
		this.minecraft = Minecraft.getInstance();
	}
	
	@Override
	public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		super.render(matrixStack, mouseX, mouseY, partialTicks);
		
		matrixStack.push();
		int guiLeft = this.width / 2 - sizeX;
		int guiTop = this.height / 2 - sizeY;
		for (ClientItemSlot slot : slots) slot.render(matrixStack, mouseX, mouseY, guiLeft, guiTop, this);
		matrixStack.pop();
	}
	
	public void deseralize(CompoundNBT nbt) {
		slots.clear();
		
		if (nbt.contains("inventory")) {
			ListNBT inv = nbt.getList("inventory", Constants.NBT.TAG_COMPOUND);
			int index = 0;
			for (INBT inbt : inv) {
				CompoundNBT item = (CompoundNBT) inbt;
				ItemStack stack = new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation(item.getString("item"))));
				if (item.contains("tag")) stack.setTag(item.getCompound("tag"));
				ClientItemSlot slot = new ClientItemSlot(stack, index++, item.getInt("x"), item.getInt("y"), this);
				slots.add(slot);
			}
		}
	}
	
	public void drawBackground(MatrixStack matrixStack, int x, int y, int width, int height) {
		minecraft.getTextureManager().bindTexture(DEMO_BACKGROUND_LOCATION);
		blit(
				matrixStack,
				x, y,
				width - 5, height - 5,
				0, 0,
				Math.min(width - 5, 243), Math.min(height - 5, 161),
				256, 256
		);
		blit(
				matrixStack,
				x, y + height - 5,
				width - 5, 5,
				0, 161,
				Math.min(width - 5, 243), 5,
				256, 256
		);
		blit(
				matrixStack,
				x + width - 5, y,
				5, height - 5,
				243, 0,
				5, Math.min(height - 5, 161),
				256, 256
		);
		blit(
				matrixStack,
				x + width - 5, y + height - 5,
				5, 5,
				243, 161,
				5, 5,
				256, 256
		);
	}
	
	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int button) {
		for (Widget button1 : buttons) {
			boolean clicked = button1.mouseClicked(mouseX, mouseY, button);
			if (clicked) return true;
		}
		
		return super.mouseClicked(mouseX, mouseY, button);
	}
	
	@Override
	public boolean mouseReleased(double mouseX, double mouseY, int button) {
		for (Widget button1 : buttons) {
			boolean clicked = button1.mouseReleased(mouseX, mouseY, button);
			if (clicked) return true;
		}
		
		return super.mouseReleased(mouseX, mouseY, button);
	}
	
	@Override
	public boolean keyReleased(int keyCode, int scanCode, int modifiers) {
		for (Widget button1 : buttons) {
			if (button1.isFocused()) {
				boolean clicked = button1.keyReleased(keyCode, scanCode, modifiers);
				if (clicked) return true;
			}
		}
		
		return super.keyReleased(keyCode, scanCode, modifiers);
	}
	
	@Override
	public boolean charTyped(char codePoint, int modifiers) {
		for (Widget button1 : buttons) {
			if (button1.isFocused()) {
				boolean clicked = button1.charTyped(codePoint, modifiers);
				if (clicked) return true;
			}
		}
		
		return super.charTyped(codePoint, modifiers);
	}
	
	@Override
	public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
		for (Widget button1 : buttons) {
			if (button1.isFocused()) {
				boolean clicked = button1.keyPressed(keyCode, scanCode, modifiers);
				if (clicked) return true;
			}
		}
		
		return super.keyPressed(keyCode, scanCode, modifiers);
	}
	
	@Override
	public boolean changeFocus(boolean focus) {
		if (!focusAlternator) {
			for (int index = 0; index < buttons.size(); index++) {
				Widget widget = buttons.get(index);
				
				if (widget.isFocused()) {
					widget.changeFocus(false);
					if (index + 1 >= buttons.size()) index = -1;
					Widget widget1 = buttons.get(index + 1);
					widget1.changeFocus(true);
				}
			}
		}
		
		focusAlternator = !focusAlternator;
		return super.changeFocus(false);
	}
	
	@Override
	public void closeScreen() {
		super.closeScreen();
	}
}
