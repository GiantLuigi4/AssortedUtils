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
	public int count = 64;
	
	public GrabItemPacket(int from) {
		this.from = from;
	}
	
	public GrabItemPacket(int from, int count) {
		this.from = from;
		this.count = count;
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
		if (from < 0) return;
		if (sender != null) {
			if (sender.openContainer instanceof SimpleContainer) {
				SimpleContainer container = (SimpleContainer) sender.openContainer;
				
				if (!container.tempSlots.containsKey(sender.getUniqueID())) return;
				
				ItemStack stack = container.getItem(sender, from);
//				container.setSlot(sender, from, ItemStack.EMPTY);
				
				if (stack == null) return;
				stack = stack.split(Math.min(count, stack.getCount()));
				
				if (container.tempSlots.get(sender.getUniqueID()).inventory.isEmpty())
					container.tempSlots.get(sender.getUniqueID()).set(stack);
			}
		}
	}
}
