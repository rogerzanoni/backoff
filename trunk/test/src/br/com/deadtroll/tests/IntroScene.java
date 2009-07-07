package br.com.deadtroll.tests;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

import br.com.deadtroll.scene.AbstractScene;

public class IntroScene extends AbstractScene {

	private long updateCount = 0;
	private long slideTime = 2000;
	private int currentSlide;
	private Image[] slides;
	private Sound sfx;
	
	@Override
	public void render(Graphics g) {
		Image img = this.slides[this.currentSlide]; 
		g.drawImage(img, (this.getGame().getContainer().getWidth()-img.getWidth())/2, (this.getGame().getContainer().getHeight()-img.getHeight())/2);
	}

	@Override
	public void init() {
		this.slides = new Image[5];
		this.currentSlide = 0;
		try {
			this.slides[0] = new Image(0,0);
			this.slides[1] = new Image("res/slick.gif");
			this.slides[2] = new Image(0,0);
			this.slides[3] = new Image("res/deadtroll.png");
			this.slides[4] = new Image(0,0);
			this.sfx = new Sound("res/boom.wav");
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void stop() {
		super.stop();
		this.slides = null;
		this.sfx = null;
	}

	@Override
	public void update(int delta) {
		this.updateCount += delta;
		boolean lastSlide = !(this.currentSlide+1<this.slides.length);
		boolean changeSlide = this.updateCount>=this.slideTime;
		if (changeSlide) {
			if (!lastSlide) {
				if (this.currentSlide==2) {
					this.sfx.play();
				}
				this.currentSlide++;
				this.updateCount = 0;
			} else {
				try {
					this.getGame().setActiveScene(TestGame.SCENE_MAIN_MENU, true);
				} catch (SlickException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public void keyPressed(int key, char c) {
		this.updateCount = this.slideTime;
	}

	@Override
	public void keyReleased(int key, char c) {
	}

}
