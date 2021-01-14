package com.tfc.assortedutils.packets;

import com.tfc.assortedutils.API.networking.SimplePacket;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.network.INetHandler;
import net.minecraft.network.PacketBuffer;
import net.minecraft.pathfinding.Path;
import net.minecraft.pathfinding.PathPoint;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.loading.FMLEnvironment;

import java.util.Arrays;

public class PathPacket extends SimplePacket {
	Path path;
	Entity entity;
	float distance;
	
	public PathPacket(Path path, Entity entity, float distance) {
		this.path = path;
		this.entity = entity;
		this.distance = distance;
	}
	
	public PathPacket(PacketBuffer buf) {
		if (FMLEnvironment.dist.isClient()) readPacketData(buf);
	}
	
	@Override
	public void readPacketData(PacketBuffer buf) {
		if (buf.readBoolean()) {
			int len = buf.readInt();
			PathPoint[] path = new PathPoint[len];
			for (int i = 0; i < len; i++) path[i] = PathPoint.createFromBuffer(buf);
			Path path1 = new Path(Arrays.asList(path), new BlockPos(buf.readInt(), buf.readInt(), buf.readInt()), false);
			Minecraft.getInstance().debugRenderer.pathfinding.addPath(buf.readInt(), path1, buf.readFloat());
		}
	}
	
	@Override
	public void writePacketData(PacketBuffer buf) {
		if (path == null) {
			buf.writeBoolean(false);
		} else {
			buf.writeBoolean(true);
			buf.writeInt(path.getCurrentPathLength());
			for (int i = 0; i < path.getCurrentPathLength(); i++) {
				
				//Writing the following:
				//      PathPoint pathpoint = new PathPoint(buf.readInt(), buf.readInt(), buf.readInt());
				//      pathpoint.field_222861_j = buf.readFloat();
				//      pathpoint.costMalus = buf.readFloat();
				//      pathpoint.visited = buf.readBoolean();
				//      pathpoint.nodeType = PathNodeType.values()[buf.readInt()];
				//      pathpoint.distanceToTarget = buf.readFloat();
				
				buf.writeInt(path.getPathPointFromIndex(i).x);
				buf.writeInt(path.getPathPointFromIndex(i).y);
				buf.writeInt(path.getPathPointFromIndex(i).z);
				buf.writeFloat(path.getPathPointFromIndex(i).field_222861_j);
				buf.writeFloat(path.getPathPointFromIndex(i).costMalus);
				buf.writeBoolean(path.getPathPointFromIndex(i).visited);
				buf.writeInt(path.getPathPointFromIndex(i).nodeType.ordinal());
				buf.writeFloat(path.getPathPointFromIndex(i).distanceToTarget);
			}
			buf.writeInt(path.getTarget().getX());
			buf.writeInt(path.getTarget().getY());
			buf.writeInt(path.getTarget().getZ());
			buf.writeInt(entity.getEntityId());
			buf.writeFloat(distance);
		}
	}
	
	@Override
	public void processPacket(INetHandler handler) {
	
	}
}
