package com.tfc.assortedutils.custom_registries.simple_container_screens;

import com.tfc.assortedutils.API.gui.container.SimpleContainer;
import com.tfc.assortedutils.API.gui.screen.SimpleContainerScreen;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.RegistryBuilder;

public class SimpleContainerScreenRegistryBuilder extends RegistryBuilder<SimpleContainerScreen<?>> {
	public SimpleContainerScreenRegistryBuilder() {
		onAdd((owner, stage, id, obj, oldObj) -> SimpleContainerScreenRegistry.register(obj.getRegistryName(), obj));
		onClear((owner, stage) -> SimpleContainerScreenRegistry.clear());
		onCreate((owner, state) -> {
		});
		onBake(((owner, stage) -> {
		}));
		onValidate((owner, stage, id, key, obj) -> {
		});
		setType((Class<SimpleContainerScreen<?>>) new SimpleContainerScreen<SimpleContainer>(null, null, null).getClass());
		allowModification();
		tagFolder("container_screens");
		setDefaultKey(new ResourceLocation("unknown:null"));
		missing((name, isNetwork) -> null);
		setName(new ResourceLocation("assorted_utils:container_screens_registry"));
	}
}
