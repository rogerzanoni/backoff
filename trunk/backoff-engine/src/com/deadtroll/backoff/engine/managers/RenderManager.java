package com.deadtroll.backoff.engine.managers;

public class RenderManager {

	private static RenderManager instance;

	private boolean drawBoundingBoxes;
	private boolean drawCollisionShapes;
	private boolean drawObjectId;
	
	private RenderManager() {
	}
	
	public static RenderManager getInstance() {
		if (RenderManager.instance==null) {
			RenderManager.instance = new RenderManager();
		}
		return RenderManager.instance;
	}

	public boolean getDrawBoundingBoxes() {
		return drawBoundingBoxes;
	}

	public void setDrawBoundingBoxes(boolean drawBoundingBoxes) {
		this.drawBoundingBoxes = drawBoundingBoxes;
	}

	public boolean getDrawCollisionShapes() {
		return drawCollisionShapes;
	}

	public void setDrawCollisionShapes(boolean drawCollisionShapes) {
		this.drawCollisionShapes = drawCollisionShapes;
	}

	public boolean getDrawObjectId() {
		return drawObjectId;
	}

	public void setDrawObjectId(boolean drawObjectId) {
		this.drawObjectId = drawObjectId;
	}
	
}
