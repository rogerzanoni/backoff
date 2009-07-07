package com.deadtroll.backoff;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import com.deadtroll.backoff.engine.model.AbstractGameObject;
import com.deadtroll.backoff.engine.viewport.ViewPort;

public class Bullet extends AbstractGameObject {

	private byte status; //0=default, 1=gone
	private int absoluteSpeed;
	private Image sprite;
	private int xSpeed;
	private int ySpeed;

	public Bullet() {
		try {
			this.setSprite(new Image("res/sprites/bullet.png"));
			this.setAbsoluteSpeed(10);
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
	
	public Image getSprite() {
		return sprite;
	}

	public void setSprite(Image sprite) {
		this.sprite = sprite;
	}

	public byte getStatus() {
		return status;
	}

	public void setStatus(byte status) {
		this.status = status;
	}

	public int getXSpeed() {
		return xSpeed;
	}

	public void setXSpeed(int speed) {
		xSpeed = speed;
	}

	public int getYSpeed() {
		return ySpeed;
	}

	public void setYSpeed(int speed) {
		ySpeed = speed;
	}

	public int getAbsoluteSpeed() {
		return absoluteSpeed;
	}

	public void setAbsoluteSpeed(int absoluteSpeed) {
		this.absoluteSpeed = absoluteSpeed;
	}

	@Override
	public Image getCurrentSprite() {
		return this.sprite;
	}

	@Override
	public void render(Graphics g, ViewPort viewPort) {
		if (viewPort.contains(this.position.x, this.position.y)) {
			g.drawImage(this.getCurrentSprite(), this.position.x, this.position.y);
		}
	}

	@Override
	public void finalizeGO() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void initializeGO() {
		// TODO Auto-generated method stub
		
	}

}
