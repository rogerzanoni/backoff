package com.deadtroll.backoff.model.bullet;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;

import com.deadtroll.backoff.engine.manager.MapManager;
import com.deadtroll.backoff.engine.model.AbstractGameObject;
import com.deadtroll.backoff.engine.viewport.ViewPort;

public class Bullet extends AbstractGameObject {
	public Bullet() {
		try {
			this.setSpriteSheet(new SpriteSheet("res/sprites/bullet.png", 5, 5));
		} catch (SlickException e) {
			e.printStackTrace();
		}
		setAlive(true);
	}

	public Image getCurrentSprite() {
		return this.spriteSheet.getSprite(0, 0);
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

	public void finalizeEntity() {
	}

	public void initializeEntity() {
	}

	public void update(int delta) {
		ViewPort viewPort = MapManager.getInstance().getViewPort();
		Shape collisionShape = getCollisionShape();
		Rectangle rect = viewPort.getRect();
		if (!rect.contains(collisionShape.getCenterX(), collisionShape.getCenterY())) {
			setAlive(false);
		}
		
		if (isAlive()) {					
			float radiusRotation = (float)((this.rotation/180)*Math.PI);
			Vector2f vector = new Vector2f((float)Math.cos(radiusRotation),(float)Math.sin(radiusRotation));
	        vector.normalise();
	        vector.scale(this.speed);
	        this.position.add(vector);
		}
	}
}
