package com.deadtroll.backoff.engine.sound;

import org.newdawn.slick.Sound;

import com.deadtroll.backoff.engine.model.IGameObject;

public class SoundEvent {
	private IGameObject source;
	private SoundEvent.Type eventType;
	private float minPitch;
	private float maxPitch;
	private float volume;
	private Sound sound;
	
	public SoundEvent(IGameObject source, SoundEvent.Type eventType,float minPitch, float maxPitch, float volume, Sound sound) {
		this.source = source;
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
	public SoundEvent.Type getEventType() {
		return eventType;
	}
	public Sound getSound() {
		return sound;
	}
	public IGameObject getSource() {
		return source;
	}
	
	public enum Type {
		FIRE,
		RELOAD,
		SHELL_DROP,
		HIT,
		DEATH
	}
	
	public enum Behaviour {
		DEFAULT,
		EXCLUSIVE,
		EXCLUSIVE_GLOBAL,
		INTERRUPT,
		INTERRUPT_ALL
	}
}
