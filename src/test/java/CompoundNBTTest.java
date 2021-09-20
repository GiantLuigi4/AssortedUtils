import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;
import tfc.assortedutils.API.nbt.ExtendedCompoundNBT;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class CompoundNBTTest {
	public static void main(String[] args) throws IOException {
		ExtendedCompoundNBT compoundNBT = new ExtendedCompoundNBT();
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		JsonObject object = gson.fromJson(readFromCL("extended_tag_compound_tests/json_in.json"), JsonObject.class);
		compoundNBT.writeJSON("hi", object);
		write("src/test/resources/extended_tag_compound_tests/json_out.json", gson.toJson(gson.fromJson(compoundNBT.getJson("hi"), JsonObject.class)));
		CompressedStreamTools.write(compoundNBT, new File("src/test/resources/extended_tag_compound_tests/json_as_nbt.nbt"));
		
		compoundNBT = new ExtendedCompoundNBT();
		compoundNBT.putVector3d("vec3d", new Vector3d(0, 10, 0));
		compoundNBT.putVector3f("vec3f", new Vector3f(10, 0, 0));
		compoundNBT.putMatrix4f("matrix4f", new Matrix4f(new Quaternion(128, 0, 45, true)));
		compoundNBT.putQuaternion("quaternion", new Quaternion(128, 0, 45, true));
		
		CompressedStreamTools.write(compoundNBT, new File("src/test/resources/extended_tag_compound_tests/position_and_rotations.nbt"));
		
		CompoundNBT source = new CompoundNBT();
		source.putString("hello", "world");
		compoundNBT = new ExtendedCompoundNBT(source, true);
		compoundNBT.putInt("int", 1);
		source.putLong("long", 10239);
		compoundNBT.putFloat("float", Float.MAX_VALUE);
		
		CompressedStreamTools.write(source, new File("src/test/resources/extended_tag_compound_tests/nbt_wrapper_test.nbt"));
	}
	
	public static String readFromCL(String resource) throws IOException {
		InputStream stream = CompoundNBTTest.class.getClassLoader().getResourceAsStream(resource);
		byte[] bytes = new byte[stream.available()];
		stream.read(bytes);
		try {
			stream.close();
		} catch (Throwable ignored) {
		}
		return new String(bytes);
	}
	
	public static void write(String path, String text) throws IOException {
		File file = new File(path);
		if (!file.exists()) {
			file.getParentFile().mkdirs();
			file.createNewFile();
		}
		FileOutputStream stream = new FileOutputStream(file);
		stream.write(text.getBytes());
		stream.close();
	}
}
