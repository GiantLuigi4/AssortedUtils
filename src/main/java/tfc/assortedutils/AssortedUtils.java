package tfc.assortedutils;

import com.tfc.better_fps_graph.API.Profiler;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;
import tfc.assortedutils.API.gui.container.ItemSlot;
import tfc.assortedutils.API.gui.container.SimpleContainer;
import tfc.assortedutils.API.networking.AutomatedSimpleChannel;
import tfc.assortedutils.custom_registries.debug_renderer.DebugRegistryBuilder;
import tfc.assortedutils.custom_registries.simple_container_screens.SimpleContainerScreenRegistryBuilder;
import tfc.assortedutils.packets.PathPacket;
import tfc.assortedutils.packets.StructurePacket;
import tfc.assortedutils.packets.container.*;
import tfc.assortedutils.registry.ItemRegistry;
import tfc.assortedutils.registry.RendererRegistry;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("assortedutils")
public class AssortedUtils {
	
	public static final AutomatedSimpleChannel NETWORK_INSTANCE = AutomatedSimpleChannel.create(
			new ResourceLocation("assorted_utils", "main"),
			() -> "2",
			"2"::equals,
			"2"::equals
	);
	
	public AssortedUtils() {
		NETWORK_INSTANCE.registerPacket(PathPacket.class, PathPacket::new);
		NETWORK_INSTANCE.registerPacket(StructurePacket.class, StructurePacket::new);
		NETWORK_INSTANCE.registerPacket(ContainerPacket.class, ContainerPacket::new);
		NETWORK_INSTANCE.registerPacket(MoveItemPacket.class, MoveItemPacket::new, (packet, context) -> {
			if (context.get().getSender() != null) {
				Container container = context.get().getSender().openContainer;
				if (container instanceof SimpleContainer) {
					ItemSlot slotSrc = ((SimpleContainer) container).slots.get(packet.from);
					ItemSlot slotTar = ((SimpleContainer) container).slots.get(packet.to);
					
					ItemStack stack1 = slotSrc.get();
					ItemStack stack2 = slotTar.get();
					
					slotSrc.set(stack2);
					slotTar.set(stack1);
				}
				context.get().setPacketHandled(true);
			}
		});
		NETWORK_INSTANCE.registerPacket(SimpleContainerActionPacket.class, SimpleContainerActionPacket::new, (packet, context) -> {
			if (packet.action == 0) {
				context.get().getSender().closeContainer();
			}
			context.get().setPacketHandled(true);
		});
		NETWORK_INSTANCE.registerPacket(UpdateContainerPacket.class, UpdateContainerPacket::new);
		NETWORK_INSTANCE.registerPacket(GrabItemPacket.class, GrabItemPacket::new);
		
		ItemRegistry.ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
		
		if (FMLEnvironment.dist.isClient()) {
			RendererRegistry.RENDERERS.register(FMLJavaModLoadingContext.get().getModEventBus());
			MinecraftForge.EVENT_BUS.addListener(Client::onRenderWorldLast);
		}
		
		FMLJavaModLoadingContext.get().getModEventBus().addListener(AssortedUtils::createRegistries);
	}
	
	public static void createRegistries(RegistryEvent.NewRegistry event) {
		new DebugRegistryBuilder().create();
		if (FMLEnvironment.dist.isClient()) new SimpleContainerScreenRegistryBuilder().create();
	}
	
	public static void createBetterFPSGraphSection(String name) {
		if (ModList.get().isLoaded("better_fps_graph"))
			Profiler.addSection(name);
	}
	
	public static void endBetterFPSGraphSection() {
		if (ModList.get().isLoaded("better_fps_graph"))
			Profiler.endSection();
	}
}
