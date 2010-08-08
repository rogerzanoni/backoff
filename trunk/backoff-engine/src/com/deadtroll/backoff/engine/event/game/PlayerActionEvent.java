package com.deadtroll.backoff.engine.event.game;

public class PlayerActionEvent extends GameEvent {
	private int action;
	
	public PlayerActionEvent(int action) {
		super(GameEventType.PLAYER_ACTION);
		this.action = action;
	}

	public int getAction() {
		return action;
	}
}
