package org.frontear.infinity.modules;

public enum Category {
	COMBAT,
	RENDER,
	PLAYER,
	NONE; // something which is not shown on the ClickGuiScreen


	@Override public String toString() {
		return Character.toUpperCase(name().charAt(0)) + name().substring(1); // sentence case
	}
}
