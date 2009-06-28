package br.com.deadtroll.tests.menu;

public class MenuItem {

	private String title;
	private boolean enabled;
	private String tooltip;
	private boolean selected;
	private String targetScene;

	public MenuItem(String title, String tooltip, boolean enabled, String targerScene) {
		this.title = title;
		this.tooltip = tooltip;
		this.enabled = enabled;
		this.targetScene = targerScene;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
		if (!this.enabled) {
			this.setSelected(false);
		}
	}

	public String getTooltip() {
		return tooltip;
	}

	public void setTooltip(String tooltip) {
		this.tooltip = tooltip;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public String getTargetScene() {
		return targetScene;
	}

	public void setTargetScene(String targetScene) {
		this.targetScene = targetScene;
	}
	
}
