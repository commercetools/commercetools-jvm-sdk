package io.sphere.sdk.subscriptions;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.annotations.ResourceValue;

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
}
