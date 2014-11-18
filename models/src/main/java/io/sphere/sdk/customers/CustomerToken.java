package io.sphere.sdk.customers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.models.Timestamped;

@JsonDeserialize(as = CustomerTokenImpl.class)
public interface CustomerToken extends Timestamped {
    String getId();

    String getCustomerId();

    String getValue();

    static TypeReference<CustomerToken> typeReference() {
        return new TypeReference<CustomerToken>() {
            @Override
            public String toString() {
                return "TypeReference<CustomerToken>";
            }
        };
    }
}
