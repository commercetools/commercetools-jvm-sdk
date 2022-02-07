package io.sphere.sdk.subscriptions;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.annotations.ResourceValue;

import java.time.ZonedDateTime;

/**
 * This payload will be sent for a ChangeSubscription if a resource was deleted.
 *
 * @param <T> the resource type
 */
@JsonDeserialize(as = ResourceDeletedPayloadImpl.class)
@ResourceValue
public interface ResourceDeletedPayload<T> extends Payload<T> {
    /**
     * The version of the resource at the time of deletion.
     *
     * @return the resource version at deletion time
     */
    Long getVersion();

    /**
     * @return The point in time when the resource was deleted
     */
    ZonedDateTime getModifiedAt();

    /**
     * @return true if the dataErasure parameter was set to true.
     */
    Boolean getDataErasure();
}
