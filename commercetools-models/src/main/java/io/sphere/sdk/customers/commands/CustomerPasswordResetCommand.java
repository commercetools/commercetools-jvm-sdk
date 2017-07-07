package io.sphere.sdk.customers.commands;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.JavaType;
import io.sphere.sdk.client.HttpRequestIntent;
import io.sphere.sdk.commands.CommandImpl;
import io.sphere.sdk.customers.Customer;
import io.sphere.sdk.customers.CustomerToken;
import io.sphere.sdk.json.SphereJsonUtils;
import io.sphere.sdk.models.Versioned;

import javax.annotation.Nullable;

import static io.sphere.sdk.http.HttpMethod.POST;

/**
 * Sets a new password for the customer. Requires a token from {@link CustomerCreatePasswordTokenCommand}.
 *
 * {@include.example io.sphere.sdk.customers.commands.CustomerPasswordResetCommandIntegrationTest#execution()}
 *
 * <p>Example for a failed request:</p>
 * {@include.example io.sphere.sdk.customers.commands.CustomerPasswordResetCommandIntegrationTest#outdatedOrWrongToken()}
 *
 * @see Customer
 * @see CustomerCreatePasswordTokenCommand
 * @see io.sphere.sdk.customers.queries.CustomerByPasswordTokenGet
 */
public final class CustomerPasswordResetCommand extends CommandImpl<Customer> {
    @Nullable
    private final String id;
    @Nullable
    private final Long version;
    private final String tokenValue;
    private final String newPassword;

    private CustomerPasswordResetCommand(final Versioned<Customer> customer, final String tokenValue, final String newPassword) {
        this.id = customer != null ? customer.getId() : null;
        this.version = customer != null ? customer.getVersion() : null;
        this.tokenValue = tokenValue;
        this.newPassword = newPassword;
    }

    /**
     * Creates a command that can set a new password for a customer with a certain token.
     * @param customer unused
     * @param token the token belonging to a customer
     * @param newPassword the new password which should be set for the customer
     * @return command object
     * @deprecated use {@link #ofTokenAndPassword(String, String)} instead
     */
    @Deprecated//do not delete soon
    public static CustomerPasswordResetCommand of(final Versioned<Customer> customer, final CustomerToken token, final String newPassword) {
        return new CustomerPasswordResetCommand(customer, token.getValue(), newPassword);
    }

    /**
     * Creates a command that can set a new password for a customer with a certain token.
     * @param customer unused
     * @param tokenValue value of the token belonging to a customer
     * @param newPassword the new password which should be set for the customer
     * @return command object
     * @deprecated use {@link #ofTokenAndPassword(String, String)} instead
     */
    @Deprecated//do not delete soon
    public static CustomerPasswordResetCommand of(final Versioned<Customer> customer, final String tokenValue, final String newPassword) {
        return new CustomerPasswordResetCommand(customer, tokenValue, newPassword);
    }

    /**
     * Creates a command that can set a new password for a customer with a certain token.
     * @param tokenValue value of the token belonging to a customer
     * @param newPassword the new password which should be set for the customer
     * @return command object
     */
    public static CustomerPasswordResetCommand ofTokenAndPassword(final String tokenValue, final String newPassword) {
        return new CustomerPasswordResetCommand(null, tokenValue, newPassword);
    }

    @Override
    protected JavaType jacksonJavaType() {
        return SphereJsonUtils.convertToJavaType(Customer.typeReference());
    }

    @Override
    public HttpRequestIntent httpRequestIntent() {
        return HttpRequestIntent.of(POST, "/customers/password/reset", SphereJsonUtils.toJsonString(this));
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

    public String getNewPassword() {
        return newPassword;
    }
}
