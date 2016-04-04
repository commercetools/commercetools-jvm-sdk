package io.sphere.sdk.customers.commands;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.JavaType;
import io.sphere.sdk.client.HttpRequestIntent;
import io.sphere.sdk.commands.CommandImpl;
import io.sphere.sdk.customers.Customer;
import io.sphere.sdk.customers.CustomerToken;
import io.sphere.sdk.models.Versioned;
import io.sphere.sdk.json.SphereJsonUtils;

import javax.annotation.Nullable;

import static io.sphere.sdk.http.HttpMethod.POST;

/**

 Verifies customer's email using a token.

 {@include.example io.sphere.sdk.customers.commands.CustomerCreateEmailTokenCommandIntegrationTest#execution()}

 @see Customer
 @see Customer#isEmailVerified()
 @see CustomerCreateEmailTokenCommand
 */
public final class CustomerVerifyEmailCommand extends CommandImpl<Customer> {

    @Nullable
    private final String id;
    @Nullable
    private final Long version;
    private final String tokenValue;

    private CustomerVerifyEmailCommand(@Nullable final String id, @Nullable final Long version, final String tokenValue) {
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

    /**
     * Creates a command to verify the email of a customer.
     * @param customer unused
     * @param tokenValue the value of the token which customer email address should be confirmed
     * @return command
     * @deprecated use {@link #ofTokenValue(String)} or {@link #ofCustomerToken(CustomerToken)}
     */
    @Deprecated//do not delete soon
    public static CustomerVerifyEmailCommand of(final Versioned<Customer> customer, final String tokenValue) {
        return new CustomerVerifyEmailCommand(customer.getId(), customer.getVersion(), tokenValue);
    }

    /**
     * Creates a command to verify the email of a customer.
     * @param customer unused
     * @param customerToken the token object
     * @return command
     * @deprecated use {@link #ofTokenValue(String)} or {@link #ofCustomerToken(CustomerToken)}
     */
    @Deprecated//do not delete soon
    public static CustomerVerifyEmailCommand of(final Versioned<Customer> customer, final CustomerToken customerToken) {
        return new CustomerVerifyEmailCommand(customer.getId(), customer.getVersion(), customerToken.getValue());
    }

    /**
     * Creates a command to verify the email of a customer.
     * @param tokenValue the value of the token which customer email address should be confirmed
     * @return command
     */
    public static CustomerVerifyEmailCommand ofTokenValue(final String tokenValue) {
        return new CustomerVerifyEmailCommand(null, null, tokenValue);
    }

    /**
     * Creates a command to verify the email of a customer.
     * @param customerToken the token object
     * @return command
     */
    public static CustomerVerifyEmailCommand ofCustomerToken(final CustomerToken customerToken) {
        return ofTokenValue(customerToken.getValue());
    }

    @JsonIgnore
    @Deprecated//do not delete soon
    @Nullable
    public String getId() {
        return id;
    }

    @JsonIgnore
    @Deprecated//do not delete soon
    @Nullable
    public Long getVersion() {
        return version;
    }

    public String getTokenValue() {
        return tokenValue;
    }
}
