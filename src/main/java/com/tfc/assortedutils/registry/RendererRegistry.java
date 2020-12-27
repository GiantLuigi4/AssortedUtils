package com.tfc.assortedutils.registry;

import com.tfc.assortedutils.API.debug.ICustomDebugRenderer;
import com.tfc.assortedutils.API.debug.vanilla.HeightmapDebugRenderer;
import com.tfc.assortedutils.API.debug.vanilla.revamped.AIDebugRenderer;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;

public class RendererRegistry {
	public static final DeferredRegister<ICustomDebugRenderer> RENDERERS = DeferredRegister.create(ICustomDebugRenderer.class, "assorted_utils");
	
	public static final RegistryObject<ICustomDebugRenderer> HEIGHTMAP_RENDERER = RENDERERS.register("debug/heightmap_tool", HeightmapDebugRenderer::new);
	public static final RegistryObject<ICustomDebugRenderer> AI_RENDERER = RENDERERS.register("debug/ai_tool", AIDebugRenderer::new);
}
