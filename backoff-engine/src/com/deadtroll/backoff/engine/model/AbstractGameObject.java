package com.deadtroll.backoff.engine.model;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Transform;
import org.newdawn.slick.geom.Vector2f;

import com.deadtroll.backoff.engine.managers.RenderManager;
import com.deadtroll.backoff.engine.viewport.ViewPort;

public abstract class AbstractGameObject implements IGameObject {
	protected long id;
	protected int layer;
	protected Vector2f position;
	protected SpriteSheet spriteSheet;
	protected float rotation;
	protected Vector2f speed;
	protected Vector2f angle;
	protected Shape collisionShape;
	private TransientStatus status;
	
	public int getLayer() {
		return this.layer;
	}

	public Vector2f getPosition() {
		return this.position;
	}

	public void setLayer(int layer) {
		this.layer = layer;
	}

	public void setPosition(Vector2f position) {
		this.position = position;
	}

	public Vector2f getCenter() {
		Image img = this.getCurrentSprite();
		return new Vector2f(this.position.x+(img.getWidth())/2,this.position.y+(img.getHeight())/2);
	}
	
	public void setSpriteSheet(SpriteSheet sprite) {
		this.spriteSheet = sprite;
	}
	
	public float getRotation() {
		return this.rotation;
	}
	
	public void setRotation(float angle) {
		this.rotation = angle;
	}

	public void render(Graphics g, ViewPort viewPort) {
		Image sprite = this.getCurrentSprite();
		sprite.rotate(this.getRotation());
		g.drawImage(sprite,this.position.x-viewPort.getX(), this.position.y-viewPort.getY());
		RenderManager rm = RenderManager.getInstance();
		if (rm.getDrawCollisionShapes()) {
			g.draw(this.getCollisionShape(viewPort));
		}
		if (rm.getDrawObjectId()) {
			g.drawString(String.valueOf(this.getId()),this.getPosition().x,this.getPosition().y-15);
		}
	}
	
	public Vector2f getAngle() {
		return angle;
	}

	public void setAngle(Vector2f angle) {
		this.angle = angle;
	}

	public Vector2f getSpeed() {
		return speed;
	}

	public void setSpeed(Vector2f speed) {
		this.speed = speed;
	}

	public Shape getCollisionShape(ViewPort viewport) {
		Image img = this.getCurrentSprite();
		Shape rect = new Rectangle(this.position.x-viewport.getX(),this.position.y-viewport.getY(),img.getWidth(),img.getHeight());
		rect = rect.transform(Transform.createRotateTransform((float)((this.rotation/180)*Math.PI),rect.getCenterX(),rect.getCenterY()));
		return rect;
	}
	
	public long getId() {
		return this.id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public void setStatus(TransientStatus status) {
		this.status = status;		
	}

	public TransientStatus getStatus() {
		return status;
	}
}
