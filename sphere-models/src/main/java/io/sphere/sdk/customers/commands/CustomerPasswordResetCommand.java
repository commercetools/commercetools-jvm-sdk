package io.sphere.sdk.customers.commands;

import com.fasterxml.jackson.core.type.TypeReference;
import io.sphere.sdk.client.HttpRequestIntent;
import io.sphere.sdk.commands.CommandImpl;
import io.sphere.sdk.customers.Customer;
import io.sphere.sdk.customers.CustomerToken;
import io.sphere.sdk.models.Versioned;
import io.sphere.sdk.json.SphereJsonUtils;

import static io.sphere.sdk.http.HttpMethod.POST;

public class CustomerPasswordResetCommand extends CommandImpl<Customer> {
    private final String id;
    private final long version;
    private final String tokenValue;
    private final String newPassword;

    private CustomerPasswordResetCommand(final Versioned<Customer> customer, final String tokenValue, final String newPassword) {
        this.id = customer.getId();
        this.version = customer.getVersion();
        this.tokenValue = tokenValue;
        this.newPassword = newPassword;
    }

    public static CustomerPasswordResetCommand of(final Versioned<Customer> customer, final CustomerToken token, final String newPassword) {
        return of(customer, token.getValue(), newPassword);
    }

    public static CustomerPasswordResetCommand of(final Versioned<Customer> customer, final String tokenValue, final String newPassword) {
        return new CustomerPasswordResetCommand(customer, tokenValue, newPassword);
    }

    @Override
    protected TypeReference<Customer> typeReference() {
        return Customer.typeReference();
    }

    @Override
    public HttpRequestIntent httpRequestIntent() {
        return HttpRequestIntent.of(POST, "/customers/password/reset", SphereJsonUtils.toJsonString(this));
    }

    public String getId() {
        return id;
    }

    public long getVersion() {
        return version;
    }

    public String getTokenValue() {
        return tokenValue;
    }

    public String getNewPassword() {
        return newPassword;
    }
}
