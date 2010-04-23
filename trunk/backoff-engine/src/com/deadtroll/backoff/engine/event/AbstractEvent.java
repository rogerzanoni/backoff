package com.deadtroll.backoff.engine.event;

public abstract class AbstractEvent {
	private long dispatchTime;

	public long getDispatchTime() {
		return dispatchTime;
	}

	public void setDispatchTime(long dispatchTime) {
		this.dispatchTime = dispatchTime;
	}
}
