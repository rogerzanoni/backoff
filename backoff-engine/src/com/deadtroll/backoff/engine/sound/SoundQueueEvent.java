package com.deadtroll.backoff.engine.sound;

/**
 * @author higure
 *
 */
public class SoundQueueEvent {
	private SoundEvent soundEvent;
	SoundEvent.Behaviour behaviour;
	private long playTime;
	
	public SoundQueueEvent(SoundEvent soundEvent, SoundEvent.Behaviour behaviour, long playTime) {
		this.soundEvent = soundEvent;
		this.playTime = playTime;
		this.behaviour = behaviour;
	}

	public long getPlayTime() {
		return playTime;
	}

	public SoundEvent getSoundEvent() {
		return soundEvent;
	}

	public SoundEvent.Behaviour getBehaviour() {
		return behaviour;
	}

	public void setBehaviour(SoundEvent.Behaviour behaviour) {
		this.behaviour = behaviour;
	}
	
}