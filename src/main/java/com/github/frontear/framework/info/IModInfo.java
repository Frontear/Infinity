package com.github.frontear.framework.info;

import com.github.frontear.MinecraftMod;
import org.frontear.MinecraftMod;

/**
 * Contains all the important information found from the specified json file
 */
public interface IModInfo {
    /**
     * @return The name of the {@link MinecraftMod}
     */
    String getName();

    /**
     * @return The version of the {@link MinecraftMod}
     */
    String getVersion();

    /**
     * This is just the {@link IModInfo#getName()} + {@link IModInfo#getVersion()}
     *
     * @return The fullname of the {@link MinecraftMod}
     */
    String getFullname();

    /**
     * @return The authors of the {@link MinecraftMod}
     */
    String getAuthors();
}
