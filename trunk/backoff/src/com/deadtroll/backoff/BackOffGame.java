package com.deadtroll.backoff;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;

import br.com.deadtroll.game.AbstractGame;

import com.deadtroll.backoff.engine.bullet.IBullet;
import com.deadtroll.backoff.engine.enemy.EnemyDescriptionMap;
import com.deadtroll.backoff.engine.enemy.IEnemy;
import com.deadtroll.backoff.engine.map.Map;
import com.deadtroll.backoff.engine.map.MapBlock;
import com.deadtroll.backoff.engine.map.MapIOUtil;
import com.deadtroll.backoff.engine.map.MapLayer;
import com.deadtroll.backoff.engine.model.TransientStatus;
import com.deadtroll.backoff.engine.player.IPlayer;
import com.deadtroll.backoff.engine.renderer.MapRenderer;
import com.deadtroll.backoff.engine.sound.SoundEventType;
import com.deadtroll.backoff.engine.sound.SoundManager;
import com.deadtroll.backoff.engine.sound.SoundSequenceType;
import com.deadtroll.backoff.engine.viewport.ViewPort;
import com.deadtroll.backoff.engine.weapon.Weapon;

public class BackOffGame extends AbstractGame {

	public static final int GAME_WIDTH = 640;
	public static final int GAME_HEIGHT = 480;
	public static final int WORLD_WIDTH = 800;
	public static final int WORLD_HEIGHT = 600;

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

	public BackOffGame() {
		super("Back Off! 0.1");
	}

