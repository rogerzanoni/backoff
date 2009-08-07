package com.deadtroll.backoff.engine.sound;



/**
 * @author higure
 *
 */
public class SoundQueueEvent {
	private SoundEvent soundEvent;
	private long playTime;
	boolean exclusive;
	boolean interrupt;
	
	public SoundQueueEvent(SoundEvent soundEvent, boolean exclusive, boolean interrupt, long playTime) {
		this.soundEvent = soundEvent;
		this.playTime = playTime;
		this.exclusive = exclusive;
		this.interrupt = interrupt;
	}

	public long getPlayTime() {
		return playTime;
	}

	public SoundEvent getSoundEvent() {
		return soundEvent;
	}

	public boolean isExclusive() {
		return exclusive;
	}

	public boolean isInterrupt() {
		return interrupt;
	}
}
