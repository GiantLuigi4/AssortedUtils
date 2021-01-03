package com.tfc.assortedutils.API.transformations.quaternion;

import net.minecraft.util.math.vector.Quaternion;

public class QuaternionHelper {
	public static Quaternion fromAngles(float xAngle, float yAngle, float zAngle, boolean degrees) {
		if (degrees) {
			xAngle *= ((float) Math.PI / 180F);
			yAngle *= ((float) Math.PI / 180F);
			zAngle *= ((float) Math.PI / 180F);
		}
		
		float f = sin(0.5F * xAngle);
		float f1 = cos(0.5F * xAngle);
		float f2 = sin(0.5F * yAngle);
		float f3 = cos(0.5F * yAngle);
		float f4 = sin(0.5F * zAngle);
		float f5 = cos(0.5F * zAngle);
		float x = f * f3 * f5 + f1 * f2 * f4;
		float y = f1 * f2 * f5 - f * f3 * f4;
		float z = f * f2 * f5 + f1 * f3 * f4;
		float w = f1 * f3 * f5 - f * f2 * f4;
		
		return new Quaternion(x, y, z, w);
	}
	
	private static float cos(float p_214904_0_) {
		return (float) Math.cos(p_214904_0_);
	}
	
	private static float sin(float p_214903_0_) {
		return (float) Math.sin(p_214903_0_);
	}
}
