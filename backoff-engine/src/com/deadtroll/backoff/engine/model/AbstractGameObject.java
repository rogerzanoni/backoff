package com.deadtroll.backoff.engine.model;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Transform;
import org.newdawn.slick.geom.Vector2f;

import com.deadtroll.backoff.engine.manager.RenderManager;
import com.deadtroll.backoff.engine.viewport.ViewPort;

public abstract class AbstractGameObject extends AbstractEntity implements IGameObject {
	protected long id;
	protected int layer;
	protected Vector2f position;
	protected Vector2f previousPosition;
	protected boolean collided;
	protected SpriteSheet spriteSheet;
	protected float rotation;
	protected float previousRotation;
	protected float speed;
	protected Vector2f angle;
	protected Shape collisionShape;
	protected int energy;
	protected int damage;
	private String name;
	private int score;
	
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
	
	public void setSpriteSheet(String sprite, int sw, int sh) {
		try {
			this.spriteSheet = new SpriteSheet(sprite, sw, sh);
		} catch (SlickException e) {
			e.printStackTrace();
		}
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
			drawString(g, viewPort, String.valueOf(this.getId()),this.getPosition().x,this.getPosition().y-15);
		}
	}
	
	public Vector2f getAngle() {
		return angle;
	}

	public void setAngle(Vector2f angle) {
		this.angle = angle;
	}

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

	public Shape getCollisionShape(ViewPort viewport) {
		Image img = this.getCurrentSprite();
		Shape rect = new Rectangle(this.position.x-viewport.getX(),this.position.y-viewport.getY(),img.getWidth(),img.getHeight());
		rect = rect.transform(Transform.createRotateTransform((float)((this.rotation/180)*Math.PI),rect.getCenterX(),rect.getCenterY()));
		return rect;
	}
	
	public Shape getCollisionShape() {
		Image img = this.getCurrentSprite();
		Shape rect = new Rectangle(this.position.x,this.position.y,img.getWidth(),img.getHeight());
		rect = rect.transform(Transform.createRotateTransform((float)((this.rotation/180)*Math.PI),rect.getCenterX(),rect.getCenterY()));
		return rect;
	}
	
	protected void drawString(Graphics g, ViewPort viewport, String string, float x, float y) {
		g.drawString(string, x-viewport.getX(), y-viewport.getY());		
	}
	
	public long getId() {
		return this.id;
	}
	
	public void setId(long id) {
		this.id = id;
	}

	public int getDamage() {
		return damage;
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}

	public int getEnergy() {
		return energy;
	}

	public void setEnergy(int energy) {
		this.energy = energy;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;		
	}
	
	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public Vector2f getPreviousPosition() {
		return previousPosition;
	}

	public void setPreviousPosition(Vector2f previousPosition) {
		this.previousPosition = previousPosition;
	}

	public boolean isCollided() {
		return collided;
	}

	public void setCollided(boolean collided) {
		this.collided = collided;
	}

	public float getPreviousRotation() {
		return previousRotation;
	}

	public void setPreviousRotation(float previousRotation) {
		this.previousRotation = previousRotation;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Class: " + getClass().getSimpleName());
		sb.append("\nName: " + this.name);
		sb.append("\nId: " + this.id);
		return sb.toString();
	}
}
