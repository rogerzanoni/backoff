package com.deadtroll.backoff.engine.sound;

import java.util.HashMap;

import org.newdawn.slick.Sound;

public class SoundManager implements ISoundEventListener {
	private HashMap<String, Sound> soundMap;
	private static SoundManager instance;

	private SoundManager() {
		soundMap = new HashMap<String, Sound>();
	}

	public static SoundManager getInstance() {
		if (instance == null)
			instance = new SoundManager();
		return instance;
	}

	public void addSound(ISoundSource so, SoundEvent event, Sound sound) {
		String key = so.getClass().getName() + event.toString();
		if (!this.soundMap.containsKey(key))
			this.soundMap.put(key, sound);
		
	}

	public void playSound(ISoundSource so, SoundEvent event) {
		String key = so.getClass().getName() + event.toString();
		if (this.soundMap.containsKey(key))
			this.soundMap.get(key).play();
	}

	public void removeSound(ISoundSource so, SoundEvent event) {
		String key = so.getClass().getName() + event.toString();
		this.soundMap.remove(key);
	}
}
