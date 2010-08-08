package com.deadtroll.backoff.engine.scene;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import com.deadtroll.backoff.engine.game.IGame;

public interface IScene {

	public void render(Graphics g);
	
	public void update(int delta);
	
	public void init() throws SlickException;
	
	public void loadResources();
	
	public void unloadResources();
	
	public void pause();
	
	public void resume() throws SlickException;
	
	public void stop();
	
	// Gets & Sets
	public void setGame(IGame game);
	
	public IGame getGame();
	
	public void setInitialized(boolean initialized);
	
	public boolean isInitialized();

	public void setPaused(boolean paused);
	
	public boolean isPaused();
}
