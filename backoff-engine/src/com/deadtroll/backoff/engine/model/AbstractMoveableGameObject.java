package com.deadtroll.backoff.engine.model;

import org.newdawn.slick.geom.Vector2f;

public abstract class AbstractMoveableGameObject extends AbstractGameObject implements IMoveable {

	protected int speed;
	protected Vector2f angle;

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public int getSpeed() {		
		return speed;
	}

	public Vector2f getAngle() {
		return angle;
	}

	public void setAngle(Vector2f angle) {
		this.angle = angle;
	}
	
}
