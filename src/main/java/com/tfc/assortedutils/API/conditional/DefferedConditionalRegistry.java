package com.tfc.assortedutils.API.conditional;

import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.util.function.Supplier;

public class DefferedConditionalRegistry {
	public static <T extends IForgeRegistryEntry<T>> RegistryObject<T> conditionallyRegister(DeferredRegister<T> register, String registryName, T item, Supplier<Boolean> condition) {
		if (condition.get()) return register.register(registryName, () -> item);
		return null;
	}
}
