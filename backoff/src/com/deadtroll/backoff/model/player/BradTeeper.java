package com.deadtroll.backoff.model.player;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.geom.Vector2f;

import com.deadtroll.backoff.engine.event.sound.SoundEventType;
import com.deadtroll.backoff.engine.manager.KeyStateManager;
import com.deadtroll.backoff.engine.manager.SoundManager;
import com.deadtroll.backoff.model.weapon.DesertEaglePistol;

public class BradTeeper extends GenericPlayer {

	public BradTeeper() {
		setName("Brad Teeper Cadarn");
		DesertEaglePistol de = new DesertEaglePistol();
		de.setMagazineAmmo(de.getMagazineSize());
		de.setAmmo(de.getMagazineSize()*15);
		addWeapon(de);
		setAlive(true);
	}

	public void addSoundEvents() {
		try {
			SoundManager.getInstance().addSound(this, SoundEventType.FIRE, new Sound("res/sound/Desert_E-EddyNL-1559.wav"));
			SoundManager.getInstance().addSound(this, SoundEventType.SHELL_DROP, new Sound("res/sound/gun_shell_drop.WAV"));
			SoundManager.getInstance().addSound(this, SoundEventType.HIT, new Sound("res/sound/hit_blood_spat.WAV"));
			SoundManager.getInstance().addSound(this, SoundEventType.RELOAD, new Sound("res/sound/gun_pump_action.WAV"));
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

	public void removeSoundEvents() {
		SoundManager.getInstance().removeSoundEvents(this);
	}

	public int getScore() {
		return 0;
	}

	public void setScore(int score) {
	}

	public void update(int delta) {
		if (!isCollided()) {
			double radiusRotation = (this.rotation/180.0)*Math.PI;
			Vector2f nextPosition = null;
			KeyStateManager keyState = KeyStateManager.getInstance();
			
			this.previousPosition = this.position;
			this.previousRotation = this.rotation;
			if (keyState.isKeyPressed(208)) {
				nextPosition = this.position.copy().sub(new Vector2f((float)Math.cos(radiusRotation),(float)Math.sin(radiusRotation)).scale(2f));
				setPosition(nextPosition);
			}
			if (keyState.isKeyPressed(200)) {
				nextPosition = this.position.copy().add(new Vector2f((float)Math.cos(radiusRotation),(float)Math.sin(radiusRotation)).scale(5f));
				setPosition(nextPosition);
			}
			if (keyState.isKeyPressed(203)) {
				setRotation(((this.rotation-10f)+360)%360);
			}
			if (keyState.isKeyPressed(205)) {
				setRotation(((this.rotation+10f)+360)%360);
			}
		} else {
			this.position = this.previousPosition;
			this.rotation = this.previousRotation;
			this.collided = false;
		}
	}
}
