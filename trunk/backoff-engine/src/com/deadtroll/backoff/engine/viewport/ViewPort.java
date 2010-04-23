package com.deadtroll.backoff.engine.viewport;

import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;

import com.deadtroll.backoff.engine.manager.EntityManager;
import com.deadtroll.backoff.engine.model.AbstractEntity;
import com.deadtroll.backoff.engine.model.IEntity;
import com.deadtroll.backoff.engine.model.IGameObject;
import com.deadtroll.backoff.engine.model.player.IPlayer;

public class ViewPort extends AbstractEntity implements IEntity {
	private float maxOffsetX;
	private float maxOffsetY;
	private Rectangle rect;
	
	public ViewPort(float width, float height, Vector2f panOffset, float worldWidth, float worldHeight) {
		this.rect = new Rectangle(panOffset.x, panOffset.y, width, height);
		this.maxOffsetX = worldWidth - width;
		this.maxOffsetY = worldHeight - height;
		setAlive(true);
	}
	
	public void scroll(Vector2f scrollTo) {
		scrollTo.x = scrollTo.x < 0 ? 0 : scrollTo.x;
		scrollTo.y = scrollTo.y < 0 ? 0 : scrollTo.y;
		this.rect.setX(scrollTo.x <= maxOffsetX ? scrollTo.x : maxOffsetX);
		this.rect.setY(scrollTo.y <= maxOffsetY ? scrollTo.y : maxOffsetY);
	}
	
	public boolean contains(IGameObject object) {
		return this.rect.contains(object.getCenter().x,object.getCenter().y);
	}

	@Override
	public String toString() {
		return "w: " + this.rect.getWidth() + " h: " + this.rect.getHeight() + " x: " + this.rect.getX() + " y: " + this.rect.getY() + "maxOffsetX: " + this.maxOffsetX + "maxOffSetY: " + this.maxOffsetY;  
	}

	public void finalizeEntity() {
	}

	public long getId() {
		return 0;
	}

	public String getName() {
		return "viewPort";
	}

	public void initializeEntity() {
	}

	public void setId(long id) {
	}

	public void setName(String name) {
	}

	public void update(int delta) {
		IPlayer player = EntityManager.getInstance().getPlayer();
		float diffx = player.getCenter().x - this.rect.getCenterX();
		float diffy = player.getCenter().y - this.rect.getCenterY();
		scroll(new Vector2f(this.rect.getX() + diffx, this.rect.getY() + diffy));
	}
	
	public float getX() {		
		return this.rect.getX();
	}
	
	public float getY() {
		return this.rect.getY();
	}
	
	public float getWidth() {
		return this.rect.getWidth();
	}
	
	public float getHeight() {
		return this.rect.getHeight();
	}
	
	public float getCenterX() {
		return this.rect.getCenterX();
	}
	
	public float getCenterY() {
		return this.rect.getCenterY();
	}

	public Rectangle getRect() {
		return rect;
	}

	public void setRect(Rectangle rect) {
		this.rect = rect;
	}
}
