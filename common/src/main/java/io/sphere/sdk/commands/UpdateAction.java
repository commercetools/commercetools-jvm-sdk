package io.sphere.sdk.commands;

import io.sphere.sdk.models.Base;

/**
 * Operation which can be performed to change the state of an entity in SPHERE.IO.
 *
 * @param <T> the context of the update action
 *
 */
public abstract class UpdateAction<T> extends Base {
    private final String action;

    protected UpdateAction(final String action) {
        this.action = action;
    }

    public String getAction() {
        return action;
    }
}
