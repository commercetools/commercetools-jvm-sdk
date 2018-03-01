package io.sphere.sdk.customers.commands;

import com.fasterxml.jackson.databind.JavaType;
import io.sphere.sdk.carts.AnonymousCartSignInMode;
import io.sphere.sdk.client.HttpRequestIntent;
import io.sphere.sdk.commands.CommandImpl;
import io.sphere.sdk.customers.CustomerSignInResult;
import io.sphere.sdk.json.SphereJsonUtils;

import javax.annotation.Nullable;

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
 * If the customer forgot the password reset it with {@link CustomerCreatePasswordTokenCommand}.
 *
 * <p>Example for a simple sign in without any carts involved:</p>
 * {@include.example io.sphere.sdk.customers.commands.CustomerSignInCommandIntegrationTest#execution()}
 *
 * <p>Example for a signin where the customer has a cart and signs in with an anonymous cart</p>
 * {@include.example io.sphere.sdk.customers.commands.CustomerSignInCommandIntegrationTest#signInWithAnonymousCart()}
 *
 *  <p>Example for invalid credentials:</p>
 * {@include.example io.sphere.sdk.customers.commands.CustomerSignInCommandIntegrationTest#executionWithInvalidEmail()}
 *
 * @see io.sphere.sdk.customers.Customer
 * @see CustomerCreatePasswordTokenCommand
 */
public final class CustomerSignInCommand extends CommandImpl<CustomerSignInResult> {
    private final String email;
    private final String password;
    @Nullable
    private final String anonymousCartId;
    @Nullable
    private final String anonymousId;
    @Nullable
    private final AnonymousCartSignInMode anonymousCartSignInMode;
    @Nullable
    private final Boolean updateProductData;

    private CustomerSignInCommand(final String email, final String password, @Nullable final String anonymousCartId, @Nullable final String anonymousId, final AnonymousCartSignInMode anonymousCartSignInMode,
                                  @Nullable final Boolean updateProductData) {
        this.email = email;
        this.password = password;
        this.anonymousCartId = anonymousCartId;
        this.anonymousId = anonymousId;
        this.anonymousCartSignInMode = anonymousCartSignInMode;
        this.updateProductData = updateProductData;
    }

    public static CustomerSignInCommand of(final String email, final String password) {
        return of(email, password, null);
    }

    public static CustomerSignInCommand of(final String email, final String password, @Nullable final String anonymousCartId) {
        return new CustomerSignInCommand(email, password, anonymousCartId, null, null, null);
    }

    public CustomerSignInCommand withAnonymousId(@Nullable final String anonymousId) {
        return new CustomerSignInCommand(email, password, anonymousCartId, anonymousId, anonymousCartSignInMode, updateProductData);
    }

    public CustomerSignInCommand withAnonymousCartSignInMode(@Nullable final AnonymousCartSignInMode anonymousCartSignInMode) {
        return new CustomerSignInCommand(email, password, anonymousCartId, anonymousId, anonymousCartSignInMode, updateProductData);
    }

    public CustomerSignInCommand withUpdateProductData(@Nullable final Boolean updateProductData) {
        return new CustomerSignInCommand(email, password, anonymousCartId, anonymousId, anonymousCartSignInMode, updateProductData);
    }

    @Override
    protected JavaType jacksonJavaType() {
        return SphereJsonUtils.convertToJavaType(CustomerSignInResult.typeReference());
    }

    @Override
    public HttpRequestIntent httpRequestIntent() {
        return HttpRequestIntent.of(POST, "/login", SphereJsonUtils.toJsonString(this));
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    @Nullable
    public String getAnonymousCartId() {
        return anonymousCartId;
    }

    @Nullable
    public String getAnonymousId() {
        return anonymousId;
    }

    @Nullable
    public AnonymousCartSignInMode getAnonymousCartSignInMode() {
        return anonymousCartSignInMode;
    }
}