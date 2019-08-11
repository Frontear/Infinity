package org.frontear.framework.config.impl;

import com.google.gson.annotations.Expose;
import org.frontear.framework.config.IConfigurable;

import java.lang.reflect.Field;
import java.util.Arrays;

// Do NOT use this class, prefer implementing IConfigurable and creating your own load method
@Deprecated public final class Configurable<C extends Configurable<C>> implements IConfigurable<C> {
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
