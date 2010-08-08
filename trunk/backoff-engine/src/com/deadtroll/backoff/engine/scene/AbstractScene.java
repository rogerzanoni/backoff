package com.deadtroll.backoff.engine.scene;

import org.newdawn.slick.SlickException;

import com.deadtroll.backoff.engine.event.EventQueue;
import com.deadtroll.backoff.engine.game.IGame;
import com.deadtroll.backoff.engine.manager.EntityManager;
import com.deadtroll.backoff.engine.manager.KeyStateManager;
import com.deadtroll.backoff.engine.manager.MapManager;
import com.deadtroll.backoff.engine.manager.SoundManager;
import com.deadtroll.backoff.engine.resource.ResourceLoader;

public abstract class AbstractScene implements IScene {

	private IGame game;
	private boolean initialized;
	private boolean paused;

	protected EntityManager entityManager;
	protected SoundManager soundManager;
	protected MapManager mapManager;
	protected EventQueue eventQueue;
	protected KeyStateManager keyStateManager;
	protected ResourceLoader resourceLoader;
	
	public AbstractScene() {
		this.soundManager = SoundManager.getInstance();
		this.entityManager = EntityManager.getInstance();
		this.mapManager = MapManager.getInstance();
		this.eventQueue = EventQueue.getInstance();
		this.keyStateManager = KeyStateManager.getInstance();
		this.resourceLoader = ResourceLoader.getInstance();
		
		loadResources();
		try {
			init();
			setInitialized(true);
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
	
	public void pause() {
		this.setPaused(true);
	}

	public void resume() throws SlickException {
		if (!this.isInitialized()) {
			this.init();
			this.setInitialized(true);
		}
		this.setPaused(false);
	}

	public void stop() {
		this.initialized = false;
		this.paused = false;
	}

	public boolean isInitialized() {
		return initialized;
	}
	
	public void setInitialized(boolean initialized) {
		this.initialized = initialized;
	}
	
	public IGame getGame() {
		return this.game;
	}
	
	public void setGame(IGame game) {
		this.game = game;
	}

	public boolean isPaused() {
		return this.paused;
	}

	public void setPaused(boolean paused) {
		this.paused = paused;
	}
}
