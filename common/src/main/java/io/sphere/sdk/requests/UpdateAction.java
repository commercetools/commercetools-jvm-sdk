package io.sphere.sdk.requests;

import io.sphere.sdk.annotations.Internal;

@Internal
public abstract class UpdateAction<T> {
    private final String action;

    protected UpdateAction(final String action) {
        this.action = action;
    }

    public String getAction() {
        return action;
    }
}
