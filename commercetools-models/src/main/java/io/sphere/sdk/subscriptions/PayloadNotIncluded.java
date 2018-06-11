package io.sphere.sdk.subscriptions;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.annotations.ResourceValue;

@ResourceValue
@JsonDeserialize(as = PayloadNotIncludedImpl.class)
public interface PayloadNotIncluded {


    String getReason();

    /**
     * @return The value of the {@code type} field in the original payload.
     */
    String getPayloadType();
}
