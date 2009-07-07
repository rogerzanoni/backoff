package br.com.deadtroll.game;

import java.util.HashMap;
import java.util.Map;

import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import br.com.deadtroll.scene.IScene;

public abstract class AbstractGame extends BasicGame implements IGame {

	private Map<String, IScene> scenes;
	private IScene activeScene;
	private GameContainer container;
	
	public AbstractGame(String gameName) {
		super(gameName);
	}
	
	public abstract void gameInit() throws SlickException;
	
	@Override
	public void update(GameContainer container, int delta) throws SlickException {
		if (this.activeScene!=null)
			this.activeScene.update(delta);
	}

	@Override
	public void render(GameContainer container, Graphics g) throws SlickException {
		if (this.activeScene!=null)
			this.activeScene.render(g);
	}

	@Override
	public void keyPressed(int key, char c) {
		if (this.activeScene!=null)
			this.activeScene.keyPressed(key, c);
	}

	@Override
	public void keyReleased(int key, char c) {
		if (this.activeScene!=null)
			this.activeScene.keyReleased(key, c);
	}
	
	@Override
	public IScene getActiveScene() {
		return this.activeScene;
	}

	@Override
	public Map<String, IScene> getScenes() {
		return this.scenes;
	}

	@Override
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

	@Override
	public void setScenes(Map<String, IScene> scenes) {
		this.scenes = scenes;
	}

	@Override
	public void init(GameContainer container) throws SlickException {
		this.container = container;
		this.scenes = new HashMap<String, IScene>();
		this.gameInit();
	}

	@Override
	public GameContainer getContainer() {
		return container;
	}

	@Override
	public void setContainer(GameContainer container) {
		this.container = container;
	}

}
