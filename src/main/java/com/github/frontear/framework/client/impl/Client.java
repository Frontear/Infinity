package com.github.frontear.framework.client.impl;

import com.github.frontear.framework.client.IClient;
import com.github.frontear.framework.config.impl.Config;
import com.github.frontear.framework.environments.IEnvironment;
import com.github.frontear.framework.info.impl.ModInfo;
import com.github.frontear.framework.logger.impl.Logger;
import com.github.frontear.framework.utils.time.Timer;
import com.google.gson.JsonParser;
import java.io.File;
import java.util.zip.ZipFile;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.apache.commons.lang3.StringUtils;

/**
 * An implementation of {@link IClient}
 */
@FieldDefaults(level = AccessLevel.PRIVATE,
    makeFinal = true)
public abstract class Client implements IClient {
    /**
     * Represents whether <i>-Dfrontear.debug=true</i> is passed as a JVM argument
     */
    public static final boolean DEBUG = Boolean.getBoolean("frontear.debug");
    /**
     * Represents the time since the {@link Client} first loaded (immediate call to the
     * constructor)
     */
    public static final Timer UPTIME = new Timer();

    /**
     * This is the expected/recommended working directory for the client. Should always point to the
     * minecraft data folder
     */
    public static final String WORKING_DIRECTORY = System.getProperty("user.dir");
    @Getter ModInfo info;
    @Getter Logger logger;
    @Getter Config config;

    /**
     * This is marked protected to prevent outside construction, and is to specify that this can
     * only be managed through singletons
     */
    protected Client(@NonNull final IEnvironment environment) {
        UPTIME.reset();

        this.info = this.construct(environment);
        this.logger = new Logger(info.getName());
        val file = new File(WORKING_DIRECTORY, "${info.getName().toLowerCase()}.json");
        this.config = new Config(file);

        logger.info("Running in environment: ${environment.getName()}");
        logger.info("Starting ${this.getSimpleName()} in debug mode");
    }

    /*
    This mainly exists due to the Fabric API loading classes but not resources until later, causing discrepancies with resources attempting to be loaded
     */
    private ModInfo construct(@NonNull final IEnvironment environment) {
        val jar = new ZipFile(new File(StringUtils.substringBetween(
            this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath(), "file:",
            "!").replace("%20", " ")));
        val stream = jar.getInputStream(jar.getEntry(environment.getInfoFilename()));
        val element = new JsonParser().parse(stream.bufferedReader());

        return new ModInfo(environment.getInfoJsonObject(element), environment);
    }
}
