package com.dunneev.seenatural.Utilities;

import org.jetbrains.annotations.Nullable;

public class LiveDataEventWrapper<B> {

    private boolean hasBeenHandled = false;
    private final Object content;

    public final boolean getHasBeenHandled() {
        return this.hasBeenHandled;
    }


    public LiveDataEventWrapper(Object content) {
        this.content = content;
    }


    /**
     * Returns the content and prevents its use again.
     */
    @Nullable
    public final Object getContentIfNotHandled() {
        Object content;
        if (this.hasBeenHandled) {
            content = null;
        } else {
            this.hasBeenHandled = true;
            content = this.content;
        }

        return content;
    }

    /**
     * Returns the content, even if it's already been handled.
     */
    public final Object peekContent() {
        return this.content;
    }

}
