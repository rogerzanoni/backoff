package com.deadtroll.backoff.model.enemy;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

import com.deadtroll.backoff.engine.ai.IBrain;
import com.deadtroll.backoff.engine.event.sound.SoundEventType;
import com.deadtroll.backoff.engine.manager.SoundManager;
import com.deadtroll.backoff.engine.model.enemy.ScriptedEnemy;

public class StateMachineZombie extends ScriptedEnemy {
	
	public StateMachineZombie(String scriptName) {
		super(scriptName);
		setAlive(true);
	}

	private IBrain brain;

	public IBrain getBrain() {
		return brain;
	}

	public void setBrain(IBrain brain) {
		this.brain = brain;
	}

	public void addSoundEvents() {
		try {
			SoundManager.getInstance().addSound(this, SoundEventType.DEATH, new Sound("res/sound/creature_scream1.WAV"));
			SoundManager.getInstance().addSound(this, SoundEventType.DEATH, new Sound("res/sound/creature_scream2.WAV"));
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

	public void removeSoundEvents() {
	}

	public void update(int delta) {
		this.brain.think();
	}
}
