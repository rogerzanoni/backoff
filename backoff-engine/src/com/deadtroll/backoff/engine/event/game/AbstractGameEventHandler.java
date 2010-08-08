package com.deadtroll.backoff.engine.event.game;

import org.newdawn.slick.geom.Rectangle;

import com.deadtroll.backoff.engine.event.AbstractEvent;
import com.deadtroll.backoff.engine.event.AbstractEventHandler;
import com.deadtroll.backoff.engine.event.EventQueue;
import com.deadtroll.backoff.engine.manager.EntityManager;
import com.deadtroll.backoff.engine.model.IGameObject;

public abstract class AbstractGameEventHandler extends AbstractEventHandler {
	protected EntityManager entityManager;
	protected EventQueue eventQueue;
	
	public AbstractGameEventHandler() {
		this.entityManager = EntityManager.getInstance();
		this.eventQueue = EventQueue.getInstance();
	}
	
	@Override
	public void processEvent(AbstractEvent e) {
		GameEvent gameEvent = (GameEvent)e;

		if (GameEventType.GO_COLLISION.equals(gameEvent.getType())) {
			GOCollisionEvent goCollisionEvent = (GOCollisionEvent)e;
			IGameObject firstCollider = goCollisionEvent.getFirstCollider();
			IGameObject secondCollider = goCollisionEvent.getSecondCollider();
			
			handleCollision(firstCollider, secondCollider);
		} else if (GameEventType.MAP_COLLISION.equals(gameEvent.getType())) {
			MapCollisionEvent mapCollisionEvent = (MapCollisionEvent)e;
			IGameObject collider = mapCollisionEvent.getCollider();
			Rectangle mapBlock = mapCollisionEvent.getMapRect();
			
			handleCollision(collider, mapBlock);
		} else if (GameEventType.PLAYER_ACTION.equals(gameEvent.getType())) {
			handlePlayerAction((PlayerActionEvent)gameEvent);
		}
	}
	
	protected abstract void handleCollision(IGameObject firstCollider, IGameObject secondCollider);
	
	protected abstract void handleCollision(IGameObject firstCollider, Rectangle mapBlock);
	
	protected abstract void handlePlayerAction(PlayerActionEvent event); 

}
