package com.deadtroll.backoff.engine.viewport;

import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;

import com.deadtroll.backoff.engine.model.IGameObject;

@SuppressWarnings("serial")
public class ViewPort extends Rectangle {
	private float maxOffsetX;
	private float maxOffsetY;
	
	public ViewPort(float width, float height, Vector2f panOffset, float worldWidth, float worldHeight) {
		super(panOffset.x, panOffset.y, width, height);
		this.maxOffsetX = worldWidth - width;
		this.maxOffsetY = worldHeight - height;
	}
	
	public void scroll(Vector2f scrollTo) {
		scrollTo.x = scrollTo.x < 0 ? 0 : scrollTo.x;
		scrollTo.y = scrollTo.y < 0 ? 0 : scrollTo.y;
		setX(scrollTo.x <= maxOffsetX ? scrollTo.x : maxOffsetX);
		setY(scrollTo.y <= maxOffsetY ? scrollTo.y : maxOffsetY);
	}
	
	public boolean contains(IGameObject object) {
		return super.contains(object.getCenter().x,object.getCenter().y);
	}

	@Override
	public String toString() {
		return "w: " + this.width + " h: " + this.height + " x: " + this.x + " y: " + this.y + "maxOffsetX: " + this.maxOffsetX + "maxOffSetY: " + this.maxOffsetY;  
	}
}
