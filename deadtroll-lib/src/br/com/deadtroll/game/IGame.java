package br.com.deadtroll.game;

import java.util.Map;

import org.newdawn.slick.GameContainer;

import br.com.deadtroll.scene.IScene;

public interface IGame {

	public Map<String, IScene> getScenes();

	public void setScenes(Map<String, IScene> scenes);

	public IScene getActiveScene();

	public void setActiveScene(String activeScene);

	public GameContainer getContainer();

	public void setContainer(GameContainer container);
	
}
