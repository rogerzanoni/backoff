package com.deadtroll.backoff;

import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;


public class Enemy implements Character {

	private int energy;
	private int x,y;
	private int speed;
	private byte status; // 0=alive/default, 1=dead
	private int damage;
	private int score;
	private SpriteSheet spriteSheet;
	
	public Enemy(String enemyName) {
		
	}
	
	public int getEnergy() {
		return energy;
	}

	public void setEnergy(int energy) {
		this.energy = energy;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public byte getStatus() {
		return status;
	}

	public void setStatus(byte status) {
		this.status = status;
	}

	public int getDamage() {
		return damage;
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public Image getCurrentSprite() {
		return this.spriteSheet.getSprite(0, 0);
	}

	public void setSpriteSheet(SpriteSheet sprite) {
		this.spriteSheet = sprite;
	}

}
