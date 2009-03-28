package br.com.deadtroll.scene;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

public interface IScene {

	public void setGameContainer(GameContainer container);

	public GameContainer getGameContainer();
	
	public void render(Graphics g);
	
	public void update(int delta);
	
	public void init();
	
	public void keyPressed(int key, char c);
	
	public void keyReleased(int key, char c);
	
}
