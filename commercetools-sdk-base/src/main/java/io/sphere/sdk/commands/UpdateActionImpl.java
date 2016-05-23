package io.sphere.sdk.commands;

import io.sphere.sdk.models.Base;

import static java.util.Objects.requireNonNull;

/**
 * Operation which can be performed to change the state of a resource in the platform.
 *
 * @param <T> the context of the update action
 *
 */
public abstract class UpdateActionImpl<T> extends Base implements UpdateAction<T> {
    private final String action;

    protected UpdateActionImpl(final String action) {
        this.action = requireNonNull(action);
    }

    @Override
    public String getAction() {
        return action;
    }
}
