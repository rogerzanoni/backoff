package com.deadtroll.backoff.model.enemy;

import org.newdawn.slick.geom.Vector2f;

import com.deadtroll.backoff.engine.model.IGameObject;

public interface IEnemy extends IGameObject {
	public Vector2f getDestination();
	public void setDestination(Vector2f destination);
}
