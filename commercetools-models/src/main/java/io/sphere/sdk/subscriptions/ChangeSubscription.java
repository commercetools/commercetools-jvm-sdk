package io.sphere.sdk.subscriptions;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.annotations.ResourceValue;

@JsonDeserialize(as = ChangeSubscription.class)
@ResourceValue
public interface ChangeSubscription<R> {
    String getResourceTypeId();

    static <R> ChangeSubscription<R> of(final Class<R> resourceType) {
        final String type = resourceType.getAnnotation(JsonTypeName.class).value();
        return new ChangeSubscriptionImpl(type);
    }
}
