package com.deadtroll.backoff.engine.ai.fsm;

import com.deadtroll.backoff.engine.ai.IBrain;

public class StateMachine implements IBrain {
	private State activeState;

	public StateMachine(State initialState) {
		this.activeState = initialState;
	}
	
	public State getActiveState() {
		return activeState;
	}
	
	public void setActiveState(State activeState) {
		this.activeState = activeState;
	}

	public void think() {
		if (this.activeState != null) {
			this.activeState.act();
			this.activeState = this.activeState.getNextState(); 
		}
	}
}
