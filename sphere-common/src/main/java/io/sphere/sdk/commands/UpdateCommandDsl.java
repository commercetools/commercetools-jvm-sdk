package io.sphere.sdk.commands;

import io.sphere.sdk.models.ResourceView;
import io.sphere.sdk.models.Versioned;

public interface UpdateCommandDsl<T extends ResourceView<T, T>, C extends UpdateCommandDsl<T, C>> extends UpdateCommand<T> {
    C withVersion(final Versioned<T> versioned);
}
