package com.tfc.assortedutils.API.gui.container;

import com.tfc.assortedutils.AssortedUtils;
import com.tfc.assortedutils.packets.ContainerPacket;
import com.tfc.assortedutils.packets.UpdateContainerPacket;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraftforge.fml.network.PacketDistributor;

import javax.annotation.Nullable;
import java.util.ArrayList;

public class SimpleContainer extends Container {
	private final ArrayList<PlayerEntity> players = new ArrayList<>();
	public ArrayList<ItemSlot> slots = new ArrayList();
	
	public SimpleContainer(@Nullable ContainerType<?> type, int id) {
		super(type, id);
	}
	
	@Override
	public Slot getSlot(int slotId) {
		return null;
	}
	
	public ItemStack getItem(int index) {
		return slots.get(index).get();
	}
	
	public void open(PlayerEntity playerEntity) {
		playerEntity.openContainer = this;
		AssortedUtils.NETWORK_INSTANCE.send(
				PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) playerEntity),
				new ContainerPacket(this, this.getType())
		);
	}
	
	public void resync() {
		ArrayList<PlayerEntity> playersToRemove = new ArrayList<>();
		for (PlayerEntity player : players) {
			if (!player.world.getPlayers().contains(player)) {
				playersToRemove.add(player);
			} else {
				AssortedUtils.NETWORK_INSTANCE.send(
						PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) player),
						new UpdateContainerPacket(this)
				);
			}
		}
		for (PlayerEntity playerEntity : playersToRemove) players.remove(playerEntity);
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
		thisNBT.put("inventory", inventoryNBT);
		return thisNBT;
	}
	
	@Override
	public boolean canInteractWith(PlayerEntity playerIn) {
		return true;
	}
}
