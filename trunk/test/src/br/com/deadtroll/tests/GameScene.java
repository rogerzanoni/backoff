package br.com.deadtroll.tests;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Vector2f;

import br.com.deadtroll.scene.AbstractScene;

import com.deadtroll.backoff.engine.Player;
import com.deadtroll.backoff.engine.enemy.EnemyDescriptionMap;
import com.deadtroll.backoff.engine.enemy.EnemyFactory;
import com.deadtroll.backoff.engine.enemy.IEnemy;
import com.deadtroll.backoff.engine.map.Map;
import com.deadtroll.backoff.engine.map.MapBlock;
import com.deadtroll.backoff.engine.map.MapIOUtil;
import com.deadtroll.backoff.engine.map.MapLayer;
import com.deadtroll.backoff.engine.renderer.MapRenderer;
import com.deadtroll.backoff.engine.weapon.Weapon;

public class GameScene extends AbstractScene {

	public static final int GAME_WIDTH = 800;
	public static final int GAME_HEIGHT = 600;

	private Player player;
	private IEnemy[] enemies;
	private Bullet[] bullets;

	private boolean downPressed;
	private boolean upPressed;
	private boolean leftPressed;
	private boolean rightPressed;
	private boolean firePressed;

	private boolean gameOver;
	private boolean victory;

	private byte heading; //0 = down, 1=up, 2=left, 3=right

	private long lastFire;

	private Map levelMap;

	private EnemyDescriptionMap enemyMap;

	private MapRenderer renderer;

