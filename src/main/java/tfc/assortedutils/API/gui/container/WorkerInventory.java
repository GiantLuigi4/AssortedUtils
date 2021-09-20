package tfc.assortedutils.API.gui.container;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class WorkerInventory implements IInventory {
	ItemStack stack = null;
	
	@Override
	public int getSizeInventory() {
		return 1;
	}
	
	@Override
	public boolean isEmpty() {
		return stack == null || stack.isEmpty();
	}
	
	@Override
	public ItemStack getStackInSlot(int index) {
		return stack;
	}
	
	@Override
	public ItemStack decrStackSize(int index, int count) {
		stack.shrink(count);
		return stack;
	}
	
	@Override
	public ItemStack removeStackFromSlot(int index) {
		ItemStack oldStack = stack;
		stack = ItemStack.EMPTY;
		return oldStack;
	}
	
	@Override
	public void setInventorySlotContents(int index, ItemStack stack) {
		this.stack = stack;
	}
	
	@Override
	public void markDirty() {
	}
	
	@Override
	public boolean isUsableByPlayer(PlayerEntity player) {
		return true;
	}
	
	@Override
	public void clear() {
		stack = ItemStack.EMPTY;
	}
}
