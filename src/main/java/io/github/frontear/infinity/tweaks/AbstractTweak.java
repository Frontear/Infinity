package io.github.frontear.infinity.tweaks;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public abstract class AbstractTweak {
    @Expose
    @SerializedName("bind")
    protected int keybind; // TODO: access rights?
    @Expose
    @SerializedName("enable")
    private boolean enabled; // TODO: access rights?

    public AbstractTweak(int keybind) {
        this.keybind = keybind;
        this.enabled = false;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void toggle() {
        enabled = !enabled;
    }
}
