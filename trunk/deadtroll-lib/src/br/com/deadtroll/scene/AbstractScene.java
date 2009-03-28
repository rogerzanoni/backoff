package br.com.deadtroll.scene;

import org.newdawn.slick.GameContainer;

public abstract class AbstractScene implements IScene {

	private GameContainer gameContainer;
	
	public GameContainer getGameContainer() {
		return this.gameContainer;
	}

	public void setGameContainer(GameContainer container) {
		this.gameContainer = container;
	}

	
}
