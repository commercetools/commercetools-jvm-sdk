package io.sphere.sdk.customobjects.occexample;

import com.fasterxml.jackson.core.type.TypeReference;
import io.sphere.sdk.customobjects.CustomObject;
import io.sphere.sdk.models.Base;

public class CustomerNumberCounter extends Base {
    private final long lastUsedNumber;
    private final String customerId;

    public CustomerNumberCounter(final long lastUsedNumber, final String customerId) {
        this.lastUsedNumber = lastUsedNumber;
        this.customerId = customerId;
    }

    public long getLastUsedNumber() {
        return lastUsedNumber;
    }

    public String getCustomerId() {
        return customerId;
    }

    public static TypeReference<CustomObject<CustomerNumberCounter>> customObjectTypeReference() {
        return new TypeReference<CustomObject<CustomerNumberCounter>>() {
        };
    }
}
