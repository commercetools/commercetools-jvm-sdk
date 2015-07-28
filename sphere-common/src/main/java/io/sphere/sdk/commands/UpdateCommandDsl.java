package io.sphere.sdk.commands;

import io.sphere.sdk.models.DefaultModelView;
import io.sphere.sdk.models.Versioned;

public interface UpdateCommandDsl<T extends DefaultModelView<T>, C extends UpdateCommandDsl<T, C>> extends UpdateCommand<T> {
    C withVersion(final Versioned<T> versioned);
}
