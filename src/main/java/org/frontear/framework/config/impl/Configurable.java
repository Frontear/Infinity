package org.frontear.framework.config.impl;

import com.google.gson.annotations.Expose;
import org.frontear.framework.config.IConfigurable;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Objects;

/**
 * An implementation of {@link IConfigurable}. Usage of this class is heavily discouraged, instead, implement {@link
 * IConfigurable} manually
 */
@Deprecated public final class Configurable<C extends Configurable<C>> implements IConfigurable<C> {
	/**
	 * Uses reflection to apply exposed fields with data collected from the serialized object
	 *
	 * @see IConfigurable#load(IConfigurable)
	 */
	@Override public void load(C self) {
		final Field[] exposed = Arrays.stream(this.getClass().getDeclaredFields())
				.filter(x -> x.isAnnotationPresent(Expose.class)).peek(this::sanitize).toArray(Field[]::new);
		for (Field field : exposed) {
			final Field equivalent = Objects.requireNonNull(get(self.getClass(), field.getName()));
			try {
				field.set(this, equivalent.get(self));
			}
			catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
	}

	private Field sanitize(Field field) {
		field.setAccessible(true);
		return field;
	}

	private Field get(Class<?> clazz, String name) {
		try {
			final Field field = clazz.getDeclaredField(name);
			return sanitize(field);
		}
		catch (NoSuchFieldException e) {
			return null;
		}
	}
}
