package com.deadtroll.backoff.model.enemy;

import org.newdawn.slick.geom.Vector2f;

import com.deadtroll.backoff.engine.ai.fsm.State;
import com.deadtroll.backoff.engine.manager.EntityManager;
import com.deadtroll.backoff.engine.model.player.IPlayer;
import com.deadtroll.backoff.engine.resource.ResourceLoader;
import com.deadtroll.backoff.engine.resource.scripting.LuaScript;
import com.deadtroll.backoff.scene.GameScene;

public class ZombieSearchingState extends State {
	private StateMachineZombie zombie;
	private LuaScript luaScript;	

	public ZombieSearchingState(StateMachineZombie zombie) {
		this.zombie = zombie;
		this.zombie.setDestination(randomDestination());
		this.luaScript = ResourceLoader.getInstance().getResource("zombieSearchingState");
	}
	
	private Vector2f randomDestination() {
		if (this.zombie.getDestination() == null || Math.random() < 0.01) {
			return new Vector2f((float)Math.random()*GameScene.WORLD_WIDTH, (float)Math.random()*GameScene.WORLD_HEIGHT);
		}
		return this.zombie.getDestination();
	}

	@Override
	public void act() {
		this.luaScript.doCall("act", this.zombie);
	}

	@Override
	public State getNextState() {
		IPlayer player = EntityManager.getInstance().getPlayer();
		float playerDistance = player.getCenter().distance(this.zombie.getCenter());
		float destinationDistance = this.zombie.getCenter().distance(this.zombie.getDestination());
		
		if (playerDistance > 100.0) {
			if (destinationDistance < 20.0f)
				return new ZombieDestinationReachedState(this.zombie);
			else
				return this;
		} else {
			return new ZombieChasingState(this.zombie);
		}
	}
}
