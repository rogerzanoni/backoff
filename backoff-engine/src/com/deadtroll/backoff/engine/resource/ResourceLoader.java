package com.deadtroll.backoff.engine.resource;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

public class ResourceLoader {
	private static ResourceLoader instance;
	private Map<String, IResource> resourceMap;

	private ResourceLoader() {
		this.resourceMap = new HashMap<String, IResource>();
	}
	
	public static ResourceLoader getInstance() {
		if (instance == null)
			instance = new ResourceLoader();
		return instance;
	}
	
	public <T extends IResource> void loadResource(Class<T> clazz, String resourceName, String file, Object... extraParameters) {
		IResource resource = this.resourceMap.get(resourceName);		
		if (resource == null) {
			Constructor<T> constructor = null;
			try {
				if (extraParameters == null || extraParameters.length == 0) {
					constructor = clazz.getConstructor(new Class [] {String.class, String.class});
					resource = constructor.newInstance(new Object[] {resourceName, file});
				} else {
					Class<?>[] parameterClasses = new Class[extraParameters.length + 2];
					parameterClasses[0] = parameterClasses[1] = String.class;
					int index = 2;
					for (Object parameter : extraParameters) {
						parameterClasses[index] = parameter.getClass();
						index++;
					}
					constructor = clazz.getConstructor(parameterClasses);
					resource = constructor.newInstance(new Object[] {resourceName, file, extraParameters});
				}
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (!resource.isLoaded()) {
			try {
				resource.load();
				resource.setLoaded(true);
				this.resourceMap.put(resourceName, resource);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void unloadResource(String resourceName) {
		IResource resource = this.resourceMap.get(resourceName);
		if (resource != null && resource.isLoaded()) {
			resource.unload();
			resource.setLoaded(false);
			this.resourceMap.remove(resource);
		}
	}
	
	@SuppressWarnings("unchecked")
	public <T extends IResource> T getResource(String resourceName) {
		return (T)this.resourceMap.get(resourceName);
	}
}
