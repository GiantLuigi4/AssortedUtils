package com.tfc.assortedutils.mixins;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Direction;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin {
	public Direction assortedUtils_entityInteractDirection = null;
}
