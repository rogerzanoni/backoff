package com.deadtroll.backoff.engine;

import java.util.ArrayList;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Vector2f;

import com.deadtroll.backoff.engine.model.IGameObject;
import com.deadtroll.backoff.engine.weapon.M9Pistol;
import com.deadtroll.backoff.engine.weapon.Weapon;

public class Player implements IGameObject {
	
	public static final int DIRECTION_UP = 0;
	public static final int DIRECTION_RIGHT = 1;
	public static final int DIRECTION_DOWN = 2;
	public static final int DIRECTION_LEFT = 3;

	int energy;
	int speed;
	long totalScore;
	int currentDirection;
	SpriteSheet spriteSheet;
	int currentSpriteX, currentSpriteY;
	Vector2f position;
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

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
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
		return this.spriteSheet.getSprite((int)(this.position.x + this.position.y) % lineTotal,
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

	@Override
	public int getLayer() {
		return this.currentLayer;
	}

	@Override
	public Vector2f getPosition() {
		return this.position;
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(this.getCurrentSprite(),this.position.x,this.position.y);
	}

	@Override
	public void update(long delta) {
		// TODO Auto-generated method stub
	}

	@Override
	public void setPosition(Vector2f position) {
		this.position = position;
	}	
	
}
