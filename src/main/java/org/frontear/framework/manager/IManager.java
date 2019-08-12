package org.frontear.framework.manager;

import javax.annotation.Nullable;
import java.util.Iterator;

public interface IManager<T> {
	/**
	 * Iterates on {@link IManager#getObjects()} to find a specified object
	 *
	 * @param target The class of the target object
	 * @param <T1>   The type of the target object
	 *
	 * @return target object, or null if not found
	 */
	@Nullable default <T1 extends T> T1 get(Class<T1> target) {
		for (Iterator<T> iter = getObjects(); iter.hasNext(); ) {
			final T object = iter.next();
			if (object.getClass() == target) {
				//noinspection unchecked
				return (T1) object;
			}
		}

		return null;
	}

	/**
	 * Provides the {@link Iterator} mechanism for the objects. This is to prevent outside modification of the
	 * collection
	 *
	 * @return {@link Iterator} for {@link T} objects
	 */
	Iterator<T> getObjects();
}
