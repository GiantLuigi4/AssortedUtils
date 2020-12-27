package com.tfc.assortedutils.API.debug;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.tfc.assortedutils.registry.ItemRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Hand;
import net.minecraftforge.registries.IForgeRegistryEntry;

public interface ICustomDebugRenderer extends IForgeRegistryEntry<ICustomDebugRenderer> {
	void render(MatrixStack stack, double playerX, double playerY, double playerZ);
	
	default boolean shouldRender() {
		return Minecraft.getInstance().player.getHeldItem(Hand.OFF_HAND).equals(ItemRegistry.DEBUG_TOOL_ALL.get());
	}
}
