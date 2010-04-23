package com.deadtroll.backoff;

import org.newdawn.slick.SlickException;

import br.com.deadtroll.game.AbstractGame;

import com.deadtroll.backoff.scene.GameScene;

public class BackOffGame extends AbstractGame {

	public static final String SCENE_INTRO = "INTRO";
	public static final String SCENE_MAIN_MENU = "MAINMENU";
	public static final String SCENE_GAME = "GAME";
	
	public BackOffGame() {
		super("Backoff 0.5");
	}

	@Override
	public void gameInit() throws SlickException {
		GameScene gameScene = new GameScene();
		gameScene.setGame(this);

		this.getScenes().put(BackOffGame.SCENE_GAME, gameScene);
		
		this.setActiveScene(BackOffGame.SCENE_GAME, false);
	}
}
