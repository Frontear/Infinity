package org.frontear.framework.events;

public interface IEvent {
    /**
     * Cancel the event, preventing any other listeners from executing. This is a one-way operation,
     * you cannot un-cancel an event
     */
    void cancel();

    /**
     * @return Whether the event has been cancelled by {@link IEvent#cancel()}
     */
    boolean isCancelled();
}
