package tfc.assortedutils.custom_registries.simple_container_screens;

import net.minecraft.util.ResourceLocation;
import tfc.assortedutils.API.gui.screen.SimpleContainerScreenFactory;
import tfc.assortedutils.registry.RendererRegistry;

import java.util.ArrayList;

/**
 * I'd recommend not directly referencing this
 * {@link RendererRegistry} for a reference as to how to register your renderers
 */
public class SimpleContainerScreenRegistry {
	private static final ArrayList<SimpleContainerScreenFactory> factories = new ArrayList<>();
	
	protected static void register(ResourceLocation name, SimpleContainerScreenFactory factory) {
		if (factory.getRegistryName() == null) factories.add(factory.setRegistryName(name));
		else factories.add(factory);
	}
	
	protected static void clear() {
		factories.clear();
	}
	
	public static Iterable<SimpleContainerScreenFactory> getAllValues() {
		return (Iterable<SimpleContainerScreenFactory>) factories.clone();
	}
	
	public static SimpleContainerScreenFactory get(ResourceLocation registryName) {
		for (SimpleContainerScreenFactory factory : factories) {
			if (factory.getRegistryName().equals(registryName)) {
				return factory;
			}
		}
		return null;
	}
}
