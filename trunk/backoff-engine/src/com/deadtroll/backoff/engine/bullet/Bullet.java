package com.deadtroll.backoff.engine.bullet;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

import com.deadtroll.backoff.engine.model.AbstractMoveableGameObject;
import com.deadtroll.backoff.engine.model.TransientStatus;

public class Bullet extends AbstractMoveableGameObject implements IBullet {
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

}
