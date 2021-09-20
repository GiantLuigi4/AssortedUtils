package tfc.assortedutils.custom_registries.debug_renderer;

import net.minecraft.util.ResourceLocation;
import tfc.assortedutils.API.debug.ICustomDebugRenderer;
import tfc.assortedutils.registry.RendererRegistry;

import java.util.ArrayList;

/**
 * I'd recommend not directly referencing this
 * {@link RendererRegistry} for a reference as to how to register your renderers
 */
public class DebugRendererRegistry {
	private static final ArrayList<ICustomDebugRenderer> renderers = new ArrayList<>();
	
	protected static void register(ResourceLocation name, ICustomDebugRenderer renderer) {
		if (renderer.getRegistryName() == null) renderers.add(renderer.setRegistryName(name));
		else renderers.add(renderer);
	}
	
	protected static void clear() {
		renderers.clear();
	}
	
	public static Iterable<ICustomDebugRenderer> getAllValues() {
		return (Iterable<ICustomDebugRenderer>) renderers.clone();
	}
}
