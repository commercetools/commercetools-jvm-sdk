package io.sphere.sdk.subscriptions;

import com.fasterxml.jackson.annotation.JsonTypeName;
import io.sphere.sdk.annotations.ResourceValue;

/**
 * This payload will be sent for a ChangeSubscription if a resource was updated.
 *
 * @param <T> the resource type
 */
@JsonTypeName("ResourceUpdated")
@ResourceValue
public interface ResourceUpdatedPayload<T> extends Payload<T> {

    /**
     * The version of the resource after the update.
     *
     * @return the resource version after the update
     */
    Long getVersion();

    /**
     * The version of the resource before the update.
     *
     * @return the resource version before the update
     */
    Long getOldVersion();
}
