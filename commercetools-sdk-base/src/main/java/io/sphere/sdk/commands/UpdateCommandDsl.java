package io.sphere.sdk.commands;

import io.sphere.sdk.models.ResourceView;
import io.sphere.sdk.models.Versioned;

/**
 * Subinterface for update commands which supports updating the version for retries on {@link io.sphere.sdk.client.ConcurrentModificationException}s
 * @param <T> the type of the resource to be updated
 * @param <C> the type of the update command
 */
public interface UpdateCommandDsl<T extends ResourceView<T, T>, C extends UpdateCommandDsl<T, C>> extends UpdateCommand<T> {
    //!do not add a getter for the version for this class hierarchy

    //note if an endpoint supports also update by key then add new method and delegate to this method with Versioned.of("key=" + key, version)

    /**
     * Creates a copy of this update command with a different value for version.
     * @param versioned the new versioned parameter which is expected to contain the most recent version of the resource, do not change the ID
     * @return the new update command
     */
    C withVersion(final Versioned<T> versioned);

    C withVersion(final Long version);
}
