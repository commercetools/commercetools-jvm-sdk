package io.sphere.sdk.subscriptions;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.annotations.ResourceValue;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;

@JsonDeserialize(as = MessageSubscriptionImpl.class)
@ResourceValue
public interface MessageSubscription {
    String getResourceTypeId();

    @Nullable
    List<String> getTypes();

    static MessageSubscription of(final String resourceTypeId, final String... types) {
        return new MessageSubscriptionImpl(resourceTypeId, Arrays.asList(types));
    }
}
