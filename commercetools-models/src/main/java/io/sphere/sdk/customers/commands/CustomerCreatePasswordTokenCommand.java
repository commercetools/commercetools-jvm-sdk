package io.sphere.sdk.customers.commands;

import com.fasterxml.jackson.databind.JavaType;
import io.sphere.sdk.client.HttpRequestIntent;
import io.sphere.sdk.commands.CommandImpl;
import io.sphere.sdk.customers.CustomerToken;
import io.sphere.sdk.json.SphereJsonUtils;

import javax.annotation.Nullable;

import static io.sphere.sdk.http.HttpMethod.POST;

/**
 * Creates a token to reset the customer password with {@link CustomerPasswordResetCommand}.
 *
 * {@include.example io.sphere.sdk.customers.commands.CustomerPasswordResetCommandIntegrationTest#execution()}
 *
 * @see io.sphere.sdk.customers.Customer
 * @see io.sphere.sdk.customers.commands.CustomerPasswordResetCommand
 * @see io.sphere.sdk.customers.queries.CustomerByPasswordTokenGet
 */
public final class CustomerCreatePasswordTokenCommand extends CommandImpl<CustomerToken> {
    private final String email; 
    @Nullable
    private final Long ttlMinutes;

    private CustomerCreatePasswordTokenCommand(final String customerEmail, @Nullable final Long ttlMinutes) {
        this.email = customerEmail;
        this.ttlMinutes = ttlMinutes;
    }

    public static CustomerCreatePasswordTokenCommand of(final String customerEmail) {
        return new CustomerCreatePasswordTokenCommand(customerEmail, null);
    }

    public static CustomerCreatePasswordTokenCommand of(final String customerEmail, @Nullable final Long ttlMinutes) {
        return new CustomerCreatePasswordTokenCommand(customerEmail, ttlMinutes);
    }
    
    @Override
    protected JavaType jacksonJavaType() {
        return SphereJsonUtils.convertToJavaType(CustomerToken.class);
    }

    @Override
    public HttpRequestIntent httpRequestIntent() {
        return HttpRequestIntent.of(POST, "/customers/password-token", SphereJsonUtils.toJsonString(this));
    }

    public String getEmail() {
        return email;
    }

    @Nullable
    public Long getTtlMinutes() {
        return ttlMinutes;
    }
}
