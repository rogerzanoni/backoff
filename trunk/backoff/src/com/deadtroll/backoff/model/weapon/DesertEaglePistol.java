package com.deadtroll.backoff.model.weapon;

import com.deadtroll.backoff.engine.model.weapon.Weapon;

public class DesertEaglePistol extends Weapon {
	public DesertEaglePistol() {
		setDamage(4);
		setMagazineSize(10);
		setMaxAmmo(30);		
		setFireRate(1.5);
		setRealoadTime(3);
		setName("Desert Eagle");
		setAmmo(0);
	}

}
