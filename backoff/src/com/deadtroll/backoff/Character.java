package com.deadtroll.backoff;

import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;

public interface Character {
	
	public int getX();
	public void setX(int x);
	
	public void setY(int y);
	public int getY();

	public int getEnergy();
	public void setEnergy(int energy);

	public Image getCurrentSprite();
	public void setSpriteSheet(SpriteSheet sprite);

	public int getSpeed();
	public void setSpeed(int speed);

}
