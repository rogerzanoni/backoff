package com.deadtroll.backoff.engine;

import java.util.ArrayList;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Vector2f;

import com.deadtroll.backoff.engine.model.AbstractGameObject;
import com.deadtroll.backoff.engine.viewport.ViewPort;
import com.deadtroll.backoff.engine.weapon.M9Pistol;
import com.deadtroll.backoff.engine.weapon.Weapon;

public class Player extends AbstractGameObject {
	
	public static final int DIRECTION_UP = 0;
	public static final int DIRECTION_RIGHT = 1;
	public static final int DIRECTION_DOWN = 2;
	public static final int DIRECTION_LEFT = 3;

	int energy;
	float speed;
	long totalScore;
	int currentDirection;
	SpriteSheet spriteSheet;
	int currentSpriteX, currentSpriteY;
	Weapon activeWeapon;
	ArrayList<Weapon> weapons;
	int currentLayer;

	public Player() {
		this.weapons = new ArrayList<Weapon>();
		Weapon firstWeapon = new M9Pistol();
		firstWeapon.setMagazineAmmo(firstWeapon.getMagazineSize());
		firstWeapon.setAmmo(firstWeapon.getMagazineSize());
		this.weapons.add(firstWeapon);
		setActiveWeapon(firstWeapon);
	}
	
	public int getEnergy() {
		return energy;
	}

	public void setEnergy(int energy) {
		this.energy = energy;
	}

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

	public long getTotalScore() {
		return totalScore;
	}

	public void setTotalScore(long totalScore) {
		this.totalScore = totalScore;
	}

	public Image getCurrentSprite() {
		int lineTotal = this.spriteSheet.getHorizontalCount();
		return this.spriteSheet.getSprite((int)(Math.abs(this.position.x) + Math.abs(this.position.y)) % lineTotal,
				this.currentDirection);

	}

	public void setSpriteSheet(SpriteSheet sprite) {
		this.spriteSheet = sprite;
	}

	public int getCurrentDirection() {
		return currentDirection;
	}

	public void setCurrentDirection(int currentDirection) {
		this.currentDirection = currentDirection;
	}

	public Weapon getActiveWeapon() {
		return activeWeapon;
	}

	public void setActiveWeapon(Weapon activeWeapon) {
		this.activeWeapon = activeWeapon;
	}

	public int getLayer() {
		return this.currentLayer;
	}

	public Vector2f getPosition() {
		return this.position;
	}

	public void render(Graphics g, ViewPort viewPort) {
		g.drawImage(this.getCurrentSprite(),this.position.x-viewPort.getX(),this.position.y-viewPort.getY());
	}

	public void setPosition(Vector2f position) {
		this.position = position;
	}

	public void setLayer(int layer) {
		this.currentLayer = layer;
	}
}
