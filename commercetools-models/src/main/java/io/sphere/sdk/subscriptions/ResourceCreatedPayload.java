package io.sphere.sdk.subscriptions;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.annotations.ResourceValue;

import java.time.ZonedDateTime;

/**
 * This payload will be sent for a ChangeSubscription if a resource was created.
 *
 * @param <T> the resource type
 */
@JsonDeserialize(as = ResourceCreatedPayloadImpl.class)
@ResourceValue
public interface ResourceCreatedPayload<T> extends Payload<T> {

    /**
     * The version of the resource after it was created.
     *
     * @return the resource version after creation
     */
    Long getVersion();

    /**
     * @return The point in time when the resource was created
     */
    ZonedDateTime getModifiedAt();
}
