package br.com.deadtroll.tests;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.openal.SoundStore;

import com.deadtroll.backoff.engine.model.TransientStatus;
import com.deadtroll.backoff.engine.player.GenericPlayer;
import com.deadtroll.backoff.engine.sound.SoundEvent;
import com.deadtroll.backoff.engine.sound.SoundEventType;
import com.deadtroll.backoff.engine.sound.SoundManager;
import com.deadtroll.backoff.engine.weapon.M9Pistol;

public class BradTeeper extends GenericPlayer {
	public BradTeeper() {
		setName("Brad Teeper Cadarn");
		M9Pistol m9 = new M9Pistol();
		m9.setMagazineAmmo(m9.getMagazineSize());
		m9.setAmmo(m9.getMagazineSize()*5);
		addWeapon(m9);
	}

	public void addSoundEvents() {
		//TODO: Remover sons da pasta de resources, somente criados para testes
		try {
			SoundManager.getInstance().addSoundEvent(this, new SoundEvent(this, SoundEventType.FIRE, 0.6F, 1.4F, SoundStore.get().getSoundVolume(), new Sound("res/sound/gun_pistol1.WAV")));
			SoundManager.getInstance().addSoundEvent(this, new SoundEvent(this, SoundEventType.FIRE, 0.6F, 1.4F, SoundStore.get().getSoundVolume(), new Sound("res/sound/gun_pistol2.WAV")));
			SoundManager.getInstance().addSoundEvent(this, new SoundEvent(this, SoundEventType.SHELL_DROP, 0.6F, 1.4F, SoundStore.get().getSoundVolume(), new Sound("res/sound/gun_shell_drop.WAV")));
			SoundManager.getInstance().addSoundEvent(this, new SoundEvent(this, SoundEventType.HIT, 0.6F, 1.4F, SoundStore.get().getSoundVolume(), new Sound("res/sound/hit_blood_spat.WAV")));
			SoundManager.getInstance().addSoundEvent(this, new SoundEvent(this, SoundEventType.RELOAD, 0.6F, 1.4F, SoundStore.get().getSoundVolume(), new Sound("res/sound/gun_pump_action.WAV")));
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void removeSoundEvents() {
		SoundManager.getInstance().removeSoundEvents(this);
	}

	public int getScore() {
		return 0;
	}

	public TransientStatus getStatus() {
		return TransientStatus.STATUS_DEFAULT;
	}

	public void setScore(int score) {
	}

	public void setStatus(TransientStatus status) {
	}
}
