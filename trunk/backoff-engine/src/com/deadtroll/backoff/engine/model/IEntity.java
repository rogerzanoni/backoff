package com.deadtroll.backoff.engine.model;

public interface IEntity {
	public long getId();
	public void setId(long id);
	
	public String getName();
	public void setName(String name);
	
	public void setAlive(boolean alive);
	public boolean isAlive();
	
	public void update(int delta);
	
	public void initializeEntity();
	public void finalizeEntity();
}
