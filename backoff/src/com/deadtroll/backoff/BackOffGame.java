package com.deadtroll.backoff;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;

import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

import com.deadtroll.backoff.engine.Player;
import com.deadtroll.backoff.engine.enemy.EnemyDescriptionMap;
import com.deadtroll.backoff.engine.enemy.EnemyFactory;
import com.deadtroll.backoff.engine.enemy.IEnemy;
import com.deadtroll.backoff.engine.map.Map;
import com.deadtroll.backoff.engine.map.MapBlock;
import com.deadtroll.backoff.engine.map.MapIOUtil;
import com.deadtroll.backoff.engine.map.MapLayer;

public class BackOffGame extends BasicGame {

	public static final int GAME_WIDTH = 800;
	public static final int GAME_HEIGHT = 600;
	
	Player player;
	IEnemy[] enemies;
	Bullet[] bullets;

	boolean downPressed;
	boolean upPressed;
	boolean leftPressed;
	boolean rightPressed;
	boolean firePressed;

	boolean gameOver;
	boolean victory;
	
	byte heading; //0 = down, 1=up, 2=left, 3=right

	long lastFire;
	long fireInterval = 200;
	
	Map levelMap;
	SpriteSheet mapSpriteSheet;
	
	EnemyDescriptionMap enemyMap;

	public BackOffGame() {
		super("Back Off! 0.1");
	}

