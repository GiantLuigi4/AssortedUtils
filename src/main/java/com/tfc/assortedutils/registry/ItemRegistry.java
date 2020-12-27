package com.tfc.assortedutils.registry;

import com.tfc.assortedutils.items.DebugToolItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ItemRegistry {
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, "assorted_utils");
	
	public static final RegistryObject<Item> DEBUG_TOOL_AI = ITEMS.register("debug/ai_tool", () -> new DebugToolItem(new Item.Properties().group(ItemGroup.MISC)));
	public static final RegistryObject<Item> DEBUG_TOOL_HEIGHTMAP = ITEMS.register("debug/heightmap_tool", () -> new DebugToolItem(new Item.Properties().group(ItemGroup.MISC)));
	public static final RegistryObject<Item> DEBUG_TOOL_ALL = ITEMS.register("debug/tool", () -> new DebugToolItem(new Item.Properties().group(ItemGroup.MISC)));
	
	public static boolean isCorrect(ItemStack stack, RegistryObject<Item> checkAgainst) {
		return
				stack.getItem() == (checkAgainst.get()) ||
						stack.getItem() == (DEBUG_TOOL_ALL.get());
	}
}
