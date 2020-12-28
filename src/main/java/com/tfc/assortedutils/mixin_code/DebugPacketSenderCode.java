package com.tfc.assortedutils.mixin_code;

import com.tfc.assortedutils.AssortedUtils;
import com.tfc.assortedutils.packets.PathPacket;
import com.tfc.assortedutils.packets.StructurePacket;
import com.tfc.assortedutils.registry.ItemRegistry;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.pathfinding.Path;
import net.minecraft.util.Hand;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.structure.StructureStart;
import net.minecraftforge.fml.network.PacketDistributor;

import javax.annotation.Nullable;

public class DebugPacketSenderCode {
	public static void sendPath(World worldIn, MobEntity entity, @Nullable Path path, float distance) {
		try {
			worldIn.getPlayers().forEach(player -> {
				if (
						player.getHeldItem(Hand.OFF_HAND).getItem().equals(ItemRegistry.DEBUG_TOOL_AI.get()) ||
								player.getHeldItem(Hand.OFF_HAND).getItem().equals(ItemRegistry.DEBUG_TOOL_ALL.get())
				) {
					AssortedUtils.NETWORK_INSTANCE.send(
							PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) player), new PathPacket(path, entity, distance)
					);
				}
			});
		} catch (Throwable ignored) {
		}
	}
	
	public static void sendStructure(ISeedReader worldIn, StructureStart<?> structureStart) {
		AssortedUtils.NETWORK_INSTANCE.send(
				PacketDistributor.ALL.noArg(),
				new StructurePacket(structureStart, worldIn.getWorld().getDimensionKey().getLocation().toString())
		);
//		worldIn.getPlayers().forEach(player->{
//		});
	}
}
