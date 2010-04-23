package com.deadtroll.backoff.engine.manager;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.geom.Rectangle;

import com.deadtroll.backoff.engine.event.AbstractEvent;
import com.deadtroll.backoff.engine.event.game.GOCollisionEvent;
import com.deadtroll.backoff.engine.event.game.MapCollisionEvent;
import com.deadtroll.backoff.engine.helper.CollisionUtil;
import com.deadtroll.backoff.engine.model.IEntity;
import com.deadtroll.backoff.engine.model.IGameObject;
import com.deadtroll.backoff.engine.model.enemy.IEnemy;
import com.deadtroll.backoff.engine.model.player.IPlayer;
import com.deadtroll.backoff.engine.viewport.ViewPort;

/**
 * Game object manager
 */
public class EntityManager {
	private IPlayer player;
	protected List<IEntity> entities = new ArrayList<IEntity>();
	protected List<IGameObject> gameObjects = new ArrayList<IGameObject>();
	protected List<IEnemy> enemies = new ArrayList<IEnemy>();
	
	private EntityManager() {
	}
	private static EntityManager instance;

	public static EntityManager getInstance() {
		if (instance == null)
			instance = new EntityManager();
		return instance;
	}
	
	public IPlayer getPlayer() {
		return player;
	}

	public void setPlayer(IPlayer player) {
		this.player = player;
	}

	public void addEntity(IEntity entity) {
		this.entities.add(entity);
		if (entity instanceof IGameObject) {
			this.gameObjects.add((IGameObject)entity);
		}
		if (entity instanceof IEnemy) {
			this.enemies.add((IEnemy)entity);
		}
		entity.initializeEntity();
	}

	public void removeEntity(IEntity entity) {
		entity.finalizeEntity();
		this.entities.remove(entity);
	}

	public List<IEntity> getEntities() {
		return entities;
	}
	
	public List<IGameObject> getGameObjects() {		
		return this.gameObjects;
	}
	
	public List<IEnemy> getEnemies() {
		return this.enemies;
	}
	
	public void clear() {
		this.entities.clear();
		this.gameObjects.clear();
		this.enemies.clear();
	}
	
	public void update(int delta) {
		IEntity entity = null;
		for (int rIndex=this.entities.size()-1; rIndex>=0; rIndex--) {
			entity = this.entities.get(rIndex);
			
			if (entity.isAlive()) {
				entity.update(delta);
			} else {
				entity.finalizeEntity();
				this.entities.remove(entity);
				this.gameObjects.remove(entity);
				this.enemies.remove(entity);
			}
		}
	}
	
	public List<AbstractEvent> generateGOCollisions() {
		ViewPort viewPort = MapManager.getInstance().getViewPort();
		List<AbstractEvent> collisionEvents = new ArrayList<AbstractEvent>();
		int matrixSize = this.gameObjects.size();
		boolean collisionMatrix[][] = new boolean[matrixSize][matrixSize];
		
		for (int i=0; i<matrixSize; i++) {
			for (int j=0; j<matrixSize; j++) {
				if (collisionMatrix[i][j] || collisionMatrix[j][i]) {
					continue;
				}
				
				IGameObject firstCollider = this.gameObjects.get(i);
				IGameObject secondCollider = this.gameObjects.get(j);
				if (CollisionUtil.checkCollision(viewPort, firstCollider, secondCollider)) {
					collisionMatrix[i][j] = true;
					collisionMatrix[j][i] = true;
					
					GOCollisionEvent event = new GOCollisionEvent(firstCollider, secondCollider);
					collisionEvents.add(event);
				}
			}
		}
		
		return collisionEvents;
	}
	
	public List<AbstractEvent> generateMapCollisions() {
		ViewPort viewPort  = MapManager.getInstance().getViewPort();
		List<AbstractEvent> collisionEvents = new ArrayList<AbstractEvent>();
		
		for (IGameObject collider : this.gameObjects) {
			Rectangle rect = CollisionUtil.checkMapCollision(viewPort, collider);
			if (rect != null) {
				MapCollisionEvent event = new MapCollisionEvent(collider, rect);
				collisionEvents.add(event);
			}
		}
		
		return collisionEvents;
	}
}
