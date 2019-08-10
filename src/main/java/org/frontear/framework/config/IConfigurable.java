package org.frontear.framework.config;

public interface IConfigurable<C extends IConfigurable<C>> {
	void load(final C self);

	default String getName() {
		return this.getClass().getSimpleName();
	}
}
