package org.frontear.framework.modding;

/**
 * An interface to specify the class which contains the loading functionality of a mod.
 * This should ideally be separate from the {@link org.frontear.framework.client.IClient}
 */
public interface IMinecraftMod {
    void init();
}
