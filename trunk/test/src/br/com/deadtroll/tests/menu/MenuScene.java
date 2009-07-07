package br.com.deadtroll.tests.menu;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;

import br.com.deadtroll.scene.AbstractScene;
import br.com.deadtroll.tests.TestGame;

public class MenuScene extends AbstractScene {

	private Music music;
	private Font menuFont;
	private List<MenuItem> menuItens;
	private Color enabledColor;
	private Color selectedColor;
	private Color disabledColor;
	private Image backgroundImage;
	
	@Override
	public void init() {
		this.menuFont = new TrueTypeFont(new java.awt.Font("Verdana",java.awt.Font.BOLD,20),true);
		
		this.enabledColor = Color.white;
		this.selectedColor = Color.orange;
		this.disabledColor = Color.gray;
	
		try {
			this.backgroundImage = new Image("res/menu_background.png");
		} catch (SlickException e) {
			e.printStackTrace();
		}
		
		this.menuItens = new ArrayList<MenuItem>();
		// TODO get from file
		this.menuItens.add(new MenuItem("Resume Game", "Resume paused game",false, TestGame.SCENE_GAME));
		this.menuItens.add(new MenuItem("New Game", "Start new game",true, TestGame.SCENE_GAME));
		this.menuItens.add(new MenuItem("Load Game", "Load saved game", false, null));
		this.menuItens.add(new MenuItem("Save Game", "Save current game",false, null));
		this.menuItens.add(new MenuItem("Exit", "Exit game",true, TestGame.SCENE_INTRO));
		
		this.menuItens.get(1).setSelected(true);
		try {
			this.music = new Music("res/menu.ogg");
			this.music.play();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void pause() {
		super.pause();
		if (this.music!=null)
			this.music.pause();
	}

	@Override
	public void stop() {
		super.stop();
		if (this.music!=null)
			this.music.stop();
		this.menuFont = null;
		this.music = null;
	}
	
	@Override
	public void keyPressed(int key, char c) {
		switch (key) {
			case 28:
				this.menuSelect();
				break;
			case 200: //UP
				this.menuUp();
				break;
			case 208: //DOWN
				this.menuDown();
				break;
			default:
		}
	}

	@Override
	public void resume() throws SlickException {
		super.resume();
		if (music!=null)
			this.music.resume();
	}
	
	private void menuSelect() {
		for (MenuItem item : this.menuItens) {
			if (item.isSelected()) {
				try {
					this.getGame().setActiveScene(item.getTargetScene(),false);
				} catch (SlickException e) {
					e.printStackTrace();
				}
				break;
			}
		}
	}
	
	private void menuDown() {
		for (int i=0; i<this.menuItens.size(); i++) {
			if (this.menuItens.get(i).isSelected()) {
				for (int j=i+1; j<this.menuItens.size(); j++) {
					if (this.menuItens.get(j).isEnabled()) {
						this.menuItens.get(j).setSelected(true);
						this.menuItens.get(i).setSelected(false);
						break;
					}
				}
				break;
			}
		}
	}

	private void menuUp() {
		for (int i=0; i<this.menuItens.size(); i++) {
			if (this.menuItens.get(i).isSelected()) {
				for (int j=i-1; j>-1; j--) {
					if (this.menuItens.get(j).isEnabled()) {
						this.menuItens.get(j).setSelected(true);
						this.menuItens.get(i).setSelected(false);
						break;
					}
				}
				break;
			}
		}
	}

	@Override
	public void keyReleased(int key, char c) {
		
	}

	@Override
	public void render(Graphics g) {
		// Backgroung
		g.drawImage(this.backgroundImage, 0, 0);
		// Menu
		g.setFont(menuFont);
		for (int i=0; i<this.menuItens.size(); i++) {
			MenuItem item = this.menuItens.get(i);
			if (!item.isEnabled()) {
				g.setColor(this.disabledColor);
			} else if (item.isSelected()) {
				g.setColor(this.selectedColor);
			} else {
				g.setColor(this.enabledColor);
			}
			g.drawString(item.getTitle(), 50, (i*50)+100);
		}
	}

	@Override
	public void update(int delta) {
	}

}
