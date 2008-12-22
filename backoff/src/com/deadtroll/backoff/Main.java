package com.deadtroll.backoff;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

public class Main {

	public static void main(String[] args) {
		try { 
		    AppGameContainer container = new AppGameContainer(new BackOffGame());
		    container.setDisplayMode(BackOffGame.GAME_WIDTH, BackOffGame.GAME_HEIGHT,false);
		    container.setMinimumLogicUpdateInterval(1000/20);
		    container.setMaximumLogicUpdateInterval(1000/30);
		    container.start();
		} catch (SlickException e) { 
		    e.printStackTrace(); 
		}
	}
	
}
