package com.tfc.assortedutils.packets.container;

import com.tfc.assortedutils.API.gui.container.SimpleContainer;
import com.tfc.assortedutils.API.gui.screen.SimpleContainerScreen;
import com.tfc.assortedutils.API.networking.SimplePacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.INetHandler;
import net.minecraft.network.PacketBuffer;

public class UpdateContainerPacket extends SimplePacket {
	CompoundNBT containerNBT;
	
	public UpdateContainerPacket(SimpleContainer container) {
		containerNBT = container.serialize();
	}
	
	public UpdateContainerPacket(PacketBuffer buffer) {
		super(buffer);
	}
	
	@Override
	public void readPacketData(PacketBuffer buf) {
		containerNBT = buf.readCompoundTag();
		Screen screen = Minecraft.getInstance().currentScreen;
		if (screen instanceof SimpleContainerScreen<?>) {
			SimpleContainerScreen<?> screen1 = ((SimpleContainerScreen<?>) screen);
			screen1.deseralize(containerNBT);
		}
	}
	
	@Override
	public void writePacketData(PacketBuffer buf) {
		buf.writeCompoundTag(containerNBT);
	}
	
	@Override
	public void processPacket(INetHandler handler) {
	}
}
