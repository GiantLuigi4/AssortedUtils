package com.tfc.assortedutils.packets.container;

import net.minecraft.network.INetHandler;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;

public class SimpleContainerActionPacket implements IPacket {
	public int action = 0;
	
	public SimpleContainerActionPacket(int action) {
		this.action = action;
	}
	
	public SimpleContainerActionPacket(PacketBuffer buf) {
		readPacketData(buf);
	}
	
	@Override
	public void readPacketData(PacketBuffer buf) {
		action = buf.readInt();
	}
	
	@Override
	public void writePacketData(PacketBuffer buf) {
		buf.writeInt(action);
	}
	
	@Override
	public void processPacket(INetHandler handler) {
	}
}
