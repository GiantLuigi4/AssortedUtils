package tfc.assortedutils.API.transformations.matrix;

import net.minecraft.util.math.vector.*;

/**
 * if the method name ends with "S" then it's server safe
 */
public class Matrix4fDistSafe extends Matrix4f {
	public Matrix4fDistSafe() {
	}
	
	public Matrix4fDistSafe(Matrix4f matrixIn) {
		super(matrixIn);
	}
	
	public Matrix4fDistSafe(Quaternion quaternionIn) {
		super(quaternionIn);
	}
	
	public Matrix4fDistSafe(float[] values) {
		super(values);
	}
	
	public void mulS(Matrix4f matrix) {
		float f = this.m00 * matrix.m00 + this.m01 * matrix.m10 + this.m02 * matrix.m20 + this.m03 * matrix.m30;
		float f1 = this.m00 * matrix.m01 + this.m01 * matrix.m11 + this.m02 * matrix.m21 + this.m03 * matrix.m31;
		float f2 = this.m00 * matrix.m02 + this.m01 * matrix.m12 + this.m02 * matrix.m22 + this.m03 * matrix.m32;
		float f3 = this.m00 * matrix.m03 + this.m01 * matrix.m13 + this.m02 * matrix.m23 + this.m03 * matrix.m33;
		float f4 = this.m10 * matrix.m00 + this.m11 * matrix.m10 + this.m12 * matrix.m20 + this.m13 * matrix.m30;
		float f5 = this.m10 * matrix.m01 + this.m11 * matrix.m11 + this.m12 * matrix.m21 + this.m13 * matrix.m31;
		float f6 = this.m10 * matrix.m02 + this.m11 * matrix.m12 + this.m12 * matrix.m22 + this.m13 * matrix.m32;
		float f7 = this.m10 * matrix.m03 + this.m11 * matrix.m13 + this.m12 * matrix.m23 + this.m13 * matrix.m33;
		float f8 = this.m20 * matrix.m00 + this.m21 * matrix.m10 + this.m22 * matrix.m20 + this.m23 * matrix.m30;
		float f9 = this.m20 * matrix.m01 + this.m21 * matrix.m11 + this.m22 * matrix.m21 + this.m23 * matrix.m31;
		float f10 = this.m20 * matrix.m02 + this.m21 * matrix.m12 + this.m22 * matrix.m22 + this.m23 * matrix.m32;
		float f11 = this.m20 * matrix.m03 + this.m21 * matrix.m13 + this.m22 * matrix.m23 + this.m23 * matrix.m33;
		float f12 = this.m30 * matrix.m00 + this.m31 * matrix.m10 + this.m32 * matrix.m20 + this.m33 * matrix.m30;
		float f13 = this.m30 * matrix.m01 + this.m31 * matrix.m11 + this.m32 * matrix.m21 + this.m33 * matrix.m31;
		float f14 = this.m30 * matrix.m02 + this.m31 * matrix.m12 + this.m32 * matrix.m22 + this.m33 * matrix.m32;
		float f15 = this.m30 * matrix.m03 + this.m31 * matrix.m13 + this.m32 * matrix.m23 + this.m33 * matrix.m33;
		this.m00 = f;
		this.m01 = f1;
		this.m02 = f2;
		this.m03 = f3;
		this.m10 = f4;
		this.m11 = f5;
		this.m12 = f6;
		this.m13 = f7;
		this.m20 = f8;
		this.m21 = f9;
		this.m22 = f10;
		this.m23 = f11;
		this.m30 = f12;
		this.m31 = f13;
		this.m32 = f14;
		this.m33 = f15;
	}
	
	public void mulS(Quaternion quaternion) {
		this.mulS(new Matrix4f(quaternion));
	}
	
	public void translateS(Vector3f vector) {
		this.m03 += vector.getX();
		this.m13 += vector.getY();
		this.m23 += vector.getZ();
	}
	
	public Vector3f transformS(Vector3f vector) {
		Vector4f vector4f = new Vector4f(vector.getX(), vector.getY(), vector.getZ(), 0);
		vector4f.transform(this);
		return new Vector3f(vector4f.getX(), vector4f.getY(), vector4f.getZ());
	}
	
	public Vector3d transformS(Vector3d vector) {
		Vector4f vector4f = new Vector4f((float) vector.getX(), (float) vector.getY(), (float) vector.getZ(), 0);
		vector4f.transform(this);
		return new Vector3d(vector4f.getX(), vector4f.getY(), vector4f.getZ());
	}
}
