package br.com.deadtroll.tests;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.TrueTypeFont;

import br.com.deadtroll.scene.AbstractScene;

public class MenuScene extends AbstractScene {

//	private Sound sfx;
	private List<Particle> particles;
	private Font menuFont;
	
	@Override
	public void init() {
		this.particles = new ArrayList<Particle>();
		for (int i=0;i<50;i++) {
			this.particles.add(new Particle());
		}
		this.menuFont = new TrueTypeFont(new java.awt.Font("Verdana",java.awt.Font.BOLD,20),true);
//		try {
//			this.sfx = new Sound("res/menu.ogg");
//			this.sfx.play();
//		} catch (SlickException e) {
//			e.printStackTrace();
//		}
	}

	@Override
	public void keyPressed(int key, char c) {
		// TODO Auto-generated method stub
	}

	@Override
	public void keyReleased(int key, char c) {
		// TODO Auto-generated method stub
	}

	@Override
	public void render(Graphics g) {
		// Particles
		Color defaultColor = g.getColor();
		for (Particle p : this.particles) {
			g.setColor(p.getColor());
			g.fillOval(p.getX(), p.getY(), p.getSize(), p.getSize());
		}
		g.setColor(defaultColor);
		// Menu
		g.setFont(menuFont);
		g.drawString("NEW GAME", 10, 50);
		g.drawString("LOAD GAME", 10, 100);
		g.drawString("SETTINGS", 10, 150);
		g.drawString("EXIT", 10, 200);
	}

	@Override
	public void update(int delta) {
		for (Particle p : this.particles) {
			p.update();
		}
	}

}
