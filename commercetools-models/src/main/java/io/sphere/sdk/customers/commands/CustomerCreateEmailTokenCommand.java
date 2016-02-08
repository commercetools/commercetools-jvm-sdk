package io.sphere.sdk.customers.commands;

import com.fasterxml.jackson.databind.JavaType;
import io.sphere.sdk.client.HttpRequestIntent;
import io.sphere.sdk.commands.CommandImpl;
import io.sphere.sdk.customers.Customer;
import io.sphere.sdk.customers.CustomerToken;
import io.sphere.sdk.models.Versioned;
import io.sphere.sdk.json.SphereJsonUtils;

import static io.sphere.sdk.http.HttpMethod.POST;

/**
 Creates a token for verifying the customer's email.

 {@include.example io.sphere.sdk.customers.commands.CustomerCreateEmailTokenCommandTest#execution()}

 @see Customer
 @see Customer#isEmailVerified()
 @see CustomerVerifyEmailCommand
 */
public final class CustomerCreateEmailTokenCommand extends CommandImpl<CustomerToken> {
    private final String id;
    private final Long version;
    private final Integer ttlMinutes;

    private CustomerCreateEmailTokenCommand(final String id, final Long version, final Integer ttlMinutes) {
        this.id = id;
        this.version = version;
        this.ttlMinutes = ttlMinutes;
    }

    public static CustomerCreateEmailTokenCommand of(final Versioned<Customer> customer, final Integer timeToLiveInMinutes) {
        return new CustomerCreateEmailTokenCommand(customer.getId(), customer.getVersion(), timeToLiveInMinutes);
    }

    public String getId() {
        return id;
    }

    public Long getVersion() {
        return version;
    }

    public Integer getTtlMinutes() {
        return ttlMinutes;
    }

    @Override
    protected JavaType jacksonJavaType() {
        return SphereJsonUtils.convertToJavaType(CustomerToken.typeReference());
    }

    @Override
    public HttpRequestIntent httpRequestIntent() {
        return HttpRequestIntent.of(POST, "/customers/email-token", SphereJsonUtils.toJsonString(this));
    }
}
