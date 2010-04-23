package com.deadtroll.backoff.engine.ai.fsm;


public abstract class State {
	
	public abstract State getNextState();
	public abstract void act();
}
