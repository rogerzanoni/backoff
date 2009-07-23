package com.deadtroll.backoff.engine.sound;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Random;

import org.newdawn.slick.Music;

import com.deadtroll.backoff.engine.model.IGameObject;

public class SoundManager implements ISoundEventListener {
	private Music currentMusic;
	//TODO: gerenciar quantidade de instancias
	private Hashtable<String, ArrayList<SoundEvent>> soundEventMap;
	private static SoundManager instance;

	private SoundManager() {
		soundEventMap = new Hashtable<String, ArrayList<SoundEvent>>();
	}

	public static SoundManager getInstance() {
		if (instance == null)
			instance = new SoundManager();
		return instance;
	}

	public void addSoundEvent(IGameObject go, SoundEvent event) {
		String key = go.getClass().getName() + event.getEventType().toString();
 		System.out.println("KeyAdd: " + key);
		if (!this.soundEventMap.containsKey(key)) {
			this.soundEventMap.put(key, new ArrayList<SoundEvent>());
		}
		
		if (!this.soundEventMap.get(key).contains(event)) { // testar contains
			this.soundEventMap.get(key).add(event);
		}
	}

	public void playSoundEvent(IGameObject go, SoundEventType evtType, SoundSequenceType sequenceType, boolean exclusive, boolean interrupt) {
		String key = go.getClass().getName() + evtType.toString();
		if (this.soundEventMap.containsKey(key)) {
			ArrayList<SoundEvent> soundEventList = this.soundEventMap.get(key);
			
			if (exclusive && isPlaying(soundEventList))
				return;
			
			if (interrupt)
				interruptEvents(soundEventList);
			
			if (sequenceType == SoundSequenceType.MULTIPLE) {
				playMultiple(soundEventList);
			} else if (sequenceType == SoundSequenceType.FIRST) {
				playSingle(soundEventList);
			} else if (sequenceType == SoundSequenceType.RANDOM) {
				playRamdom(soundEventList);
			}
		}
	}
	
	private boolean isPlaying(ArrayList<SoundEvent> soundEventList) {
		// TODO: verificar tambem se o evento esta na lista dos sons sequenciais
		for (SoundEvent soundEvent: soundEventList) {
			if (soundEvent.getSound().playing())
				return true;
		}
		return false;
	}
	
	private void interruptEvents(ArrayList<SoundEvent> soundEventList) {
		for (SoundEvent soundEvent: soundEventList) {
			if (soundEvent.getSound().playing())
				soundEvent.getSound().stop();
		}
	}
	
	private void playSingle(ArrayList<SoundEvent> soundEventList) {
		SoundEvent event = soundEventList.get(0);
		float pitch = getPitchVariation(event.getMinPitch(), event.getMaxPitch());
		event.getSound().play(pitch, event.getVolume());
	}
	
	private void playRamdom(ArrayList<SoundEvent> soundEventList) {
		Random random = new Random();
		int index = random.nextInt(soundEventList.size());
		SoundEvent event = soundEventList.get(index);
		float pitch = getPitchVariation(event.getMinPitch(), event.getMaxPitch());
		soundEventList.get(index).getSound().play(pitch, event.getVolume());
	}
	
	private void playMultiple(ArrayList<SoundEvent> soundEventList) {
		float pitch;
		for (SoundEvent event: soundEventList) {
			pitch = getPitchVariation(event.getMinPitch(), event.getMaxPitch());
			event.getSound().play(pitch, event.getVolume());
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
	}

	public void removeSoundEvent(IGameObject go, SoundEventType event) {
		String key = go.getClass().getName() + event.toString();
		this.soundEventMap.remove(key);
	}
	
	public void removeSoundEvents(IGameObject go) {
		for (SoundEventType evtType: SoundEventType.values()) {
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
}
