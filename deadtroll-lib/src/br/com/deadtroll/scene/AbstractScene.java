package br.com.deadtroll.scene;

import org.newdawn.slick.SlickException;

import br.com.deadtroll.game.IGame;

public abstract class AbstractScene implements IScene {

	private IGame game;
	private boolean initialized;
	private boolean paused;

	@Override
	public void pause() {
		this.setPaused(true);
	}

	@Override
	public void resume() throws SlickException {
		if (!this.isInitialized()) {
			this.init();
			this.setInitialized(true);
		}
		this.setPaused(false);
	}
	
	@Override
	public void stop() {
		this.initialized = false;
		this.paused = false;
	}

	@Override
	public boolean isInitialized() {
		return initialized;
	}
	
	@Override
	public void setInitialized(boolean initialized) {
		this.initialized = initialized;
	}
	
	@Override
	public IGame getGame() {
		return this.game;
	}
	
	@Override
	public void setGame(IGame game) {
		this.game = game;
	}

	@Override
	public boolean isPaused() {
		return this.paused;
	}

	@Override
	public void setPaused(boolean paused) {
		this.paused = paused;
	}

}
