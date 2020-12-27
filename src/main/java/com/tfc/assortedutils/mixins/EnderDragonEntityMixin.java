package com.tfc.assortedutils.mixins;

import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.network.DebugPacketSender;
import net.minecraft.pathfinding.Path;
import net.minecraft.pathfinding.PathPoint;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EnderDragonEntity.class)
public class EnderDragonEntityMixin {
	@Inject(at = @At("RETURN"), method = "makePath(Lnet/minecraft/pathfinding/PathPoint;Lnet/minecraft/pathfinding/PathPoint;)Lnet/minecraft/pathfinding/Path;")
	public void findPath(PathPoint start, PathPoint finish, CallbackInfoReturnable<Path> cir) {
		DebugPacketSender.sendPath(
				((EnderDragonEntity) (Object) this).world,
				((EnderDragonEntity) (Object) this),
				cir.getReturnValue(),
				0
		);
	}
}
