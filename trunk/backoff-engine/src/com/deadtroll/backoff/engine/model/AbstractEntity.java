package com.deadtroll.backoff.engine.model;

public abstract class AbstractEntity implements IEntity {
	protected boolean alive;

	public AbstractEntity() {
		this.alive = true;
	}
	
	public boolean isAlive() {
		return alive;
	}

	public void setAlive(boolean alive) {
		this.alive = alive;
	}
}
