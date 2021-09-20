package tfc.assortedutils.API.debug;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistryEntry;

import javax.annotation.Nullable;

public abstract class CustomDebugRenderer implements ICustomDebugRenderer, IForgeRegistryEntry<ICustomDebugRenderer> {
	private ResourceLocation registryName = null;
	
	@Override
	public abstract void render(MatrixStack stack, double playerX, double playerY, double playerZ);
	
	@Override
	public ICustomDebugRenderer setRegistryName(ResourceLocation name) {
		registryName = name;
		return this;
	}
	
	@Nullable
	@Override
	public ResourceLocation getRegistryName() {
		return registryName;
	}
	
	@Override
	public Class<ICustomDebugRenderer> getRegistryType() {
		return ICustomDebugRenderer.class;
	}
}
