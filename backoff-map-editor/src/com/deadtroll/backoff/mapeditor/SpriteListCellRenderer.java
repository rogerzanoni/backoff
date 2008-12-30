package com.deadtroll.backoff.mapeditor;

import java.awt.Component;
import java.awt.image.BufferedImage;

import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;

public class SpriteListCellRenderer extends DefaultListCellRenderer {

	private static final long serialVersionUID = -7732928416511459578L;

	@Override
	public Component getListCellRendererComponent(JList list, Object obj, int index, boolean arg3, boolean arg4) {
		JLabel l = (JLabel)super.getListCellRendererComponent(list, obj, index, arg3, arg4);
		if (obj instanceof BufferedImage) {
			l.setIcon(new ImageIcon((BufferedImage)obj));
		}
		l.setText(""+index);
		return l; 
	}
}
