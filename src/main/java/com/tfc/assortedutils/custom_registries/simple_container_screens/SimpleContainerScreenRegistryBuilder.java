package com.tfc.assortedutils.custom_registries.simple_container_screens;

import com.tfc.assortedutils.API.gui.screen.SimpleContainerScreenFactory;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.RegistryBuilder;

public class SimpleContainerScreenRegistryBuilder extends RegistryBuilder<SimpleContainerScreenFactory<?>> {
	public SimpleContainerScreenRegistryBuilder() {
		onAdd((owner, stage, id, obj, oldObj) -> SimpleContainerScreenRegistry.register(obj.getRegistryName(), obj));
		onClear((owner, stage) -> SimpleContainerScreenRegistry.clear());
		onCreate((owner, state) -> {
		});
		onBake(((owner, stage) -> {
		}));
		onValidate((owner, stage, id, key, obj) -> {
		});
		setType((Class<SimpleContainerScreenFactory<?>>) SimpleContainerScreenFactory.build(null).getClass());
		allowModification();
		tagFolder("container_screens");
		setDefaultKey(new ResourceLocation("unknown:null"));
		missing((name, isNetwork) -> null);
		setName(new ResourceLocation("assorted_utils:container_screens_registry"));
	}
}
