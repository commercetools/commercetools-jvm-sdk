package io.sphere.sdk.subscriptions;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.annotations.ResourceValue;

@JsonDeserialize(as = ChangeSubscriptionImpl.class)
@ResourceValue
public interface ChangeSubscription {
    @JsonProperty("resourceTypeId")
    String getResourceTypeId();

    static <R> ChangeSubscription of(final Class<R> resourceType) {
        final String type = resourceType.getAnnotation(JsonTypeName.class).value();
        return new ChangeSubscriptionImpl(type);
    }
}
