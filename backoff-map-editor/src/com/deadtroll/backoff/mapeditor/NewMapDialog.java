package com.deadtroll.backoff.mapeditor;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileFilter;

import com.deadtroll.backoff.engine.map.Map;

public class NewMapDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	
	private JLabel lblName;
	private JLabel lblResourcesFile;
	private JLabel lblMapSize;
	private JLabel lblMapSizeWidth;
	private JLabel lblMapSizeHeight;
	private JLabel lblResourcesSize;
	private JLabel lblResourcesSizeWidth;
	private JLabel lblResourcesSizeHeight;
	private JLabel lblLayers;
	private JLabel lblPlayerLayer;
	private JButton buttonSearch;
	private JButton buttonOK;
	private JButton buttonCancel;
	private JTextField txtName;
	private JTextField txtResourcesFile;
	private JTextField txtMapWidth;
	private JTextField txtMapHeight;
	private JTextField txtResourcesWidth;
	private JTextField txtResourcesHeight;
	private JTextField txtLayers;
	private JTextField txtPlayerLayer;
	private JPanel previewPanel;

	private File resourceFile;
	
	public NewMapDialog(Frame parent, boolean modal) {
		super(parent, modal);
		this.initComponents();
		this.configure();
	}

	private void initComponents() {
		this.setLayout(null);
		
		this.lblName = new JLabel("Map name");
		this.lblLayers = new JLabel("Number of layers");
		this.lblMapSize = new JLabel("Map size (in blocks)");
		this.lblMapSizeHeight = new JLabel("H:");
		this.lblMapSizeWidth = new JLabel("W:");
		this.lblResourcesFile = new JLabel("Resources file");
		this.lblResourcesSize = new JLabel("Resources size (in blocks)");
		this.lblResourcesSizeHeight = new JLabel("H:");
		this.lblResourcesSizeWidth = new JLabel("W:");
		this.lblPlayerLayer = new JLabel("Player layer (from 1st to layer number)");
		
		this.txtName = new JTextField();
		this.txtResourcesFile = new JTextField();
		this.txtMapHeight = new JTextField();
		this.txtMapWidth = new JTextField();
		this.txtResourcesHeight = new JTextField();
		this.txtResourcesWidth = new JTextField();
		this.txtLayers = new JTextField();
		this.txtPlayerLayer = new JTextField();
		
		this.previewPanel = new JPanel();

		this.buttonSearch = new JButton("...");
		this.buttonSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				NewMapDialog.this.searchButtonAction();
			}
		});
		this.buttonOK = new JButton("OK");
		this.buttonCancel = new JButton("Cancel");
		
		this.lblName.setSize(280,20);
		this.lblName.setLocation(10,10);
		this.txtName.setSize(240,20);
		this.txtName.setLocation(10,30);
		
		this.lblResourcesFile.setSize(280,20);
		this.lblResourcesFile.setLocation(10,50);
		this.txtResourcesFile.setSize(240,20);
		this.txtResourcesFile.setLocation(10,70);
		this.buttonSearch.setSize(30,20);
		this.buttonSearch.setLocation(255,70);

		this.lblMapSize.setSize(280,20);
		this.lblMapSize.setLocation(10,95);
		this.lblMapSizeWidth.setSize(20,20);
		this.lblMapSizeWidth.setLocation(10,115);
		this.txtMapWidth.setSize(60, 20);
		this.txtMapWidth.setLocation(35, 115);
		this.lblMapSizeHeight.setSize(20,20);
		this.lblMapSizeHeight.setLocation(100, 115);
		this.txtMapHeight.setSize(60, 20);
		this.txtMapHeight.setLocation(125, 115);
		
		this.lblResourcesSize.setSize(280,20);
		this.lblResourcesSize.setLocation(10,140);
		this.lblResourcesSizeWidth.setSize(20,20);
		this.lblResourcesSizeWidth.setLocation(10,160);
		this.txtResourcesWidth.setSize(60, 20);
		this.txtResourcesWidth.setLocation(35, 160);
		this.lblResourcesSizeHeight.setSize(20,20);
		this.lblResourcesSizeHeight.setLocation(100, 160);
		this.txtResourcesHeight.setSize(60, 20);
		this.txtResourcesHeight.setLocation(125, 160);
		
		this.lblLayers.setSize(280,20);
		this.lblLayers.setLocation(10,185);
		this.txtLayers.setSize(60,20);
		this.txtLayers.setLocation(10,205);
		
		this.lblPlayerLayer.setSize(280,20);
		this.lblPlayerLayer.setLocation(10,230);
		this.txtPlayerLayer.setSize(60,20);
		this.txtPlayerLayer.setLocation(10,250);
		
		this.previewPanel.setSize(380, 450);
		this.previewPanel.setLocation(300, 10);
		this.previewPanel.setBackground(Color.BLACK);
		
		this.buttonOK.setSize(100, 20);
		this.buttonOK.setLocation(10, 430);
		this.buttonOK.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				NewMapDialog.this.okButtonAction();
			}
		});
		this.buttonCancel.setSize(100, 20);
		this.buttonCancel.setLocation(150, 430);
		this.buttonCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				NewMapDialog.this.cancelButtonAction();
			}
		});
		
		this.add(this.lblName);
		this.add(this.txtName);
		this.add(this.lblResourcesFile);
		this.add(this.txtResourcesFile);
		this.add(this.buttonSearch);
		this.add(this.lblMapSize);
		this.add(this.lblMapSizeWidth);
		this.add(this.txtMapWidth);
		this.add(this.lblMapSizeHeight);
		this.add(this.txtMapHeight);
		this.add(this.lblResourcesSize);
		this.add(this.lblResourcesSizeWidth);
		this.add(this.txtResourcesWidth);
		this.add(this.lblResourcesSizeHeight);
		this.add(this.txtResourcesHeight);
		this.add(this.lblLayers);
		this.add(this.txtLayers);
		this.add(this.lblPlayerLayer);
		this.add(this.txtPlayerLayer);
		this.add(this.previewPanel);
		this.add(this.buttonOK);
		this.add(this.buttonCancel);
	}

	private void configure() {
		this.setTitle("New Map");
		this.setSize(new Dimension(700,500));
		this.setResizable(false);
	}
	
	protected void searchButtonAction() {
		JFileChooser fileChooser = new JFileChooser("../");
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fileChooser.setFileFilter(new FileFilter() {

			@Override
			public boolean accept(File f) {
				if (f.isDirectory() || 
						f.getName().toUpperCase().endsWith(".PNG") ||
						f.getName().toUpperCase().endsWith(".JPG") ||
						f.getName().toUpperCase().endsWith(".JPEG") ||
						f.getName().toUpperCase().endsWith(".BMP") ||
						f.getName().toUpperCase().endsWith(".GIF")) {
					return true;
				}
				return false;
			}

			@Override
			public String getDescription() {
				return "Image Resource Files";
			}
			
		});
		int status = fileChooser.showOpenDialog(this);
		if (status==JFileChooser.APPROVE_OPTION) {
			this.resourceFile = fileChooser.getSelectedFile();
			this.txtResourcesFile.setText(this.resourceFile.getAbsolutePath());
			this.updatePreview();
		}
	}
	
	protected void okButtonAction() {
		try {
			Map map = new Map();
			map.setDescription(this.txtName.getText());
			map.setMapHeight(Integer.parseInt(this.txtMapHeight.getText()));
			map.setMapWidth(Integer.parseInt(this.txtMapWidth.getText()));

			int resourceW = Integer.parseInt(this.txtResourcesWidth.getText());
			int resourceH = Integer.parseInt(this.txtResourcesHeight.getText());

			map.setSpriteSheet(this.txtResourcesFile.getText());
			map.setSpriteSheetHeight(resourceH);
			map.setSpriteSheetWidth(resourceW);
			map.setPlayerLayer(Integer.parseInt(this.txtPlayerLayer.getText())-1);

			map.initializeMapLayers(Integer.parseInt(this.txtLayers.getText()));
			
			ApplicationController.getInstance().setCurrentMap(map);

			this.dispose();
		} catch (Throwable t) {
			JOptionPane.showMessageDialog(this, "Invalid data. Please check it out.");
			t.printStackTrace();
		}
	}
	
	protected void cancelButtonAction() {
		this.dispose();
	}

	private void updatePreview() {
		// TODO implementar
	}
	
}