	@Override
	public void gameInit() throws SlickException {
		try {
			this.levelMap = MapIOUtil.loadMap("level01.map");
			this.renderer = new MapRenderer();
			this.renderer.setViewPort(new ViewPort(GAME_WIDTH, GAME_HEIGHT, new Vector2f(0,0), WORLD_WIDTH, WORLD_HEIGHT));
			this.renderer.setMap(this.levelMap);
			
			this.player = new BradTeeper();
			this.player.setSpriteSheet(new SpriteSheet("res/sprites/brad.png",29,18));
			this.player.setEnergy(100);
			this.player.setPosition(new Vector2f(1,1));
			this.player.setLayer(1);
			this.player.setDebugMode(true);
			this.renderer.addGameObject(this.player);
			
			this.bullets = new Bullet[200];
			
			this.enemies = new DummyEnemy[5];
			
			this.enemyMap = new EnemyDescriptionMap("res/foe");
			
			for (int i=0; i<this.enemies.length; i++) {
				this.enemies[i] = EnemyFactory.getInstance().getEnemyInstance("zombie", this.enemyMap);
				this.enemies[i].setPosition(new Vector2f((float)(Math.random()*GAME_WIDTH),(float)(Math.random()*GAME_HEIGHT)));
				this.enemies[i].setSpeed(new Vector2f(1,1));
				this.renderer.addGameObject(this.enemies[i]);
			}
			
			this.downPressed = false;
			this.leftPressed = false;
			this.rightPressed = false;
			this.upPressed = false;
			this.firePressed = false;
		} catch (Exception e){
			throw new SlickException(e.getMessage(),e);
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
	public void update(GameContainer container, int delta) throws SlickException {
		SoundManager.getInstance().update(delta);
		if (!this.gameOver && !this.victory) {
			updatePlayerPosition();
			updateViewPortPosition();
			updateEnemyPosition();
			updateBulletPosition();
			if (testFireCondition(System.currentTimeMillis())) {
				for (int i=0; i<bullets.length;i++) {
					if (bullets[i]==null) {
						Bullet b = new Bullet();
						float x = this.player.getPosition().x+(this.player.getCurrentSprite().getWidth()/2)-(b.getCurrentSprite().getHeight()/2); 
						float y = this.player.getPosition().y+this.player.getCurrentSprite().getHeight();
						double rotation = (this.player.getRotation()/180)*Math.PI;
						b.setSpeed(new Vector2f((float)Math.cos(rotation),(float)Math.sin(rotation)).scale(15f));
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
			this.renderer.render(g);
			g.drawString("Energy: "+this.player.getEnergy(), 10, 22);
			g.drawString("Score: "+this.player.getTotalScore(), 10, 34);
			g.drawString("Magazine Ammo: "+this.player.getActiveWeapon().getMagazineAmmo(), 10, 46);
			g.drawString("Ammo: "+this.player.getActiveWeapon().getAmmo(), 10, 58);
			g.drawString("ViewPort: " + this.renderer.getViewPort(), 10, 70);
			g.drawString("PlayerPos: " + this.player.getCenter()+" (rot: "+this.player.getRotation()+")", 10, 82);
			g.drawString("Map: w: " + WORLD_WIDTH  + " h: " + WORLD_HEIGHT , 10, 94);
		}
	}

	private void updateKeyStatus(int key, boolean pressed) {
		if (this.gameOver || this.victory) {
			this.gameOver=false;
			this.victory=false;
			try {
				this.init(this.getContainer());
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
		double rotation = (this.player.getRotation()/180)*Math.PI;
		Vector2f nextPosition = null;
		if (this.downPressed) {
			nextPosition = this.player.getPosition().copy().sub(new Vector2f((float)Math.cos(rotation),(float)Math.sin(rotation)).scale(2f));
			if (checkForLayerCollision(nextPosition.x, nextPosition.y, this.player.getCurrentSprite().getWidth(), this.player.getCurrentSprite().getHeight())) {
				this.player.setPosition(nextPosition);
			}
		}
		if (this.upPressed) {
			nextPosition = this.player.getPosition().copy().add(new Vector2f((float)Math.cos(rotation),(float)Math.sin(rotation)).scale(5f));
			if (checkForLayerCollision(nextPosition.x, nextPosition.y, this.player.getCurrentSprite().getWidth(), this.player.getCurrentSprite().getHeight())) {
				this.player.setPosition(nextPosition);
			}
		}
		if (this.leftPressed) {
			this.player.setRotation(((this.player.getRotation()-10f)+360)%360);
		}
		if (this.rightPressed) {
			this.player.setRotation(((this.player.getRotation()+10f)+360)%360);
		}
	}
	
	private void updateViewPortPosition() {
		ViewPort viewPort = this.renderer.getViewPort();
		float diffx = this.player.getCenter().x - viewPort.getCenterX();
		float diffy = this.player.getCenter().y - viewPort.getCenterY();
		viewPort.scroll(new Vector2f(viewPort.getX() + diffx, viewPort.getY() + diffy));
	}

	private boolean checkForLayerCollision(float x, float y, int objectWidth, int objectHeight) {
		if (x<0 || y<0)
			return false;
		
		Image tile = this.levelMap.getMapSpriteSheet().getSprite(0, 0);

		int startX = (int)(this.player.getPosition().getX()/tile.getWidth());
		int startY = (int)(this.player.getPosition().getY()/tile.getHeight());
		
		int endX = (int)((this.player.getPosition().getX()+this.player.getCurrentSprite().getWidth())/tile.getWidth());
		int endY = (int)((this.player.getPosition().getY()+this.player.getCurrentSprite().getHeight())/tile.getHeight());
		
		Rectangle playerRect = new Rectangle(x,y,objectWidth,objectHeight);

		MapLayer ml = this.levelMap.getLayers()[this.player.getLayer()];
		for (int i=startX; i<endX; i++) {
			for (int j=startY; j<endY; j++) {
				MapBlock mb = ml.getMatrix()[i][j];
				if (mb!=null) {
					Shape rect = this.player.getCollisionShape(this.renderer.getViewPort());
					if (rect.contains(playerRect.getX(), playerRect.getY()) || rect.contains(playerRect.getX()+playerRect.getWidth(), playerRect.getY()+playerRect.getHeight())) {
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
					if (this.checkForLayerCollision(e.getPosition().x+e.getSpeed().x, e.getPosition().y, eW, eH)) {
						e.setPosition(new Vector2f(e.getPosition().x+e.getSpeed().x,e.getPosition().y));
					}
				} else {
					if (this.checkForLayerCollision(e.getPosition().x-e.getSpeed().x, e.getPosition().y, eW, eH)) {
						e.setPosition(new Vector2f(e.getPosition().x-e.getSpeed().x,e.getPosition().y));
					}
				}
				
				if ((int)this.player.getPosition().y>e.getPosition().y) {
					if (this.checkForLayerCollision(e.getPosition().x, e.getPosition().y+e.getSpeed().y, eW, eH)) {
						e.setPosition(new Vector2f(e.getPosition().x,e.getPosition().y+e.getSpeed().y));
					}
				} else {
					if (this.checkForLayerCollision(e.getPosition().x, e.getPosition().y-e.getSpeed().y, eW, eH)) {
						e.setPosition(new Vector2f(e.getPosition().x,e.getPosition().y-e.getSpeed().y));
					}
				}
			}
		}
	}
	
	private void updateBulletPosition() {
		for (int i=0; i<this.bullets.length; i++) {
			if (this.bullets[i]!=null) {
				IBullet b = this.bullets[i];
				if (this.checkForLayerCollision(b.getPosition().x, b.getPosition().y, b.getCurrentSprite().getWidth(), b.getCurrentSprite().getHeight())) {
					b.setPosition(b.getPosition().add(b.getSpeed())); 
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
					this.player.playSoundEvent(SoundEventType.FIRE, SoundSequenceType.RANDOM, false, false);
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
							this.enemies[i].playSoundEvent(SoundEventType.DEATH, SoundSequenceType.RANDOM, true, false);
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
					this.player.playSoundEvent(SoundEventType.HIT, SoundSequenceType.FIRST, true, false);
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
						b.getPosition().y>BackOffGame.GAME_HEIGHT 		|| 
						(b.getPosition().x+b.getCurrentSprite().getWidth())<0 	||
						b.getPosition().x>BackOffGame.GAME_WIDTH 		||
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
