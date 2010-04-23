package com.deadtroll.backoff.engine.model.enemy;

import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Vector2f;

import com.deadtroll.backoff.engine.model.AbstractGameObject;

public abstract class GenericEnemy extends AbstractGameObject implements IEnemy {
	private Vector2f destination;

	public Image getCurrentSprite() {
		int lineTotal = this.spriteSheet.getHorizontalCount();
		return this.spriteSheet.getSprite((int)(Math.abs(this.position.x) + Math.abs(this.position.y)) % lineTotal,	0);
	}
	
	public void finalizeEntity() {
	}

	public void initializeEntity() {
		addSoundEvents();
	}

	public void update(int delta) {
	}

	public Vector2f getDestination() {
		return destination;
	}

	public void setDestination(Vector2f destination) {
		this.destination = destination;
	}
}
