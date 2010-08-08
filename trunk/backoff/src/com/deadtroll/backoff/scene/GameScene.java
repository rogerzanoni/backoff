package com.deadtroll.backoff.scene;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Vector2f;

import com.deadtroll.backoff.engine.ai.fsm.StateMachine;
import com.deadtroll.backoff.engine.event.game.GOCollisionEvent;
import com.deadtroll.backoff.engine.event.game.GameEvent;
import com.deadtroll.backoff.engine.event.game.MapCollisionEvent;
import com.deadtroll.backoff.engine.event.game.PlayerActionEvent;
import com.deadtroll.backoff.engine.event.handler.SoundEventHandler;
import com.deadtroll.backoff.engine.event.input.KeyInputEvent;
import com.deadtroll.backoff.engine.event.sound.SoundEvent;
import com.deadtroll.backoff.engine.map.Map;
import com.deadtroll.backoff.engine.map.MapIOUtil;
import com.deadtroll.backoff.engine.renderer.Renderer;
import com.deadtroll.backoff.engine.resource.scripting.LuaScript;
import com.deadtroll.backoff.engine.scene.AbstractScene;
import com.deadtroll.backoff.engine.viewport.ViewPort;
import com.deadtroll.backoff.event.handler.BackoffEventHandler;
import com.deadtroll.backoff.event.handler.BackoffKeyEventHandler;
import com.deadtroll.backoff.model.enemy.StateMachineZombie;
import com.deadtroll.backoff.model.enemy.ZombieSearchingState;
import com.deadtroll.backoff.model.player.BradTeeper;
import com.deadtroll.backoff.model.player.IBackoffPlayer;

public class GameScene extends AbstractScene {
	public static final int GAME_WIDTH = 640;
	public static final int GAME_HEIGHT = 480;
	public static final int WORLD_WIDTH = 800;
	public static final int WORLD_HEIGHT = 600;

	private IBackoffPlayer player;

	private boolean gameOver;
	private boolean victory;
	
	private Map levelMap;
	
	private Renderer renderer;
	
	public GameScene() {
		SoundEventHandler soundEventHandler;
		soundEventHandler = new SoundEventHandler();
		this.eventQueue.addHandler(SoundEvent.class, soundEventHandler);

		BackoffEventHandler gameEventHandler = new BackoffEventHandler();
		this.eventQueue.addHandler(GameEvent.class, gameEventHandler);
		this.eventQueue.addHandler(GOCollisionEvent.class, gameEventHandler);
		this.eventQueue.addHandler(MapCollisionEvent.class, gameEventHandler);
		this.eventQueue.addHandler(PlayerActionEvent.class, gameEventHandler);
		
		BackoffKeyEventHandler keyInput = new BackoffKeyEventHandler();
		this.eventQueue.addHandler(KeyInputEvent.class, keyInput);
	}
	

	public void init() throws SlickException {
		try {
			this.renderer = new Renderer();
			this.levelMap = MapIOUtil.loadDTMMap("res/level01.dtm");
			
			this.player = new BradTeeper();
			this.player.setSpriteSheet(new SpriteSheet("res/sprites/brad.png",29,18));
			this.player.setEnergy(100);
			this.player.setPosition(new Vector2f(1,1));
			this.player.setLayer(1);
			this.entityManager.addEntity(this.player);
			this.entityManager.setPlayer(this.player);
			
			this.mapManager.setViewPort(new ViewPort(GAME_WIDTH, GAME_HEIGHT, new Vector2f(0,0), WORLD_WIDTH, WORLD_HEIGHT));
			this.entityManager.addEntity(this.mapManager.getViewPort());
			this.mapManager.setMap(this.levelMap);

			for (int i=0; i<5; i++) {
				double rand = Math.random();
				StateMachineZombie enemy=null;
				if (rand < 0.5)
					enemy = new StateMachineZombie("fastzombie");
				else
					enemy = new StateMachineZombie("slowzombie");
				
				enemy.setBrain(new StateMachine(new ZombieSearchingState(enemy)));
				enemy.setPosition(new Vector2f((float)(Math.random()*GAME_WIDTH),(float)(Math.random()*GAME_HEIGHT)));
				enemy.setLayer(this.player.getLayer());
				
				enemy.call("create");
				
				this.entityManager.addEntity(enemy);
			}
		} catch (Exception e){
			throw new SlickException(e.getMessage(),e);
		}
	}

	public void update(int delta) {
		if (!this.gameOver && !this.victory && isInitialized()) {
			if (this.keyStateManager.isKeyPressed(57)) {
				PlayerActionEvent actionEvent = new PlayerActionEvent(IBackoffPlayer.FIRE_ACTION);
				this.eventQueue.enqueueEvent(actionEvent);
			}
			
			if (this.player.getEnergy()<=0) {
				this.gameOver=true;
			}
			if (this.entityManager.getGameObjects().size() == 1 && player.isAlive()) {
				this.victory = true;
			}
		} else {
			if ((this.gameOver || this.victory) && this.keyStateManager.anyKeyPressed()) {
				this.gameOver=false;
                this.victory=false;
                try {
                	this.init();
                } catch (Exception e) {
                    e.printStackTrace();
                }
			}
		}
	}

	public void render(Graphics g) {
		if (this.gameOver) {
			this.entityManager.clear();
			g.setBackground(new Color(0,0,0));
			String gameOverMessage = "GAME OVER!\n Press any key to start again.";
			int messageWidth = g.getFont().getWidth(gameOverMessage);
			int messageHeight = g.getFont().getHeight(gameOverMessage);
			g.drawString(gameOverMessage, (GameScene.GAME_WIDTH/2)-(messageWidth/2), (GameScene.GAME_HEIGHT/2)-(messageHeight/2));
		} else if (this.victory) {
			this.entityManager.clear();
			g.setBackground(new Color(0,0,0));
			String gameOverMessage = "WELL DONE!\n Press any key to start again.";
			int messageWidth = g.getFont().getWidth(gameOverMessage);
			int messageHeight = g.getFont().getHeight(gameOverMessage);
			g.drawString(gameOverMessage, (GameScene.GAME_WIDTH/2)-(messageWidth/2), (GameScene.GAME_HEIGHT/2)-(messageHeight/2));
		} else if (isInitialized()) {
			this.renderer.render(g);
		}
	}

	public void loadResources() {
		this.resourceLoader.loadResource(LuaScript.class, "fastzombie", "res/scripts/fastzombie.lua");
		this.resourceLoader.loadResource(LuaScript.class, "slowzombie", "res/scripts/slowzombie.lua");
		
		this.resourceLoader.loadResource(LuaScript.class, "zombieChasingState", "res/scripts/zombie_chasingstate.lua");
		this.resourceLoader.loadResource(LuaScript.class, "zombieSearchingState", "res/scripts/zombie_searchingstate.lua");
	}


	public void unloadResources() {
		this.resourceLoader.unloadResource("fastzombie");
		this.resourceLoader.unloadResource("slowzombie");
		this.resourceLoader.unloadResource("zombieChasingState");
		this.resourceLoader.unloadResource("zombieSearchingState");
	}
}
