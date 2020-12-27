package com.tfc.assortedutils;

import com.tfc.assortedutils.custom_registries.debug_renderer.DebugRegistryBuilder;
import com.tfc.assortedutils.packets.PathPacket;
import com.tfc.assortedutils.registry.ItemRegistry;
import com.tfc.assortedutils.registry.RendererRegistry;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("assortedutils")
public class AssortedUtils {
	
	public static final SimpleChannel NETWORK_INSTANCE = NetworkRegistry.newSimpleChannel(
			new ResourceLocation("assorted_utils", "main"),
			() -> "1",
			"1"::equals,
			"1"::equals
	);
	
	public AssortedUtils() {
		NETWORK_INSTANCE.registerMessage(0, PathPacket.class, PathPacket::writePacketData, PathPacket::new, (packet, context) -> {
			context.get().setPacketHandled(true);
		});
		
		ItemRegistry.ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
		RendererRegistry.RENDERERS.register(FMLJavaModLoadingContext.get().getModEventBus());
		FMLJavaModLoadingContext.get().getModEventBus().addListener(AssortedUtils::createRegistries);
		
		MinecraftForge.EVENT_BUS.addListener(Client::onRenderWorldLast);
	}
	
	public static void createRegistries(RegistryEvent.NewRegistry event) {
		new DebugRegistryBuilder().create();
	}
}
