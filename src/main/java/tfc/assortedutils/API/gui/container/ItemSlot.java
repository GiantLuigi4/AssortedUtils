package tfc.assortedutils.API.gui.container;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class ItemSlot {
	public IInventory inventory;
	public int index;
	public int x, y;
	
	public ItemSlot(IInventory inventory, int index, int x, int y) {
		this.inventory = inventory;
		this.index = index;
		this.x = x;
		this.y = y;
	}
	
	public void set(ItemStack stack) {
		inventory.setInventorySlotContents(index, stack);
	}
	
	public ItemStack get() {
		return inventory.getStackInSlot(index);
	}
}
