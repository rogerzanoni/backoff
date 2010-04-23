package com.deadtroll.backoff.model.enemy;


import com.deadtroll.backoff.engine.ai.fsm.State;
import com.deadtroll.backoff.engine.manager.EntityManager;
import com.deadtroll.backoff.engine.model.player.IPlayer;

public class ZombieDestinationReachedState extends State {
	private StateMachineZombie zombie;

	public ZombieDestinationReachedState(StateMachineZombie zombie) {
		this.zombie = zombie;
		this.zombie.setDestination(null);
	}

	@Override
	public void act() {		
	}

	@Override
	public State getNextState() {
		IPlayer player = EntityManager.getInstance().getPlayer();
		float playerDistance = player.getCenter().distance(this.zombie.getCenter());
		
		if (playerDistance > 100.0) {
			if (Math.random() < 0.01) 
				return new ZombieSearchingState(this.zombie);
			else
				return this;
		} else {
			return new ZombieChasingState(this.zombie);
		}
	}

}
