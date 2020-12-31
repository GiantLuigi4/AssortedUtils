package com.tfc.assortedutils.mixins;

import com.tfc.assortedutils.mixin_code.VoxelshapeEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public class GameRendererMixin {
	@Shadow
	@Final
	private Minecraft mc;
	
	@Inject(at = @At("HEAD"), method = "getMouseOver(F)V")
	public void pickPost(float partialTicks, CallbackInfo ci) {
		VoxelshapeEntity.pickPost(mc, partialTicks);
	}
}
