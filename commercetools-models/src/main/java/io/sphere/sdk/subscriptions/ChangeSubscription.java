package io.sphere.sdk.subscriptions;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.annotations.ResourceValue;

@JsonDeserialize(as = ChangeSubscriptionImpl.class)
@ResourceValue
public interface ChangeSubscription {
    @JsonProperty("resourceTypeId")
    String getResourceTypeId();

    static ChangeSubscription of(final String resourceType) {
        return new ChangeSubscriptionImpl(resourceType);
    }
}
