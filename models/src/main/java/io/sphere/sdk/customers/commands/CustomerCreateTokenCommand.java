package io.sphere.sdk.customers.commands;

import com.fasterxml.jackson.core.type.TypeReference;
import io.sphere.sdk.commands.CommandImpl;
import io.sphere.sdk.customers.CustomerToken;
import io.sphere.sdk.http.HttpRequest;
import io.sphere.sdk.utils.JsonUtils;

import static io.sphere.sdk.http.HttpMethod.POST;

public class CustomerCreateTokenCommand extends CommandImpl<CustomerToken> {
    private final String email;

    public CustomerCreateTokenCommand(final String customerEmail) {
        this.email = customerEmail;
    }

    @Override
    protected TypeReference<CustomerToken> typeReference() {
        return CustomerToken.typeReference();
    }

    @Override
    public HttpRequest httpRequest() {
        return HttpRequest.of(POST, "/customers/password-token", JsonUtils.toJson(this));
    }

    public String getEmail() {
        return email;
    }
}
