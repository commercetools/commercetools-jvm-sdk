package io.sphere.sdk.customobjects.occexample;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.models.Base;

public class CustomerNumberCounter extends Base {
    private final Long lastUsedNumber;
    private final String customerId;

    @JsonCreator
    public CustomerNumberCounter(final Long lastUsedNumber, final String customerId) {
        this.lastUsedNumber = lastUsedNumber;
        this.customerId = customerId;
    }

    public long getLastUsedNumber() {
        return lastUsedNumber;
    }

    public String getCustomerId() {
        return customerId;
    }
}
