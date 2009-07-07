package com.deadtroll.backoff.engine.player;

import com.deadtroll.backoff.engine.weapon.Weapon;

public interface IPlayerAcessor {
	public String getName();
	public Weapon getActiveWeapon();
	public long getTotalScore();
}
