package com.deadtroll.backoff.engine.sound;

import org.newdawn.slick.Sound;

public class SoundEvent {
	private SoundEventType eventType;
	private float minPitch;
	private float maxPitch;
	private float volume;
	private Sound sound;
		
	public SoundEvent(SoundEventType eventType,float minPitch, float maxPitch, float volume, Sound sound) {
		this.eventType = eventType;
		this.minPitch = minPitch;
		this.maxPitch = maxPitch;
		this.volume = volume;
		this.sound = sound;
	}
	
	public float getMaxPitch() {
		return maxPitch;
	}
	public float getMinPitch() {
		return minPitch;
	}
	public float getVolume() {
		return volume;
	}
	public SoundEventType getEventType() {
		return eventType;
	}
	public Sound getSound() {
		return sound;
	}
}
