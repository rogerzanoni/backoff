package com.deadtroll.backoff.engine.renderer;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Rectangle;

import com.deadtroll.backoff.engine.map.Map;
import com.deadtroll.backoff.engine.map.MapBlock;
import com.deadtroll.backoff.engine.map.MapLayer;
import com.deadtroll.backoff.engine.model.IGameObject;

public class MapRenderer implements IRenderer {

	protected List<IGameObject> gameObjects = new ArrayList<IGameObject>();
	protected Map map;

	@Override
	public void addGameObject(IGameObject gameObject) {
		this.gameObjects.add(gameObject);
	}

	@Override
	public void removeGameObject(IGameObject gameObject) {
		this.gameObjects.remove(gameObject);
	}

	public void setMap(Map map) {
		this.map = map;
	}

	@Override
	public void render(Graphics g) {
		int currentLayer = 0;
		Rectangle viewport = this.map.getVisibleArea();
		SpriteSheet sheet = this.map.getMapSpriteSheet();
		for (MapLayer ml : this.map.getLayers()) {
			for (int i=0; i<this.map.getMapWidth(); i++) {
				for (int j=0; j<this.map.getMapHeight(); j++) {
					MapBlock mb = ml.getMatrix()[i][j];
					if (mb!=null) {
						//TODO testar viewport
						int id = mb.getSpriteId();
						Image tile = sheet.getSprite((id%sheet.getHorizontalCount()),id/sheet.getHorizontalCount());
						g.drawImage(tile, tile.getWidth()*i, tile.getHeight()*j);
					}
				}
			}
			for (IGameObject go : this.gameObjects) {
				if (go.getLayer()==currentLayer) {
					if (viewport.includes(go.getPosition().x,go.getPosition().y)) {
						go.render(g);	
					}
				}
			}
			currentLayer++;
		}
	}
}