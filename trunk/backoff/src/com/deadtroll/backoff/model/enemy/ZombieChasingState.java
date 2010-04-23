package com.deadtroll.backoff.model.enemy;


import com.deadtroll.backoff.engine.ai.fsm.State;
import com.deadtroll.backoff.engine.manager.ScriptManager;
import com.deadtroll.backoff.engine.scripting.LuaScript;

public class ZombieChasingState extends State {
	private StateMachineZombie zombie;
	private LuaScript luaScript;

	public ZombieChasingState(StateMachineZombie zombie) {
		this.zombie = zombie;
		this.luaScript = ScriptManager.getInstance().getScript("zombieChasingState"); 
	}
	
	@Override
	public void act() {
		this.luaScript.doCall("act", this.zombie);
	}

	@Override
	public State getNextState() {
		return this;
	}
}
