package org.frontear.framework.manager.impl;

import com.google.common.collect.*;
import com.google.common.reflect.ClassPath;
import com.google.common.reflect.TypeToken;
import org.frontear.framework.logger.impl.Logger;
import org.frontear.framework.manager.IManager;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Set;

public abstract class Manager<T> implements IManager<T> {
	private static final Logger logger = new Logger("Manager");
	protected ImmutableSet<T> objects;

	@Override public UnmodifiableIterator<T> getObjects() {
		return objects.iterator();
	}

	@SuppressWarnings("UnstableApiUsage") protected final ImmutableCollection<T> reflectionSearch(String pkg) {
		logger.debug("Attempting to find parent...");
		//noinspection unchecked
		final Class<T> parent = (Class<T>) new TypeToken<T>(getClass()) {}.getRawType();
		logger.debug("Found parent: %s", parent.getSimpleName());
		final Set<T> objects = Sets.newHashSet();

		try {
			logger.debug("Searching ClassLoader for classes in '%s'", pkg);
			for (ClassPath.ClassInfo info : ClassPath.from(Thread.currentThread().getContextClassLoader())
					.getTopLevelClasses(pkg)) {
				final Class<?> target = info.load();
				logger.debug("Found target: %s", target.getSimpleName());

				try {
					if (!target.isAnnotationPresent(Deprecated.class) && parent.isAssignableFrom(target)) {
						logger.debug("Target of type '%s'", parent.getSimpleName());
						final Constructor<? extends T> constructor = target.asSubclass(parent).getDeclaredConstructor();
						constructor.setAccessible(true);
						logger.debug("Instantiating '%s'", target.getSimpleName());
						objects.add(constructor.newInstance());
					}
				}
				catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
					e.printStackTrace();
				}
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}

		return (this.objects = ImmutableSet.copyOf(objects));
	}
}
