package com.dunneev.seenatural.Utilities;

import org.jetbrains.annotations.Nullable;

/**
 *
 * @param <T>
 */
public class Event<T> {

    private boolean hasBeenHandled = false;
    private final Object content;

    public final boolean getHasBeenHandled() {
        return this.hasBeenHandled;
    }


    public Event(T content) {
        this.content = content;
    }


    /**
     * Returns the content and prevents its use again.
     */
    @Nullable
    public final T getContentIfNotHandled() {
        T content;
        if (this.hasBeenHandled) {
            content = null;
        } else {
            this.hasBeenHandled = true;
            content = (T) this.content;
        }

        return content;
    }

    /**
     * Returns the content, even if it's already been handled.
     */
    public final T peekContent() {
        return (T) this.content;
    }

}
