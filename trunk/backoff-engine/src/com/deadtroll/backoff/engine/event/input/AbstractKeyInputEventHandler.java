package com.deadtroll.backoff.engine.event.input;

import com.deadtroll.backoff.engine.event.AbstractEvent;
import com.deadtroll.backoff.engine.event.AbstractEventHandler;

public abstract class AbstractKeyInputEventHandler extends AbstractEventHandler {	
	
	@Override
	public void processEvent(AbstractEvent e) {
		KeyInputEvent keyEvent = (KeyInputEvent)e;
		int key = keyEvent.getKey();
		char character = keyEvent.getCharacter();
		if (KeyEventType.KEY_PRESS.equals(keyEvent.getType())) {
			keyPressed(key, character);
		} else {
			keyReleased(key, character);			
		}
	}	
	
	protected abstract void keyPressed(int key, char c);
	protected abstract void keyReleased(int key, char c);
}
