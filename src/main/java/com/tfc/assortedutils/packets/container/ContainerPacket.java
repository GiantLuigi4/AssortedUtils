package com.tfc.assortedutils.packets.container;

import com.tfc.assortedutils.API.gui.container.SimpleContainer;
import com.tfc.assortedutils.API.gui.screen.SimpleContainerScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.INetHandler;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.registries.ForgeRegistries;

public class ContainerPacket implements IPacket {
	String regName;
	CompoundNBT containerNBT;
	
	public ContainerPacket(SimpleContainer container, ContainerType<?> type) {
		regName = type.getRegistryName().toString();
		containerNBT = container.serialize();
	}
	
	public ContainerPacket(PacketBuffer buf) {
		readPacketData(buf);
	}
	
	@Override
	public void readPacketData(PacketBuffer buf) {
		read(buf);
	}
	
	@OnlyIn(Dist.CLIENT)
	public void read(PacketBuffer buf) {
		if (FMLEnvironment.dist.isClient()) {
			regName = buf.readString();
			containerNBT = buf.readCompoundTag();
			ContainerType<?> type = ForgeRegistries.CONTAINERS.getValue(new ResourceLocation(regName));
			SimpleContainerScreen container = new SimpleContainerScreen<>(StringTextComponent.EMPTY, Minecraft.getInstance(), type);
			container.deseralize(containerNBT);
			Minecraft.getInstance().currentScreen = container;
		}
	}
	
	@Override
	public void writePacketData(PacketBuffer buf) {
		buf.writeString(regName);
		buf.writeCompoundTag(containerNBT);
	}
	
	@Override
	public void processPacket(INetHandler handler) {
	}
}
