package com.deadtroll.backoff;

import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;

public class Player implements Character {

	public static final int DIRECTION_UP = 0;
	public static final int DIRECTION_RIGHT = 1;
	public static final int DIRECTION_DOWN = 2;
	public static final int DIRECTION_LEFT = 3;
	
	int energy;
	int x,y;
	int speed;
	long totalScore;
	int currentDirection;

	SpriteSheet spriteSheet;
	int currentSpriteX, currentSpriteY;
	
	public int getEnergy() {
		return energy;
	}

	public void setEnergy(int energy) {
		this.energy = energy;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public long getTotalScore() {
		return totalScore;
	}

	public void setTotalScore(long totalScore) {
		this.totalScore = totalScore;
	}

	public Image getCurrentSprite() {
		int lineTotal = this.spriteSheet.getHorizontalCount();
		return this.spriteSheet.getSprite((this.x+this.y)%lineTotal, this.currentDirection);
		
	}

	public void setSpriteSheet(SpriteSheet sprite) {
		this.spriteSheet = sprite;
	}

	public int getCurrentDirection() {
		return currentDirection;
	}

	public void setCurrentDirection(int currentDirection) {
		this.currentDirection = currentDirection;
	}

}