package tfc.assortedutils.mixins;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.fml.loading.FMLEnvironment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import tfc.assortedutils.API.entities.VoxelShapeEntityRaytraceResult;
import tfc.assortedutils.mixin_code.MixinFieldAccessor;
import tfc.assortedutils.mixin_code.MixinHelper;
import tfc.assortedutils.mixin_code.VoxelshapeEntity;

@Mixin(Entity.class)
public class EntityMixin {
	@Inject(at = @At("HEAD"), cancellable = true, method = "applyPlayerInteraction")
	public void applyInteraction(PlayerEntity player, Vector3d vec, Hand hand, CallbackInfoReturnable<ActionResultType> cir) {
		Direction dir = MixinFieldAccessor.getUseEntityDir(player);
		if (dir != null) {
			Entity entity = (Entity) (Object) this;
			ActionResultType resultType = VoxelshapeEntity.onInteract(
					player, new VoxelShapeEntityRaytraceResult(
							entity, vec, MixinFieldAccessor.getUseEntityDir(player)
					)
			);
			if (resultType != ActionResultType.PASS) {
				cir.setReturnValue(resultType);
				cir.cancel();
			}
		}
		if (FMLEnvironment.dist.isClient()) {
			MixinFieldAccessor.setUseEntityDir(MixinHelper.getMinecraftInstancePlayer(), null);
		}
	}
}
