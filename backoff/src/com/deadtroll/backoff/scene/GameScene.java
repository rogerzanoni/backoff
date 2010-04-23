package com.deadtroll.backoff.scene;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Vector2f;

import br.com.deadtroll.scene.AbstractScene;

import com.deadtroll.backoff.engine.ai.fsm.StateMachine;
import com.deadtroll.backoff.engine.event.AbstractEvent;
import com.deadtroll.backoff.engine.event.EventQueue;
import com.deadtroll.backoff.engine.event.game.GOCollisionEvent;
import com.deadtroll.backoff.engine.event.game.GameEvent;
import com.deadtroll.backoff.engine.event.game.GameEventType;
import com.deadtroll.backoff.engine.event.game.MapCollisionEvent;
import com.deadtroll.backoff.engine.event.handler.SoundEventHandler;
import com.deadtroll.backoff.engine.event.sound.SoundEvent;
import com.deadtroll.backoff.engine.manager.EntityManager;
import com.deadtroll.backoff.engine.manager.EventQueueManager;
import com.deadtroll.backoff.engine.manager.KeyStateManager;
import com.deadtroll.backoff.engine.manager.MapManager;
import com.deadtroll.backoff.engine.manager.ScriptManager;
import com.deadtroll.backoff.engine.manager.SoundManager;
import com.deadtroll.backoff.engine.map.Map;
import com.deadtroll.backoff.engine.map.MapIOUtil;
import com.deadtroll.backoff.engine.model.player.IPlayer;
import com.deadtroll.backoff.engine.renderer.Renderer;
import com.deadtroll.backoff.engine.viewport.ViewPort;
import com.deadtroll.backoff.event.handler.GameEventHandler;
import com.deadtroll.backoff.model.enemy.StateMachineZombie;
import com.deadtroll.backoff.model.enemy.ZombieSearchingState;
import com.deadtroll.backoff.model.player.BradTeeper;

public class GameScene extends AbstractScene {
	//managers
	protected EntityManager entityManager;
	protected SoundManager soundManager;
	protected MapManager mapManager;
	protected EventQueueManager eventQueueManager;
	protected KeyStateManager keyStateManager;

	public static final int GAME_WIDTH = 640;
	public static final int GAME_HEIGHT = 480;
	public static final int WORLD_WIDTH = 800;
	public static final int WORLD_HEIGHT = 600;

	private IPlayer player;

	private boolean gameOver;
	private boolean victory;
	
	private Map levelMap;
	
	private Renderer renderer;
	
	public GameScene() {
		this.soundManager = SoundManager.getInstance();
		this.entityManager = EntityManager.getInstance();
		this.mapManager = MapManager.getInstance();
		this.eventQueueManager = EventQueueManager.getInstance();
		this.keyStateManager = KeyStateManager.getInstance();

		EventQueue mainQueue;
		mainQueue = new EventQueue();

		SoundEventHandler soundEventHandler;
		soundEventHandler = new SoundEventHandler();
		mainQueue.addHandler(SoundEvent.class, soundEventHandler);

		GameEventHandler gameEventHandler = new GameEventHandler();
		mainQueue.addHandler(GameEvent.class, gameEventHandler);
		mainQueue.addHandler(GOCollisionEvent.class, gameEventHandler);
		mainQueue.addHandler(MapCollisionEvent.class, gameEventHandler);

		this.eventQueueManager.addQueue(mainQueue);
		
		loadScripts();
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

			for (int i=0; i<10; i++) {
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

	private void loadScripts() {
		ScriptManager.getInstance().loadScript("fastzombie", "res/scripts/fastzombie.lua");
		ScriptManager.getInstance().loadScript("slowzombie", "res/scripts/slowzombie.lua");
		
		ScriptManager.getInstance().loadScript("zombieChasingState", "res/scripts/zombie_chasingstate.lua");
		ScriptManager.getInstance().loadScript("zombieSearchingState", "res/scripts/zombie_searchingstate.lua");
	}


	public void keyPressed(int key, char c) {
		this.updateKeyStatus(key, true);
	}

	public void keyReleased(int key, char c) {
		this.updateKeyStatus(key, false);
	}

	public void update(int delta) {
		if (!this.gameOver && !this.victory) {
			this.eventQueueManager.postEvents(generateGameEvents());
			this.eventQueueManager.update(delta);
			this.entityManager.update(delta);

			if (this.player.getEnergy()<=0) {
				this.gameOver=true;
			}	
			if (this.entityManager.getGameObjects().size() == 1 && player.isAlive()) {
				this.victory = true;
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
		} else {
			this.renderer.render(g);
			g.drawString("Energy: "+this.player.getEnergy(), 10, 22);
			g.drawString("Score: "+this.player.getTotalScore(), 10, 34);
			g.drawString("Magazine Ammo: "+this.player.getActiveWeapon().getMagazineAmmo(), 10, 46);
			g.drawString("Ammo: "+this.player.getActiveWeapon().getAmmo(), 10, 58);
			g.drawString("ViewPort: " + this.mapManager.getViewPort(), 10, 70);
			g.drawString("PlayerPos: " + this.player.getCenter()+" (rot: "+this.player.getRotation()+")", 10, 82);
			g.drawString("Map: w: " + WORLD_WIDTH  + " h: " + WORLD_HEIGHT , 10, 94);
			g.drawString("GameObjects: " + this.entityManager.getGameObjects().size(), 10, 106);
		}
	}

	private void updateKeyStatus(int key, boolean pressed) {
		if (this.gameOver || this.victory) {
			this.gameOver=false;
			this.victory=false;
			try {
				this.init();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			this.keyStateManager.updateState(key, pressed);
		}
	}
	
	private List<AbstractEvent> generateGameEvents() {
		List<AbstractEvent> gameEvents = new ArrayList<AbstractEvent>();
		
		if (this.keyStateManager.isFirePressed()) {
			GameEvent gameEvent = new GameEvent(GameEventType.PLAYER_FIRE);
			gameEvents.add(gameEvent);
		}
		
		gameEvents.addAll(this.entityManager.generateGOCollisions());
		gameEvents.addAll(this.entityManager.generateMapCollisions());
		
		return gameEvents;
	}
}
