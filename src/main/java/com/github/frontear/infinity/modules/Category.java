package com.github.frontear.infinity.modules;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE,
    makeFinal = true)
public enum Category {
    COMBAT,
    RENDER,
    PLAYER,
    NONE; // something which is not shown on the ClickGuiScreen

    String name;

    Category() {
        this.name = "${Character.toUpperCase(name().charAt(0))}${name().substring(1).toLowerCase()}"; // sentence case
    }

    @Override
    public String toString() {
        return name;
    }
}
