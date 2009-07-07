package com.deadtroll.backoff.engine.model;

import org.newdawn.slick.SpriteSheet;

public interface IMoveable extends IMoveableAcessor {
	public void setSpriteSheet(SpriteSheet sprite);
	public void setSpeed(int speed);
	public void setHeading(Heading heading);
}
