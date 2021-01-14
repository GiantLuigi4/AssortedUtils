package com.tfc.assortedutils.packets.container;

import com.tfc.assortedutils.API.networking.SimplePacket;
import net.minecraft.network.INetHandler;
import net.minecraft.network.PacketBuffer;

public class SimpleContainerActionPacket extends SimplePacket {
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
