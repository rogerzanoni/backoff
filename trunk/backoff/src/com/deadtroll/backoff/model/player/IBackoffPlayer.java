package com.deadtroll.backoff.model.player;

import com.deadtroll.backoff.engine.model.player.IPlayer;
import com.deadtroll.backoff.model.weapon.Weapon;

public interface IBackoffPlayer extends IPlayer {
	public static final int FIRE_ACTION = 1;
	
	public void setActiveWeapon(Weapon activeWeapon);
	public void addWeapon(Weapon weapon);
	public void removeWeapon(Weapon weapon);
	public void setTotalScore(long totalScore);
	public void addScorePoints(int scorePoints);
	public void removeScorePoints(int scorePoints);
	public Weapon getActiveWeapon();
	public long getTotalScore();
}
