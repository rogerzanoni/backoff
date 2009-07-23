package com.deadtroll.backoff.engine.sound;

import org.newdawn.slick.Music;

import com.deadtroll.backoff.engine.model.IGameObject;

public interface ISoundEventListener {
	public void playSoundEvent(IGameObject go, SoundEventType evtType, SoundSequenceType sequenceType, boolean exclusive, boolean interrupt);
	public void addSoundEvent(IGameObject go, SoundEvent event);
	public void removeSoundEvent(IGameObject go, SoundEventType evtType);
	public void removeSoundEvents(IGameObject go);
	
	public void update(int delta);
	
	public void setCurrentMusic(Music music);
	public void playMusic();
	public void pauseMusic();
	public void resumeMusic();
	public void stopMusic();
	public boolean isMusicPaying();
}
