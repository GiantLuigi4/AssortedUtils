package tfc.assortedutils.custom_registries.simple_container_screens;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.RegistryBuilder;
import tfc.assortedutils.API.gui.screen.SimpleContainerScreenFactory;

public class SimpleContainerScreenRegistryBuilder extends RegistryBuilder<SimpleContainerScreenFactory> {
	public SimpleContainerScreenRegistryBuilder() {
		onAdd((owner, stage, id, obj, oldObj) -> SimpleContainerScreenRegistry.register(obj.getRegistryName(), obj));
		onClear((owner, stage) -> SimpleContainerScreenRegistry.clear());
		onCreate((owner, state) -> {
		});
		onBake(((owner, stage) -> {
		}));
		onValidate((owner, stage, id, key, obj) -> {
		});
		setType(SimpleContainerScreenFactory.class);
		allowModification();
		tagFolder("container_screens");
		setDefaultKey(new ResourceLocation("unknown:null"));
		missing((name, isNetwork) -> null);
		setName(new ResourceLocation("assorted_utils:container_screens_registry"));
	}
}
