package org.frontear.framework.graphics.color;

import com.google.common.base.Preconditions;
import java.awt.Color;
import java.util.WeakHashMap;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ColorFactory {
    private final WeakHashMap<Integer, Color> colors = new WeakHashMap<>(); // todo: is this enough?

    public Color from(final int red, final int green, final int blue) {
        return from(red, green, blue, 255);
    }

    public Color from(final int red, final int green, final int blue, final int alpha) {
        Preconditions.checkArgument(
            (red >= 0 && red <= 255) && (green >= 0 && green <= 255) && (blue >= 0 && blue <= 255)
                && (alpha >= 0 && alpha <= 255),
            "Color values are outside normal bounds of 0-255 [r: $red, g: $green, b: $blue]", red,
            green,
            blue);

        final int key = red + green + blue + alpha;
        return colors.getOrDefault(key, new Color(red, green, blue, alpha));
    }
}
