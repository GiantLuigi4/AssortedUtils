package com.tfc.assortedutils.API.networking;

import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.loading.FMLEnvironment;

public class SimpleSidedPacket extends SimplePacket {
	public SimpleSidedPacket(PacketBuffer buffer) {
		super(buffer);
	}
	
	@Override
	public void readPacketData(PacketBuffer buf) {
		if (FMLEnvironment.dist.isClient()) readClient(buf);
		else readServer(buf);
		readCommon(buf);
	}
	
	@Override
	public void writePacketData(PacketBuffer buf) {
		if (FMLEnvironment.dist.isClient()) writeClient(buf);
		else writeServer(buf);
		writeCommon(buf);
	}
	
	/**
	 * Gets called if the game is a client
	 *
	 * @param buffer the packet buffer
	 */
	public void writeClient(PacketBuffer buffer) {
	}
	
	/**
	 * Gets called if the game is a dedicated server
	 *
	 * @param buffer the packet buffer
	 */
	public void writeServer(PacketBuffer buffer) {
	}
	
	/**
	 * Gets called regardless of side
	 *
	 * @param buffer the packet buffer
	 */
	public void writeCommon(PacketBuffer buffer) {
	}
	
	/**
	 * Gets called if the game is a client
	 *
	 * @param buffer the packet buffer
	 */
	public void readClient(PacketBuffer buffer) {
	}
	
	/**
	 * Gets called regardless of side
	 *
	 * @param buffer the packet buffer
	 */
	public void readCommon(PacketBuffer buffer) {
	}
	
	/**
	 * Gets called if the game is a dedicated server
	 *
	 * @param buffer the packet buffer
	 */
	public void readServer(PacketBuffer buffer) {
	}
}
