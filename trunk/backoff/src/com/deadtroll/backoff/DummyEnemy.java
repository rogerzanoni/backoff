package com.deadtroll.backoff;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

import com.deadtroll.backoff.engine.enemy.GenericEnemy;
import com.deadtroll.backoff.engine.sound.SoundEvent;
import com.deadtroll.backoff.engine.sound.SoundManager;

public class DummyEnemy extends GenericEnemy {

	public DummyEnemy(String enemyName) {
		super(enemyName);
	}

	public void addSoundBuffers() {
		try {
			SoundManager.getInstance().addSound(this, SoundEvent.DEATH, new Sound("res/sound/creature_scream1.WAV"));
		} catch (SlickException e) {
			e.printStackTrace();
		}
		setSoundEventListener(SoundManager.getInstance());
	}

	public void removeSoundBuffers() {
		SoundManager.getInstance().removeSound(this, SoundEvent.DEATH);
	}

}
