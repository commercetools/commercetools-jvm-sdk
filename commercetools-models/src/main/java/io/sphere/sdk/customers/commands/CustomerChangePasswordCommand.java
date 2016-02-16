package io.sphere.sdk.customers.commands;

import com.fasterxml.jackson.databind.JavaType;
import io.sphere.sdk.client.HttpRequestIntent;
import io.sphere.sdk.commands.CommandImpl;
import io.sphere.sdk.customers.Customer;
import io.sphere.sdk.models.Versioned;
import io.sphere.sdk.json.SphereJsonUtils;

import static io.sphere.sdk.http.HttpMethod.POST;

/**
 * Updates the password of a customer. No tokens required.
 *
 * {@include.example io.sphere.sdk.customers.commands.CustomerChangePasswordCommandIntegrationTest#execution()}
 *
 * @see Customer
 */
public final class CustomerChangePasswordCommand extends CommandImpl<Customer> {
    private final String id;
    private final Long version;
    private final String currentPassword;
    private final String newPassword;

    private CustomerChangePasswordCommand(final String id, final Long version, final String currentPassword, final String newPassword) {
        this.id = id;
        this.version = version;
        this.currentPassword = currentPassword;
        this.newPassword = newPassword;
    }

    public static CustomerChangePasswordCommand of(final Versioned<Customer> customer, final String currentPassword, final String newPassword) {
        return new CustomerChangePasswordCommand(customer.getId(), customer.getVersion(), currentPassword, newPassword);
    }

    @Override
    protected JavaType jacksonJavaType() {
        return SphereJsonUtils.convertToJavaType(Customer.typeReference());
    }

    @Override
    public HttpRequestIntent httpRequestIntent() {
        return HttpRequestIntent.of(POST, "/customers/password/", SphereJsonUtils.toJsonString(this));
    }

    public String getId() {
        return id;
    }

    public Long getVersion() {
        return version;
    }

    public String getCurrentPassword() {
        return currentPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }
}
