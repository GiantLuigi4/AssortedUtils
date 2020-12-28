package com.tfc.assortedutils.packets;

import com.tfc.assortedutils.registry.RendererRegistry;
import net.minecraft.network.INetHandler;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.gen.feature.structure.StructurePiece;
import net.minecraft.world.gen.feature.structure.StructureStart;
import net.minecraftforge.fml.loading.FMLEnvironment;

import java.util.ArrayList;
import java.util.List;

public class StructurePacket implements IPacket {
	StructureStart<?> structureStart;
	String dimension;
	
	public StructurePacket(StructureStart<?> structureStart, String dimension) {
		this.structureStart = structureStart;
		this.dimension = dimension;
	}
	
	public StructurePacket(PacketBuffer buf) {
		if (FMLEnvironment.dist.isClient()) readPacketData(buf);
	}
	
	@Override
	public void readPacketData(PacketBuffer buf) {
		MutableBoundingBox start = new MutableBoundingBox(
				buf.readInt(),
				buf.readInt(),
				buf.readInt(),
				buf.readInt(),
				buf.readInt(),
				buf.readInt()
		);
		int amt = buf.readInt();
		ArrayList<MutableBoundingBox> pieces = new ArrayList<>();
//		ArrayList<Boolean> unknown = new ArrayList<>();
		for (int i = 0; i < amt; i++) {
			pieces.add(
					new MutableBoundingBox(
							buf.readInt(),
							buf.readInt(),
							buf.readInt(),
							buf.readInt(),
							buf.readInt(),
							buf.readInt()
					)
			);
//			unknown.add(false);
		}
		RendererRegistry.STRUCTURE_RENDERER.get().addStructure(
				new ResourceLocation(buf.readString()), buf.readString(), start, pieces
		);
//		Minecraft.getInstance().debugRenderer.structure.func_223454_a(
//				start, pieces, unknown,
//				Minecraft.getInstance().player.getEntityWorld().getDimensionType()
//		);
	}
	
	@Override
	public void writePacketData(PacketBuffer buf) {
		buf.writeInt(structureStart.getBoundingBox().minX);
		buf.writeInt(structureStart.getBoundingBox().minY);
		buf.writeInt(structureStart.getBoundingBox().minZ);
		buf.writeInt(structureStart.getBoundingBox().maxX);
		buf.writeInt(structureStart.getBoundingBox().maxY);
		buf.writeInt(structureStart.getBoundingBox().maxZ);
		
		List<StructurePiece> boundingBoxList = structureStart.getComponents();
		buf.writeInt(boundingBoxList.size());
		for (StructurePiece piece : boundingBoxList) {
			buf.writeInt(piece.getBoundingBox().minX);
			buf.writeInt(piece.getBoundingBox().minY);
			buf.writeInt(piece.getBoundingBox().minZ);
			buf.writeInt(piece.getBoundingBox().maxX);
			buf.writeInt(piece.getBoundingBox().maxY);
			buf.writeInt(piece.getBoundingBox().maxZ);
		}
		
		buf.writeString(dimension);
		buf.writeString(structureStart.getStructure().getStructureName());
	}
	
	@Override
	public void processPacket(INetHandler handler) {
	}
}
