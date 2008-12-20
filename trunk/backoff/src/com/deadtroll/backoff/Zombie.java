package com.deadtroll.backoff;

import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;

public class Zombie extends Enemy {

	public Zombie(String enemyDescription) {
		super(enemyDescription);
	}

	SpriteSheet spriteSheet;
	int currentSpriteX, currentSpriteY;

//	public Zombie() {
//		super();
//		try {
//			this.setSpriteSheet(new SpriteSheet("res/zombie.png",50,50));
//			this.setSpeed(1);
//			this.setDamage(1);
//			this.setScore(100);
//		} catch (SlickException e) {
//			e.printStackTrace();
//		}
//	}

	public Image getCurrentSprite() {
		return this.spriteSheet.getSprite(this.currentSpriteX, this.currentSpriteY);
	}

	public void setSpriteSheet(SpriteSheet sprite) {
		this.spriteSheet = sprite;	
	}
	
}
