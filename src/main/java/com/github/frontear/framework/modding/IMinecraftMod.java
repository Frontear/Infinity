package com.github.frontear.framework.modding;

import com.github.frontear.framework.client.IClient;

/**
 * An interface to specify the class which contains the loading functionality of a mod. This should
 * ideally be separate from the {@link IClient}
 */
public interface IMinecraftMod {
    void init();
}
