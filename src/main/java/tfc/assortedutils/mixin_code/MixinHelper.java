package tfc.assortedutils.mixin_code;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.RayTraceResult;

public class MixinHelper {
	public static PlayerEntity getMinecraftInstancePlayer() {
		return Minecraft.getInstance().player;
	}
	
	public static RayTraceResult getMinecraftInstanceObjectMouseOver() {
		return Minecraft.getInstance().objectMouseOver;
	}
}
