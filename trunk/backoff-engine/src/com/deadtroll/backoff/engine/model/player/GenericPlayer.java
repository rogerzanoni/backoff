package com.deadtroll.backoff.engine.model.player;

import java.util.ArrayList;

import org.newdawn.slick.Image;

import com.deadtroll.backoff.engine.model.AbstractGameObject;
import com.deadtroll.backoff.engine.model.weapon.Weapon;

public abstract class GenericPlayer extends AbstractGameObject implements IPlayer {
	protected long totalScore;
	protected Weapon activeWeapon;
	protected ArrayList<Weapon> weapons;
	
	public GenericPlayer() {
		this.weapons = new ArrayList<Weapon>();
	}

	public void addDamage(int damage) {
		this.damage += damage;
		setEnergy(getEnergy() - damage);
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

	public void initializeEntity() {
		addSoundEvents();
	}
	
	public void finalizeEntity() {
		removeSoundEvents();
	}
}
