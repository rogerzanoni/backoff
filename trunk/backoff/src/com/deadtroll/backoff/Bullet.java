package com.deadtroll.backoff;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

import com.deadtroll.backoff.engine.bullet.IBullet;
import com.deadtroll.backoff.engine.model.AbstractGameObject;
import com.deadtroll.backoff.engine.model.TransientStatus;

public class Bullet extends AbstractGameObject implements IBullet {
	private TransientStatus status;

	public Bullet() {
		try {
			this.setSpriteSheet(new SpriteSheet("res/sprites/bullet.png", 5, 5));
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

	public Image getCurrentSprite() {
		return this.spriteSheet.getSprite(0, 0);
	}

	public void setStatus(TransientStatus status) {
		this.status = status;		
	}

	public TransientStatus getStatus() {
		return status;
	}

	public void finalizeGO() {
	}

	public void initializeGO() {
	}

	public void addDamage(int damage) {
	}

	public int getDamage() {
		return 0;
	}

	public int getEnergy() {
		return 0;
	}

	public int getScore() {
		return 0;
	}

	public void setDamage(int damage) {
	}

	public void setEnergy(int energy) {		
	}

	public void setScore(int score) {		
	}

	public void addSoundEvents() {
	}

	public void removeSoundEvents() {
	}
}
