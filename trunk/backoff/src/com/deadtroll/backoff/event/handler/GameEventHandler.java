package com.deadtroll.backoff.event.handler;

import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.openal.SoundStore;

import com.deadtroll.backoff.engine.event.AbstractEvent;
import com.deadtroll.backoff.engine.event.AbstractEventHandler;
import com.deadtroll.backoff.engine.event.game.GOCollisionEvent;
import com.deadtroll.backoff.engine.event.game.GameEvent;
import com.deadtroll.backoff.engine.event.game.GameEventType;
import com.deadtroll.backoff.engine.event.game.MapCollisionEvent;
import com.deadtroll.backoff.engine.event.sound.SoundEvent;
import com.deadtroll.backoff.engine.event.sound.SoundEventBehaviour;
import com.deadtroll.backoff.engine.event.sound.SoundEventType;
import com.deadtroll.backoff.engine.manager.EntityManager;
import com.deadtroll.backoff.engine.manager.EventQueueManager;
import com.deadtroll.backoff.engine.model.IGameObject;
import com.deadtroll.backoff.engine.model.bullet.IBullet;
import com.deadtroll.backoff.engine.model.enemy.IEnemy;
import com.deadtroll.backoff.engine.model.player.IPlayer;
import com.deadtroll.backoff.engine.model.weapon.Weapon;
import com.deadtroll.backoff.model.bullet.Bullet;

public class GameEventHandler extends AbstractEventHandler  {
	private EntityManager entityManager;
	private EventQueueManager eventQueueManager;
	private long lastFire;
	
	public GameEventHandler() {
		this.entityManager = EntityManager.getInstance();
		this.eventQueueManager = EventQueueManager.getInstance();
	}
	
	@Override
	public void processEvent(AbstractEvent e) {
		GameEvent gameEvent = (GameEvent)e;

		if (gameEvent.getType().equals(GameEventType.GO_COLLISION)) {
			GOCollisionEvent goCollisionEvent = (GOCollisionEvent)e;
			IGameObject firstCollider = goCollisionEvent.getFirstCollider();
			IGameObject secondCollider = goCollisionEvent.getSecondCollider();
			
			handleCollision(firstCollider, secondCollider);
		} else if (gameEvent.getType().equals(GameEventType.MAP_COLLISION)) {
			MapCollisionEvent mapCollisionEvent = (MapCollisionEvent)e;
			IGameObject collider = mapCollisionEvent.getCollider();
			Rectangle mapBlock = mapCollisionEvent.getMapRect();
			
			handleCollision(collider, mapBlock);
		} else if (gameEvent.getType().equals(GameEventType.PLAYER_FIRE)) {
			handleFire();
		}
	}
	
	private void handleCollision(IGameObject firstCollider, IGameObject secondCollider) {
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
		} else if ((firstCollider instanceof IBullet || secondCollider instanceof IBullet) &&
				   (firstCollider instanceof IEnemy || secondCollider instanceof IEnemy)) {
			IBullet bullet = null;
			IEnemy enemy = null;
			if (firstCollider instanceof IBullet) {
				bullet = (IBullet)firstCollider;
				enemy = (IEnemy)secondCollider;
			} else {
				bullet = (IBullet)secondCollider;
				enemy = (IEnemy)firstCollider;
			}
			handleCollision(bullet, enemy);						
		}
	}
	
	private void handleCollision(IGameObject firstCollider, Rectangle mapBlock) {
		firstCollider.setCollided(true);
		
		if (firstCollider instanceof IBullet) {
			firstCollider.setAlive(false);
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
		this.eventQueueManager.postEvent(hitEvent);
	}
	
	private void handleCollision(IBullet bullet, IEnemy enemy) {
		IPlayer player = this.entityManager.getPlayer();
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
		IPlayer player = this.entityManager.getPlayer();
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
				this.eventQueueManager.postEvent(fireEvent);
				
				SoundEvent shellDropEvent = new SoundEvent();
				shellDropEvent.setEventType(SoundEventType.SHELL_DROP);
				shellDropEvent.setBehaviour(SoundEventBehaviour.EXCLUSIVE);
				shellDropEvent.setSource(player);
				shellDropEvent.setVolume(SoundStore.get().getSoundVolume());
				shellDropEvent.setMinPitch(0.6f);
				shellDropEvent.setMaxPitch(0.8f);
				this.eventQueueManager.postEvent(shellDropEvent, 650);
				this.lastFire = now;
			}
		}
	}
}
