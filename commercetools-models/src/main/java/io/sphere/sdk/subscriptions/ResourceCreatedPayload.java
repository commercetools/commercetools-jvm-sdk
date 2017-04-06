package io.sphere.sdk.subscriptions;

import com.fasterxml.jackson.annotation.JsonTypeName;
import io.sphere.sdk.annotations.ResourceValue;

/**
 * This payload will be sent for a ChangeSubscription if a resource was created.
 *
 * @param <T> the resource type
 */
@JsonTypeName("ResourceCreated")
@ResourceValue
public interface ResourceCreatedPayload<T> extends Payload<T> {

    /**
     * The version of the resource after it was created.
     *
     * @return the resource version after creation
     */
    Long getVersion();
}
