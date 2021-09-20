package tfc.assortedutils.packets.container;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;
import tfc.assortedutils.API.gui.container.SimpleContainer;
import tfc.assortedutils.API.networking.SimplePacket;

import java.util.function.Supplier;

public class GrabItemPacket extends SimplePacket {
	public int from;
	
	public GrabItemPacket(int from) {
		this.from = from;
	}
	
	public GrabItemPacket(PacketBuffer buf) {
		readPacketData(buf);
	}
	
	@Override
	public void readPacketData(PacketBuffer buf) {
		from = buf.readInt();
	}
	
	@Override
	public void writePacketData(PacketBuffer buf) {
		buf.writeInt(from);
	}
	
	@Override
	public void handle(Supplier<NetworkEvent.Context> context) {
		ServerPlayerEntity sender = context.get().getSender();
		if (sender != null) {
			if (sender.openContainer instanceof SimpleContainer) {
				SimpleContainer container = (SimpleContainer) sender.openContainer;
				
				if (!container.tempSlots.containsKey(sender.getUniqueID())) return;
				
				ItemStack stack = container.getItem(from);
				if (stack == null) return;
				if (container.tempSlots.get(sender.getUniqueID()).inventory.isEmpty())
					container.tempSlots.get(sender.getUniqueID()).set(stack);
			}
		}
	}
}
