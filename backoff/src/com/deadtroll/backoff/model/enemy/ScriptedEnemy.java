package com.deadtroll.backoff.model.enemy;

import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Vector2f;

import com.deadtroll.backoff.engine.model.ScriptedGameObject;

public abstract class ScriptedEnemy extends ScriptedGameObject implements IEnemy {
	
	public ScriptedEnemy(String scriptName) {
		super(scriptName);
	}

	private Vector2f destination;

	public Image getCurrentSprite() {
		int lineTotal = this.spriteSheet.getHorizontalCount();
		return this.spriteSheet.getSprite((int)(Math.abs(this.position.x) + Math.abs(this.position.y)) % lineTotal,	0);
	}
	
	public void initializeEntity() {
		addSoundEvents();
	}
	
	public void finalizeEntity() {
	}

	public Vector2f getDestination() {
		return destination;
	}

	public void setDestination(Vector2f destination) {
		this.destination = destination;
	}
}
