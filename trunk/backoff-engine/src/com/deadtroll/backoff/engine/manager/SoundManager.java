package com.deadtroll.backoff.engine.manager;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Random;

import org.newdawn.slick.Music;
import org.newdawn.slick.Sound;


import com.deadtroll.backoff.engine.event.sound.SoundEventType;
import com.deadtroll.backoff.engine.model.IGameObject;

public class SoundManager {
	private Music currentMusic;
	private Hashtable<String, ArrayList<Sound>> soundMap;
	private static SoundManager instance;

	private SoundManager() {
		soundMap = new Hashtable<String, ArrayList<Sound>>();
	}

	public static SoundManager getInstance() {
		if (instance == null)
			instance = new SoundManager();
		return instance;
	}

	public void addSound(IGameObject go, SoundEventType evtType, Sound sound) {
		String key = go.getClass().getName() + evtType.toString();
		if (!this.soundMap.containsKey(key)) {
			this.soundMap.put(key, new ArrayList<Sound>());
		}
		
		if (!this.soundMap.get(key).contains(sound)) {
			this.soundMap.get(key).add(sound);
		}
	}
	
	public ArrayList<Sound> getSoundList(IGameObject go, SoundEventType evtType) {
		String key = go.getClass().getName() + evtType.toString();
		if (this.soundMap.containsKey(key)) {
			ArrayList<Sound> soundList = this.soundMap.get(key);
			return soundList;
		}
		return null;
	}
	
	public boolean isPlaying(IGameObject go, SoundEventType evtType) {
		ArrayList<Sound> soundList = getSoundList(go, evtType);
		if (soundList != null) {
			for (Sound sound: soundList) {
				if (sound.playing())
					return true;
			}
		}
		return false;
	}
	
	public void removeSound(IGameObject go, SoundEventType event) {
		String key = go.getClass().getName() + event.toString();
		this.soundMap.remove(key);
	}
	
	public void removeSoundEvents(IGameObject go) {
		for (SoundEventType evtType: SoundEventType.values()) {
			removeSound(go, evtType);
		}
	}
	
	public Sound getSingle(IGameObject go, SoundEventType evtType) {
		ArrayList<Sound> soundList = getSoundList(go, evtType);
		return soundList.get(0);
	}
	
	public Sound getRandom(IGameObject go, SoundEventType evtType) {
		ArrayList<Sound> soundList = getSoundList(go, evtType);
		Random random = new Random();
		int index = random.nextInt(soundList.size());
		return soundList.get(index);
	}
	
	public void interruptAllSounds(IGameObject go) {
		for (SoundEventType evtType: SoundEventType.values()) {
			interruptSounds(go, evtType);
		}
	}
	
	public void interruptSounds(IGameObject go, SoundEventType evtType) {
		ArrayList<Sound> soundList = getSoundList(go, evtType);
		if (soundList != null) {
			for (Sound sound: soundList) {
				if (sound.playing())
					sound.stop();
			}
		}
	}

	public void setCurrentMusic(Music music) {
		boolean playing = false;
		if (this.currentMusic != null) {
			playing = this.currentMusic.playing();
			this.currentMusic.stop();
		}
		this.currentMusic = music;
		if (playing) {
			playMusic();
		}
	}
	
	public void setMusicVolume(float volume) {
		if (this.currentMusic != null) {
			this.currentMusic.setVolume(volume);
		}		
	}
	
	public float getMusicVolume() {
		if (this.currentMusic != null) {
			return this.currentMusic.getVolume();
		}
		return 0.0f;
	}
	
	public void playMusic() {
		if (this.currentMusic != null) {
			this.currentMusic.loop();
		}
	}
	
	public void pauseMusic() {
		if (this.currentMusic != null) {
			this.currentMusic.pause();
		}
	}
	
	public void resumeMusic() {
		if (this.currentMusic != null) {
			this.currentMusic.resume();
		}
	}
	
	public void stopMusic() {
		if (this.currentMusic != null) {
			this.currentMusic.stop();
		}
	}

	public boolean isMusicPaying() {
		if (this.currentMusic != null) {
			return this.currentMusic.playing();
		}
		return false;
	}
	
	public void fadeMusic(int duration, float endVolume, boolean stopAfterFade) {
		if (this.currentMusic != null) {
			this.currentMusic.fade(duration, endVolume, stopAfterFade);
		}
	}
	
}
