package com.deadtroll.backoff.engine.sound;

public interface ISoundSource {
	void addSoundBuffers();
	void setSoundEventListener(ISoundEventListener listener);
	void removeSoundBuffers();
	void playSoundEvent(SoundEvent event);
}
