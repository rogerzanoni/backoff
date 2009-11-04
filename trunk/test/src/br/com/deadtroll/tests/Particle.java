package br.com.deadtroll.tests;

import org.newdawn.slick.Color;

public class Particle {

	private int x;
	private int y;
	private int size;
	private Color color;
	private int speed;
//	private int orbitWay = 1;
	

	public Particle() {
		this((int)(Math.random()*800),(int)(Math.random()*600),new Color((int)(Math.random()*255),(int)(Math.random()*255),(int)(Math.random()*255)),3,20);
	}
	
	public Particle(int x, int y, Color color, int size, int speed) {
		this.x = x;
		this.y = y;
		this.color = color;
		this.size = size;
		this.speed = speed;
	}
	
	public void update() {
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
	
	public int getSize() {
		return size;
	}
	
	public void setSize(int size) {
		this.size = size;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

}
