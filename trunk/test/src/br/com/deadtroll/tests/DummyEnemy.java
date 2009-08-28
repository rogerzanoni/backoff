package br.com.deadtroll.tests;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.openal.SoundStore;

import com.deadtroll.backoff.engine.enemy.GenericEnemy;
import com.deadtroll.backoff.engine.sound.SoundEvent;
import com.deadtroll.backoff.engine.sound.SoundManager;

public class DummyEnemy extends GenericEnemy {

	public DummyEnemy(String enemyName) {
		super(enemyName);
	}

	public void addSoundEvents() {
		try {
			SoundManager.getInstance().addSoundEvent(this, new SoundEvent(this, SoundEvent.Type.DEATH, 0.8F, 1.6F, SoundStore.get().getSoundVolume(), new Sound("res/sound/creature_scream1.WAV")));
			SoundManager.getInstance().addSoundEvent(this, new SoundEvent(this, SoundEvent.Type.DEATH, 0.8F, 1.6F, SoundStore.get().getSoundVolume(), new Sound("res/sound/creature_scream2.WAV")));
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

	public void removeSoundEvents() {
	}
}
