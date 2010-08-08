package com.deadtroll.backoff.engine.renderer;

import org.lwjgl.opengl.GLContext;
import org.lwjgl.opengl.Pbuffer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.ImageBuffer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

import com.deadtroll.backoff.engine.manager.EntityManager;
import com.deadtroll.backoff.engine.manager.MapManager;
import com.deadtroll.backoff.engine.map.Map;
import com.deadtroll.backoff.engine.map.MapBlock;
import com.deadtroll.backoff.engine.map.MapLayer;
import com.deadtroll.backoff.engine.model.IGameObject;
import com.deadtroll.backoff.engine.viewport.ViewPort;

public class Renderer implements IRenderer {
	protected boolean updated;
	protected Image renderedMapLayers[];
	protected boolean offScreenBuffers;
	
	// managers
	protected EntityManager entityManager;
	protected MapManager mapManager;
	
	public Renderer() {
//		this.offScreenBuffers = checkOffScreenBuffersSupport();
		this.entityManager = EntityManager.getInstance();
		this.mapManager = MapManager.getInstance();
	}

	public void render(Graphics g) {
		if (this.offScreenBuffers) {
			renderOffScreenMap(g);
		} else {
			renderMap(g);
		}
	}
	
	protected void renderOffScreenMap(Graphics g) {
		Map map = this.mapManager.getMap();
		if (this.isUpdated() || this.renderedMapLayers==null) {
			int currentLayer = 0;
			this.renderedMapLayers = new Image[map.getLayers().length];

			for (MapLayer ml : map.getLayers()) {
				SpriteSheet sheet = map.getMapSpriteSheet();
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
		
		ViewPort viewPort = mapManager.getViewPort();
		for (@SuppressWarnings("unused")MapLayer ml : map.getLayers()) {
			g.drawImage(this.renderedMapLayers[currentLayer].getSubImage((int)viewPort.getX(), (int)viewPort.getY(), (int)viewPort.getWidth(), (int)viewPort.getHeight()),0,0);
			for (IGameObject go : this.entityManager.getGameObjects()) {
				go.render(g, viewPort);
			}
			currentLayer++;
		}
	}
	
	protected void renderMap(Graphics g) {
		Map map = this.mapManager.getMap();
		int currentLayer = 0;
		SpriteSheet sheet = map.getMapSpriteSheet();
		Image block = sheet.getSprite(0,0);
		ViewPort viewPort = this.mapManager.getViewPort();
		
		int startX = (int)(viewPort.getX()/block.getWidth());
		int startY = (int)(viewPort.getY()/block.getHeight());
		
		int endX = (int)((viewPort.getX()+viewPort.getWidth())/block.getWidth());
		int endY = (int)((viewPort.getY()+viewPort.getHeight())/block.getHeight());
			
		for (MapLayer ml : map.getLayers()) {
			sheet.startUse();
			for (int i=startX; i<=endX; i++) {
				for (int j=startY; j<=endY; j++) {
					float posX = block.getWidth()*i;
					float posY = block.getHeight()*j;
					MapBlock mb = ml.getMatrix()[i][j];
					if (mb!=null) {
						int id = mb.getSpriteId();
						float x = posX-viewPort.getX();
						float y = posY-viewPort.getY();
						int xCell = id%sheet.getHorizontalCount();
						int yCell = id/sheet.getHorizontalCount();						
						sheet.renderInUse((int)x, (int)y, xCell, yCell);
					}
				}
			}
			sheet.endUse();
			
			for (IGameObject go : this.entityManager.getGameObjects()) {				
				go.render(g, viewPort);
			}
			currentLayer++;
		}
	}

	public boolean isUpdated() {
		return updated;
	}

	public void setUpdated(boolean updated) {
		this.updated = updated;
	}
	
	protected boolean checkOffScreenBuffersSupport() {
		boolean fbo = GLContext.getCapabilities().GL_EXT_framebuffer_object;
		boolean pbuffer = (Pbuffer.getCapabilities() & Pbuffer.PBUFFER_SUPPORTED) != 0;
		boolean pbufferRT = (Pbuffer.getCapabilities() & Pbuffer.RENDER_TEXTURE_SUPPORTED) != 0;
		return fbo || pbuffer || pbufferRT;
	}
}