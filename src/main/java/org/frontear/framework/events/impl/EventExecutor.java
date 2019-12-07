package org.frontear.framework.events.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import lombok.val;
import org.frontear.framework.events.IEvent;
import org.frontear.framework.events.IEventExecutor;
import org.frontear.framework.logger.impl.Logger;

/**
 * An implementation of {@link org.frontear.framework.events.IEventExecutor}
 */
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EventExecutor implements IEventExecutor {
    Logger logger = new Logger();
    Map<Class<? extends IEvent>, List<Consumer<IEvent>>> listeners = Maps.newHashMap();

    @Override
    public <E extends IEvent> void register(@NonNull final Object instance, @NonNull final Class<E> event,
        @NonNull final Consumer<E> listener) {
        //noinspection unchecked
        Objects.requireNonNull(listeners.putIfAbsent(event, Lists.newArrayListWithExpectedSize(1)))
            .add(
                ((Consumer<IEvent>) listener)); // this is such a code smell
    }

    @Override
    public <E extends IEvent> void unregister(@NonNull final Class<E> event,
        @NonNull final Consumer<E> listener) {
        listeners.get(event).remove(listener);
    }

    @Override
    public <E extends IEvent> E fire(@NonNull final E event) {
        val key = event.getClass();

        for (val consumer : listeners.get(key)) {
            consumer.accept(event);

            if (event.isCancelled()) {
                logger.debug(
                    "${key.getSimpleName()} was cancelled early, stopping execution of listeners");
                break;
            }
        }

        return event;
    }
}
