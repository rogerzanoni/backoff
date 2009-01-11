package com.deadtroll.backoff.engine.weapon;

public class M9Pistol extends Weapon {
	public M9Pistol() {
		setDamage(2);
		setMagazineSize(15);
		setMaxAmmo(50);		
		setFireRate(3);
		setRealoadTime(2.2);
		setName("M9");
		setAmmo(0);
	}
}
