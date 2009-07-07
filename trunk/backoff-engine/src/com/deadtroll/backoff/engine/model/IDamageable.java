package com.deadtroll.backoff.engine.model;

public interface IDamageable extends IDamageableAcessor {
	public void setEnergy(int energy);
	public void setDamage(int damage);
	public void addDamage(int damage);
}
