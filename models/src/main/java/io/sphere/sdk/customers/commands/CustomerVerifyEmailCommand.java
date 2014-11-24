package io.sphere.sdk.customers.commands;

import com.fasterxml.jackson.core.type.TypeReference;
import io.sphere.sdk.commands.CommandImpl;
import io.sphere.sdk.customers.Customer;
import io.sphere.sdk.customers.CustomerToken;
import io.sphere.sdk.http.HttpRequest;
import io.sphere.sdk.models.Versioned;
import io.sphere.sdk.utils.JsonUtils;

import static io.sphere.sdk.http.HttpMethod.POST;

/**

 Verifies customer's email using a token.

 {@include.example io.sphere.sdk.customers.commands.CustomerCreateEmailTokenCommandTest#execution()}
 */
public class CustomerVerifyEmailCommand extends CommandImpl<Customer> {

    private final String id;
    private final long version;
    private final String tokenValue;

    public CustomerVerifyEmailCommand(final String id, final long version, final String tokenValue) {
        this.id = id;
        this.version = version;
        this.tokenValue = tokenValue;
    }

    @Override
    protected TypeReference<Customer> typeReference() {
        return Customer.typeReference();
    }

    @Override
    public HttpRequest httpRequest() {
        return HttpRequest.of(POST, "/customers/email/confirm", JsonUtils.toJson(this));
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

    public long getVersion() {
        return version;
    }

    public String getTokenValue() {
        return tokenValue;
    }
}
