package com.deadtroll.backoff.engine.event.sound;

import com.deadtroll.backoff.engine.event.AbstractEvent;
import com.deadtroll.backoff.engine.model.IGameObject;

public class SoundEvent extends AbstractEvent {
	private IGameObject source;
	private SoundEventType eventType;
	private SoundEventBehaviour behaviour;
	private float minPitch;
	private float maxPitch;
	private float volume;
	private boolean random;
	
	public SoundEventBehaviour getBehaviour() {
		return behaviour;
	}
	public void setBehaviour(SoundEventBehaviour behaviour) {
		this.behaviour = behaviour;
	}
	public SoundEventType getEventType() {
		return eventType;
	}
	public void setEventType(SoundEventType eventType) {
		this.eventType = eventType;
	}
	public float getMaxPitch() {
		return maxPitch;
	}
	public void setMaxPitch(float maxPitch) {
		this.maxPitch = maxPitch;
	}
	public float getMinPitch() {
		return minPitch;
	}
	public void setMinPitch(float minPitch) {
		this.minPitch = minPitch;
	}
	public boolean isRandom() {
		return random;
	}
	public void setRandom(boolean random) {
		this.random = random;
	}
	public IGameObject getSource() {
		return source;
	}
	public void setSource(IGameObject source) {
		this.source = source;
	}
	public float getVolume() {
		return volume;
	}
	public void setVolume(float volume) {
		this.volume = volume;
	}
}
