package io.sphere.sdk.subscriptions;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.annotations.ResourceValue;

import java.time.ZonedDateTime;

/**
 * This payload will be sent for a ChangeSubscription if a resource was updated.
 *
 * @param <T> the resource type
 */
@JsonDeserialize(as = ResourceUpdatedPayloadImpl.class)
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

    /**
     * @return The point in time when the resource was updated
     */
    ZonedDateTime getModifiedAt();
}
