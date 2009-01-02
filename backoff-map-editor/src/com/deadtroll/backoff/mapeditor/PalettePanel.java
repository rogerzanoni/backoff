package com.deadtroll.backoff.mapeditor;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class PalettePanel extends JPanel {

	private static final long serialVersionUID = -6011064243807766897L;

	private String spriteSheet; 
	private int spriteSheetWidth;
	private int spriteSheetHeight;
	private JList spriteList;
	private JScrollPane scrollSprite;
	private JList layerList;
	private JScrollPane scrollLayer;
	
	public PalettePanel() {
		this.initComponents();
	}

	private void initComponents() {
		this.setLayout(null);
		
		this.scrollSprite = new JScrollPane();
		this.scrollLayer = new JScrollPane();

		this.spriteList = new JList();
		this.spriteList.setCellRenderer(new SpriteListCellRenderer());
		this.spriteList.addListSelectionListener(new ListSelectionListener() {

			public void valueChanged(ListSelectionEvent evt) {
				if (!evt.getValueIsAdjusting()) {
					PalettePanel.this.listSelectionAction(PalettePanel.this.spriteList.getSelectedIndex());
				}
			}
			
		});
		
		this.layerList = new JList();
		this.layerList.addListSelectionListener(new ListSelectionListener() {

			public void valueChanged(ListSelectionEvent evt) {
				if (!evt.getValueIsAdjusting()) {
					PalettePanel.this.layerSelectionAction(PalettePanel.this.layerList.getSelectedIndex());
				}
			}
			
		});
		
		this.scrollSprite.setSize(160,400);
		this.scrollSprite.setLocation(10,10);
		
		this.scrollLayer.setSize(160,120);
		this.scrollLayer.setLocation(10,420);

		this.scrollSprite.setViewportView(this.spriteList);
		this.scrollLayer.setViewportView(this.layerList);
		
		this.add(this.scrollSprite);
		this.add(this.scrollLayer);
	}

	protected void layerSelectionAction(int index) {
		ApplicationController.getInstance().setCurrentLayer(index);
		this.getParent().invalidate();
	}

	protected void listSelectionAction(int index) {
		ApplicationController.getInstance().setCurrentSpriteIndex(index);
	}

	public void updatePalette() {
		HashMap<Integer, BufferedImage> spriteMap = new HashMap<Integer, BufferedImage>();
		if (this.spriteSheetWidth>0 && this.spriteSheetHeight>0) {
			try {
				DefaultListModel listModel = new DefaultListModel();
				BufferedImage img = ImageIO.read(new File(this.spriteSheet));
				
				int blockWidth = img.getWidth()/this.spriteSheetWidth;
				int blockHeight = img.getHeight()/this.spriteSheetHeight;
				int id=0;
				for (int i=0; i<this.spriteSheetHeight; i++) {
					for (int j = 0; j<this.spriteSheetWidth; j++) {
						BufferedImage clip = img.getSubimage(j*blockWidth, i*blockHeight, blockWidth, blockHeight);
						spriteMap.put(id, clip);
						listModel.addElement(clip);
						id++;
					}
				}
				this.spriteList.setModel(listModel);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		DefaultListModel listModel = new DefaultListModel();
		int numLayers = ApplicationController.getInstance().getCurrentMap().getLayers().length;
		for (int i=0; i<numLayers; i++) {
			listModel.add(i, "Layer "+i);
		}
		this.layerList.setModel(listModel);
		this.layerList.setSelectedIndex(0);

		ApplicationController.getInstance().setSpriteMap(spriteMap);
	}

	public String getSpriteSheet() {
		return spriteSheet;
	}

	public void setSpriteSheet(String spriteSheet) {
		this.spriteSheet = spriteSheet;
	}

	public int getSpriteSheetHeight() {
		return spriteSheetHeight;
	}

	public void setSpriteSheetHeight(int spriteSheetHeight) {
		this.spriteSheetHeight = spriteSheetHeight;
	}

	public int getSpriteSheetWidth() {
		return spriteSheetWidth;
	}

	public void setSpriteSheetWidth(int spriteSheetWidth) {
		this.spriteSheetWidth = spriteSheetWidth;
	}
	
}