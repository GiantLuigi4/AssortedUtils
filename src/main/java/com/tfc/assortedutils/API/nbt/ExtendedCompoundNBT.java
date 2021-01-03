package com.tfc.assortedutils.API.nbt;

import com.google.gson.*;
import net.minecraft.nbt.*;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.common.util.Constants;

import java.util.Map;

public class ExtendedCompoundNBT extends CompoundNBT {
	public ExtendedCompoundNBT(Map<String, INBT> tagMap) {
		super(tagMap);
	}
	
	public ExtendedCompoundNBT() {
	}
	
	public ExtendedCompoundNBT(CompoundNBT src) {
		this.merge(src);
	}
	
	private static INBT toINBT(JsonElement element) {
		if (element.isJsonObject()) {
			ExtendedCompoundNBT json = new ExtendedCompoundNBT();
			json.writeJSONToBase((JsonObject) element);
			return json;
		} else if (element.isJsonPrimitive()) {
			JsonPrimitive primitive = ((JsonPrimitive) element);
			if (primitive.isString()) return StringNBT.valueOf(primitive.getAsString());
			else if (primitive.isBoolean()) return ByteNBT.valueOf(primitive.getAsBoolean());
			if (primitive.isNumber()) {
				if (primitive.getAsString().contains(".")) return DoubleNBT.valueOf(primitive.getAsDouble());
				else return IntNBT.valueOf(primitive.getAsInt());
			}
		} else if (element.isJsonArray()) {
			JsonArray array = (JsonArray) element;
			CompoundNBT list = new CompoundNBT();
			list.putLong("isList", 1);
			int index = 0;
			for (JsonElement element1 : array)
				list.put((index++) + "", toINBT(element1));
			return list;
		}
		return ByteNBT.valueOf((byte) 42);
	}
	
	public static JsonElement getElement(INBT inbt) {
		if (inbt.getType().equals(NBTTypes.getGetTypeByID(Constants.NBT.TAG_STRING)))
			return new JsonPrimitive(((StringNBT) inbt).getString());
		else if (inbt.getType().equals(NBTTypes.getGetTypeByID(Constants.NBT.TAG_INT)))
			return new JsonPrimitive(((IntNBT) inbt).getInt());
		else if (inbt.getType().equals(NBTTypes.getGetTypeByID(Constants.NBT.TAG_DOUBLE)))
			return new JsonPrimitive(((DoubleNBT) inbt).getDouble());
		else if (inbt.getType().equals(NBTTypes.getGetTypeByID(Constants.NBT.TAG_BYTE))) {
			byte val = (((ByteNBT) inbt).getByte());
			if (val == 42) return JsonNull.INSTANCE;
			else return new JsonPrimitive(((ByteNBT) inbt).getByte() != 0);
		} else {
			ExtendedCompoundNBT nbt = new ExtendedCompoundNBT((CompoundNBT) inbt);
			if (nbt.contains("isList") && nbt.get("isList").getType().equals(NBTTypes.getGetTypeByID(Constants.NBT.TAG_LONG))) {
				JsonArray array = new JsonArray();
				for (String key1 : nbt.keySet()) {
					if (!key1.equals("isList")) {
						INBT inbt1 = nbt.get(key1);
						if (inbt1 != null) array.add(getElement(inbt1));
					}
				}
				return array;
			}
			return nbt.asJson();
		}
	}
	
	public void writeJSON(String key, JsonObject object) {
		ExtendedCompoundNBT jsonAsNBT = new ExtendedCompoundNBT();
		for (Map.Entry<String, JsonElement> entry : object.entrySet())
			jsonAsNBT.writeJsonElement(entry.getKey(), entry.getValue());
		this.put(key, jsonAsNBT);
	}
	
	public void writeJsonElement(String key, JsonElement element) {
		this.put(key, toINBT(element));
	}
	
	protected void writeJSONToBase(JsonObject object) {
		for (Map.Entry<String, JsonElement> entry : object.entrySet())
			this.writeJsonElement(entry.getKey(), entry.getValue());
	}
	
	public JsonObject getJson(String key) {
		JsonObject object = new JsonObject();
		CompoundNBT nbt = this.getCompound(key);
		for (String key1 : nbt.keySet()) {
			INBT inbt = nbt.get(key1);
			if (inbt != null) object.add(key1, getElement(inbt));
		}
		return object;
	}
	
