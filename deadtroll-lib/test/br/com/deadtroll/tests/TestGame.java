package br.com.deadtroll.tests;

import br.com.deadtroll.game.AbstractGame;

public class TestGame extends AbstractGame {

	public static final String SCENE_INTRO = "INTRO";
	public static final String SCENE_MAIN_MENU = "MAINMENU";
	
	public TestGame() {
		super("Test Game");
	}

	@Override
	public void gameInit() {
		IntroScene introScene = new IntroScene();
		introScene.setGameContainer(this.getContainer());
		this.getScenes().put(TestGame.SCENE_INTRO, introScene);
		this.setActiveScene(introScene);
	}
	
	
}
