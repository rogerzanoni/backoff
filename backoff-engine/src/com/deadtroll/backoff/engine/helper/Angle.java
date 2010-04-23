package com.deadtroll.backoff.engine.helper;

import org.newdawn.slick.geom.Vector2f;

public class Angle {
	public static final float RADTODEG = 57.29577951f;
	public static final float DEGTORAD = 0.017453293f;

	public static float calcAngle2D(Vector2f p1, Vector2f p2) {
		float ang = (float) Math.atan((p2.getY() - p1.getY())
				/ (p2.getX() - p1.getX())) * RADTODEG;
		// first quadrant
		if (p2.getY() < p1.getY() && p2.getX() > p1.getX())
			return ang;
		// second or third quadrant
		else if ((p2.getY() < p1.getY() && p2.getX() < p1.getX())
				|| (p2.getY() > p1.getY() && p2.getX() < p1.getX()))
			return ang + 180.0f;
		// fourth quadrant
		else
			return ang + 360.0f;
	}
}
