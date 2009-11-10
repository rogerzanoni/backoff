package com.deadtroll.backoff.engine.enemy;

import org.newdawn.slick.Image;

import com.deadtroll.backoff.engine.model.AbstractGameObject;
import com.deadtroll.backoff.engine.model.TransientStatus;

public abstract class GenericEnemy extends AbstractGameObject implements IEnemy {
	private int energy;
	private int damage;
	private int score;
	private String name;

	public GenericEnemy(String enemyName) {
		this.name = enemyName;
	}

	public int getEnergy() {
		return energy;
	}

	public void setEnergy(int energy) {
		this.energy = energy;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public Image getCurrentSprite() {
		int lineTotal = this.spriteSheet.getHorizontalCount();
		return this.spriteSheet.getSprite((int)(Math.abs(this.position.x) + Math.abs(this.position.y)) % lineTotal,	0);	}

	public int getDamage() {
		return damage;
	}

	public void addDamage(int damage) {
		this.energy -= damage;
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}

	public String getName() {
		return name;
	}
	
	public void initializeGO() {
		addSoundEvents();
	}
	
	public void finalizeGO() {
	}
}
