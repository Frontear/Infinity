package com.github.frontear.infinity.modules;

import lombok.val;

public enum ModuleCategory {
    COMBAT, RENDER, PLAYER, NONE;

    private final String name;

    ModuleCategory() {
        val n = name();

        this.name =
            Character.toUpperCase(n.charAt(0)) + n.substring(1).toLowerCase(); // sentence case
    }

    @Override
    public String toString() {
        return name;
    }
}
