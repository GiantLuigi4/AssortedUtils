package com.tfc.assortedutils.mixins;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkInstance;
import net.minecraftforge.fml.network.NetworkRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.function.Predicate;
import java.util.function.Supplier;

@Mixin(NetworkRegistry.class)
public interface NetworkRegistryAccessor {
	@Invoker(value = "createInstance", remap = false)
	static NetworkInstance create(ResourceLocation name, Supplier<String> networkProtocolVersion, Predicate<String> clientAcceptedVersions, Predicate<String> serverAcceptedVersions) {
		return null;
	}
}
