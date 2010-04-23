package com.deadtroll.backoff.engine.event.game;

import org.newdawn.slick.geom.Rectangle;

import com.deadtroll.backoff.engine.model.IGameObject;

public class MapCollisionEvent extends GameEvent {
	private IGameObject collider;
	private Rectangle mapRect;
	
	public MapCollisionEvent(IGameObject collider, Rectangle mapRect) {
		super(GameEventType.MAP_COLLISION);
		this.collider = collider;
		this.mapRect = mapRect;
	}
	
	public IGameObject getCollider() {
		return collider;
	}
	public void setCollider(IGameObject collider) {
		this.collider = collider;
	}
	public Rectangle getMapRect() {
		return mapRect;
	}
	public void setMapRect(Rectangle mapRect) {
		this.mapRect = mapRect;
	}
}
