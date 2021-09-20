package tfc.assortedutils.packets.container;

import net.minecraft.network.INetHandler;
import net.minecraft.network.PacketBuffer;
import tfc.assortedutils.API.networking.SimplePacket;

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
