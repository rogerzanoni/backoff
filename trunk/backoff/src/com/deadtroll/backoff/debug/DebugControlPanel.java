package com.deadtroll.backoff.debug;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;
import javax.swing.JFrame;

import com.deadtroll.backoff.engine.managers.RenderManager;

public class DebugControlPanel extends JFrame {

	private JCheckBox chkDrawCollisionShapes;
	private JCheckBox chkDrawObjectId;
	
	public DebugControlPanel() {
		super("Debug Control Panel");
		this.initComponents();
	}

	private void initComponents() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(null);
		
		this.chkDrawCollisionShapes = new JCheckBox("Draw Collision Shapes");
		this.add(this.chkDrawCollisionShapes);
		this.chkDrawCollisionShapes.setLocation(10, 10);
		this.chkDrawCollisionShapes.setSize(200,20);
		this.chkDrawCollisionShapes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				DebugControlPanel.this.chkDrawCollisionShapesAction();				
			}
		});
		
		this.chkDrawObjectId = new JCheckBox("Draw Objects ID");
		this.add(this.chkDrawObjectId);
		this.chkDrawObjectId.setLocation(10, 40);
		this.chkDrawObjectId.setSize(200,20);
		this.chkDrawObjectId.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				DebugControlPanel.this.chkDrawObjectIdAction();				
			}
		});
	}

	protected void chkDrawCollisionShapesAction() {
		RenderManager.getInstance().setDrawCollisionShapes(this.chkDrawCollisionShapes.isSelected());
	}
	
	protected void chkDrawObjectIdAction() {
		RenderManager.getInstance().setDrawObjectId(this.chkDrawObjectId.isSelected());
	}
	
}
