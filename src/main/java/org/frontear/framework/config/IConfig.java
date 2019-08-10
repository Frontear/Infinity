package org.frontear.framework.config;

public interface IConfig {
	void register(final IConfigurable<?> object);

	void unregister(final IConfigurable<?> object);

	void load();

	void save();
}
