package io.sphere.sdk.commands;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.models.Base;

import static java.util.Objects.requireNonNull;

/**
 * Operation which can be performed to change the state of a resource in the platform.
 *
 * @param <T> the context of the update action
 *
 */
public abstract class StagedUpdateActionBase<T> extends Base implements StagedUpdateAction<T> {
    private final String action;

    @JsonCreator
    protected StagedUpdateActionBase(final String action) {
        this.action = requireNonNull(action);
    }

    @Override
    public String getAction() {
        return action;
    }
}
