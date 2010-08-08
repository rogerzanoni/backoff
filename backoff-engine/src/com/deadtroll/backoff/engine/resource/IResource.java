package com.deadtroll.backoff.engine.resource;

public interface IResource {
	public void load() throws Exception;
	public void unload();
	
	public boolean isLoaded();
	public void setLoaded(boolean loaded);
}
