package com.tfc.assortedutils.API.debug;

import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;

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
