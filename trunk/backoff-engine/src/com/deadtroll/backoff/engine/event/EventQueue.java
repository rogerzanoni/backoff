package com.deadtroll.backoff.engine.event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventQueue {
	private Map<Class<?>, List<AbstractEventHandler>> eventHandlers;
	private List<AbstractEvent> eventQueue;

	public EventQueue() {
		this.eventHandlers = new HashMap<Class<?>, List<AbstractEventHandler>>();
		this.eventQueue = new ArrayList<AbstractEvent>();
	}
	
	public void addHandler(Class<?> clazz, AbstractEventHandler handler) {
		List<AbstractEventHandler> handlers;
		handlers = this.eventHandlers.get(clazz);
		
		if (handlers == null) {
			handlers = new ArrayList<AbstractEventHandler>();
			this.eventHandlers.put(clazz, handlers);
		}
		if (!handlers.contains(handler)) {
			handlers.add(handler);
		}
	}
	
	public void enqueueEvent(AbstractEvent event) {
		synchronized (this.eventQueue) {
			event.setDispatchTime(System.currentTimeMillis());
			this.eventQueue.add(event);
		}
	}
	
	public void enqueueEvent(AbstractEvent event, long delay) {
		synchronized (this.eventQueue) {
			event.setDispatchTime(System.currentTimeMillis() + delay);
			this.eventQueue.add(event);
		}
	}
	
	public void update(int delta) { 
		synchronized (this.eventQueue) {
			long timeNow = System.currentTimeMillis();
			List<AbstractEvent> processedEvents = new ArrayList<AbstractEvent>();
			
			List<AbstractEvent> eventQueueCopy = new ArrayList<AbstractEvent>();
			eventQueueCopy.addAll(this.eventQueue);
			
			for (AbstractEvent event : eventQueueCopy) {
				if (event != null) {
					if (event.getDispatchTime() <= timeNow) {
						List<AbstractEventHandler> handlers = this.eventHandlers.get(event.getClass());
						if (handlers != null) {
							for (AbstractEventHandler eventHandler : handlers) {
								eventHandler.processEvent(event);
							}
						}
						processedEvents.add(event);
					}
				}
			}
	
			this.eventQueue.removeAll(processedEvents);
		}
	}
}
