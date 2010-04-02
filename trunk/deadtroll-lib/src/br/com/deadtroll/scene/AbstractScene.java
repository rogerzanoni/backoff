package br.com.deadtroll.scene;

import org.newdawn.slick.SlickException;

import br.com.deadtroll.game.IGame;

public abstract class AbstractScene implements IScene {

	private IGame game;
	private boolean initialized;
	private boolean paused;

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
