package com.deadtroll.backoff.engine.event.handler;

import org.newdawn.slick.Sound;

import com.deadtroll.backoff.engine.event.AbstractEvent;
import com.deadtroll.backoff.engine.event.AbstractEventHandler;
import com.deadtroll.backoff.engine.event.sound.SoundEvent;
import com.deadtroll.backoff.engine.event.sound.SoundEventBehaviour;
import com.deadtroll.backoff.engine.manager.SoundManager;

public class SoundEventHandler extends AbstractEventHandler {
	private SoundManager soundManager;
	
	public SoundEventHandler() {
		this.soundManager = SoundManager.getInstance();
	}

	@Override
	public void processEvent(AbstractEvent e) {
		SoundEvent event = (SoundEvent)e;
		playSoundEvent(event);
	}

	private void playSoundEvent(SoundEvent event) {
		SoundEventBehaviour behaviour = event.getBehaviour();
		Sound sound = event.isRandom() ? this.soundManager.getRandom(event.getSource(), event.getEventType())
									   : this.soundManager.getSingle(event.getSource(), event.getEventType());
		
		if (behaviour == SoundEventBehaviour.INTERRUPT_ALL) {
			this.soundManager.interruptAllSounds(event.getSource());
		} else if (behaviour == SoundEventBehaviour.INTERRUPT) {
			this.soundManager.interruptSounds(event.getSource(), event.getEventType());
		} else if (behaviour == SoundEventBehaviour.EXCLUSIVE) {
			if (sound.playing())
				return;
		} else if (behaviour == SoundEventBehaviour.EXCLUSIVE_GLOBAL) {
			if (this.soundManager.isPlaying(event.getSource(), event.getEventType()))
				return;
		}
		float pitch = getPitchVariation(event.getMinPitch(), event.getMaxPitch());
		sound.play(pitch, event.getVolume());
	}
	
	private float getPitchVariation(float minPitch, float maxPitch) {
		if (minPitch != maxPitch) {
			float maxVariation = maxPitch - minPitch;
			return minPitch + (float)(Math.random() * maxVariation);
		}
		return minPitch;
	}
}
