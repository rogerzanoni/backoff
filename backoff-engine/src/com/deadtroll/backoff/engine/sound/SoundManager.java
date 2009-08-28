package com.deadtroll.backoff.engine.sound;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Random;

import org.newdawn.slick.Music;

import com.deadtroll.backoff.engine.model.IGameObject;

public class SoundManager {
	private Music currentMusic;
	private Hashtable<String, ArrayList<SoundEvent>> soundEventMap;
	private ArrayList<SoundQueueEvent> soundEventQueue;
	private static SoundManager instance;

	private SoundManager() {
		soundEventMap = new Hashtable<String, ArrayList<SoundEvent>>();
		soundEventQueue = new ArrayList<SoundQueueEvent>();
	}

	public static SoundManager getInstance() {
		if (instance == null)
			instance = new SoundManager();
		return instance;
	}

	public void addSoundEvent(IGameObject go, SoundEvent event) {
		String key = go.getClass().getName() + event.getEventType().toString();
		if (!this.soundEventMap.containsKey(key)) {
			this.soundEventMap.put(key, new ArrayList<SoundEvent>());
		}
		
		if (!this.soundEventMap.get(key).contains(event)) { // testar contains
			this.soundEventMap.get(key).add(event);
		}
	}
	
	private ArrayList<SoundEvent> getSoundList(IGameObject go, SoundEvent.Type evtType) {
		String key = go.getClass().getName() + evtType.toString();
		if (this.soundEventMap.containsKey(key)) {
			ArrayList<SoundEvent> soundEventList = this.soundEventMap.get(key);
			return soundEventList;
		}
		return null;
	}
	
	private boolean isPlaying(IGameObject go, SoundEvent.Type evtType) {
		ArrayList<SoundEvent> soundEventList = getSoundList(go, evtType);
		if (soundEventList != null) {
			for (SoundEvent soundEvent: soundEventList) {
				if (soundEvent.getSound().playing())
					return true;
			}
		}
		return false;
	}
	
	private void interruptAllEvents(IGameObject go) {
		for (SoundEvent.Type evtType: SoundEvent.Type.values()) {
			ArrayList<SoundEvent> soundEventList = getSoundList(go, evtType);
			if (soundEventList != null) {
				for (SoundEvent soundEvent: soundEventList) {
					if (soundEvent.getSound().playing())
						soundEvent.getSound().stop();
				}
			}
		}
	}
	
	private SoundEvent getSingle(IGameObject go, SoundEvent.Type evtType) {
		ArrayList<SoundEvent> soundEventList = getSoundList(go, evtType);
		return soundEventList.get(0);
	}
	
	private SoundEvent getRandom(IGameObject go, SoundEvent.Type evtType) {
		ArrayList<SoundEvent> soundEventList = getSoundList(go, evtType);
		Random random = new Random();
		int index = random.nextInt(soundEventList.size());
		return soundEventList.get(index);
	}
	
	private void playSoundEvent(SoundEvent event, SoundEvent.Behaviour behaviour) {
		if (behaviour == SoundEvent.Behaviour.INTERRUPT_ALL) {
			interruptAllEvents(event.getSource());
		} else if (behaviour == SoundEvent.Behaviour.INTERRUPT) {
			event.getSound().stop();
		} else if (behaviour == SoundEvent.Behaviour.EXCLUSIVE) {
			if (event.getSound().playing())
				return;
		} else if (behaviour == SoundEvent.Behaviour.EXCLUSIVE_GLOBAL) {
			if (isPlaying(event.getSource(), event.getEventType()))
				return;
		}
		float pitch = getPitchVariation(event.getMinPitch(), event.getMaxPitch());
		event.getSound().play(pitch, event.getVolume());
	}
	
	public void playSingle(IGameObject go, SoundEvent.Type evtType, SoundEvent.Behaviour behaviour) {
		playSoundEvent(getSingle(go, evtType), behaviour);
	}
	
	public void playRandom(IGameObject go, SoundEvent.Type evtType, SoundEvent.Behaviour behaviour) {
		playSoundEvent(getRandom(go, evtType), behaviour);
	}
	
	public void playMultiple(IGameObject go, SoundEvent.Type evtType, SoundEvent.Behaviour behaviour) {
		ArrayList<SoundEvent> soundEventList = getSoundList(go, evtType);
		for (SoundEvent event: soundEventList) {
			playSoundEvent(event, behaviour);
		}
	}
	
	public void enqueueSingle(IGameObject go, SoundEvent.Type evtType, SoundEvent.Behaviour behaviour, long playTime) {
		this.soundEventQueue.add(new SoundQueueEvent(getSingle(go, evtType), behaviour, System.currentTimeMillis() + playTime));
	}
	
	public void enqueueRandom(IGameObject go, SoundEvent.Type evtType, SoundEvent.Behaviour behaviour, long playTime) {
		this.soundEventQueue.add(new SoundQueueEvent(getRandom(go, evtType), behaviour, System.currentTimeMillis() + playTime));
	}
	
	public void enqueueMultiple(IGameObject go, SoundEvent.Type evtType, SoundEvent.Behaviour behaviour, long playTime) {
		ArrayList<SoundEvent> soundEventList = getSoundList(go, evtType);
		for (SoundEvent event: soundEventList) {
			this.soundEventQueue.add(new SoundQueueEvent(event, behaviour, System.currentTimeMillis() + playTime));
		}
	}
	
	private float getPitchVariation(float minPitch, float maxPitch) {
		if (minPitch != maxPitch) {
			float maxVariation = maxPitch - minPitch;
			return minPitch + (float)(Math.random() * maxVariation);
		}
		return minPitch;
	}
	
	public void update(int delta) {
		Music.poll(delta);
		
		for (int rIndex = this.soundEventQueue.size()-1; rIndex >= 0; rIndex--) {
			SoundQueueEvent soundQueueEvent = this.soundEventQueue.get(rIndex);
			if (soundQueueEvent.getPlayTime() <= System.currentTimeMillis()) {
				playSoundEvent(soundQueueEvent.getSoundEvent(), soundQueueEvent.behaviour);
				this.soundEventQueue.remove(rIndex);
			}
		}
	}

	public void removeSoundEvent(IGameObject go, SoundEvent.Type event) {
		String key = go.getClass().getName() + event.toString();
		this.soundEventMap.remove(key);
	}
	
	public void removeSoundEvents(IGameObject go) {
		for (SoundEvent.Type evtType: SoundEvent.Type.values()) {
			removeSoundEvent(go, evtType);
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
