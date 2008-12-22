package com.deadtroll.backoff.mapeditor;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;

public class MapCanvas extends JPanel {

	private static final long serialVersionUID = -5874855195395652057L;
	
	private int horizontalBlocks;
	private int verticalBlocks;
	private Point currentMouseLocation;

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

			public void mouseClicked(MouseEvent arg0) {
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

		if (this.currentMouseLocation!=null) {
			for (int i=0; i<this.horizontalBlocks; i++) {
				if (this.currentMouseLocation.getX()>(blockWidth*(i))
						&& this.currentMouseLocation.getX()<(blockWidth*(i+1))) {
					for (int j=0; j<this.verticalBlocks; j++) {
						if (this.currentMouseLocation.getY()>(blockHeight*(j))
								&& this.currentMouseLocation.getY()<(blockHeight*(j+1))) {
							g.setColor(Color.GRAY);
							g.fillRect(blockWidth*(i), blockHeight*(j), blockWidth, blockHeight);
							break;
						}
					}
					break;
				}
			}
		}
	}
	
//	public void setCursorImage(Image img) {
//		Toolkit toolkit = Toolkit.getDefaultToolkit();
//		Point hotSpot = new Point(0,0);
//		Cursor cursor = toolkit.createCustomCursor(img, hotSpot, "Pencil");
//		this.setCursor(cursor);
//	}
	
}

