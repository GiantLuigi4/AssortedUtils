package tfc.assortedutils.mixins;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tfc.assortedutils.mixin_code.VoxelshapeEntity;

@Mixin(GameRenderer.class)
public class GameRendererMixin {
	@Shadow
	@Final
	private Minecraft mc;
	
	@Inject(at = @At("TAIL"), method = "getMouseOver(F)V")
	public void pickPost(float partialTicks, CallbackInfo ci) {
		VoxelshapeEntity.pickPost(mc, partialTicks);
	}
}
