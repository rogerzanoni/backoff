package com.deadtroll.backoff;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

import com.deadtroll.backoff.engine.player.GenericPlayer;
import com.deadtroll.backoff.engine.sound.SoundEvent;
import com.deadtroll.backoff.engine.sound.SoundManager;
import com.deadtroll.backoff.engine.weapon.M9Pistol;

public class BradTeeper extends GenericPlayer {
	
	public BradTeeper() {
		setName("Brad Teeper Cadarn");
		M9Pistol m9 = new M9Pistol();
		m9.setMagazineAmmo(m9.getMagazineSize());
		m9.setAmmo(m9.getMagazineSize());
		addWeapon(m9);
		
		setSoundEventListener(SoundManager.getInstance());
		addSoundBuffers();
	}

	public void addSoundBuffers() {
		//TODO: Remover sons da pasta de resources, somente criados para testes
		try {
			SoundManager.getInstance().addSound(this, SoundEvent.FIRE, new Sound("res/sound/gun_pistol1.WAV"));
			SoundManager.getInstance().addSound(this, SoundEvent.HIT, new Sound("res/sound/hit_blood_spat.WAV"));
			SoundManager.getInstance().addSound(this, SoundEvent.RELOAD, new Sound("res/sound/gun_pump_action.WAV"));
			SoundManager.getInstance().addSound(this, SoundEvent.DEATH, new Sound("res/sound/creature_scream2.WAV"));
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void removeSoundBuffers() {
		SoundManager.getInstance().removeSound(this, SoundEvent.FIRE);
		SoundManager.getInstance().removeSound(this, SoundEvent.HIT);
		SoundManager.getInstance().removeSound(this, SoundEvent.RELOAD);
		SoundManager.getInstance().removeSound(this, SoundEvent.DEATH);
	}
}
