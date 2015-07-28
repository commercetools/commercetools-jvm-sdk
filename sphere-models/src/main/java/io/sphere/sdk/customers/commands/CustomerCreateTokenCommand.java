package io.sphere.sdk.customers.commands;

import com.fasterxml.jackson.core.type.TypeReference;
import io.sphere.sdk.client.HttpRequestIntent;
import io.sphere.sdk.commands.CommandImpl;
import io.sphere.sdk.customers.CustomerToken;
import io.sphere.sdk.json.SphereJsonUtils;

import static io.sphere.sdk.http.HttpMethod.POST;

public final class CustomerCreateTokenCommand extends CommandImpl<CustomerToken> {
    private final String email;

    private CustomerCreateTokenCommand(final String customerEmail) {
        this.email = customerEmail;
    }

    public static CustomerCreateTokenCommand of(final String customerEmail) {
        return new CustomerCreateTokenCommand(customerEmail);
    }

    @Override
    protected TypeReference<CustomerToken> typeReference() {
        return CustomerToken.typeReference();
    }

    @Override
    public HttpRequestIntent httpRequestIntent() {
        return HttpRequestIntent.of(POST, "/customers/password-token", SphereJsonUtils.toJsonString(this));
    }

    public String getEmail() {
        return email;
    }
}
