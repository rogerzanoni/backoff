package com.deadtroll.backoff.engine.renderer;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.ImageBuffer;
import org.newdawn.slick.SlickException;
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
	protected boolean updated;
	protected Image renderedMapLayers[];

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
		if (this.isUpdated() || this.renderedMapLayers==null) {
			int currentLayer = 0;
			this.renderedMapLayers = new Image[this.map.getLayers().length];

			for (MapLayer ml : this.map.getLayers()) {
				SpriteSheet sheet = this.map.getMapSpriteSheet();
				Image block = sheet.getSprite(0,0);
				this.renderedMapLayers[currentLayer] = new ImageBuffer(map.getMapWidth()*block.getWidth(),map.getMapHeight()*block.getHeight()).getImage();
				
				for (int i=0; i<map.getMapWidth(); i++) {
					for (int j=0; j<map.getMapHeight(); j++) {
						float posX = block.getWidth()*i;
						float posY = block.getHeight()*j;
						MapBlock mb = ml.getMatrix()[i][j];
						if (mb!=null) {
							int id = mb.getSpriteId();
							Image tile = sheet.getSprite((id%sheet.getHorizontalCount()),id/sheet.getHorizontalCount());
							try {
								this.renderedMapLayers[currentLayer].getGraphics().drawImage(tile, posX, posY);
							} catch (SlickException e) {
								throw new RuntimeException(e);
							}
						}
					}
				}
				currentLayer++;
			}
		}
		
		int currentLayer = 0;
		
		for (MapLayer ml : this.map.getLayers()) {
			g.drawImage(this.renderedMapLayers[currentLayer].getSubImage((int)this.viewPort.getX(), (int)this.viewPort.getY(), (int)this.viewPort.getWidth(), (int)this.viewPort.getHeight()),0,0);
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

	public boolean isUpdated() {
		return updated;
	}

	public void setUpdated(boolean updated) {
		this.updated = updated;
	}
	
}