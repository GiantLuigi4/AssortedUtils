package tfc.assortedutils.API.misc;

import net.minecraft.item.ItemStack;

public class ItemStackUtils {
	public static boolean areMergable(ItemStack stack1, ItemStack stack2) {
		if (stack1.isItemEqual(stack2)) {
			if (stack1.hasTag() && stack2.hasTag()) {
				if (stack1.getTag() != null) return stack1.getTag().equals(stack2.getTag());
				else return stack2.getTag() == null;
			} else return stack1.hasTag() == stack2.hasTag();
		}
		return false;
	}
	
	public static void merge(ItemStack src, ItemStack other) {
		if (other.isItemEqual(src)) {
			if (other.hasTag() && src.hasTag()) {
				if (other.getTag() != null) {
					if (!other.getTag().equals(src.getTag())) return;
				} else if (src.getTag() != null) return;
			} else if (other.hasTag() != src.hasTag()) return;
			int amt = other.getCount();
			amt = Math.abs(amt);
			int maxChange = (other.getMaxStackSize() - src.getCount());
			if (amt > maxChange) amt = maxChange;
			src.grow(amt);
			other.shrink(amt);
		}
	}
}
