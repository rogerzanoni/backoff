package com.deadtroll.backoff.engine.model.weapon;


public abstract class Weapon {

	private String name;
	private int damage;
	private double fireRate;
	private int ammo;
	private int magazineSize;
	private int magazineAmmo;
	private int maxAmmo;
	private double realoadTime;
	private double precision;

	public int getDamage() {
		return damage;
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}

	public double getFireRate() {
		return fireRate;
	}

	public void setFireRate(double fireRate) {
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

	public double getRealoadTime() {
		return realoadTime;
	}

	public void setRealoadTime(double realoadTime) {
		this.realoadTime = realoadTime;
	}

	public int getMagazineAmmo() {
		return magazineAmmo;
	}

	public void setMagazineAmmo(int magazineAmmo) {
		this.magazineAmmo = magazineAmmo;
	}
	
	public double getPrecision() {
		return precision;
	}

	public void setPrecision(double precision) {
		this.precision = precision;
	}
	
	private boolean canFire() {
		return getMagazineAmmo() > 0;
	}

	private boolean canReload() {
		return getAmmo() > 0 && getMagazineAmmo() < getMagazineSize();
	}
	
	private void reload() {
		if (canReload()) {
			int needed = getMagazineSize() - getMagazineAmmo();
			
			if (needed <= getAmmo()) {
				setMagazineAmmo(needed);
				setAmmo(getAmmo()-getMagazineSize());
			} else {
				setMagazineAmmo(getAmmo());
				setAmmo(0);
			}
		}
	}
	
	public boolean fire() {
		if (!canFire())
			reload();
		
		if (canFire()) {
			setMagazineAmmo(getMagazineAmmo()-1);
			return true;
		}
		
		return false;
	}
}