	@Override
	public void init() {
		try {
			this.levelMap = MapIOUtil.loadMap("res/level01.map");
			this.levelMap.setVisibleArea(new org.newdawn.slick.geom.Rectangle(0,0,GAME_WIDTH,GAME_HEIGHT));
			this.renderer = new MapRenderer();
			this.renderer.setMap(this.levelMap);
			
			this.player = new Player();
			this.player.setSpriteSheet(new SpriteSheet("res/sprites/player.png",32,32));
			this.player.setSpeed(3);
			this.player.setEnergy(100);
			this.player.setPosition(new Vector2f(0,0));
			this.renderer.addGameObject(this.player);

			this.bullets = new Bullet[200];

			this.enemies = new IEnemy[5];

			this.enemyMap = new EnemyDescriptionMap("res/foe");

			for (int i=0; i<this.enemies.length; i++) {
				this.enemies[i] = EnemyFactory.getInstance().getEnemyInstance("zombie", this.enemyMap);
				this.enemies[i].setPosition(new Vector2f((float)Math.random()*GAME_WIDTH,(float)Math.random()*GAME_HEIGHT));
				this.renderer.addGameObject(this.enemies[i]);
			}

			this.downPressed = false;
			this.leftPressed = false;
			this.rightPressed = false;
			this.upPressed = false;
		} catch (Exception e){
			e.printStackTrace();
		}
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
	public void update(int delta) {
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
								b.setX((int)this.player.getPosition().x+(this.player.getCurrentSprite().getWidth()/2)-(b.getSprite().getHeight()/2));
								b.setY((int)this.player.getPosition().y+this.player.getCurrentSprite().getHeight());
								break;
							case 1:
								b.setYSpeed(-b.getAbsoluteSpeed());
								b.setXSpeed(0);
								b.setX((int)this.player.getPosition().x+(this.player.getCurrentSprite().getWidth()/2)-(b.getSprite().getHeight()/2));
								b.setY((int)this.player.getPosition().y-b.getSprite().getHeight());
								break;
							case 2:
								b.setXSpeed(-b.getAbsoluteSpeed());
								b.setYSpeed(0);
								b.setX((int)this.player.getPosition().x-b.getSprite().getWidth());
								b.setY((int)this.player.getPosition().y+(this.player.getCurrentSprite().getHeight()/2)-(b.getSprite().getHeight()/2));
								break;
							case 3:
								b.setXSpeed(b.getAbsoluteSpeed());
								b.setYSpeed(0);
								b.setX((int)this.player.getPosition().x+this.player.getCurrentSprite().getWidth());
								b.setY((int)this.player.getPosition().y+(this.player.getCurrentSprite().getHeight()/2)-(b.getSprite().getHeight()/2));
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

	public void render(Graphics g) {
		if (this.gameOver) {
			g.setBackground(new Color(0,0,0));
			String gameOverMessage = "GAME OVER!\n Press any key to start again.";
			int messageWidth = g.getFont().getWidth(gameOverMessage);
			int messageHeight = g.getFont().getHeight(gameOverMessage);
			g.drawString(gameOverMessage, (GAME_WIDTH/2)-(messageWidth/2), (GAME_HEIGHT/2)-(messageHeight/2));
		} else if (this.victory) {
			g.setBackground(new Color(0,0,0));
			String gameOverMessage = "WELL DONE!\n Press any key to start again.";
			int messageWidth = g.getFont().getWidth(gameOverMessage);
			int messageHeight = g.getFont().getHeight(gameOverMessage);
			g.drawString(gameOverMessage, (GAME_WIDTH/2)-(messageWidth/2), (GAME_HEIGHT/2)-(messageHeight/2));
		} else {
			this.renderer.render(g);
			g.drawString("Energy: "+this.player.getEnergy(), 10, 22);
			g.drawString("Score: "+this.player.getTotalScore(), 10, 34);
			g.drawString("Magazine Ammo: "+this.player.getActiveWeapon().getMagazineAmmo(), 10, 46);
			g.drawString("Ammo: "+this.player.getActiveWeapon().getAmmo(), 10, 58);
		}
	}

	private void updateKeyStatus(int key, boolean pressed) {
		if (this.gameOver || this.victory) {
			this.gameOver=false;
			this.victory=false;
			try {
				this.init();
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
				case 1:
					this.getGame().setActiveScene(TestGame.SCENE_MAIN_MENU, false);
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
			if (checkForLayerCollision(this.player.getPosition().x, this.player.getPosition().y+this.player.getSpeed(), plW, plH)) {
				this.player.getPosition().y = this.player.getPosition().y+this.player.getSpeed();
			}
			this.heading = 0;
		}
		if (this.upPressed) {
			this.player.setCurrentDirection(Player.DIRECTION_UP);
			if (checkForLayerCollision(this.player.getPosition().x, this.player.getPosition().y-this.player.getSpeed(), plW, plH)) {
				this.player.getPosition().y = this.player.getPosition().y-this.player.getSpeed();
			}
			this.heading = 1;
		}
		if (this.leftPressed) {
			this.player.setCurrentDirection(Player.DIRECTION_LEFT);
			if (checkForLayerCollision(this.player.getPosition().y-this.player.getSpeed(), this.player.getPosition().y, plW, plH)) {
				this.player.getPosition().x = this.player.getPosition().x-this.player.getSpeed();
			}
			this.heading = 2;
		}
		if (this.rightPressed) {
			this.player.setCurrentDirection(Player.DIRECTION_RIGHT);
			if (checkForLayerCollision(this.player.getPosition().x+this.player.getSpeed(), this.player.getPosition().y, plW, plH)) {
				this.player.getPosition().x = this.player.getPosition().x+this.player.getSpeed();
			}
			this.heading = 3;
		}
	}

	private boolean checkForLayerCollision(float x, float y, int objectWidth, int objectHeight) {
		org.newdawn.slick.geom.Rectangle playerRect = new org.newdawn.slick.geom.Rectangle(x,y,objectWidth,objectHeight);
		
		Image tile = this.levelMap.getMapSpriteSheet().getSprite(0, 0);
		
		MapLayer ml = this.levelMap.getLayers()[this.player.getLayer()];
		for (int i=0; i<this.levelMap.getMapWidth(); i++) {
			for (int j=0; j<this.levelMap.getMapHeight(); j++) {
				MapBlock mb = ml.getMatrix()[i][j];
				if (mb!=null) {
					org.newdawn.slick.geom.Rectangle rect = new org.newdawn.slick.geom.Rectangle(tile.getWidth()*i, tile.getHeight()*j, tile.getWidth(),tile.getHeight());
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

				if ((int)this.player.getPosition().x>e.getPosition().x) {
					if (this.checkForLayerCollision(e.getPosition().x+e.getSpeed(), e.getPosition().y, eW, eH)) {
						e.setPosition(new Vector2f(e.getPosition().x+e.getSpeed(),e.getPosition().y));
					}
				} else {
					if (this.checkForLayerCollision(e.getPosition().x-e.getSpeed(), e.getPosition().y, eW, eH)) {
						e.setPosition(new Vector2f(e.getPosition().x-e.getSpeed(),e.getPosition().y));
					}
				}
				
				if ((int)this.player.getPosition().y>e.getPosition().y) {
					if (this.checkForLayerCollision(e.getPosition().x, e.getPosition().y+e.getSpeed(), eW, eH)) {
						e.setPosition(new Vector2f(e.getPosition().x,e.getPosition().y+e.getSpeed()));
					}
				} else {
					if (this.checkForLayerCollision(e.getPosition().x, e.getPosition().y-e.getSpeed(), eW, eH)) {
						e.setPosition(new Vector2f(e.getPosition().x,e.getPosition().y-e.getSpeed()));
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
			Weapon weapon = this.player.getActiveWeapon();
			
			long fireInterval = new Double(1/weapon.getFireRate()*1000).longValue();
			
			if (now-this.lastFire>fireInterval) {
				if (weapon.fire()) {
					this.lastFire = now;
					return true;
				}
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
				boundingBoxes[i] = new Rectangle(new Point((int)e.getPosition().x,(int)e.getPosition().y),new Dimension(e.getCurrentSprite().getWidth(),e.getCurrentSprite().getHeight())); 
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
		Rectangle playerRect = new Rectangle((int)this.player.getPosition().x, (int)this.player.getPosition().y, this.player.getCurrentSprite().getWidth(), this.player.getCurrentSprite().getHeight());
		Rectangle[] boundingBoxes = new Rectangle[this.enemies.length];
		boolean hasEnemies = false;
		for (int i=0;i<this.enemies.length;i++) {
			if (this.enemies[i]!=null) {
				hasEnemies = true;
				IEnemy e = this.enemies[i];
				boundingBoxes[i] = new Rectangle(new Point((int)e.getPosition().x,(int)e.getPosition().y),new Dimension(e.getCurrentSprite().getWidth(),e.getCurrentSprite().getHeight())); 
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
						b.getY()>GameScene.GAME_HEIGHT 		|| 
						(b.getX()+b.getSprite().getWidth())<0 	||
						b.getX()>GameScene.GAME_WIDTH 		||
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