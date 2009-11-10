package com.deadtroll.backoff.engine.player;

import java.util.ArrayList;

import org.newdawn.slick.Image;

import com.deadtroll.backoff.engine.model.AbstractGameObject;
import com.deadtroll.backoff.engine.weapon.Weapon;

public abstract class GenericPlayer extends AbstractGameObject implements IPlayer {
	protected int energy;
	protected int damage;
	protected long totalScore;
	protected String name;
	protected Weapon activeWeapon;
	protected ArrayList<Weapon> weapons;
	
	public GenericPlayer() {
		this.weapons = new ArrayList<Weapon>();
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void addDamage(int damage) {
		this.damage += damage;
		setEnergy(getEnergy() - damage);
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}

	public void setEnergy(int energy) {
		this.energy = energy;
	}

	public int getDamage() {
		return damage;
	}

	public int getEnergy() {
		return energy;
	}
	
	public Image getCurrentSprite() {
		int lineTotal = this.spriteSheet.getHorizontalCount();
		return this.spriteSheet.getSprite((int)(Math.abs(this.position.x) + Math.abs(this.position.y)) % lineTotal,	0);
	}

	public void addWeapon(Weapon weapon) {
		this.weapons.add(weapon);
		if (this.activeWeapon == null)
			setActiveWeapon(weapon);
	}

	public Weapon getActiveWeapon() {
		return activeWeapon;
	}

	public void removeWeapon(Weapon weapon) {
		this.weapons.remove(weapon);
		
	}

	public void setActiveWeapon(Weapon activeWeapon) {
		this.activeWeapon = activeWeapon;
	}

	public void addScorePoints(int scorePoints) {
		this.totalScore -= scorePoints;
	}

	public void removeScorePoints(int scorePoints) {
		this.totalScore -= scorePoints;
	}

	public void setTotalScore(long totalScore) {
		this.totalScore = totalScore;
	}

	public long getTotalScore() {
		return totalScore;
	}

	public void initializeGO() {
		addSoundEvents();
	}
	
	public void finalizeGO() {
		removeSoundEvents();
	}
}
