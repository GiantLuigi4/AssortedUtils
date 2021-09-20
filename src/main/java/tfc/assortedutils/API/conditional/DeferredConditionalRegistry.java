package tfc.assortedutils.API.conditional;

import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.util.function.Supplier;

public class DeferredConditionalRegistry {
	/**
	 * registers something assuming the condition is met
	 *
	 * @param register     the registry to register to
	 * @param registryName the registry name of the object
	 * @param item         the object to register
	 * @param condition    the condition, I.E. ()->ModList.get().isLoaded("outer_end"), which would make it only register if "outer_end" is installed
	 * @param <T>          the class of the registry
	 * @return the registry object, or null if the condition is not met
	 */
	public static <T extends IForgeRegistryEntry<T>> RegistryObject<T> conditionallyRegister(DeferredRegister<T> register, String registryName, T item, Supplier<Boolean> condition) {
		if (condition.get()) return register.register(registryName, () -> item);
		return null;
	}
}
