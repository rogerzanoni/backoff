package com.deadtroll.backoff.engine.map;

public class MapLayer {

	private String id;
	private int zOrder;
	private MapBlock[][] matrix;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public MapBlock[][] getMatrix() {
		return matrix;
	}

	public void setMatrix(MapBlock[][] matrix) {
		this.matrix = matrix;
	}

	public int getZOrder() {
		return zOrder;
	}

	public void setZOrder(int order) {
		zOrder = order;
	}

}
