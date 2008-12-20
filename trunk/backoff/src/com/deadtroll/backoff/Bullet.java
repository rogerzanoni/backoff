package com.deadtroll.backoff;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Bullet {

	private byte status; //0=default, 1=gone
	private int absoluteSpeed;
	private Image sprite;
	private int x;
	private int y;
	private int xSpeed;
	private int ySpeed;

	public Bullet() {
		try {
			this.setSprite(new Image("res/bullet.png"));
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

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
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

}
