package io.sphere.sdk.subscriptions;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.annotations.ResourceValue;

import javax.annotation.Nullable;
import java.util.List;

@JsonDeserialize(as = MessageSubscriptionImpl.class)
@ResourceValue
public interface MessageSubscription {
    String getResourceTypeId();

    @Nullable
    List<String> getTypes();

    /**
     * Creates a message subscription for all message types of the given resource type.
     *
     * @param resourceTypeId the resource type id
     * @return new message subscription
     */
    static MessageSubscription of(final String resourceTypeId) {
        return new MessageSubscriptionImpl(resourceTypeId, null);
    }

    /**
     * Creates a message subscription for the given messages types of the given resource type.
     *
     * @param resourceTypeId the resource type id
     * @param types          the message type ids
     * @return new message subscription
     */
    static MessageSubscription of(final String resourceTypeId, final List<String> types) {
        return new MessageSubscriptionImpl(resourceTypeId, types);
    }
}
