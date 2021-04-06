package io.sphere.sdk.models;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.annotations.ResourceValue;
import io.sphere.sdk.customers.Customer;

import javax.annotation.Nullable;

@ResourceValue
@JsonDeserialize(as = ClientLoggingImpl.class)
public interface ClientLogging {
    @Nullable
    public String getClientId();

    @Nullable
    public String getExternalUserId();

    public Reference<Customer> getCustomer();

    @Nullable
    public String getAnonymousId();
}
