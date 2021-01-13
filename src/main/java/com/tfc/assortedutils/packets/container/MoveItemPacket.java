package com.tfc.assortedutils.packets.container;

import net.minecraft.network.INetHandler;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;

public class MoveItemPacket implements IPacket {
	public int from;
	public int to;
	
	public MoveItemPacket(int from, int to) {
		this.from = from;
		this.to = to;
	}
	
	public MoveItemPacket(PacketBuffer buf) {
		readPacketData(buf);
	}
	
	@Override
	public void readPacketData(PacketBuffer buf) {
		from = buf.readInt();
		to = buf.readInt();
	}
	
	@Override
	public void writePacketData(PacketBuffer buf) {
		buf.writeInt(from);
		buf.writeInt(to);
	}
	
	@Override
	public void processPacket(INetHandler handler) {
	}
}
