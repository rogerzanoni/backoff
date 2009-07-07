package br.com.deadtroll.tests;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;

import br.com.deadtroll.scene.AbstractScene;

import com.deadtroll.backoff.engine.bullet.Bullet;
import com.deadtroll.backoff.engine.bullet.IBullet;
import com.deadtroll.backoff.engine.enemy.EnemyDescriptionMap;
import com.deadtroll.backoff.engine.enemy.IEnemy;
import com.deadtroll.backoff.engine.map.Map;
import com.deadtroll.backoff.engine.map.MapBlock;
import com.deadtroll.backoff.engine.map.MapIOUtil;
import com.deadtroll.backoff.engine.map.MapLayer;
import com.deadtroll.backoff.engine.model.Heading;
import com.deadtroll.backoff.engine.model.TransientStatus;
import com.deadtroll.backoff.engine.player.IPlayer;
import com.deadtroll.backoff.engine.renderer.MapRenderer;
import com.deadtroll.backoff.engine.sound.SoundEvent;
import com.deadtroll.backoff.engine.viewport.ViewPort;
import com.deadtroll.backoff.engine.weapon.Weapon;

public class GameScene extends AbstractScene {

	public static final int GAME_WIDTH = 800;
	public static final int GAME_HEIGHT = 600;
	public static final int WORLD_WIDTH = 1024;
	public static final int WORLD_HEIGHT = 768;

	private IPlayer player;
	private IEnemy[] enemies;
	private IBullet[] bullets;

	private boolean downPressed;
	private boolean upPressed;
	private boolean leftPressed;
	private boolean rightPressed;
	private boolean firePressed;

	private boolean gameOver;
	private boolean victory;

	private long lastFire;
	
	private Map levelMap;
	
	private EnemyDescriptionMap enemyMap;
	
	private MapRenderer renderer;

	@Override
	public void init() throws SlickException {
		try {
			this.levelMap = MapIOUtil.loadMap("res/level01.map");
			this.renderer = new MapRenderer();
			this.renderer.setViewPort(new ViewPort(GAME_WIDTH, GAME_HEIGHT, new Vector2f(0,0), WORLD_WIDTH, WORLD_HEIGHT));
			this.renderer.setMap(this.levelMap);
			
			this.player = new BradTeeper();
			this.player.setSpriteSheet(new SpriteSheet("res/sprites/player.png",32,32));
			this.player.setHeading(Heading.DOWN);
			this.player.setSpeed(3);
			this.player.setEnergy(100);
			this.player.setPosition(new Vector2f(0,0));
			this.player.setLayer(1);
			this.player.setDebugMode(true);
			this.renderer.addGameObject(this.player);
			
			this.bullets = new Bullet[200];
			
			this.enemies = new DummyEnemy[5];
			
			this.enemyMap = new EnemyDescriptionMap("res/foe");
			
			for (int i=0; i<this.enemies.length; i++) {
				this.enemies[i] = EnemyFactory.getInstance().getEnemyInstance("zombie", this.enemyMap);
				this.enemies[i].setPosition(new Vector2f((float)(Math.random()*GAME_WIDTH),(float)(Math.random()*GAME_HEIGHT)));
				this.renderer.addGameObject(this.enemies[i]);
			}
			
			this.downPressed = false;
			this.leftPressed = false;
			this.rightPressed = false;
			this.upPressed = false;
		} catch (Exception e){
			throw new SlickException(e.getMessage(),e);
		}
	}

