package com.deadtroll.backoff.engine.model;

import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Vector2f;

public interface IMoveable extends IMoveableAcessor {

	public void setSpriteSheet(SpriteSheet sprite);

	public void setSpeed(Vector2f speed);

	public void setAngle(Vector2f angle);

	public Vector2f getAngle();

}
