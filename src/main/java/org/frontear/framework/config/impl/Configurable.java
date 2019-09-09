package org.frontear.framework.config.impl;

import com.google.gson.annotations.Expose;
import org.frontear.framework.config.IConfigurable;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.stream.Stream;

/**
 * An implementation of {@link IConfigurable}. Usage of this class is heavily discouraged, instead, implement {@link
 * IConfigurable} manually
 */
public final class Configurable<C extends Configurable<C>> implements IConfigurable<C> {
	/**
	 * Uses reflection to apply exposed fields with data collected from the serialized object
	 *
	 * @see IConfigurable#load(IConfigurable)
	 */
	@Override public void load(C self) {
		final Stream<Field> exposed = Arrays.stream(this.getClass().getDeclaredFields())
				.filter(x -> x.isAnnotationPresent(Expose.class)).peek(x -> x.setAccessible(true));
		exposed.forEach(x -> {
			try {
				final Field equivalent = self.getClass().getDeclaredField(x.getName());
				equivalent.setAccessible(true);

				x.set(this, equivalent.get(self));
			}
			catch (NoSuchFieldException | IllegalAccessException e) {
				e.printStackTrace();
			}
		});
	}
}
