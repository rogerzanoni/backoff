package com.deadtroll.backoff.engine.manager;


public class KeyStateManager {
	private boolean downPressed;
	private boolean upPressed;
	private boolean leftPressed;
	private boolean rightPressed;
	private boolean firePressed;
	
	private static KeyStateManager instance;
	
	private KeyStateManager() {
	}
	
	public static KeyStateManager getInstance() {
		if (instance == null)
			instance = new KeyStateManager();
		return instance;
	}

	public boolean isDownPressed() {
		return downPressed;
	}

	public void setDownPressed(boolean downPressed) {
		this.downPressed = downPressed;
	}

	public boolean isUpPressed() {
		return upPressed;
	}

	public void setUpPressed(boolean upPressed) {
		this.upPressed = upPressed;
	}

	public boolean isLeftPressed() {
		return leftPressed;
	}

	public void setLeftPressed(boolean leftPressed) {
		this.leftPressed = leftPressed;
	}

	public boolean isRightPressed() {
		return rightPressed;
	}

	public void setRightPressed(boolean rightPressed) {
		this.rightPressed = rightPressed;
	}

	public boolean isFirePressed() {
		return firePressed;
	}

	public void setFirePressed(boolean firePressed) {
		this.firePressed = firePressed;
	}
	
	public void clear() {
		this.downPressed = false;
		this.firePressed = false;
		this.leftPressed = false;
		this.rightPressed = false;
		this.upPressed = false;
	}
	
	public void updateState(int key, boolean pressed) {
		switch (key) {
			case 57:
				this.firePressed = pressed;
				break;
			case 200:
				this.upPressed = pressed;
				break;
			case 203:
				this.leftPressed = pressed;
				break;
			case 205:
				this.rightPressed = pressed;
				break;
			case 208:
				this.downPressed = pressed;
				break;
			default:
				break;
		}
	}
}
