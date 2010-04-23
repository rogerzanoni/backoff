package com.deadtroll.backoff.engine.helper;

import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

import com.deadtroll.backoff.engine.manager.MapManager;
import com.deadtroll.backoff.engine.map.Map;
import com.deadtroll.backoff.engine.map.MapBlock;
import com.deadtroll.backoff.engine.map.MapLayer;
import com.deadtroll.backoff.engine.model.IGameObject;
import com.deadtroll.backoff.engine.viewport.ViewPort;

public class CollisionUtil {
	
	public static boolean checkCollision(ViewPort viewport, IGameObject collider, IGameObject collided) {
		Shape collidedShape = collided.getCollisionShape(viewport);
		Shape colliderShape = collider.getCollisionShape(viewport);
		if (collidedShape.intersects(colliderShape))
			return true;
		return collidedShape.contains(colliderShape.getCenterX(), colliderShape.getCenterY());
	}
	
	public static Rectangle checkMapCollision(ViewPort viewport, IGameObject collider) {
		MapManager mapManager = MapManager.getInstance();
		Map map = mapManager.getMap();
		Image tile = map.getMapSpriteSheet().getSprite(0, 0);
		MapLayer ml = map.getLayers()[collider.getLayer()];
		Shape collisionShape = collider.getCollisionShape(viewport);

		int startX = (int)((viewport.getX() + collisionShape.getX())/tile.getWidth());
		int startY = (int)((viewport.getY() + collisionShape.getY())/tile.getHeight());
		
		int endX = (int)(viewport.getX() + collisionShape.getX() + viewport.getWidth())/tile.getWidth();
		int endY = (int)(viewport.getY() + collisionShape.getY() + viewport.getWidth())/tile.getHeight();

		for (int i=startX; i<=endX; i++) {
			for (int j=startY; j<=endY; j++) {
				MapBlock mb = ml.getMatrix()[i][j];
				if (mb!=null) {
					Rectangle rect = new Rectangle(tile.getWidth()*i, tile.getHeight()*j, tile.getWidth(),tile.getHeight());
					if (rect.intersects(collisionShape)) {
						return rect;
					}
					if (rect.contains(collisionShape.getCenterX(), collisionShape.getCenterY())) {
						return rect;
					}
				}
			}
		}
		return null;
	}
}