	private JsonObject asJson() {
		JsonObject object = new JsonObject();
		for (String key1 : this.keySet()) {
			INBT inbt = this.get(key1);
			object.add(key1, getElement(inbt));
		}
		return object;
	}
	
	public void putVector3d(String key, Vector3d vector3d) {
		ListNBT list = new ListNBT();
		list.add(DoubleNBT.valueOf(vector3d.x));
		list.add(DoubleNBT.valueOf(vector3d.y));
		list.add(DoubleNBT.valueOf(vector3d.z));
		put(key, list);
	}
	
	public Vector3d getVector3d(String key) {
		ListNBT list = this.getList(key, Constants.NBT.TAG_DOUBLE);
		return new Vector3d(list.getDouble(0), list.getDouble(1), list.getDouble(2));
	}
	
	public void putVector3f(String key, Vector3f vector3d) {
		ListNBT list = new ListNBT();
		list.add(FloatNBT.valueOf(vector3d.getX()));
		list.add(FloatNBT.valueOf(vector3d.getY()));
		list.add(FloatNBT.valueOf(vector3d.getZ()));
		put(key, list);
	}
	
	public Vector3f getVector3f(String key) {
		ListNBT list = this.getList(key, Constants.NBT.TAG_FLOAT);
		return new Vector3f(list.getFloat(0), list.getFloat(1), list.getFloat(2));
	}
	
	public void putQuaternion(String key, Quaternion quaternion) {
		ListNBT list = new ListNBT();
		list.add(FloatNBT.valueOf(quaternion.getX()));
		list.add(FloatNBT.valueOf(quaternion.getY()));
		list.add(FloatNBT.valueOf(quaternion.getZ()));
		list.add(FloatNBT.valueOf(quaternion.getW()));
		put(key, list);
	}
	
	public Quaternion getQuaternion(String key) {
		ListNBT list = this.getList(key, Constants.NBT.TAG_FLOAT);
		return new Quaternion(list.getFloat(0), list.getFloat(1), list.getFloat(2), list.getFloat(3));
	}
	
	/**
	 * I literally don't know why you'd need this, but it's here so
	 *
	 * @param key      the entry key of the matrix
	 * @param matrix4f the matrix to write to nbt
	 */
	public void putMatrix4f(String key, Matrix4f matrix4f) {
		ListNBT list = new ListNBT();
		list.add(FloatNBT.valueOf(matrix4f.m00));
		list.add(FloatNBT.valueOf(matrix4f.m01));
		list.add(FloatNBT.valueOf(matrix4f.m02));
		list.add(FloatNBT.valueOf(matrix4f.m03));
		list.add(FloatNBT.valueOf(matrix4f.m10));
		list.add(FloatNBT.valueOf(matrix4f.m11));
		list.add(FloatNBT.valueOf(matrix4f.m12));
		list.add(FloatNBT.valueOf(matrix4f.m13));
		list.add(FloatNBT.valueOf(matrix4f.m20));
		list.add(FloatNBT.valueOf(matrix4f.m21));
		list.add(FloatNBT.valueOf(matrix4f.m22));
		list.add(FloatNBT.valueOf(matrix4f.m23));
		list.add(FloatNBT.valueOf(matrix4f.m30));
		list.add(FloatNBT.valueOf(matrix4f.m31));
		list.add(FloatNBT.valueOf(matrix4f.m32));
		list.add(FloatNBT.valueOf(matrix4f.m33));
		put(key, list);
	}
	
	/**
	 * if you manage to find a use for "putMatrix4f", you'd probably want this
	 *
	 * @param key they key of the matrix4f you want to get from nbt
	 * @return the matrix4f from the nbt
	 */
	public Matrix4f getMatrix4f(String key) {
		ListNBT list = this.getList(key, Constants.NBT.TAG_FLOAT);
		return new Matrix4f(
				new float[]{
						list.getFloat(0),
						list.getFloat(1),
						list.getFloat(2),
						list.getFloat(3),
						list.getFloat(4),
						list.getFloat(5),
						list.getFloat(6),
						list.getFloat(7),
						list.getFloat(8),
						list.getFloat(9),
						list.getFloat(10),
						list.getFloat(11),
						list.getFloat(12),
						list.getFloat(13),
						list.getFloat(14),
						list.getFloat(15),
				}
		);
	}
}
