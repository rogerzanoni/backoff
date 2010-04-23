package com.deadtroll.backoff.engine.event.game;

import com.deadtroll.backoff.engine.model.IGameObject;

public class GOCollisionEvent extends GameEvent {
	private IGameObject firstCollider;
	private IGameObject secondCollider;
	
	public GOCollisionEvent(IGameObject firstCollider, IGameObject secondCollider) {
		super(GameEventType.GO_COLLISION);
		this.firstCollider = firstCollider;
		this.secondCollider = secondCollider;
	}
	
	public IGameObject getFirstCollider() {
		return firstCollider;
	}
	public void setFirstCollider(IGameObject firstCollider) {
		this.firstCollider = firstCollider;
	}
	public IGameObject getSecondCollider() {
		return secondCollider;
	}
	public void setSecondCollider(IGameObject secondCollider) {
		this.secondCollider = secondCollider;
	}		
}
