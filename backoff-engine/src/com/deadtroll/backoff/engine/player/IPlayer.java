package com.deadtroll.backoff.engine.player;

import com.deadtroll.backoff.engine.model.IDamageable;
import com.deadtroll.backoff.engine.model.IGameObject;
import com.deadtroll.backoff.engine.model.IMoveable;
import com.deadtroll.backoff.engine.sound.ISoundSource;
import com.deadtroll.backoff.engine.weapon.Weapon;

public interface IPlayer extends IPlayerAcessor, IGameObject, IMoveable, IDamageable, ISoundSource {
	public void setName(String name);
	public void setActiveWeapon(Weapon activeWeapon);
	public void addWeapon(Weapon weapon);
	public void removeWeapon(Weapon weapon);
	public void setTotalScore(long totalScore);
	public void addScorePoints(int scorePoints);
	public void removeScorePoints(int scorePoints);
}
