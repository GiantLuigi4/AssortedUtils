package com.tfc.assortedutils.entities;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;

import java.util.function.Supplier;

public class EntityRegistryHelper {
	public static <T extends Entity> Supplier<EntityType<T>> buildType(EntityType.IFactory<T> entityConstructor, ResourceLocation registryName) {
		return buildType(entityConstructor, registryName, 1, 1);
	}
	
	public static <T extends Entity> Supplier<EntityType<T>> buildType(EntityType.IFactory<T> entityConstructor, ResourceLocation registryName, float width, float height) {
		return () -> EntityType.Builder
				.create(entityConstructor, EntityClassification.CREATURE)
				.setTrackingRange(64).setUpdateInterval(2).size(width, height)
				.build(registryName.toString());
	}
}
