package tfc.assortedutils.API.debug.vanilla.revamped;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MutableBoundingBox;
import tfc.assortedutils.API.debug.CustomDebugRenderer;
import tfc.assortedutils.API.rendering.RenderHelper;
import tfc.assortedutils.AssortedUtils;
import tfc.assortedutils.items.DebugToolItem;
import tfc.assortedutils.registry.ItemRegistry;
import tfc.assortedutils.utils.BiObject;
import tfc.assortedutils.utils.Color;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class StructureDebugRenderer extends CustomDebugRenderer {
	public HashMap<ResourceLocation, HashMap<String, ArrayList<BiObject<MutableBoundingBox, ArrayList<MutableBoundingBox>>>>> structureMap = new HashMap<>();
	
	@Override
	public void render(MatrixStack stack, double playerX, double playerY, double playerZ) {
		AssortedUtils.createBetterFPSGraphSection("assortedutils:drawStructures");
		RenderSystem.enableDepthTest();
//		Minecraft.getInstance().debugRenderer.structure.render(
//				new MatrixStack(), Minecraft.getInstance().getRenderTypeBuffers().getBufferSource(),
//				playerX, playerY, playerZ
//		);
		structureMap.forEach((dimension, structures) -> {
			if (dimension.equals(Minecraft.getInstance().world.getDimensionKey().getLocation())) {
				structures.forEach((structName, boundingBoxes) -> {
					Color c = new Color(((int) Math.abs(structName.length() * 3732.12382f)) % 255, Math.abs(Objects.hash(structName)) % 255, Math.abs(Objects.hash(structName.toLowerCase())) % 255);
					
					AssortedUtils.createBetterFPSGraphSection("assortedutils:drawBoundingBoxes_" + structName);
					boundingBoxes.forEach((boxes) -> {
						if (boxes.obj1.intersectsWith(new MutableBoundingBox(
								(int) Minecraft.getInstance().player.getPositionVec().x - 32,
								(int) Minecraft.getInstance().player.getPositionVec().y - 32,
								(int) Minecraft.getInstance().player.getPositionVec().z - 32,
								(int) Minecraft.getInstance().player.getPositionVec().x + 32,
								(int) Minecraft.getInstance().player.getPositionVec().y + 32,
								(int) Minecraft.getInstance().player.getPositionVec().z + 32
						))) {
							boxes.obj2.forEach(bb -> {
								RenderSystem.enableBlend();
								RenderSystem.defaultAlphaFunc();
								RenderSystem.defaultBlendFunc();
								RenderSystem.color4f(0.0F, 1.0F, 0.0F, 0.75F);
								RenderSystem.disableTexture();
								
								RenderHelper.drawBoxOutline(null, new MutableBoundingBox(bb.minX, bb.minY, bb.minZ, bb.maxX + 1, bb.maxY + 1, bb.maxZ + 1), 1, 0, 0, 1);
								
								RenderSystem.enableBlend();
								RenderSystem.defaultAlphaFunc();
								RenderSystem.defaultBlendFunc();
								RenderSystem.color4f(0.0F, 1.0F, 0.0F, 0.75F);
								RenderSystem.disableTexture();
							});
						}
						MutableBoundingBox bb = boxes.obj1;
						
						RenderSystem.enableBlend();
						RenderSystem.defaultAlphaFunc();
						RenderSystem.defaultBlendFunc();
						RenderSystem.color4f(0.0F, 1.0F, 0.0F, 0.75F);
						RenderSystem.disableTexture();
						
						RenderHelper.drawBoxOutline(null, new MutableBoundingBox(bb.minX, bb.minY, bb.minZ, bb.maxX + 1, bb.maxY + 1, bb.maxZ + 1), c.getRed() / 255f, c.getBlue() / 255f, c.getGreen() / 255f, 1);
						
						RenderSystem.enableBlend();
						RenderSystem.defaultAlphaFunc();
						RenderSystem.defaultBlendFunc();
						RenderSystem.color4f(0.0F, 1.0F, 0.0F, 0.75F);
						RenderSystem.disableTexture();
					});
				});
			}
		});
//		for (Map<String, MutableBoundingBox> boxes : Minecraft.getInstance().debugRenderer.structure.subBoxes.values().toArray(new Map[0])) {
//			for (MutableBoundingBox bb : boxes.values()) {
//				RenderSystem.enableBlend();
//				RenderSystem.defaultAlphaFunc();
//				RenderSystem.defaultBlendFunc();
//				RenderSystem.color4f(0.0F, 1.0F, 0.0F, 0.75F);
//				RenderSystem.disableTexture();
//
//				RenderHelper.drawBox(stack,new MutableBoundingBox(bb.minX,bb.minY,bb.minZ,bb.maxX+1,bb.maxY+1,bb.maxZ+1),1,0,0,0.5f);
//
//				RenderSystem.enableBlend();
//				RenderSystem.defaultAlphaFunc();
//				RenderSystem.defaultBlendFunc();
//				RenderSystem.color4f(0.0F, 1.0F, 0.0F, 0.75F);
//				RenderSystem.disableTexture();
//			}
//		}
//		for (Map<String, MutableBoundingBox> boxes : Minecraft.getInstance().debugRenderer.structure.mainBoxes.values().toArray(new Map[0])) {
//			for (MutableBoundingBox bb : boxes.values()) {
//				RenderSystem.enableBlend();
//				RenderSystem.defaultAlphaFunc();
//				RenderSystem.defaultBlendFunc();
//				RenderSystem.color4f(0.0F, 1.0F, 0.0F, 0.75F);
//				RenderSystem.disableTexture();
//
//				RenderHelper.drawBox(stack,bb,1,0,1,0.5f);
//
//				RenderSystem.enableBlend();
//				RenderSystem.defaultAlphaFunc();
//				RenderSystem.defaultBlendFunc();
//				RenderSystem.color4f(0.0F, 1.0F, 0.0F, 0.75F);
//				RenderSystem.disableTexture();
//			}
//		}
		AssortedUtils.endBetterFPSGraphSection();
	}
	
	@Override
	public boolean shouldRender() {
		return
				Minecraft.getInstance().player.getHeldItem(Hand.OFF_HAND).getItem().equals(ItemRegistry.DEBUG_TOOL_STRUCTURE.get());
	}
	
	public void addStructure(ResourceLocation dimension, String structureName, MutableBoundingBox mainBB, ArrayList<MutableBoundingBox> subBBs) {
		if (Minecraft.getInstance().player.getHeldItem(Hand.OFF_HAND).getItem() instanceof DebugToolItem) {
			HashMap<String, ArrayList<BiObject<MutableBoundingBox, ArrayList<MutableBoundingBox>>>> nameToStructureArrayMap;
			if (structureMap.containsKey(dimension)) nameToStructureArrayMap = structureMap.get(dimension);
			else nameToStructureArrayMap = new HashMap<>();
			
			if (!structureMap.containsKey(dimension)) structureMap.put(dimension, nameToStructureArrayMap);
			
			ArrayList<BiObject<MutableBoundingBox, ArrayList<MutableBoundingBox>>> structures;
			if (nameToStructureArrayMap.containsKey(structureName))
				structures = nameToStructureArrayMap.get(structureName);
			else structures = new ArrayList<>();
			
			if (!nameToStructureArrayMap.containsKey(structureName))
				nameToStructureArrayMap.put(structureName, new ArrayList<>());
			
			structures.add(new BiObject<>(mainBB, subBBs));
		}
	}
}
