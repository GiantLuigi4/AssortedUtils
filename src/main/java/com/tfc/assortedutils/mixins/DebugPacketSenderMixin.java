package com.tfc.assortedutils.mixins;

import com.tfc.assortedutils.mixin_code.DebugPacketSenderCode;
import net.minecraft.entity.MobEntity;
import net.minecraft.network.DebugPacketSender;
import net.minecraft.pathfinding.Path;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.structure.StructureStart;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import javax.annotation.Nullable;

@Mixin(DebugPacketSender.class)
public class DebugPacketSenderMixin {
	/**
	 * @author TFC The Flying Creeper (aka GiantLuigi4)
	 * @reason bring in some work around to the original code for this method not existing, and seemingly also for the packet associated with this not existing
	 */
	@Overwrite
	public static void sendPath(World worldIn, MobEntity p_218803_1_, @Nullable Path p_218803_2_, float p_218803_3_) {
		DebugPacketSenderCode.sendPath(worldIn, p_218803_1_, p_218803_2_, p_218803_3_);
	}
	
	
	/**
	 * @author TFC The Flying Creeper (aka GiantLuigi4)
	 * @reason bring in some work around to the original code for this method not existing, and seemingly also for the packet associated with this not existing
	 */
	@Overwrite
	public static void sendStructureStart(ISeedReader worldIn, StructureStart<?> p_218804_1_) {
		DebugPacketSenderCode.sendStructure(worldIn, p_218804_1_);
	}
}
