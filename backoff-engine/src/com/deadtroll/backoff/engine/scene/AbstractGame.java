package com.deadtroll.backoff.engine.scene;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import com.deadtroll.backoff.engine.event.AbstractEvent;
import com.deadtroll.backoff.engine.event.EventQueue;
import com.deadtroll.backoff.engine.event.input.KeyEventType;
import com.deadtroll.backoff.engine.event.input.KeyInputEvent;
import com.deadtroll.backoff.engine.game.IGame;
import com.deadtroll.backoff.engine.manager.EntityManager;
import com.deadtroll.backoff.engine.manager.KeyStateManager;

public abstract class AbstractGame extends BasicGame implements IGame {
	private Map<String, IScene> scenes;
	private IScene activeScene;
	private GameContainer container;
	private EventQueue eventQueue;
	private KeyStateManager keyStateManager;
	protected EntityManager entityManager;
	
	public AbstractGame(String gameName) {
		super(gameName);
		this.eventQueue = EventQueue.getInstance();
		this.keyStateManager = KeyStateManager.getInstance();
		this.entityManager = EntityManager.getInstance();
	}
	
	public abstract void gameInit() throws SlickException;
	
	@Override
	public void update(GameContainer container, int delta) throws SlickException {
		if (this.activeScene!=null)
		{
			generateGameEvents();
			//TODO: zanoni: criar método para atualização de entidades e processamento de eventos somente para a cena atual
			this.eventQueue.update(delta);
			this.entityManager.update(delta);
			
			this.activeScene.update(delta);
		}
	}

	public void render(GameContainer container, Graphics g) throws SlickException {
		if (this.activeScene!=null)
			this.activeScene.render(g);
	}

	@Override
	public void keyPressed(int key, char c) {
		this.keyStateManager.updateState(key, true);
		KeyInputEvent e = new KeyInputEvent(key, c, KeyEventType.KEY_PRESS); 
		this.eventQueue.enqueueEvent(e);
	}

	@Override
	public void keyReleased(int key, char c) {
		this.keyStateManager.updateState(key, false);
		KeyInputEvent e = new KeyInputEvent(key, c, KeyEventType.KEY_RELEASE); 
		this.eventQueue.enqueueEvent(e);
	}
	
	public IScene getActiveScene() {
		return this.activeScene;
	}

	public Map<String, IScene> getScenes() {
		return this.scenes;
	}

	public void setActiveScene(String activeScene, boolean stopCurrentScene) throws SlickException {
		if (this.activeScene!=null) {
			if (stopCurrentScene) {
				this.activeScene.stop();
			} else {
				this.activeScene.pause();
			}
		}
		this.activeScene = this.scenes.get(activeScene);
		if (this.activeScene!=null) {
			this.activeScene.resume();
		}
	}

	public void setScenes(Map<String, IScene> scenes) {
		this.scenes = scenes;
	}

	@Override
	public void init(GameContainer container) throws SlickException {
		this.container = container;
		this.scenes = new HashMap<String, IScene>();
		this.gameInit();
	}

	public GameContainer getContainer() {
		return container;
	}

	public void setContainer(GameContainer container) {
		this.container = container;
	}
	
	private void generateGameEvents() {
		List<AbstractEvent> gameEvents = new ArrayList<AbstractEvent>();		
		gameEvents.addAll(this.entityManager.generateGOCollisions());
		gameEvents.addAll(this.entityManager.generateMapCollisions());
		for (AbstractEvent event: gameEvents) {
			this.eventQueue.enqueueEvent(event);
		}
	}

}
