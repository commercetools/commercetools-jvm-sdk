package io.sphere.sdk.commands;

import io.sphere.sdk.models.ResourceView;
import io.sphere.sdk.models.Versioned;

public interface UpdateCommandDsl<T extends ResourceView<T, T>, C extends UpdateCommandDsl<T, C>> extends UpdateCommand<T> {
    //!do not add a getter for the version for this class hierarchy

    //note if an endpoint supports also update by key then add new method and deletegate to this method with Versioned.of("key=" + key, version)
    C withVersion(final Versioned<T> versioned);
}
