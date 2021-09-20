package tfc.assortedutils.API.gui.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;

public class SimpleContainerScreen<T extends Container> extends SimpleScreen {
	public final ContainerType<T> type;
	
	public int selectedSlot = -1;
	public ItemStack selectedStack;
	
	public SimpleContainerScreen(ITextComponent titleIn, Minecraft minecraft, ContainerType<T> type) {
		super(titleIn, minecraft);
		this.type = type;
	}
	
	public SimpleContainerScreen(ITextComponent titleIn, ContainerType<T> type) {
		super(titleIn);
		this.type = type;
	}
	
	@Override
	public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		for (ClientItemSlot slot : this.slots) if (slot.index == selectedSlot) slot.set(ItemStack.EMPTY);
		
		super.render(matrixStack, mouseX, mouseY, partialTicks);
	}
	
	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int button) {
		int guiLeft = this.width / 2 - sizeX;
		int guiTop = this.height / 2 - sizeY;
		
		for (ClientItemSlot slot : this.slots) {
			if (slot.click((int) mouseX, (int) mouseY, guiLeft, guiTop, this)) {
				if (selectedSlot == -1) {
					selectedSlot = slot.index;
					selectedStack = slot.stack;
					slot.set(ItemStack.EMPTY);
				} else {
					slots.get(selectedSlot).set(selectedStack);
					if (selectedSlot != slot.index) slot.moveStack(selectedSlot);
					selectedSlot = -1;
					selectedStack = ItemStack.EMPTY;
				}
				
				break;
			}
		}
		
		return super.mouseClicked(mouseX, mouseY, button);
	}
}
