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
import java.util.stream.Stream;

public abstract class Manager<T> implements IManager<T> {
	private static final Logger logger = new Logger("Manager");
	private final ImmutableSet<T> objects;

	/**
	 * @param objects The objects that will be managed
	 */
	public Manager(ImmutableSet<T> objects) {
		this.objects = objects;
	}

	/**
	 * @param pkg The package that will be searched through via {@link Manager#reflectionSearch(String)}
	 */
	public Manager(String pkg) {
		this.objects = reflectionSearch(pkg);
	}

	/**
	 * Searches for all classes that reside in a specified package, and instantiates them into a {@link Set<T>}. It
	 * makes use of {@link ClassPath}
	 *
	 * @param pkg The package to search
	 *
	 * @return An {@link ImmutableSet} of elements which were instantiated
	 */
	@SuppressWarnings("UnstableApiUsage") protected final ImmutableSet<T> reflectionSearch(String pkg) {
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

		return ImmutableSet.copyOf(objects);
	}

	/**
	 * Makes use of an {@link UnmodifiableIterator<T>}, as the objects are not meant to be modified after they are set
	 * through either {@link Manager#reflectionSearch(String) or through manual creation
	 *
	 * @return {@link ImmutableSet#iterator()}
	 */
	@Override public Stream<T> getObjects() {
		return objects.stream();
	}
}
