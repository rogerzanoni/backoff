package com.deadtroll.backoff.engine.model.player;

import com.deadtroll.backoff.engine.model.IGameObject;
import com.deadtroll.backoff.engine.model.weapon.Weapon;

public interface IPlayer extends IGameObject {
	public void setActiveWeapon(Weapon activeWeapon);
	public void addWeapon(Weapon weapon);
	public void removeWeapon(Weapon weapon);
	public void setTotalScore(long totalScore);
	public void addScorePoints(int scorePoints);
	public void removeScorePoints(int scorePoints);
	public Weapon getActiveWeapon();
	public long getTotalScore();
}
