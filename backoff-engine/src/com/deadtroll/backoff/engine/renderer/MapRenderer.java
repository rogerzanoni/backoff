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
import com.deadtroll.backoff.engine.viewport.ViewPort;

public class MapRenderer implements IRenderer {

	protected List<IGameObject> gameObjects = new ArrayList<IGameObject>();
	protected Map map;
	protected ViewPort viewPort;

	public void addGameObject(IGameObject gameObject) {
		this.gameObjects.add(gameObject);
	}

	public void removeGameObject(IGameObject gameObject) {
		this.gameObjects.remove(gameObject);
	}

	public void setMap(Map map) {
		this.map = map;
	}

	public void render(Graphics g) {
		int currentLayer = 0;
		SpriteSheet sheet = this.map.getMapSpriteSheet();
		for (MapLayer ml : this.map.getLayers()) {
			for (int i=0; i<this.map.getMapWidth(); i++) {
				for (int j=0; j<this.map.getMapHeight(); j++) {
					MapBlock mb = ml.getMatrix()[i][j];
					if (mb!=null) {
						int id = mb.getSpriteId();
						Image tile = sheet.getSprite((id%sheet.getHorizontalCount()),id/sheet.getHorizontalCount());
						float posX = tile.getWidth()*i;
						float posY = tile.getHeight()*j;						
						if (viewPort.intersects(new Rectangle(posX, posY, tile.getWidth(),tile.getHeight()))) {
							g.drawImage(tile, posX-viewPort.getX(), posY-viewPort.getY());
						}
					}
				}
			}
			for (IGameObject go : this.gameObjects) {
				if (go.getLayer()==currentLayer) {
					if (viewPort.contains(go)) {
						go.render(g, viewPort);
					}
				}
			}
			currentLayer++;
		}
	}

	public ViewPort getViewPort() {
		return viewPort;
	}

	public void setViewPort(ViewPort viewPort) {
		this.viewPort = viewPort;
	}
}