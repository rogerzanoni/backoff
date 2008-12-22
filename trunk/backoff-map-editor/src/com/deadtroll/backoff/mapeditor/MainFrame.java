package com.deadtroll.backoff.mapeditor;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

public class MainFrame extends JFrame {

	private static final long serialVersionUID = -8554714199727598269L;

	private MapPanel mapPanel;
	private JPanel palettePanel;
	private JSplitPane splitPane;
	
	private JMenuBar menuBar;
	private JMenu fileMenu;
	
	public MainFrame() {
		this.initComponents();
		
		this.configureFrame();
	}

	private void initComponents() {
		this.menuBar = new JMenuBar();
		this.fileMenu = new JMenu("File");
		this.menuBar.add(this.fileMenu);
		
		this.setJMenuBar(menuBar);
		
		this.splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		this.mapPanel = new MapPanel();
		this.palettePanel = new JPanel();
		
		this.palettePanel.setBackground(Color.BLUE);
		
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
}
