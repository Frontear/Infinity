package io.github.frontear.infinity.tweaks;

public class AbstractTweak {
    private int keybind;
    private boolean enabled;

    public AbstractTweak(int keybind) {
        this.keybind = keybind;
        this.enabled = false;
    }

    public int getKeyBind() {
        return keybind;
    }
    public boolean isEnabled() {
        return enabled;
    }

    public void toggle() {
        enabled = !enabled;
    }
}
