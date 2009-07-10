package com.deadtroll.backoff.mapeditor;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.deadtroll.backoff.engine.map.Map;

public class MapPanel extends JPanel {

	private static final long serialVersionUID = -7267431963914318401L;

	private MapCanvas canvas;
	private Map map;
	private JScrollPane scrollPane;

	public MapPanel() {
		this.initComponents();
	}

	private void initComponents() {
		this.canvas = new MapCanvas(20,20);
		this.canvas.setBackground(Color.WHITE);

		this.setLayout(null);
		

		this.canvas.setLocation(new Point(5,5));
		this.canvas.setSize(new Dimension(600,540));
		this.canvas.setMinimumSize(new Dimension(600,540));
		
		this.scrollPane = new JScrollPane(this.canvas);
		this.scrollPane.setSize(new Dimension(600,540));
		this.scrollPane.setAutoscrolls(true);
		this.add(this.scrollPane);
		this.setDoubleBuffered(true);
	}

	public Map getMap() {
		return map;
	}

	public void setMap(Map map) {
		this.map = map;
		if (map!=null) {
			try {
				BufferedImage img = ImageIO.read(new File(this.map.getSpriteSheetPath()));
				int tileWidth = img.getWidth()/this.map.getSpriteSheetWidth();
				int tileHeight = img.getHeight()/this.map.getSpriteSheetHeight();
				
				this.canvas.setHorizontalBlocks(this.map.getMapWidth());
				this.canvas.setVerticalBlocks(this.map.getMapHeight());
				
				this.canvas.setSize(tileWidth*this.map.getMapWidth(),tileHeight*this.map.getMapHeight());
				this.canvas.setMap(map);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
