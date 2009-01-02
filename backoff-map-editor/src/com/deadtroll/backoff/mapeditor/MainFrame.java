package com.deadtroll.backoff.mapeditor;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;
import javax.swing.JSplitPane;
import javax.swing.filechooser.FileFilter;

import com.deadtroll.backoff.engine.map.Map;
import com.deadtroll.backoff.engine.map.MapIOUtil;

public class MainFrame extends JFrame {

	private static final long serialVersionUID = -8554714199727598269L;

	private MapPanel mapPanel;
	private PalettePanel palettePanel;
	private JSplitPane splitPane;

	private JMenuBar menuBar;
	private JMenu fileMenu;
	private JMenuItem newFileMenuItem;
	private JMenuItem openFileMenuItem;
	private JMenuItem saveFileMenuItem;
	private JMenuItem closeMenuItem;

	private Map map;
	private String fileLocation;

	public MainFrame() {
		this.initComponents();
		this.configureFrame();
	}

	private void initComponents() {
		this.menuBar = new JMenuBar();
		this.fileMenu = new JMenu("File");
		this.newFileMenuItem = new JMenuItem("New");
		this.openFileMenuItem = new JMenuItem("Open");
		this.saveFileMenuItem = new JMenuItem("Save");
		this.closeMenuItem = new JMenuItem("Close");
		
		this.newFileMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				MainFrame.this.newMapAction();
			}
		});
		
		this.openFileMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				MainFrame.this.openMapAction();
			}
		});
		
		this.saveFileMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				MainFrame.this.saveMapAction();
			}
		});
		
		this.fileMenu.add(this.newFileMenuItem);
		this.fileMenu.add(this.openFileMenuItem);
		this.fileMenu.add(new JSeparator());
		this.fileMenu.add(this.saveFileMenuItem);
		this.fileMenu.add(new JSeparator());
		this.fileMenu.add(this.closeMenuItem);
		
		this.menuBar.add(this.fileMenu);
		
		this.setJMenuBar(menuBar);
		
		this.splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		this.mapPanel = new MapPanel();
		this.palettePanel = new PalettePanel();
		
		this.splitPane.setRightComponent(this.palettePanel);
		this.splitPane.setLeftComponent(this.mapPanel);
		this.splitPane.setDividerLocation(610);
		this.splitPane.setDividerSize(0);

		this.getContentPane().add(this.splitPane);
	}

	private void configureFrame() {
		this.setTitle("Back-off Map Editor");
		this.setExtendedState(this.getExtendedState() | JFrame.MAXIMIZED_BOTH);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(new Dimension(800,600));
		this.setResizable(false);
	}

	private void newMapAction() {
		new NewMapDialog(this,true).setVisible(true);
		this.map = ApplicationController.getInstance().getCurrentMap();
		this.mapPanel.setMap(this.map);
		this.palettePanel.setSpriteSheet(this.map.getSpriteSheet());
		this.palettePanel.setSpriteSheetWidth(this.map.getSpriteSheetWidth());
		this.palettePanel.setSpriteSheetHeight(this.map.getSpriteSheetHeight());
		this.palettePanel.updatePalette();
		this.fileLocation = "";
	}

	protected void openMapAction() {
		JFileChooser fileChooser = new JFileChooser("../");
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fileChooser.setFileFilter(new FileFilter() {

			@Override
			public boolean accept(File f) {
				return f.isDirectory() || f.getName().toUpperCase().endsWith(".MAP");
			}

			@Override
			public String getDescription() {
				return "Map Definition Files (*.map)";
			}

		});
		int status = fileChooser.showOpenDialog(this);
		if (status==JFileChooser.APPROVE_OPTION) {
			this.fileLocation = fileChooser.getSelectedFile().getAbsolutePath();
		}
		try {
			Map m = MapIOUtil.loadMap(this.fileLocation);
			ApplicationController.getInstance().setCurrentMap(m);
			this.map = m;
			this.mapPanel.setMap(this.map);
			this.palettePanel.setSpriteSheet(this.map.getSpriteSheet());
			this.palettePanel.setSpriteSheetWidth(this.map.getSpriteSheetWidth());
			this.palettePanel.setSpriteSheetHeight(this.map.getSpriteSheetHeight());
			this.palettePanel.updatePalette();
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
	
	protected void saveMapAction() {
		if (this.map!=null) {
			if (this.fileLocation.length()==0) {
				JFileChooser fileChooser = new JFileChooser("../");
				fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				fileChooser.setFileFilter(new FileFilter() {

					@Override
					public boolean accept(File f) {
						return f.isDirectory() || f.getName().toUpperCase().endsWith(".MAP");
					}

					@Override
					public String getDescription() {
						return "Map Definition Files (*.map)";
					}

				});
				int status = fileChooser.showSaveDialog(this);
				if (status==JFileChooser.APPROVE_OPTION) {
					this.fileLocation = fileChooser.getSelectedFile().getAbsolutePath();
				}
			}
			if (!this.fileLocation.toUpperCase().endsWith(".MAP")) {
				this.fileLocation += ".map";
			}
			try {
				MapIOUtil.saveMap(map, this.fileLocation);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
