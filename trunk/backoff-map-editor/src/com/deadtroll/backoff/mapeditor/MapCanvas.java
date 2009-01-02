package com.deadtroll.backoff.mapeditor;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import com.deadtroll.backoff.engine.map.Map;
import com.deadtroll.backoff.engine.map.MapBlock;

public class MapCanvas extends JPanel {

	private static final long serialVersionUID = -5874855195395652057L;
	
	private int horizontalBlocks;
	private int verticalBlocks;
	private Point currentMouseLocation;
	private Map map;

	public MapCanvas(int horizontalBlocks, int verticalBlocks) {
		this.horizontalBlocks = horizontalBlocks;
		this.verticalBlocks = verticalBlocks;
		this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		this.addEvents();
	}
	
	private void addEvents() {
		this.addMouseMotionListener(new MouseMotionListener() {

			public void mouseDragged(MouseEvent arg0) {
			}

			public void mouseMoved(MouseEvent evt) {
				MapCanvas.this.currentMouseLocation = evt.getPoint();
				MapCanvas.this.repaint();
			}
			
		});
		
		this.addMouseListener(new MouseListener() {

			public void mouseClicked(MouseEvent evt) {
				MapCanvas.this.processClick(evt.getButton()==MouseEvent.BUTTON3);
			}

			public void mouseEntered(MouseEvent arg0) {
			}

			public void mouseExited(MouseEvent arg0) {
				MapCanvas.this.currentMouseLocation = null;
				MapCanvas.this.repaint();
			}

			public void mousePressed(MouseEvent arg0) {
			}

			public void mouseReleased(MouseEvent arg0) {
			}
			
		});
	}

	protected void processClick(boolean erase) {
		if (MapCanvas.this.map!=null) {
			int blockWidth = (int)(this.getWidth()/this.horizontalBlocks);
			int blockHeight = (int)(this.getHeight()/this.verticalBlocks);
			if (this.currentMouseLocation!=null) {
				for (int i=0; i<this.horizontalBlocks; i++) {
					if (this.currentMouseLocation.getX()>(blockWidth*(i))
							&& this.currentMouseLocation.getX()<(blockWidth*(i+1))) {
						for (int j=0; j<this.verticalBlocks; j++) {
							if (this.currentMouseLocation.getY()>(blockHeight*(j))
									&& this.currentMouseLocation.getY()<(blockHeight*(j+1))) {
								
								Map m = ApplicationController.getInstance().getCurrentMap();
								int layerIndex = ApplicationController.getInstance().getCurrentLayer();
								int spriteIndex = ApplicationController.getInstance().getCurrentSpriteIndex();
								MapBlock mb = null;
								if (!erase) {
									mb = new MapBlock();
									mb.setSpriteId(spriteIndex);
								}
								m.getLayers()[layerIndex].getMatrix()[i][j] = mb;	
								break;
							}
						}
						break;
					}
				}
			}
		}		
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		
		g.setColor(Color.BLACK);
		
		int blockWidth = (int)(this.getWidth()/this.horizontalBlocks);
		int blockHeight = (int)(this.getHeight()/this.verticalBlocks);
		
		for (int i=0; i<this.horizontalBlocks; i++) {
			g.drawLine(blockWidth*(i+1), 0, blockWidth*(i+1), this.getHeight());
		}
		
		for (int i=0; i<this.verticalBlocks; i++) {
			g.drawLine(0, blockHeight*(i+1), this.getWidth(), blockHeight*(i+1));
		}

		Map m = ApplicationController.getInstance().getCurrentMap();
		int layerIndex = ApplicationController.getInstance().getCurrentLayer();
		if (m!=null) {
			for (int i=0; i<this.horizontalBlocks; i++) {
				for (int j=0; j<this.verticalBlocks; j++) {
					MapBlock mb = m.getLayers()[layerIndex].getMatrix()[i][j];
					if (mb!=null) {
						BufferedImage img = ApplicationController.getInstance().getSpriteMap().get(mb.getSpriteId());
						Image scaled = img.getScaledInstance(blockWidth, blockHeight, Image.SCALE_FAST);
						
						g.drawImage(scaled,blockWidth*(i), blockHeight*(j),null);
					}
				}
			}
		}
	}

	public int getHorizontalBlocks() {
		return horizontalBlocks;
	}

	public void setHorizontalBlocks(int horizontalBlocks) {
		this.horizontalBlocks = horizontalBlocks;
	}

	public int getVerticalBlocks() {
		return verticalBlocks;
	}

	public void setVerticalBlocks(int verticalBlocks) {
		this.verticalBlocks = verticalBlocks;
	}

	public Map getMap() {
		return map;
	}

	public void setMap(Map map) {
		this.map = map;
	}

//	public void setCursorImage(Image img) {
//		Toolkit toolkit = Toolkit.getDefaultToolkit();
//		Point hotSpot = new Point(0,0);
//		Cursor cursor = toolkit.createCustomCursor(img, hotSpot, "Pencil");
//		this.setCursor(cursor);
//	}
	
}

