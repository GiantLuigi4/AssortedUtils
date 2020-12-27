package com.tfc.assortedutils.mixins;

import com.tfc.assortedutils.mixin_code.DebugPacketSenderCode;
import net.minecraft.entity.MobEntity;
import net.minecraft.network.DebugPacketSender;
import net.minecraft.pathfinding.Path;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import javax.annotation.Nullable;

@Mixin(DebugPacketSender.class)
public class DebugPacketSenderMixin {
	/**
	 * @author TFC The Flying Creeper (aka GiantLuigi4)
	 */
	@Overwrite
	public static void sendPath(World worldIn, MobEntity p_218803_1_, @Nullable Path p_218803_2_, float p_218803_3_) {
		DebugPacketSenderCode.sendPath(worldIn, p_218803_1_, p_218803_2_, p_218803_3_);
	}
}
