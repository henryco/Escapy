package com.x.game.utils.primitives;

public interface EscapyGeometry {

	public static float invSqrt(float x) {
		/**
		 * https://www.wikiwand.com/en/Fast_inverse_square_root
		 * https://www.wikiwand.com/en/Fast_inverse_square_root#/Overview_of_the_code
		 **/
		float half = 0.5f * x;
		int i = Float.floatToIntBits(x);
		i = 0x5f3759df - (i >> 1); // magic constant
		x = Float.intBitsToFloat(i);
		x = x * (1.5f - (half * x * x));
		return x;
	}

	public static float[] lineCoeff_a_b(float[] point1, float[] point2) {
		float a = (point2[1] - point1[1]) / (point2[0] - point1[0]);
		float b = ((point1[0] * (point1[1] - point2[1])) / (point2[0] - point1[0])) + point1[1];

		return new float[] { a, b };
	}

	public static float linePerp_a(float a) {
		return ((-1) / a);
	}

	public static float[] linePerpCoeff_a_b(float a, float[] point) {
		float new_a = ((-1) / a);
		return new float[] { new_a, (point[1] - (new_a * point[0])) };
	}

	public static float[] linePerpCoeff_a_b(float a, float x, float y) {
		float new_a = ((-1) / a);
		return new float[] { new_a, (y - (new_a * x)) };
	}

	public static float lineCoeff_a(float[] point1, float[] point2) {
		return (point2[1] - point1[1]) / (point2[0] - point1[0]);
	}

	public static float lineCoeff_b(float a, float p_x, float p_y) {
		return (p_y - (a * p_x));
	}

	public static float lineCoeff_b(float[] point1, float[] point2) {
		return ((point1[0] * (point1[1] - point2[1])) / (point2[0] - point1[0])) + point1[1];
	}

	public static float f_x_Line(float[] a_b, float x) {
		return ((a_b[0] * x) + a_b[1]);
	}

	public static float f_x_Line(float a, float b, float x) {
		return ((a * x) + b);
	}

	public static float f_x_LineA_B_C(float[] A_B_C, float x) {
		return ((-1) * ((A_B_C[0] * x + A_B_C[2]) / A_B_C[1]));
	}

	public static float f_y_Line(float[] a_b, float y) {
		return ((y - a_b[1]) / a_b[0]);
	}

	public static float x_f_Line(float a, float b, float y) {
		return ((y - b) / a);
	}

	public static float common_f_y_Line(float[] a_b_1, float[] a_b_2) {
		return ((a_b_2[1] - a_b_1[1]) / (a_b_1[0] - a_b_2[0]));
	}

	public static float common_f_x_Line(float[] a_b_1, float[] a_b_2) {
		return (((a_b_2[0] * a_b_1[1]) - (a_b_1[0] * a_b_2[1])) / (a_b_2[0] - a_b_1[0]));
	}

	public static float lineCoeff_Ax(float[] point1, float[] point2) {
		if (point1[0] != point2[0])
			return (-1) * lineCoeff_a(point1, point2);
		return 1;
	}

	public static float lineCoeff_By(float[] point1, float[] point2) {
		if (point1[0] != point2[0])
			return 1;
		return 0;
	}

	public static float lineCoeff_C(float[] point1, float[] point2) {
		if (point1[0] != point2[0])
			return (-1) * lineCoeff_b(point1, point2);
		return point1[0];
	}

	public static float[] getCmnPerpPoint(float[] ab, float[] point0, float[] point1, float[] point1_target) {
		float[] cmnPoint = new float[2];
		if (point0[0] == point1[0]) {
			cmnPoint[0] = point1[0];
			cmnPoint[1] = point1_target[1];
			return cmnPoint;
		}
		if (point0[1] == point1[1]) {
			cmnPoint[0] = point1_target[0];
			cmnPoint[1] = point1[1];
			return cmnPoint;
		}

		float[] trgd_ab = linePerpCoeff_a_b(ab[0], point1_target);
		cmnPoint[0] = common_f_y_Line(trgd_ab, ab);
		cmnPoint[1] = common_f_x_Line(trgd_ab, ab);
		return cmnPoint;
	}

	public static float[] lineCoeff_A_B_C_perp(float[] point0, float[] pointIntersect) {
		if (point0[0] == pointIntersect[0])
			return new float[] { 1, 0, (-1) * pointIntersect[1] };
		if (point0[1] == pointIntersect[1])
			return new float[] { 0, 1, (-1) * pointIntersect[1] };

		float[] peprLine_ab = linePerpCoeff_a_b(lineCoeff_a(point0, pointIntersect), pointIntersect);
		return new float[] { 1, (-1) * peprLine_ab[0], (-1) * peprLine_ab[1] };
	}

	public static float[] cirlcePointRadiusIntersected(float radius, float[] circlePoint, float[] Point) {
		float[] Vec = new float[] { Point[0] - circlePoint[0], Point[1] - circlePoint[1] };
		float inv = radius * invSqrt(((Vec[0] * Vec[0]) + (Vec[1] * Vec[1])));

		circlePoint[0] += (Vec[0] * inv);
		circlePoint[1] += (Vec[1] * inv);

		return circlePoint;
	}

}
