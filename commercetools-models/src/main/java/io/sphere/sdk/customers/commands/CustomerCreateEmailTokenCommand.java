package io.sphere.sdk.customers.commands;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.JavaType;
import io.sphere.sdk.client.HttpRequestIntent;
import io.sphere.sdk.commands.CommandImpl;
import io.sphere.sdk.customers.Customer;
import io.sphere.sdk.customers.CustomerToken;
import io.sphere.sdk.json.SphereJsonUtils;
import io.sphere.sdk.models.Identifiable;
import io.sphere.sdk.models.Versioned;

import javax.annotation.Nullable;

import static io.sphere.sdk.http.HttpMethod.POST;

/**
 Creates a token for verifying the customer's email.

 {@include.example io.sphere.sdk.customers.commands.CustomerCreateEmailTokenCommandIntegrationTest#execution()}

 @see Customer
 @see Customer#isEmailVerified()
 @see CustomerVerifyEmailCommand
 */
public final class CustomerCreateEmailTokenCommand extends CommandImpl<CustomerToken> {
    private final String id;
    @Nullable
    private final Long version;
    private final Integer ttlMinutes;

    private CustomerCreateEmailTokenCommand(final String id, final Long version, final Integer ttlMinutes) {
        this.id = id;
        this.version = version;
        this.ttlMinutes = ttlMinutes;
    }

    /**
     * Creates a command object to create a token to verify a customer's email.
     * No optimistic version control is used.
     * @param id the id belonging to the customer
     * @param timeToLiveInMinutes the time in minutes the token is valid, platform limitation apply
     * @return command
     */
    public static CustomerCreateEmailTokenCommand ofCustomerId(final String id, final Integer timeToLiveInMinutes) {
        return new CustomerCreateEmailTokenCommand(id, null, timeToLiveInMinutes);
    }

    /**
     * Creates a command object to create a token to verify a customer's email.
     * No optimistic version control is used.
     * @param customer customer
     * @param timeToLiveInMinutes the time in minutes the token is valid, platform limitation apply
     * @return command
     */
    public static CustomerCreateEmailTokenCommand of(final Identifiable<Customer> customer, final Integer timeToLiveInMinutes) {
        final Long version = customer != null && customer instanceof Versioned
                ? ((Versioned<Customer>)customer).getVersion()
                :null;
        return new CustomerCreateEmailTokenCommand(customer.getId(), version, timeToLiveInMinutes);
    }

    public String getId() {
        return id;
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
