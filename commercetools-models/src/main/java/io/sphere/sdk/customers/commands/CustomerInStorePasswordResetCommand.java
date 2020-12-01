package io.sphere.sdk.customers.commands;

import com.fasterxml.jackson.databind.JavaType;
import io.sphere.sdk.client.HttpRequestIntent;
import io.sphere.sdk.commands.CommandImpl;
import io.sphere.sdk.customers.Customer;
import io.sphere.sdk.json.SphereJsonUtils;
import io.sphere.sdk.models.Versioned;

import javax.annotation.Nullable;

import static io.sphere.sdk.http.HttpMethod.POST;

import static io.sphere.sdk.client.SphereRequestUtils.urlEncode;

public final class CustomerInStorePasswordResetCommand extends CommandImpl<Customer> {

    @Nullable
    private final String id;
    @Nullable
    private final Long version;
    private final String tokenValue;
    private final String newPassword;
    private final String storeKey;

    private CustomerInStorePasswordResetCommand(final String storeKey, final Versioned<Customer> customer, final String tokenValue, final String newPassword) {
        this.id = customer != null ? customer.getId() : null;
        this.version = customer != null ? customer.getVersion() : null;
        this.tokenValue = tokenValue;
        this.newPassword = newPassword;
        this.storeKey = storeKey;
    }

    /**
     * Creates a command that can set a new password for a customer with a certain token.
     * @param tokenValue value of the token belonging to a customer
     * @param newPassword the new password which should be set for the customer
     * @return command object
     */
    public static CustomerInStorePasswordResetCommand ofTokenAndPassword(final String storeKey, final String tokenValue, final String newPassword) {
        return new CustomerInStorePasswordResetCommand(storeKey, null, tokenValue, newPassword);
    }

    @Override
    protected JavaType jacksonJavaType() {
        return SphereJsonUtils.convertToJavaType(Customer.typeReference());
    }

    @Override
    public HttpRequestIntent httpRequestIntent() {
        return HttpRequestIntent.of(POST, "/in-store/key=" + urlEncode(storeKey) + "/customers/password/reset", SphereJsonUtils.toJsonString(this));
    }

    public String getTokenValue() {
        return tokenValue;
    }

    public String getNewPassword() {
        return newPassword;
    }
}
