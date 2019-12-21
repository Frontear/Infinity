package com.github.frontear.framework.manager.impl;

import com.github.frontear.framework.client.impl.Client;
import com.github.frontear.framework.manager.IManager;
import com.google.common.collect.*;
import com.google.common.reflect.*;
import java.util.Set;
import java.util.stream.Stream;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.frontear.framework.client.impl.Client;
import org.frontear.framework.logger.impl.Logger;
import org.frontear.framework.manager.IManager;

@FieldDefaults(level = AccessLevel.PRIVATE,
    makeFinal = true)
public abstract class Manager<T> implements IManager<T> {
    Logger logger = new Logger();
    ImmutableMap<Class<? extends T>, T> objects;

    /**
     * Creates the manager with an ImmutableSet of objects
     *
     * @param objects The objects that will be managed
     */
    public Manager(@NonNull final ImmutableMap<Class<? extends T>, T> objects) {
        this.objects = objects;
    }

    /**
     * Creates the manager, and requests to search the ClassPath for classes that are of the type
     * being managed
     *
     * @param pkg The package that will be searched through via {@link Manager#reflectionSearch(String)}
     */
    public Manager(@NonNull final String pkg) {
        this.objects = reflectionSearch(pkg);
    }

    /**
     * Searches for all classes that reside in a specified package, and instantiates them into a
     * {@link Set}. It makes use of {@link ClassPath}
     *
     * @param pkg The package to search
     *
     * @return An {@link ImmutableSet} of elements which were instantiated
     */
    @SuppressWarnings("UnstableApiUsage")
    private ImmutableMap<Class<? extends T>, T> reflectionSearch(final String pkg) {
        logger.debug("Attempting to find parent...");
        //noinspection unchecked
        val parent = (Class<T>) new TypeToken<T>(getClass()) {
        }
            .getRawType(); // won't work if manager is abstracted to another generic implementation
        logger.debug("Found parent: ${parent.getSimpleName()}");
        val objects = Maps.<Class<? extends T>, T>newLinkedHashMap(); // forced order of elements

        logger.debug("Searching ClassLoader for classes in '$pkg'");
        for (val info : ClassPath.from(Thread.currentThread().getContextClassLoader())
            .getTopLevelClasses(pkg)) {
            val target = info.load();
            logger.debug("Found target: ${target.getSimpleName()}");

            if ((Client.DEBUG || !target.isAnnotationPresent(Deprecated.class)) && parent
                .isAssignableFrom(target)) {
                val element = target.asSubclass(parent);
                if (objects.containsKey(element)) {
                    logger.debug(
                        "%s already contains an object of type %s. The previous value will been removed!",
                        this
                            .getClass().getSimpleName(), element.getSimpleName());
                }
                logger.debug("Target of type '${parent.getSimpleName()}'");
                val constructor = element.getDeclaredConstructor();
                constructor.setAccessible(true);
                logger.debug("Instantiating '${element.getSimpleName()}'");
                objects.put(element, constructor.newInstance());
            }
        }

        return ImmutableMap.copyOf(objects);
    }

    /**
     * Streams {@link IManager#getObjects()} to find a retrieve a specific object based on the class
     * type
     *
     * @param target The specified object, which extends T
     *
     * @return target object, or will throw a {@link NullPointerException} if the target cannot be
     * found
     */
    @NonNull
    @Override
    public <T1 extends T> T1 get(@NonNull final Class<T1> target) {
        //noinspection unchecked
        return (T1) objects.get(target);
    }

    /**
     * Makes use of an {@link UnmodifiableIterator}, as the objects are not meant to be modified
     * after they are set through either {@link Manager#reflectionSearch(String)} or through manual
     * creation
     *
     * @return {@link ImmutableSet#stream()}
     */
    @Override
    public Stream<T> getObjects() {
        return objects.values().stream();
    }
}
