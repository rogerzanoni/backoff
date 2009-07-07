package com.deadtroll.backoff.engine.model;

public abstract class AbstractMoveableGameObject extends AbstractGameObject implements IMoveable {
	protected int speed;
	protected Heading heading;

	public void setHeading(Heading heading) {
		this.heading = heading;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public Heading getHeading() {
		return heading;
	}

	public int getSpeed() {		
		return speed;
	}
}
