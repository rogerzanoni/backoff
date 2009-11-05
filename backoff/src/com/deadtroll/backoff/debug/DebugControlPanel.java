package com.deadtroll.backoff.debug;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JTextArea;

import com.deadtroll.backoff.engine.managers.RenderManager;
import com.deadtroll.backoff.engine.managers.SoundManager;
import com.deadtroll.backoff.engine.sound.SoundQueueEvent;

public class DebugControlPanel extends JFrame {

	private JCheckBox chkDrawCollisionShapes;
	private JCheckBox chkDrawObjectId;
	private JTextArea txaSoundQueue;
	
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
		
		this.txaSoundQueue = new JTextArea();
		this.add(this.txaSoundQueue);
		this.txaSoundQueue.setLocation(10,70);
		this.txaSoundQueue.setSize(500,350);
		this.txaSoundQueue.setEditable(false);
		this.txaSoundQueue.setEnabled(false);
		
		new Thread() {
			@Override
			public void run() {
				try {
					while (true) {
						Thread.sleep(100);
						DebugControlPanel.this.updateSoundQueue();
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}.start();
		this.setSize(550,550);
		this.setMinimumSize(new Dimension(550,500));
	}

	protected void chkDrawCollisionShapesAction() {
		RenderManager.getInstance().setDrawCollisionShapes(this.chkDrawCollisionShapes.isSelected());
	}
	
	protected void chkDrawObjectIdAction() {
		RenderManager.getInstance().setDrawObjectId(this.chkDrawObjectId.isSelected());
	}

	private void updateSoundQueue() {
		List<SoundQueueEvent> soundQueue = SoundManager.getInstance().getSoundEventQueue();
		this.txaSoundQueue.setText("");
		StringBuilder sb = new StringBuilder("");
		for (SoundQueueEvent s : soundQueue) {
			sb.append(s.getSoundEvent().getSource().getClass().getName()+s.getSoundEvent().getEventType().toString()+" - "+(System.currentTimeMillis()+s.getPlayTime())+" - "+s.getBehaviour().name());
		}
		this.txaSoundQueue.setText(sb.toString());
	}
}