	@Override
	public void init(GameContainer container) throws SlickException {
		try {
			this.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void start() throws Exception {
		this.levelMap = MapIOUtil.loadMap("level01.map");
		
		Image img = new Image(this.levelMap.getSpriteSheet());
		this.mapSpriteSheet = new SpriteSheet(this.levelMap.getSpriteSheet(),img.getWidth()/this.levelMap.getSpriteSheetWidth(),img.getHeight()/this.levelMap.getSpriteSheetHeight());
		
		this.player = new Player();
		this.player.setSpriteSheet(new SpriteSheet("res/sprites/player.png",32,32));
		this.player.setSpeed(3);
		this.player.setEnergy(100);
		this.player.setX((BackOffGame.GAME_WIDTH/2)-(this.player.getCurrentSprite().getWidth()/2));
		this.player.setY((BackOffGame.GAME_HEIGHT/2)-(this.player.getCurrentSprite().getHeight()/2));
		
		this.bullets = new Bullet[200];
		
		this.enemies = new IEnemy[5];
		
		this.enemyMap = new EnemyDescriptionMap("res/foe");
		
		for (int i=0; i<this.enemies.length; i++) {
			this.enemies[i] = EnemyFactory.getInstance().getEnemyInstance("zombie", this.enemyMap);
			this.enemies[i].setX((int)(Math.random()*GAME_WIDTH));
			this.enemies[i].setY((int)(Math.random()*GAME_HEIGHT));
		}
		
		this.downPressed = false;
		this.leftPressed = false;
		this.rightPressed = false;
		this.upPressed = false;
		
	}

	@Override
	public void keyPressed(int key, char c) {
		this.updateKeyStatus(key, true);
	}

	@Override
	public void keyReleased(int key, char c) {
		this.updateKeyStatus(key, false);
	}

	@Override
	public void update(GameContainer container, int delta) throws SlickException {
		if (!this.gameOver && !this.victory) {
			updatePlayerPosition();
			updateEnemyPosition();
			updateBulletPosition();
			if (testFireCondition(System.currentTimeMillis())) {
				for (int i=0; i<bullets.length;i++) {
					if (bullets[i]==null) {
						Bullet b = new Bullet();
						switch (this.heading) {
						case 0:
							b.setYSpeed(b.getAbsoluteSpeed());
							b.setXSpeed(0);
							b.setX(this.player.getX()+(this.player.getCurrentSprite().getWidth()/2)-(b.getSprite().getHeight()/2));
							b.setY(this.player.getY()+this.player.getCurrentSprite().getHeight());
							break;
						case 1:
							b.setYSpeed(-b.getAbsoluteSpeed());
							b.setXSpeed(0);
							b.setX(this.player.getX()+(this.player.getCurrentSprite().getWidth()/2)-(b.getSprite().getHeight()/2));
							b.setY(this.player.getY()-b.getSprite().getHeight());
							break;
						case 2:
							b.setXSpeed(-b.getAbsoluteSpeed());
							b.setYSpeed(0);
							b.setX(this.player.getX()-b.getSprite().getWidth());
							b.setY(this.player.getY()+(this.player.getCurrentSprite().getHeight()/2)-(b.getSprite().getHeight()/2));
							break;
						case 3:
							b.setXSpeed(b.getAbsoluteSpeed());
							b.setYSpeed(0);
							b.setX(this.player.getX()+this.player.getCurrentSprite().getWidth());
							b.setY(this.player.getY()+(this.player.getCurrentSprite().getHeight()/2)-(b.getSprite().getHeight()/2));
							break;
						}
						bullets[i] = b;
						break;
					}
				}
			}
			this.checkForBulletCollisions();
			this.checkForPlayerStatus();
			this.cleanBullets();
			this.cleanEnemies();
		}
	}

	public void render(GameContainer container, Graphics g) throws SlickException {
		if (this.gameOver) {
			g.setBackground(new Color(0,0,0));
			String gameOverMessage = "GAME OVER!\n Press any key to start again.";
			int messageWidth = g.getFont().getWidth(gameOverMessage);
			int messageHeight = g.getFont().getHeight(gameOverMessage);
			g.drawString(gameOverMessage, (BackOffGame.GAME_WIDTH/2)-(messageWidth/2), (BackOffGame.GAME_HEIGHT/2)-(messageHeight/2));
		} else if (this.victory) {
			g.setBackground(new Color(0,0,0));
			String gameOverMessage = "WELL DONE!\n Press any key to start again.";
			int messageWidth = g.getFont().getWidth(gameOverMessage);
			int messageHeight = g.getFont().getHeight(gameOverMessage);
			g.drawString(gameOverMessage, (BackOffGame.GAME_WIDTH/2)-(messageWidth/2), (BackOffGame.GAME_HEIGHT/2)-(messageHeight/2));
		} else {
			int currentLayer = 0;
			for (MapLayer ml : this.levelMap.getLayers()) {
				if (currentLayer==this.levelMap.getPlayerLayer()) {
					g.drawImage(this.player.getCurrentSprite(),this.player.getX(),this.player.getY());
					for (IEnemy e : this.enemies) {
						if (e!=null) {
							g.drawImage(e.getCurrentSprite(), e.getX(), e.getY());
						}
					}
					for (Bullet b: this.bullets) {
						if (b!=null) {
							g.drawImage(b.getSprite(), b.getX(), b.getY());
						}
					}
				}
				for (int i=0; i<this.levelMap.getMapWidth(); i++) {
					for (int j=0; j<this.levelMap.getMapHeight(); j++) {
						MapBlock mb = ml.getMatrix()[i][j];
						if (mb!=null) {
							int id = mb.getSpriteId();
							Image tile = this.mapSpriteSheet.getSprite((id%this.mapSpriteSheet.getHorizontalCount()),id/this.mapSpriteSheet.getHorizontalCount());
							g.drawImage(tile, tile.getWidth()*i, tile.getHeight()*j);
						}
					}
				}
				currentLayer++;
			}
			g.drawString("Energy: "+this.player.getEnergy(), 10, 22);
			g.drawString("Score: "+this.player.getTotalScore(), 10, 34);
		}
	}

	private void updateKeyStatus(int key, boolean pressed) {
		if (this.gameOver || this.victory) {
			this.gameOver=false;
			this.victory=false;
			try {
				this.start();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			switch (key) {
				case 57:
					this.firePressed = pressed;
					break;
				case 200:
					this.upPressed = pressed;
					break;
				case 203:
					this.leftPressed = pressed;
					break;
				case 205:
					this.rightPressed = pressed;
					break;
				case 208:
					this.downPressed = pressed;
					break;
				default:
					break;
			}
		}
	}

	private void updatePlayerPosition() {
		int plW = this.player.getCurrentSprite().getWidth();
		int plH = this.player.getCurrentSprite().getHeight();

		if (this.downPressed) {
			this.player.setCurrentDirection(Player.DIRECTION_DOWN);
			if (checkForLayerCollision(this.player.getX(), this.player.getY()+this.player.getSpeed(), plW, plH)) {
				this.player.setY(this.player.getY()+this.player.getSpeed());
			}
			this.heading = 0;
		}
		if (this.upPressed) {
			this.player.setCurrentDirection(Player.DIRECTION_UP);
			if (checkForLayerCollision(this.player.getX(), this.player.getY()-this.player.getSpeed(), plW, plH)) {
				this.player.setY(this.player.getY()-this.player.getSpeed());
			}
			this.heading = 1;
		}
		if (this.leftPressed) {
			this.player.setCurrentDirection(Player.DIRECTION_LEFT);
			if (checkForLayerCollision(this.player.getX()-this.player.getSpeed(), this.player.getY(), plW, plH)) {
				this.player.setX(this.player.getX()-this.player.getSpeed());
			}
			this.heading = 2;
		}
		if (this.rightPressed) {
			this.player.setCurrentDirection(Player.DIRECTION_RIGHT);
			if (checkForLayerCollision(this.player.getX()+this.player.getSpeed(), this.player.getY(), plW, plH)) {
				this.player.setX(this.player.getX()+this.player.getSpeed());
			}
			this.heading = 3;
		}
	}

	private boolean checkForLayerCollision(int x, int y, int objectWidth, int objectHeight) {
		int playerLayer = this.levelMap.getPlayerLayer();
		Rectangle playerRect = new Rectangle(new Point(x,y),new Dimension(objectWidth, objectHeight));
		
		Image tile = this.mapSpriteSheet.getSprite(0, 0);
		
		MapLayer ml = this.levelMap.getLayers()[playerLayer];
		for (int i=0; i<this.levelMap.getMapWidth(); i++) {
			for (int j=0; j<this.levelMap.getMapHeight(); j++) {
				MapBlock mb = ml.getMatrix()[i][j];
				if (mb!=null) {
					Rectangle rect = new Rectangle(new Point(tile.getWidth()*i, tile.getHeight()*j), new Dimension(tile.getWidth(),tile.getHeight()));
					if (rect.intersects(playerRect)) {
						return false;
					}
				}
			}
		}
		return true;
	}

	private void updateEnemyPosition() {
		for (IEnemy e : this.enemies) {
			if (e!=null) {
				int eW = e.getCurrentSprite().getWidth();
				int eH = e.getCurrentSprite().getHeight();

				if (this.player.getX()>e.getX()) {
					if (this.checkForLayerCollision(e.getX()+e.getSpeed(), e.getY(), eW, eH)) {
						e.setX(e.getX()+e.getSpeed());
					}
				} else {
					if (this.checkForLayerCollision(e.getX()-e.getSpeed(), e.getY(), eW, eH)) {
						e.setX(e.getX()-e.getSpeed());
					}
				}
				
				if (this.player.getY()>e.getY()) {
					if (this.checkForLayerCollision(e.getX(), e.getY()+e.getSpeed(), eW, eH)) {
						e.setY(e.getY()+e.getSpeed());
					}
				} else {
					if (this.checkForLayerCollision(e.getX(), e.getY()-e.getSpeed(), eW, eH)) {
						e.setY(e.getY()-e.getSpeed());
					}
				}
			}
		}
	}
	
	private void updateBulletPosition() {
		for (int i=0; i<this.bullets.length; i++) {
			if (this.bullets[i]!=null) {
				Bullet b = this.bullets[i];
				if (this.checkForLayerCollision(b.getX()+b.getXSpeed(), b.getY()+b.getYSpeed(), b.getSprite().getWidth(), b.getSprite().getHeight())) {
					b.setX(b.getX()+b.getXSpeed());
					b.setY(b.getY()+b.getYSpeed()); 
				} else {
					this.bullets[i] = null;
				}
			}
		}
	}

	private boolean testFireCondition(long now) {
		if (this.firePressed) {
			if (now-this.lastFire>this.fireInterval) {
				this.lastFire = now;
				return true;
			}
		}
		return false;
	}
	
	private void checkForBulletCollisions() {
		// Preenche bounding boxes
		Rectangle[] boundingBoxes = new Rectangle[this.enemies.length];
		for (int i=0;i<this.enemies.length;i++) {
			if (this.enemies[i]!=null) {
				IEnemy e = this.enemies[i];
				boundingBoxes[i] = new Rectangle(new Point(e.getX(),e.getY()),new Dimension(e.getCurrentSprite().getWidth(),e.getCurrentSprite().getHeight())); 
			}
		}
		for (Bullet b : this.bullets) {
			if (b!=null) {
				for (int i=0;i<boundingBoxes.length;i++) {
					if (boundingBoxes[i]!=null) {
						if (boundingBoxes[i].contains(new Point(b.getX(),b.getY()))) {
							this.enemies[i].setStatus((byte)1);
							b.setStatus((byte)1);
							this.updateScore(this.enemies[i]);
							break;
						}
					}
				}
			}
		}
	}
	
	private void updateScore(IEnemy enemy) {
		this.player.setTotalScore(this.player.getTotalScore()+enemy.getScore());
	}

	private void checkForPlayerStatus() {
		Rectangle playerRect = new Rectangle(this.player.getX(), this.player.getY(), this.player.getCurrentSprite().getWidth(), this.player.getCurrentSprite().getHeight());
		Rectangle[] boundingBoxes = new Rectangle[this.enemies.length];
		boolean hasEnemies = false;
		for (int i=0;i<this.enemies.length;i++) {
			if (this.enemies[i]!=null) {
				hasEnemies = true;
				IEnemy e = this.enemies[i];
				boundingBoxes[i] = new Rectangle(new Point(e.getX(),e.getY()),new Dimension(e.getCurrentSprite().getWidth(),e.getCurrentSprite().getHeight())); 
			}
		}
		
		if (!hasEnemies) {
			this.victory=true;
		}
		
		for (int i=0;i<boundingBoxes.length;i++) {
			if (boundingBoxes[i]!=null) {
				if (boundingBoxes[i].intersects(playerRect)) {
					this.player.setEnergy(this.player.getEnergy()-this.enemies[i].getDamage());
				}
			}
		}
		if (this.player.getEnergy()<=0) {
			this.gameOver=true;
		}
	}
	
	private void cleanBullets() {
		for (int i=0; i<this.bullets.length; i++) {
			if (this.bullets[i]!=null) {
				Bullet b = this.bullets[i];
				if (	(b.getY()+b.getSprite().getHeight())<0 	|| 
						b.getY()>BackOffGame.GAME_HEIGHT 		|| 
						(b.getX()+b.getSprite().getWidth())<0 	||
						b.getX()>BackOffGame.GAME_WIDTH 		||
						b.getStatus()==1 ){
					this.bullets[i] = null;
				}
			}
		}
	}
	
	private void cleanEnemies() {
		for (int i=0; i<this.enemies.length; i++) {
			if (this.enemies[i]!=null && this.enemies[i].getStatus()==1) {
				this.enemies[i] = null;
			}
		}
	}
	
}
