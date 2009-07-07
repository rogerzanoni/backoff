package com.deadtroll.backoff.engine.sound;

import org.newdawn.slick.Sound;

public interface ISoundEventListener {
	public void playSound(ISoundSource so, SoundEvent event);
	public void addSound(ISoundSource so, SoundEvent event, Sound sound);
	public void removeSound(ISoundSource so, SoundEvent event);
}
