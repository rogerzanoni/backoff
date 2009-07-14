package com.deadtroll.backoff.engine.model;

import org.newdawn.slick.geom.Vector2f;

public abstract class AbstractMoveableGameObject extends AbstractGameObject implements IMoveable {

	protected Vector2f speed;
	protected Vector2f angle;

	public Vector2f getAngle() {
		return angle;
	}

	public void setAngle(Vector2f angle) {
		this.angle = angle;
	}

	public Vector2f getSpeed() {
		return speed;
	}

	public void setSpeed(Vector2f speed) {
		this.speed = speed;
	}
	
}
