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

 Verifies customer's email using a token.

 {@include.example io.sphere.sdk.customers.commands.CustomerCreateEmailTokenCommandTest#execution()}

 @see Customer
 @see Customer#isEmailVerified()
 @see CustomerCreateEmailTokenCommand
 */
public final class CustomerVerifyEmailCommand extends CommandImpl<Customer> {

    private final String id;
    private final Long version;
    private final String tokenValue;

    private CustomerVerifyEmailCommand(final String id, final Long version, final String tokenValue) {
        this.id = id;
        this.version = version;
        this.tokenValue = tokenValue;
    }

    @Override
    protected JavaType jacksonJavaType() {
        return SphereJsonUtils.convertToJavaType(Customer.typeReference());
    }

    @Override
    public HttpRequestIntent httpRequestIntent() {
        return HttpRequestIntent.of(POST, "/customers/email/confirm", SphereJsonUtils.toJsonString(this));
    }

    public static CustomerVerifyEmailCommand of(final Versioned<Customer> customer, final String tokenValue) {
        return new CustomerVerifyEmailCommand(customer.getId(), customer.getVersion(), tokenValue);
    }

    public static CustomerVerifyEmailCommand of(final Versioned<Customer> customer, final CustomerToken customerToken) {
        return of(customer, customerToken.getValue());
    }

    public String getId() {
        return id;
    }

    public Long getVersion() {
        return version;
    }

    public String getTokenValue() {
        return tokenValue;
    }
}
