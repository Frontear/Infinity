package org.frontear.framework.graphics.impl;

import com.google.common.base.Preconditions;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.frontear.framework.graphics.IRenderable;
import org.frontear.framework.graphics.IRenderer;

@FieldDefaults(level = AccessLevel.PROTECTED)
public abstract class Renderable implements IRenderable {
    @Getter @Setter int x, y, width, height;
    @Setter @NonNull private IRenderer renderer;

    @Override
    public void render() throws IllegalArgumentException {
        Preconditions.checkArgument(renderer != null && renderer.isActive());
    }
}
