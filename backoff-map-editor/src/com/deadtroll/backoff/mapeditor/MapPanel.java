package com.deadtroll.backoff.mapeditor;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;

import javax.swing.JPanel;

import com.deadtroll.backoff.engine.map.Map;

public class MapPanel extends JPanel {

	private static final long serialVersionUID = -7267431963914318401L;

	private MapCanvas canvas;
	private Map map;
	
	public MapPanel() {
		this.initComponents();
	}
	
	private void initComponents() {
		this.canvas = new MapCanvas(20,20);
		this.canvas.setBackground(Color.WHITE);

		this.setLayout(null);
		this.add(this.canvas);
		
		this.canvas.setLocation(new Point(5,5));
		this.canvas.setSize(new Dimension(600,540));
		this.canvas.setMinimumSize(new Dimension(600,540));
		this.canvas.setMaximumSize(new Dimension(600,540));
		
		this.setDoubleBuffered(true);
	}

	public Map getMap() {
		return map;
	}

	public void setMap(Map map) {
		this.map = map;
	}

}
