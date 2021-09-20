package tfc.assortedutils.packets.container;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;
import tfc.assortedutils.API.gui.container.SimpleContainer;
import tfc.assortedutils.API.networking.SimplePacket;

import java.util.function.Supplier;

public class MoveItemPacket extends SimplePacket {
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
	public void handle(Supplier<NetworkEvent.Context> context) {
		if (to == -1) return;
		ServerPlayerEntity sender = context.get().getSender();
		if (sender != null) {
			if (sender.openContainer instanceof SimpleContainer) {
				SimpleContainer container = (SimpleContainer) sender.openContainer;
				
				if (!container.tempSlots.containsKey(sender.getUniqueID())) return;
				
				ItemStack src;
				if (from == -1) src = container.tempSlots.get(sender.getUniqueID()).get();
				else src = container.getItem(from);
				if (src == null) return;
				
				ItemStack dst = container.getItem(to);
				container.setSlot(to, src);
				if (!(dst == null || dst.isEmpty())) {
					if (from == -1) container.tempSlots.get(sender.getUniqueID()).set(dst);
					else container.setSlot(from, dst);
				}
			}
		}
	}
}
