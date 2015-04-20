package io.sphere.sdk.commands;

import io.sphere.sdk.models.Versioned;

public interface UpdateCommandDsl<T> extends UpdateCommand<T> {
    public UpdateCommandDsl<T> withVersion(final Versioned<T> versioned);
}
