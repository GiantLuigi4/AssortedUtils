package com.tfc.assortedutils.packets;

import com.tfc.assortedutils.API.gui.container.SimpleContainer;
import com.tfc.assortedutils.API.gui.screen.SimpleContainerScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.INetHandler;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;

public class UpdateContainerPacket implements IPacket {
	CompoundNBT containerNBT;
	
	public UpdateContainerPacket(SimpleContainer container) {
		containerNBT = container.serialize();
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
