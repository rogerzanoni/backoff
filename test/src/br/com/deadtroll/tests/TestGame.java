package br.com.deadtroll.tests;

import org.newdawn.slick.SlickException;

import br.com.deadtroll.game.AbstractGame;
import br.com.deadtroll.tests.menu.MenuScene;

public class TestGame extends AbstractGame {

	public static final String SCENE_INTRO = "INTRO";
	public static final String SCENE_MAIN_MENU = "MAINMENU";
	public static final String SCENE_GAME = "GAME";
	
	public TestGame() {
		super("Test Game");
	}

	@Override
	public void gameInit() throws SlickException {
		IntroScene introScene = new IntroScene();
		introScene.setGame(this);
		
		MenuScene menuScene = new MenuScene();
		menuScene.setGame(this);
		
		GameScene gameScene = new GameScene();
		gameScene.setGame(this);

		this.getScenes().put(TestGame.SCENE_INTRO, introScene);
		this.getScenes().put(TestGame.SCENE_MAIN_MENU, menuScene);
		this.getScenes().put(TestGame.SCENE_GAME, gameScene);
		
		this.setActiveScene(TestGame.SCENE_INTRO, false);
	}

}
