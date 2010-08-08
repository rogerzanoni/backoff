package com.deadtroll.backoff.engine.resource;

public abstract class AbstractResource implements IResource {
	protected String name;
	protected String file;
	protected boolean loaded;
	
	public AbstractResource(String name, String file) {
		this.name = name;
		this.file = file;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getFile() {
		return file;
	}
	public void setFile(String file) {
		this.file = file;
	}

	public boolean isLoaded() {
		return loaded;
	}

	public void setLoaded(boolean loaded) {
		this.loaded = loaded;
	}
}