	@Override
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
			g.drawString("ViewPort: " + this.renderer.getViewPort(), 10, 70);
			g.drawString("PlayerPos: " + this.player.getCenter(), 10, 82);
			g.drawString("Map: w: " + WORLD_WIDTH  + " h: " + WORLD_HEIGHT , 10, 94);
		}
	}

	@Override
	public void update(int delta) {
		if (!this.gameOver && !this.victory) {
			updatePlayerPosition();
			updateViewPortPosition();
			updateEnemyPosition();
			updateBulletPosition();
			if (testFireCondition(System.currentTimeMillis())) {
				for (int i=0; i<bullets.length;i++) {
					if (bullets[i]==null) {
						Bullet b = new Bullet();
						float x = 0;
						float y = 0;						
						switch (this.player.getHeading()) {						
						case DOWN:
							x = this.player.getPosition().x+(this.player.getCurrentSprite().getWidth()/2)-(b.getCurrentSprite().getHeight()/2);
							y = this.player.getPosition().y+this.player.getCurrentSprite().getHeight();
							break;
						case UP:
							x = this.player.getPosition().x+(this.player.getCurrentSprite().getWidth()/2)-(b.getCurrentSprite().getHeight()/2);
							y = this.player.getPosition().y-b.getCurrentSprite().getHeight();
							break;
						case LEFT:
							x = this.player.getPosition().x-b.getCurrentSprite().getWidth();
							y = this.player.getPosition().y+(this.player.getCurrentSprite().getHeight()/2)-(b.getCurrentSprite().getHeight()/2);
							break;
						case RIGHT:
							x = this.player.getPosition().x+this.player.getCurrentSprite().getWidth();
							y = this.player.getPosition().y+(this.player.getCurrentSprite().getHeight()/2)-(b.getCurrentSprite().getHeight()/2);
							break;
						}
						b.setHeading(this.player.getHeading());
						b.setPosition(new Vector2f(x,y));
						this.renderer.addGameObject(b);
						bullets[i] = b;
						break;
					}
				}
			}
			this.checkForBulletCollisions();
			this.checkForPlayerStatus();
			this.cleanBullets();
			this.cleanEnemies();if (!this.gameOver && !this.victory) {
				updatePlayerPosition();
				updateViewPortPosition();
				updateEnemyPosition();
				updateBulletPosition();
				if (testFireCondition(System.currentTimeMillis())) {
					for (int i=0; i<bullets.length;i++) {
						if (bullets[i]==null) {
							Bullet b = new Bullet();
							float x = 0;
							float y = 0;						
							switch (this.player.getHeading()) {						
							case DOWN:
								x = this.player.getPosition().x+(this.player.getCurrentSprite().getWidth()/2)-(b.getCurrentSprite().getHeight()/2);
								y = this.player.getPosition().y+this.player.getCurrentSprite().getHeight();
								break;
							case UP:
								x = this.player.getPosition().x+(this.player.getCurrentSprite().getWidth()/2)-(b.getCurrentSprite().getHeight()/2);
								y = this.player.getPosition().y-b.getCurrentSprite().getHeight();
								break;
							case LEFT:
								x = this.player.getPosition().x-b.getCurrentSprite().getWidth();
								y = this.player.getPosition().y+(this.player.getCurrentSprite().getHeight()/2)-(b.getCurrentSprite().getHeight()/2);
								break;
							case RIGHT:
								x = this.player.getPosition().x+this.player.getCurrentSprite().getWidth();
								y = this.player.getPosition().y+(this.player.getCurrentSprite().getHeight()/2)-(b.getCurrentSprite().getHeight()/2);
								break;
							}
							b.setHeading(this.player.getHeading());
							b.setPosition(new Vector2f(x,y));
							this.renderer.addGameObject(b);
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
	}

	@Override
	public void keyPressed(int key, char c) {
		this.updateKeyStatus(key, true);
	}

	@Override
	public void keyReleased(int key, char c) {
		this.updateKeyStatus(key, false);
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
				default:
					break;
			}
		}
	}

	private void updatePlayerPosition() {
		int plW = this.player.getCurrentSprite().getWidth();
		int plH = this.player.getCurrentSprite().getHeight();

		if (this.downPressed) {
			this.player.setHeading(Heading.DOWN);
			if (checkForLayerCollision(this.player.getPosition().x, this.player.getPosition().y+this.player.getSpeed(), plW, plH)) {
				this.player.setPosition(new Vector2f(this.player.getPosition().x,this.player.getPosition().y+this.player.getSpeed()));
			}
		}
		if (this.upPressed) {
			this.player.setHeading(Heading.UP);
			if (checkForLayerCollision(this.player.getPosition().x, this.player.getPosition().y-this.player.getSpeed(), plW, plH)) {
				this.player.getPosition().y = this.player.getPosition().y-this.player.getSpeed();
			}
		}
		if (this.leftPressed) {
			this.player.setHeading(Heading.LEFT);
			if (checkForLayerCollision(this.player.getPosition().y-this.player.getSpeed(), this.player.getPosition().y, plW, plH)) {
				this.player.getPosition().x = this.player.getPosition().x-this.player.getSpeed();
			}
		}
		if (this.rightPressed) {
			this.player.setHeading(Heading.RIGHT);
			if (checkForLayerCollision(this.player.getPosition().x+this.player.getSpeed(), this.player.getPosition().y, plW, plH)) {
				this.player.getPosition().x = this.player.getPosition().x+this.player.getSpeed();
			}
		}
	}
	
	private void updateViewPortPosition() {
		ViewPort viewPort = this.renderer.getViewPort();
		float diffx = this.player.getCenter().x - viewPort.getCenterX();
		float diffy = this.player.getCenter().y - viewPort.getCenterY();
		viewPort.scroll(new Vector2f(viewPort.getX() + diffx, viewPort.getY() + diffy));
	}

	private boolean checkForLayerCollision(float x, float y, int objectWidth, int objectHeight) {
		Rectangle playerRect = new Rectangle(x,y,objectWidth,objectHeight);
		
		Image tile = this.levelMap.getMapSpriteSheet().getSprite(0, 0);
		
		MapLayer ml = this.levelMap.getLayers()[this.player.getLayer()];
		for (int i=0; i<this.levelMap.getMapWidth(); i++) {
			for (int j=0; j<this.levelMap.getMapHeight(); j++) {
				MapBlock mb = ml.getMatrix()[i][j];
				if (mb!=null) {
					Rectangle rect = new Rectangle(tile.getWidth()*i, tile.getHeight()*j, tile.getWidth(),tile.getHeight());
					if (playerRect.intersects(rect)) {
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
				IBullet b = this.bullets[i];
				int xSpeed=0;
				int ySpeed=0;
				if (b.getHeading() == Heading.UP)
					ySpeed = -b.getSpeed();
				else if (b.getHeading() == Heading.DOWN)
					ySpeed = b.getSpeed();
				else if (b.getHeading() == Heading.LEFT)
					xSpeed = -b.getSpeed();
				else if (b.getHeading() == Heading.RIGHT)
					xSpeed = b.getSpeed();
				if (this.checkForLayerCollision(b.getPosition().x+xSpeed, b.getPosition().y+ySpeed, b.getCurrentSprite().getWidth(), b.getCurrentSprite().getHeight())) {
					b.setPosition(new Vector2f(b.getPosition().x+xSpeed,b.getPosition().y+ySpeed)); 
				} else {
					this.renderer.removeGameObject(this.bullets[i]);
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
					this.player.playSoundEvent(SoundEvent.FIRE);
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
				boundingBoxes[i] = new Rectangle(e.getPosition().x,e.getPosition().y,e.getCurrentSprite().getWidth(),e.getCurrentSprite().getHeight()); 
			}
		}
		for (IBullet b : this.bullets) {
			if (b!=null) {
				for (int i=0;i<boundingBoxes.length;i++) {
					if (boundingBoxes[i]!=null) {
						if (boundingBoxes[i].contains(b.getPosition().x,b.getPosition().y)) {
							this.enemies[i].setStatus(TransientStatus.STATUS_GONE);
							this.enemies[i].playSoundEvent(SoundEvent.DEATH);
							b.setStatus(TransientStatus.STATUS_GONE);
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
				boundingBoxes[i] = new Rectangle(e.getPosition().x,e.getPosition().y,e.getCurrentSprite().getWidth(),e.getCurrentSprite().getHeight()); 
			}
		}
		
		if (!hasEnemies) {
			this.victory=true;
		}
		
		for (int i=0;i<boundingBoxes.length;i++) {
			if (boundingBoxes[i]!=null) {
				if (boundingBoxes[i].intersects(playerRect)) {
					this.player.addDamage(this.enemies[i].getDamage());
					this.player.playSoundEvent(SoundEvent.HIT);
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
				IBullet b = this.bullets[i];
				if (	(b.getPosition().y+b.getCurrentSprite().getHeight())<0 	|| 
						b.getPosition().y>GAME_HEIGHT 		|| 
						(b.getPosition().x+b.getCurrentSprite().getWidth())<0 	||
						b.getPosition().x>GAME_WIDTH 		||
						b.getStatus()==TransientStatus.STATUS_GONE ){
					this.renderer.removeGameObject(this.bullets[i]);
					this.bullets[i] = null;
				}
			}
		}
	}
	
	private void cleanEnemies() {
		for (int i=0; i<this.enemies.length; i++) {
			if (this.enemies[i]!=null && this.enemies[i].getStatus()==TransientStatus.STATUS_GONE) {
				this.renderer.removeGameObject(this.enemies[i]);
				this.enemies[i] = null;
			}
		}
	}

}
