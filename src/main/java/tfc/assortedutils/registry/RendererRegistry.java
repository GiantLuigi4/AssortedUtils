package tfc.assortedutils.registry;

import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import tfc.assortedutils.API.debug.ICustomDebugRenderer;
import tfc.assortedutils.API.debug.vanilla.HeightmapDebugRenderer;
import tfc.assortedutils.API.debug.vanilla.revamped.AIDebugRenderer;
import tfc.assortedutils.API.debug.vanilla.revamped.StructureDebugRenderer;

public class RendererRegistry {
	public static final DeferredRegister<ICustomDebugRenderer> RENDERERS = DeferredRegister.create(ICustomDebugRenderer.class, "assorted_utils");
	
	public static final RegistryObject<HeightmapDebugRenderer> HEIGHTMAP_RENDERER = RENDERERS.register("debug/heightmap_tool", HeightmapDebugRenderer::new);
	public static final RegistryObject<AIDebugRenderer> AI_RENDERER = RENDERERS.register("debug/ai_tool", AIDebugRenderer::new);
	public static final RegistryObject<StructureDebugRenderer> STRUCTURE_RENDERER = RENDERERS.register("debug/structure_tool", StructureDebugRenderer::new);
}
