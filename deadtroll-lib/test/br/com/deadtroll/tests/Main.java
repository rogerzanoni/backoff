package br.com.deadtroll.tests;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

public class Main {

	public static void main(String[] args) {
		try { 
		    AppGameContainer container = new AppGameContainer(new TestGame());
		    container.setDisplayMode(800, 600,false);
		    container.setMinimumLogicUpdateInterval(1000/20);
		    container.setMaximumLogicUpdateInterval(1000/30);
		    container.start();
		} catch (SlickException e) { 
		    e.printStackTrace(); 
		}
	}
	
}
