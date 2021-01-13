package com.tfc.assortedutils.mixins;

import com.tfc.assortedutils.API.entities.VoxelShapeEntityRaytraceResult;
import com.tfc.assortedutils.mixin_code.MixinHelper;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.client.CUseEntityPacket;
import net.minecraftforge.fml.loading.FMLEnvironment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CUseEntityPacket.class)
public class CUseEntityPacketMixin {
	public int assortedUtils_entityInteractDirection;
	
	@Inject(at = @At("TAIL"), method = "readPacketData")
	public void read(PacketBuffer buf, CallbackInfo ci) {
		assortedUtils_entityInteractDirection = buf.readInt();
	}
	
	@Inject(at = @At("TAIL"), method = "writePacketData")
	public void write(PacketBuffer buf, CallbackInfo ci) {
		if (FMLEnvironment.dist.isClient())
			if (MixinHelper.getMinecraftInstanceObjectMouseOver() instanceof VoxelShapeEntityRaytraceResult)
				buf.writeInt(((VoxelShapeEntityRaytraceResult) MixinHelper.getMinecraftInstanceObjectMouseOver()).getDir().ordinal());
			else buf.writeInt(-1);
	}
}
