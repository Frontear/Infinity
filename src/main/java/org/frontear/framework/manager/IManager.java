package org.frontear.framework.manager;

import javax.annotation.Nullable;
import java.util.NoSuchElementException;
import java.util.stream.Stream;

public interface IManager<T> {
	/**
	 * Streams {@link IManager#getObjects()} to find a specific object based on a class type
	 *
	 * @param target The specified object, which extends {@link T}
	 * @param <T1>   The type of the target object which must extend {@link T}
	 *
	 * @return target object, or throws {@link NoSuchElementException} if object cannot be found
	 */
	@Nullable default <T1 extends T> T1 get(Class<T1> target) {
		//noinspection unchecked,OptionalGetWithoutIsPresent
		return (T1) getObjects().filter(x -> x.getClass() == target).findFirst().get();
	}

	/**
	 * Provides the {@link Stream} mechanism for the objects. This is so that they can make use of the powerful
	 * LINQ-like features of streams
	 *
	 * @return {@link Stream} for {@link T} objects
	 */
	Stream<T> getObjects();
}
