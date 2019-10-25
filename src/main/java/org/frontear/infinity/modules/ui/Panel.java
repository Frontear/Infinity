package org.frontear.infinity.modules.ui;

import com.google.common.collect.Sets;
import java.awt.Color;
import java.util.Set;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import lombok.val;
import org.frontear.framework.graphics.IRenderer;
import org.frontear.framework.graphics.color.ColorFactory;
import org.frontear.framework.graphics.impl.Renderable;
import org.frontear.framework.graphics.shapes.Rectangle;

@FieldDefaults(level = AccessLevel.PRIVATE,
    makeFinal = true)
public final class Panel extends Renderable {
    private static final Color DEFAULT = ColorFactory.from(0, 0, 0, 127);
    Set<Button> buttons = Sets.newLinkedHashSet();
    Rectangle background;

    public Panel(int x, int y, int width, int height) {
        super(x, y, width, height, null);

        this.background = new Rectangle(x, y, width, height, DEFAULT);
    }

    public void add(Button button) {
        buttons.add(button);

        this.setHeight(this.getHeight());
        this.setWidth(this.getWidth());
        this.setPosition(this.getX(), this.getY());
    }

    @Override
    public void setWidth(int width) {
        buttons.forEach(x -> x.setWidth(width));
        background.setWidth(width);
    }

    @Override
    public void setHeight(int height) {
        buttons.forEach(x -> x.setHeight(height));
        background.setHeight(height * buttons.size());
    }

    @Override
    public void render() throws IllegalArgumentException {
        background.render();
        buttons.forEach(Button::render);
    }

    @Override
    public void setRenderer(@NonNull final IRenderer renderer) {
        background.setRenderer(renderer);
        buttons.forEach(x -> x.setRenderer(renderer));
    }

    public void mouse(int mouseX, int mouseY, int button) {
        buttons.forEach(x -> x.click(mouseX, mouseY,
            mouseX >= x.getX() && mouseY >= x.getY() && mouseX < x.getX() + x.getWidth()
                && mouseY < x.getY() + x.getHeight(), button));
    }

    public void setPosition(int x, int y) {
        background.setX(x);
        background.setY(y);

        for (val button : buttons) {
            button.setPosition(x, y);
            y += button.getHeight();
        }
    }
}
