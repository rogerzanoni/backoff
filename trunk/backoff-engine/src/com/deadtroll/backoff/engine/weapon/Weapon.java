package com.deadtroll.backoff.engine.weapon;

public abstract class Weapon {

	private String name;
	private int damage;
	private int fireRate;
	private int ammo;
	private int magazineSize;
	private int maxAmmo;

	public int getDamage() {
		return damage;
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}

	public int getFireRate() {
		return fireRate;
	}

	public void setFireRate(int fireRate) {
		this.fireRate = fireRate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAmmo() {
		return ammo;
	}

	public void setAmmo(int ammo) {
		this.ammo = ammo;
	}

	public int getMagazineSize() {
		return magazineSize;
	}

	public void setMagazineSize(int magazineSize) {
		this.magazineSize = magazineSize;
	}

	public int getMaxAmmo() {
		return maxAmmo;
	}

	public void setMaxAmmo(int maxAmmo) {
		this.maxAmmo = maxAmmo;
	}

}
