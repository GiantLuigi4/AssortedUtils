package tfc.assortedutils.mixins;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.play.ServerPlayNetHandler;
import net.minecraft.network.play.client.CUseEntityPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tfc.assortedutils.mixin_code.MixinFieldAccessor;

@Mixin(ServerPlayNetHandler.class)
public class ServerNetPlayHandlerMixin {
	@Shadow
	public ServerPlayerEntity player;
	
	@Inject(at = @At("HEAD"), method = "processUseEntity")
	public void useEntity(CUseEntityPacket packetIn, CallbackInfo ci) {
		MixinFieldAccessor.setUseEntityDir(player, MixinFieldAccessor.getUseEntityDir(packetIn));
	}
}
