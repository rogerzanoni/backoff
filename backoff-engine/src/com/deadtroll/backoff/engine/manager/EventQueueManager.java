package com.deadtroll.backoff.engine.manager;

import java.util.ArrayList;
import java.util.List;

import com.deadtroll.backoff.engine.event.AbstractEvent;
import com.deadtroll.backoff.engine.event.EventQueue;
import com.deadtroll.backoff.engine.model.AbstractEntity;

public class EventQueueManager extends AbstractEntity {
	private List<EventQueue> eventQueues;
	private static EventQueueManager instance;

	private EventQueueManager() {
		eventQueues = new ArrayList<EventQueue>();
	}
	
	public static EventQueueManager getInstance() {
		if (instance == null)
			instance = new EventQueueManager();
		return instance;
	}
	
	public void addQueue(EventQueue eventQueue) {
		if (!this.eventQueues.contains(eventQueue))
			this.eventQueues.add(eventQueue);
	}
	
	public void removeQueue(EventQueue eventQueue) {
		this.eventQueues.remove(eventQueue);
	}
	
	public void update(int delta) {
		for (EventQueue eventQueue : this.eventQueues) {
			eventQueue.update(delta);
		}
	}
	
	public void postEvent(AbstractEvent event) {
		for (EventQueue eventQueue : this.eventQueues) {
			eventQueue.enqueueEvent(event);
		}
	}
	
	public void postEvents(List<AbstractEvent> events) {
		for (AbstractEvent event : events) {
			for (EventQueue eventQueue : this.eventQueues) {
				eventQueue.enqueueEvent(event);
			}
		}
	}
	
	public void postEvents(List<AbstractEvent> events, long delay) {
		for (AbstractEvent event : events) {
			for (EventQueue eventQueue : this.eventQueues) {
				eventQueue.enqueueEvent(event, delay);
			}
		}
	}

	public void postEvent(AbstractEvent event, long delay) {
		for (EventQueue eventQueue : this.eventQueues) {
			eventQueue.enqueueEvent(event, delay);
		}
	}

	public void finalizeEntity() {
	}

	public long getId() {
		return 0;
	}

	public String getName() {
		return getClass().getName();
	}

	public void initializeEntity() {
	}

	public void setId(long id) {		
	}

	public void setName(String name) {
	}
}
