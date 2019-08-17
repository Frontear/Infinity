package org.frontear.framework.config.impl;

import com.google.gson.annotations.Expose;
import org.frontear.framework.config.IConfigurable;

import java.lang.reflect.Field;
import java.util.Arrays;

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
		Field[] this_exposed = Arrays.stream(this.getClass().getDeclaredFields())
				.filter(x -> x.isAnnotationPresent(Expose.class)).toArray(Field[]::new);

		for (Field exposed_field : this_exposed) {
			final String name = exposed_field.getName();
			exposed_field.setAccessible(true);
			try {
				Field target = self.getClass().getDeclaredField(name);
				target.setAccessible(true);
				exposed_field.set(this, target.get(self));
			}
			catch (NoSuchFieldException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}
	}
}
