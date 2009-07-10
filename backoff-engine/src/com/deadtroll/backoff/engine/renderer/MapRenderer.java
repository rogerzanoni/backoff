package com.deadtroll.backoff.engine.renderer;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;

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
		gameObject.initializeGO();
	}

	public void removeGameObject(IGameObject gameObject) {
		gameObject.finalizeGO();
		this.gameObjects.remove(gameObject);
	}

	public void setMap(Map map) {
		this.map = map;
	}

	public void render(Graphics g) {
		int currentLayer = 0;
		SpriteSheet sheet = this.map.getMapSpriteSheet();
		Image block = sheet.getSprite(0,0);
		int startX = (int)(this.viewPort.getX()/block.getWidth());
		int startY = (int)(this.viewPort.getY()/block.getHeight());
		
		int endX = (int)((this.viewPort.getX()+this.viewPort.getWidth())/block.getWidth());
		int endY = (int)((this.viewPort.getY()+this.viewPort.getHeight())/block.getHeight());
		
		for (MapLayer ml : this.map.getLayers()) {
			for (int i=startX; i<=endX; i++) {
				for (int j=startY; j<=endY; j++) {
					float posX = block.getWidth()*i;
					float posY = block.getHeight()*j;
					MapBlock mb = ml.getMatrix()[i][j];
					if (mb!=null) {
						int id = mb.getSpriteId();
						Image tile = sheet.getSprite((id%sheet.getHorizontalCount()),id/sheet.getHorizontalCount());
						g.drawImage(tile, posX-viewPort.getX(), posY-viewPort.getY());
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