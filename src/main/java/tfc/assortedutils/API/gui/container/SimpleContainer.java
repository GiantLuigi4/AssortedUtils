package tfc.assortedutils.API.gui.container;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraftforge.fml.network.PacketDistributor;
import tfc.assortedutils.AssortedUtils;
import tfc.assortedutils.packets.container.ContainerPacket;
import tfc.assortedutils.packets.container.UpdateContainerPacket;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class SimpleContainer extends Container {
	private final ArrayList<PlayerEntity> players = new ArrayList<>();
	public final HashMap<UUID, ItemSlot> tempSlots = new HashMap<>();
	public ArrayList<ItemSlot> slots = new ArrayList<>();
	
	public SimpleContainer(@Nullable ContainerType<?> type, int id) {
		super(type, id);
	}
	
	public boolean isInteractable;
	
	@Override
	public Slot getSlot(int slotId) {
		return null;
	}
	
	public ItemStack getItem(int index) {
		return slots.get(index).get();
	}
	
	public void open(PlayerEntity playerEntity) {
		players.add(playerEntity);
		playerEntity.openContainer = this;
		AssortedUtils.NETWORK_INSTANCE.send(
				PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) playerEntity),
				new ContainerPacket(this, this.getType())
		);
		if (!tempSlots.containsKey(playerEntity.getUniqueID())) {
			tempSlots.put(playerEntity.getUniqueID(), new ItemSlot(new WorkerInventory(), -1, -100, -100));
		}
	}
	
	@Override
	public void onContainerClosed(PlayerEntity playerEntity) {
		super.onContainerClosed(playerEntity);
		players.remove(playerEntity);
		if (tempSlots.containsKey(playerEntity.getUniqueID())) {
			playerEntity.dropItem(tempSlots.get(playerEntity.getUniqueID()).get(), true);
			tempSlots.remove(playerEntity.getUniqueID());
		}
	}
	
	public void resync() {
		ArrayList<PlayerEntity> playersToRemove = new ArrayList<>();
		for (PlayerEntity player : players) {
			if (
					player.world == null ||
							!player.world.getPlayers().contains(player) ||
							player.openContainer != this
			) {
				playersToRemove.add(player);
			} else {
				AssortedUtils.NETWORK_INSTANCE.send(
						PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) player),
						new UpdateContainerPacket(this)
				);
			}
		}
		for (PlayerEntity playerEntity : playersToRemove) {
			players.remove(playerEntity);
			if (tempSlots.containsKey(playerEntity.getUniqueID())) {
				playerEntity.dropItem(tempSlots.get(playerEntity.getUniqueID()).get(), true);
				tempSlots.remove(playerEntity.getUniqueID());
			}
		}
	}
	
	public CompoundNBT serialize() {
		CompoundNBT thisNBT = new CompoundNBT();
		ListNBT inventoryNBT = new ListNBT();
		slots.forEach((item) -> {
			CompoundNBT slot = new CompoundNBT();
			slot.putString("item", item.get().getItem().getRegistryName().toString());
			if (item.get().hasTag()) slot.put("tag", item.get().getOrCreateTag());
			slot.putInt("x", item.x);
			slot.putInt("y", item.y);
			inventoryNBT.add(slot);
		});
		thisNBT.putBoolean("interactable", isInteractable);
		thisNBT.put("inventory", inventoryNBT);
//		if (isInteractable) {
//			CompoundNBT slot = new CompoundNBT();
//			ItemSlot item = tempSlots;
//			slot.putString("item", item.get().getItem().getRegistryName().toString());
//			if (item.get().hasTag()) slot.put("tag", item.get().getOrCreateTag());
//			thisNBT.put("workerSlot", slot);
//		}
		return thisNBT;
	}
	
	@Override
	public boolean canInteractWith(PlayerEntity playerIn) {
		return true;
	}
	
	public void setSlot(int index, ItemStack value) {
		slots.get(index).set(value);
	}
}
