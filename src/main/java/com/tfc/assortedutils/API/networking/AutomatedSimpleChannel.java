package com.tfc.assortedutils.API.networking;

import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.NetworkInstance;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

import java.lang.reflect.Method;
import java.util.function.*;

public class AutomatedSimpleChannel extends SimpleChannel {
	private static final Method createInstance;
	
	static {
		try {
			createInstance = NetworkRegistry.class.getDeclaredMethod("createInstance", ResourceLocation.class, Supplier.class, Predicate.class, Predicate.class);
		} catch (NoSuchMethodException err) {
			RuntimeException exception = new RuntimeException(err);
			exception.setStackTrace(err.getStackTrace());
			throw exception;
		}
	}
	
	private int index = 0;
	
	public AutomatedSimpleChannel(NetworkInstance instance) {
		super(instance);
	}
	
	public AutomatedSimpleChannel(NetworkInstance instance, Consumer<NetworkEvent.ChannelRegistrationChangeEvent> registryChangeNotify) {
		super(instance, registryChangeNotify);
	}
	
	public static AutomatedSimpleChannel create(final ResourceLocation name, Supplier<String> networkProtocolVersion, Predicate<String> clientAcceptedVersions, Predicate<String> serverAcceptedVersions) {
		try {
			return new AutomatedSimpleChannel((NetworkInstance) createInstance.invoke(null, name, networkProtocolVersion, clientAcceptedVersions, serverAcceptedVersions));
		} catch (Throwable err) {
			RuntimeException exception = new RuntimeException(err);
			//I should start doing this so the exception message is a bit more useful, lol
			exception.setStackTrace(err.getStackTrace());
			throw exception;
		}
	}
	
	public <MSG extends SimplePacket> void registerPacket(Class<MSG> packetClass, Function<PacketBuffer, MSG> packetSupplier) {
		registerMessage(index++, packetClass, SimplePacket::writePacketData, packetSupplier, (packet, context) -> {
			context.get().setPacketHandled(true);
		});
	}
	
	public <MSG extends SimplePacket> void registerPacket(Class<MSG> packetClass, Function<PacketBuffer, MSG> packetSupplier, BiConsumer<MSG, Supplier<NetworkEvent.Context>> messageConsumer) {
		registerMessage(index++, packetClass, SimplePacket::writePacketData, packetSupplier, messageConsumer);
	}
}
