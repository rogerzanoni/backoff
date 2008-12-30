package com.deadtroll.backoff.mapeditor;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

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
	
	public PalettePanel() {
		this.initComponents();
	}

	private void initComponents() {
		this.setLayout(null);
		
		this.scrollSprite = new JScrollPane();

		this.spriteList = new JList();
		this.spriteList.setCellRenderer(new SpriteListCellRenderer());
		this.spriteList.addListSelectionListener(new ListSelectionListener() {

			public void valueChanged(ListSelectionEvent evt) {
				if (!evt.getValueIsAdjusting()) {
					PalettePanel.this.listSelectionAction(evt.getFirstIndex());
				}
			}
			
		});
		
		this.scrollSprite.setSize(160,400);
		this.scrollSprite.setLocation(10,10);

		this.scrollSprite.setViewportView(this.spriteList);
		
		this.add(this.scrollSprite);
	}

	protected void listSelectionAction(int index) {
		BufferedImage img = (BufferedImage)this.spriteList.getModel().getElementAt(index);
		ApplicationController.getInstance().setCurrentSprite(img);
		ApplicationController.getInstance().setCurrentSpriteIndex(index);
	}

	public void updatePalette() {
		if (this.spriteSheetWidth>0 && this.spriteSheetHeight>0) {
			try {
				DefaultListModel listModel = new DefaultListModel();
				BufferedImage img = ImageIO.read(new File(this.spriteSheet));
				
				int blockWidth = img.getWidth()/this.spriteSheetWidth;
				int blockHeight = img.getHeight()/this.spriteSheetHeight;
				
				for (int i=0; i<this.spriteSheetHeight; i++) {
					for (int j = 0; j<this.spriteSheetWidth; j++) {
						BufferedImage clip = img.getSubimage(j*blockWidth, i*blockHeight, blockWidth, blockHeight);
						listModel.addElement(clip);
					}
				}
				this.spriteList.setModel(listModel);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
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