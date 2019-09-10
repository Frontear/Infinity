package org.frontear.framework.manager.impl;

import com.google.common.collect.*;
import com.google.common.reflect.ClassPath;
import com.google.common.reflect.TypeToken;
import lombok.NonNull;
import lombok.SneakyThrows;
import org.frontear.framework.client.impl.Client;
import org.frontear.framework.logger.impl.Logger;
import org.frontear.framework.manager.IManager;

import javax.annotation.Nullable;
import java.lang.reflect.Constructor;
import java.util.*;
import java.util.stream.Stream;

public abstract class Manager<T> implements IManager<T> {
	private static final Logger logger = new Logger();
	private final ImmutableMap<Class<? extends T>, T> objects;

	/**
	 * Creates the manager with an ImmutableSet of objects
	 *
	 * @param objects The objects that will be managed
	 */
	public Manager(@NonNull ImmutableMap<Class<? extends T>, T> objects) {
		this.objects = objects;
	}

	/**
	 * Creates the manager, and requests to search the ClassPath for classes that are of the type being managed
	 *
	 * @param pkg The package that will be searched through via {@link Manager#reflectionSearch(String)}
	 */
	public Manager(@NonNull String pkg) {
		this.objects = reflectionSearch(pkg);
	}

	/**
	 * Searches for all classes that reside in a specified package, and instantiates them into a {@link Set}. It makes
	 * use of {@link ClassPath}
	 *
	 * @param pkg The package to search
	 *
	 * @return An {@link ImmutableSet} of elements which were instantiated
	 */
	@SuppressWarnings("UnstableApiUsage") @SneakyThrows private ImmutableMap<Class<? extends T>, T> reflectionSearch(String pkg) {
		logger.debug("Attempting to find parent...");
		//noinspection unchecked
		final Class<T> parent = (Class<T>) new TypeToken<T>(getClass()) {}
				.getRawType(); // won't work if manager is abstracted to another generic implementation
		logger.debug("Found parent: %s", parent.getSimpleName());
		final Map<Class<? extends T>, T> objects = Maps.newLinkedHashMap(); // forced order of elements

		logger.debug("Searching ClassLoader for classes in '%s'", pkg);
		for (ClassPath.ClassInfo info : ClassPath.from(Thread.currentThread().getContextClassLoader())
				.getTopLevelClasses(pkg)) {
			final Class<?> target = info.load();
			logger.debug("Found target: %s", target.getSimpleName());

			if ((Client.DEBUG || !target.isAnnotationPresent(Deprecated.class)) && parent.isAssignableFrom(target)) {
				logger.debug("Target of type '%s'", parent.getSimpleName());
				final Constructor<? extends T> constructor = target.asSubclass(parent).getDeclaredConstructor();
				constructor.setAccessible(true);
				logger.debug("Instantiating '%s'", target.getSimpleName());
				objects.put(target.asSubclass(parent), constructor.newInstance());
			}
		}

		return ImmutableMap.copyOf(objects);
	}

	/**
	 * Streams {@link IManager#getObjects()} to find a retrieve a specific object based on the class type
	 *
	 * @param target The specified object, which extends T
	 *
	 * @return target object, or will throw a {@link NullPointerException} if the target cannot be found
	 */
	@NonNull @Override public <T1 extends T> T1 get(@NonNull Class<T1> target) {
		//noinspection unchecked
		return (T1) objects.get(target);
	}

	/**
	 * Makes use of an {@link UnmodifiableIterator}, as the objects are not meant to be modified after they are set
	 * through either {@link Manager#reflectionSearch(String)} or through manual creation
	 *
	 * @return {@link ImmutableSet#stream()}
	 */
	@Override public Stream<T> getObjects() {
		return objects.values().stream();
	}
}
