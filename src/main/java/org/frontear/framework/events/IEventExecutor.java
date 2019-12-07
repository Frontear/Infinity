package org.frontear.framework.events;

import java.util.function.Consumer;
import lombok.NonNull;

/**
 * An event service that will keep a track of objects registering to events, and fire the listeners
 * as necessary.
 */
public interface IEventExecutor {
    /**
     * Registers an object to a specific {@link IEvent}, binding it via a listener passed in as a
     * {@link Consumer}
     *
     * @param instance The object wherein the listener resides
     * @param event    The event that you are targeting
     * @param listener The listener that will invoke once the event is {@link
     *                 IEventExecutor#fire(IEvent)}
     */
    <E extends IEvent> void register(@NonNull final Object instance, @NonNull final E event,
        @NonNull final Consumer<E> listener);

    /**
     * Removes the registration of an listener from the execution backing, preventing it from being
     * invoked
     *
     * @param event    The event that you are targeting
     * @param listener The listener that will be removed
     */
    <E extends IEvent> void unregister(@NonNull final E event, @NonNull final Consumer<E> listener);

    /**
     * Fires an event, invoking all actively registered listeners
     *
     * @param event The event whose listeners shall be invoked
     *
     * @return The event instance, as a matter of convenience
     */
    <E extends IEvent> E fire(@NonNull final E event);
}
