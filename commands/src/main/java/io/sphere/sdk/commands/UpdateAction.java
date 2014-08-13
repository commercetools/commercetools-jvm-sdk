package io.sphere.sdk.commands;

import io.sphere.sdk.annotations.Internal;
import io.sphere.sdk.models.Base;

@Internal
public abstract class UpdateAction<T> extends Base {
    private final String action;

    protected UpdateAction(final String action) {
        this.action = action;
    }

    public String getAction() {
        return action;
    }
}
