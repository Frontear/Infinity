package org.frontear.framework.config.impl;

import com.google.gson.annotations.Expose;
import java.lang.reflect.Field;
import java.util.Arrays;
import lombok.NonNull;
import lombok.val;
import org.frontear.framework.config.IConfigurable;

/**
 * An implementation of {@link IConfigurable}. Usage of this class is heavily discouraged, instead,
 * implement {@link IConfigurable} manually
 */
public final class Configurable<C extends Configurable<C>> implements IConfigurable<C> {
    /**
     * Uses reflection to apply exposed fields with data collected from the serialized object
     *
     * @see IConfigurable#load(IConfigurable)
     */
    public void load(@NonNull final C self) {
        val exposed = Arrays.stream(this.getClass().getDeclaredFields())
            .filter(x -> x.isAnnotationPresent(Expose.class)).peek(x -> x.setAccessible(true));
        exposed.forEach(x -> this.apply(self, x));
    }

    private void apply(@NonNull final C self, @NonNull final Field field) {
        val equivalent = self.getClass().getDeclaredField(field.getName());
        equivalent.setAccessible(true);

        field.set(this, equivalent.get(self));
    }
}
