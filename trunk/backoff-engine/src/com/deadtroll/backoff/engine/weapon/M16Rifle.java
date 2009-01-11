package com.deadtroll.backoff.engine.weapon;

public class M16Rifle extends Weapon {
	public M16Rifle() {
		setDamage(2);
		setMagazineSize(20);
		setMaxAmmo(150);		
		setFireRate(15);
		setRealoadTime(3);
		setName("M16 Rifle");
		setAmmo(0);
	}

}
