package com.tfc.assortedutils.API.debug;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.RegistryBuilder;

public class DebugRegistryBuilder extends RegistryBuilder<ICustomDebugRenderer> {
	public DebugRegistryBuilder() {
		onAdd((owner, stage, id, obj, oldObj) -> DebugRendererRegistry.register(obj.getRegistryName(), obj));
		onClear((owner, stage) -> DebugRendererRegistry.clear());
		onCreate((owner, state) -> {
		});
		onBake(((owner, stage) -> {
		}));
		onValidate((owner, stage, id, key, obj) -> {
		});
		setType(ICustomDebugRenderer.class);
		allowModification();
		tagFolder("renderers");
		setDefaultKey(new ResourceLocation("unknown:null"));
		missing((name, isNetwork) -> null);
		setName(new ResourceLocation("assorted_utils:debug_renderer_registry"));
		disableSync();
	}
}
