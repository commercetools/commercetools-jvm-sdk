package io.sphere.sdk.customers.commands;

import com.fasterxml.jackson.core.type.TypeReference;
import io.sphere.sdk.commands.CommandImpl;
import io.sphere.sdk.customers.CustomerSignInResult;
import io.sphere.sdk.client.HttpRequestIntent;
import io.sphere.sdk.json.SphereJsonUtils;

import java.util.Optional;

import static io.sphere.sdk.http.HttpMethod.POST;

/**
 * Retrieves the authenticated customer (a customer that matches the given email/password pair).
 * Before signing in, a customer might have created an anonymous cart.
 * After signing in, the content of the anonymous cart should be in the customer's cart.
 * If the customer did not have a cart associated to him, then the anonymous cart becomes the customer's cart.
 * If a customer already had a cart associated to him, then the content of the anonymous cart will be copied to the customer's cart.
 * If a line item in the anonymous cart matches an existing line item in the customer's cart (same product ID and variant ID),
 * then the maximum quantity of both line items is used as the new quantity.
 *
 * {@include.example io.sphere.sdk.customers.commands.CustomerSignInCommandTest#execution()}
 *
 *  <p>Example for invalid credentials:</p>
 * {@include.example io.sphere.sdk.customers.commands.CustomerSignInCommandTest#executionWithInvalidEmail()}
 */
public class CustomerSignInCommand extends CommandImpl<CustomerSignInResult> {
    private final String email;
    private final String password;
    private final Optional<String> anonymousCartId;

    private CustomerSignInCommand(final String email, final String password, final Optional<String> anonymousCartId) {
        this.email = email;
        this.password = password;
        this.anonymousCartId = anonymousCartId;
    }

    public static CustomerSignInCommand of(final String email, final String password) {
        return of(email, password, Optional.<String>empty());
    }

    public static CustomerSignInCommand of(final String email, final String password, final String anonymousCartId) {
        return of(email, password, Optional.of(anonymousCartId));
    }

    public static CustomerSignInCommand of(final String email, final String password, final Optional<String> anonymousCartId) {
        return new CustomerSignInCommand(email, password, anonymousCartId);
    }

    @Override
    protected TypeReference<CustomerSignInResult> typeReference() {
        return CustomerSignInResult.typeReference();
    }

    @Override
    public HttpRequestIntent httpRequestIntent() {
        return HttpRequestIntent.of(POST, "/login", SphereJsonUtils.toJson(this));
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Optional<String> getAnonymousCartId() {
        return anonymousCartId;
    }
}