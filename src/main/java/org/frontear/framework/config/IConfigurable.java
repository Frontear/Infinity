package org.frontear.framework.config;

import lombok.NonNull;

/**
 * The interface for all configurable objects which are managed via the {@link IConfig}
 *
 * @param <C> the object that is currently extending {@link IConfigurable}
 */
public interface IConfigurable<C extends IConfigurable<C>> {
	/**
	 * This is called when a property with the same name as {@link IConfigurable#getName()} is found from {@link
	 * IConfig#load()}
	 *
	 * @param self the object that was serialized by {@link IConfig}
	 */
	void load(final @NonNull C self);

	/**
	 * Represents the name that is found from the json properties. Default returns {@link Class#getSimpleName()}
	 *
	 * @return the name of the json property
	 */
	default String getName() {
		return this.getSimpleName();
	}
}
