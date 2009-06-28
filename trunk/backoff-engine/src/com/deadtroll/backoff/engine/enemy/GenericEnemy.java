package com.deadtroll.backoff.engine.enemy;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Vector2f;


public class GenericEnemy implements IEnemy {

	private int energy;
	private int speed;
	private byte status; // 0=alive/default, 1=dead
	private int damage;
	private int score;
	private Vector2f position;
	private int layer;

	private SpriteSheet spriteSheet;

	public GenericEnemy(String enemyName) {
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

	@Override
	public int getLayer() {
		return this.layer;
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
	public void setPosition(Vector2f position) {
		this.position = position;
	}

	@Override
	public void setLayer(int layer) {
		this.layer = layer;
	}

}
