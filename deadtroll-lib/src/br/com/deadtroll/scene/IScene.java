package br.com.deadtroll.scene;

import org.newdawn.slick.Graphics;

import br.com.deadtroll.game.IGame;

public interface IScene {

	public void render(Graphics g);
	
	public void update(int delta);
	
	public void init();
	
	public void pause();
	
	public void resume();
	
	public void keyPressed(int key, char c);
	
	public void keyReleased(int key, char c);
	
	// Gets & Sets
	
	public void setGame(IGame game);
	
	public IGame getGame();
	
	public void setInitialized(boolean initialized);
	
	public boolean isInitialized();

	public void setPaused(boolean paused);
	
	public boolean isPaused();
}
