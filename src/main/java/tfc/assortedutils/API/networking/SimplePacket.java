package tfc.assortedutils.API.networking;

import net.minecraft.network.INetHandler;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;

public class SimplePacket implements IPacket {
	public SimplePacket(PacketBuffer buffer) {
		this.readPacketData(buffer);
	}
	
	public SimplePacket() {
	}
	
	@Override
	public void readPacketData(PacketBuffer buf) {
	}
	
	@Override
	public void writePacketData(PacketBuffer buf) {
	}
	
	@Override
	public void processPacket(INetHandler handler) {
	}
}
