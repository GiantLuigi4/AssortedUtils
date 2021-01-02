package com.tfc.assortedutils.mixin_code;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.play.client.CUseEntityPacket;
import net.minecraft.util.Direction;

import java.lang.reflect.Field;

public class MixinFieldAccessor {
	private static final Field CUseEntityPacketMixin$assortedUtils_entityInteractDirection;
	private static final Field PlayerEntityMixin$assortedUtils_entityInteractDirection;
	
	static {
		try {
			CUseEntityPacketMixin$assortedUtils_entityInteractDirection = CUseEntityPacket.class.getField("assortedUtils_entityInteractDirection");
			PlayerEntityMixin$assortedUtils_entityInteractDirection = PlayerEntity.class.getField("assortedUtils_entityInteractDirection");
		} catch (Throwable err) {
			throw new RuntimeException(err);
		}
	}
	
	public static Direction getUseEntityDir(PlayerEntity entity) {
		try {
			return (Direction) PlayerEntityMixin$assortedUtils_entityInteractDirection.get(entity);
		} catch (Throwable err) {
			return null;
		}
	}
	
	public static Direction getUseEntityDir(CUseEntityPacket packet) {
		try {
			return Direction.values()[(int) CUseEntityPacketMixin$assortedUtils_entityInteractDirection.get(packet)];
		} catch (Throwable err) {
			return null;
		}
	}
	
	public static void setUseEntityDir(PlayerEntity player, Direction dir) {
		try {
			PlayerEntityMixin$assortedUtils_entityInteractDirection.set(player, dir);
		} catch (Throwable err) {
			throw new RuntimeException(err);
		}
	}
}
