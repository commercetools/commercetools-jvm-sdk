package io.sphere.sdk.customers.commands;

import com.fasterxml.jackson.core.type.TypeReference;
import io.sphere.sdk.client.HttpRequestIntent;
import io.sphere.sdk.commands.CommandImpl;
import io.sphere.sdk.customers.Customer;
import io.sphere.sdk.customers.CustomerToken;
import io.sphere.sdk.models.Versioned;
import io.sphere.sdk.json.JsonUtils;

import static io.sphere.sdk.http.HttpMethod.POST;

/**
 Creates a token for verifying the customer's email.

 {@include.example io.sphere.sdk.customers.commands.CustomerCreateEmailTokenCommandTest#execution()}
 */
public class CustomerCreateEmailTokenCommand extends CommandImpl<CustomerToken> {
    private final String id;
    private final long version;
    private final int ttlMinutes;

    private CustomerCreateEmailTokenCommand(final String id, final long version, final int ttlMinutes) {
        this.id = id;
        this.version = version;
        this.ttlMinutes = ttlMinutes;
    }

    public static CustomerCreateEmailTokenCommand of(final Versioned<Customer> customer, final int timeToLiveInMinutes) {
        return new CustomerCreateEmailTokenCommand(customer.getId(), customer.getVersion(), timeToLiveInMinutes);
    }

    public String getId() {
        return id;
    }

    public long getVersion() {
        return version;
    }

    public int getTtlMinutes() {
        return ttlMinutes;
    }

    @Override
    protected TypeReference<CustomerToken> typeReference() {
        return CustomerToken.typeReference();
    }

    @Override
    public HttpRequestIntent httpRequestIntent() {
        return HttpRequestIntent.of(POST, "/customers/email-token", JsonUtils.toJson(this));
    }
}
