package com.deadtroll.backoff.event.handler;

import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.openal.SoundStore;

import com.deadtroll.backoff.engine.event.game.AbstractGameEventHandler;
import com.deadtroll.backoff.engine.event.game.PlayerActionEvent;
import com.deadtroll.backoff.engine.event.sound.SoundEvent;
import com.deadtroll.backoff.engine.event.sound.SoundEventBehaviour;
import com.deadtroll.backoff.engine.event.sound.SoundEventType;
import com.deadtroll.backoff.engine.model.IGameObject;
import com.deadtroll.backoff.engine.model.player.IPlayer;
import com.deadtroll.backoff.model.bullet.Bullet;
import com.deadtroll.backoff.model.enemy.IEnemy;
import com.deadtroll.backoff.model.player.IBackoffPlayer;
import com.deadtroll.backoff.model.weapon.Weapon;

public class BackoffEventHandler extends AbstractGameEventHandler  {
	private long lastFire;
	
	@Override
	protected void handleCollision(IGameObject firstCollider, IGameObject secondCollider) {
		if ((firstCollider instanceof IPlayer || secondCollider instanceof IPlayer) &&
			(firstCollider instanceof IEnemy || secondCollider instanceof IEnemy)) {
			IPlayer player = null;
			IEnemy enemy = null;
			if (firstCollider instanceof IPlayer) {
				player = (IPlayer)firstCollider;
				enemy = (IEnemy)secondCollider;
			} else {
				player = (IPlayer)secondCollider;
				enemy = (IEnemy)firstCollider;
			}
			handleCollision(player, enemy);
		} else if ((firstCollider instanceof Bullet || secondCollider instanceof Bullet) &&
				   (firstCollider instanceof IEnemy || secondCollider instanceof IEnemy)) {
			Bullet bullet = null;
			IEnemy enemy = null;
			if (firstCollider instanceof Bullet) {
				bullet = (Bullet)firstCollider;
				enemy = (IEnemy)secondCollider;
			} else {
				bullet = (Bullet)secondCollider;
				enemy = (IEnemy)firstCollider;
			}
			handleCollision(bullet, enemy);						
		}
	}
	
	@Override
	protected void handleCollision(IGameObject firstCollider, Rectangle mapBlock) {
		firstCollider.setCollided(true);
		
		if (firstCollider instanceof Bullet) {
			firstCollider.setAlive(false);
		}
	}
	
	@Override
	protected void handlePlayerAction(PlayerActionEvent event) {
		int action = event.getAction();
		if (IBackoffPlayer.FIRE_ACTION == action) {
			handleFire();
		}		
	}
	
	private void handleCollision(IPlayer player, IEnemy enemy) {
		player.setEnergy(player.getEnergy()-enemy.getDamage());

		SoundEvent hitEvent = new SoundEvent();
		hitEvent.setSource(player);
		hitEvent.setEventType(SoundEventType.HIT);
		hitEvent.setBehaviour(SoundEventBehaviour.EXCLUSIVE);
		hitEvent.setVolume(SoundStore.get().getSoundVolume());
		hitEvent.setMinPitch(0.6f);
		hitEvent.setMaxPitch(0.8f);
		this.eventQueue.enqueueEvent(hitEvent);
	}
	
	private void handleCollision(Bullet bullet, IEnemy enemy) {
		IBackoffPlayer player = (IBackoffPlayer)this.entityManager.getPlayer();
		bullet.setCollided(true);
		bullet.setAlive(false);
		
		int energy = enemy.getEnergy();
		enemy.setEnergy(energy-player.getActiveWeapon().getDamage());
		if (enemy.getEnergy() <= 0) {
			enemy.setAlive(false);
			player.setTotalScore(player.getTotalScore()+enemy.getScore());
		}
	}
	
	private void handleFire() {
		IBackoffPlayer player = (IBackoffPlayer)this.entityManager.getPlayer();
		Weapon weapon = player.getActiveWeapon();
		long now = System.currentTimeMillis();
		
		long fireInterval = new Double(1/weapon.getFireRate()*1000).longValue();
		
		if (now-this.lastFire>fireInterval) {
			if (weapon.fire()) {
				Bullet b = new Bullet();
				b.setLayer(player.getLayer());
				float x = player.getPosition().x+(player.getCurrentSprite().getWidth()/2)-(b.getCurrentSprite().getHeight()/2); 
				float y = player.getPosition().y+player.getCurrentSprite().getHeight();	
								
				b.setSpeed(15.0f);
				b.setPosition(new Vector2f(x,y));
				b.setRotation(player.getRotation());
				this.entityManager.addEntity(b);
				
				SoundEvent fireEvent = new SoundEvent();
				fireEvent.setEventType(SoundEventType.FIRE);
				fireEvent.setBehaviour(SoundEventBehaviour.DEFAULT);
				fireEvent.setSource(player);
				fireEvent.setVolume(SoundStore.get().getSoundVolume()-0.2f);
				fireEvent.setMinPitch(0.6f);
				fireEvent.setMaxPitch(0.8f);
				this.eventQueue.enqueueEvent(fireEvent);
				
				SoundEvent shellDropEvent = new SoundEvent();
				shellDropEvent.setEventType(SoundEventType.SHELL_DROP);
				shellDropEvent.setBehaviour(SoundEventBehaviour.EXCLUSIVE);
				shellDropEvent.setSource(player);
				shellDropEvent.setVolume(SoundStore.get().getSoundVolume());
				shellDropEvent.setMinPitch(0.6f);
				shellDropEvent.setMaxPitch(0.8f);
				this.eventQueue.enqueueEvent(shellDropEvent, 650);
				this.lastFire = now;
			}
		}
	}
}
