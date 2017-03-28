package io.sphere.sdk.subscriptions;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.annotations.ResourceValue;
import io.sphere.sdk.models.TypeRegistry;

@JsonDeserialize(as = ChangeSubscriptionImpl.class)
@ResourceValue
public interface ChangeSubscription {
    @JsonProperty("resourceTypeId")
    String getResourceTypeId();

    @JsonIgnore
    default <T> Class<T> getResourceType() {
        return TypeRegistry.of().toClass(getResourceTypeId());
    }

    static <R> ChangeSubscription of(final Class<R> resourceType) {
        final String type = TypeRegistry.of().toType(resourceType);
        return new ChangeSubscriptionImpl(type);
    }
}
