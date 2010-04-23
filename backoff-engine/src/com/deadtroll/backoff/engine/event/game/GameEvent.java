package com.deadtroll.backoff.engine.event.game;

import com.deadtroll.backoff.engine.event.AbstractEvent;

public class GameEvent extends AbstractEvent {
	private GameEventType type;

	public GameEvent(GameEventType type) {
		this.type = type;
	}
	
	public GameEventType getType() {
		return type;
	}

	public void setType(GameEventType type) {
		this.type = type;
	}
}
