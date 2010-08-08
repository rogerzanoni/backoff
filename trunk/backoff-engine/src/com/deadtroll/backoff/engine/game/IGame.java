package com.deadtroll.backoff.engine.game;

import java.util.Map;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

import com.deadtroll.backoff.engine.scene.IScene;


public interface IGame {

	public Map<String, IScene> getScenes();

	public void setScenes(Map<String, IScene> scenes);

	public IScene getActiveScene();

	public void setActiveScene(String activeScene, boolean stopCurrentScene) throws SlickException;

	public GameContainer getContainer();

	public void setContainer(GameContainer container);
	
}
