package com.deadtroll.backoff;

import javax.swing.JFrame;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

import com.deadtroll.backoff.debug.DebugControlPanel;
import com.deadtroll.backoff.scene.GameScene;

public class Main {

	static boolean debugMode = false;
	
	public static void main(String[] args) {
		if (args.length>0) {
			Main.readConsoleArgs(args);
		}
		if (Main.debugMode) {
	    	System.out.println("DEBUG");
	    	JFrame f = new DebugControlPanel();
	    	f.setSize(200,300);
	    	f.setVisible(true);
	    }
		try { 
		    AppGameContainer container = new AppGameContainer(new BackOffGame());
		    container.setDisplayMode(GameScene.GAME_WIDTH,GameScene.GAME_HEIGHT,false);
		    container.setMinimumLogicUpdateInterval(1000/20);
		    container.setMaximumLogicUpdateInterval(1000/30);
		    container.start();
		} catch (SlickException e) { 
		    e.printStackTrace(); 
		}
	}

	private static void readConsoleArgs(String[] args) {
		for (String s : args) {
			if (s.startsWith("-debug")) {
				Main.debugMode = true;
			}
		}
	}
	
}
