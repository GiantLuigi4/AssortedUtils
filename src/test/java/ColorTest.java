import com.tfc.assortedutils.utils.Color;

import java.util.Arrays;

public class ColorTest {
	public static void main(String[] args) {
		Color color = new Color(255, 0, 0);
		System.out.println(Arrays.toString(Color.toHSV(color)));
		System.out.println();
		
		color = new Color(0, 255, 0);
		System.out.println(Arrays.toString(Color.toHSV(color)));
		System.out.println();
		
		color = new Color(0, 128, 0);
		System.out.println(Arrays.toString(Color.toHSV(color)));
		System.out.println();
		
		color = new Color(0, 0, 0);
		System.out.println(Arrays.toString(Color.toHSV(color)));
		System.out.println();
		
		color = new Color(25, 1, 1, false);
		System.out.println(color);
		System.out.println();
		
		color = new Color(255, 255, 255);
		System.out.println(color);
		float[] hsv = Color.toHSV(color);
		System.out.println(Arrays.toString(Color.toHSV(color)));
		color = new Color(hsv[0], hsv[1], hsv[2], false);
		System.out.println(color);
		System.out.println();
		
		color = new Color(0, 255, 255);
		System.out.println(color);
		hsv = Color.toHSV(color);
		System.out.println(Arrays.toString(Color.toHSV(color)));
		color = new Color(hsv[0], hsv[1], hsv[2], false);
		System.out.println(color);
		System.out.println();
		
		color = new Color(255, 0, 255);
		System.out.println(color);
		hsv = Color.toHSV(color);
		System.out.println(Arrays.toString(Color.toHSV(color)));
		color = new Color(hsv[0], hsv[1], hsv[2], false);
		System.out.println(color);
		System.out.println();
	}
}
