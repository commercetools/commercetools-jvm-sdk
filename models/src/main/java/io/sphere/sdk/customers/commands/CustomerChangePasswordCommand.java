package io.sphere.sdk.customers.commands;

import com.fasterxml.jackson.core.type.TypeReference;
import io.sphere.sdk.client.HttpRequestIntent;
import io.sphere.sdk.commands.CommandImpl;
import io.sphere.sdk.customers.Customer;
import io.sphere.sdk.models.Versioned;
import io.sphere.sdk.json.JsonUtils;

import static io.sphere.sdk.http.HttpMethod.POST;

/**
 *
 * {@include.example io.sphere.sdk.customers.commands.CustomerChangePasswordCommandTest#execution()}
 */
public class CustomerChangePasswordCommand extends CommandImpl<Customer> {
    private final String id;
    private final long version;
    private final String currentPassword;
    private final String newPassword;

    private CustomerChangePasswordCommand(final String id, final long version, final String currentPassword, final String newPassword) {
        this.id = id;
        this.version = version;
        this.currentPassword = currentPassword;
        this.newPassword = newPassword;
    }

    public static CustomerChangePasswordCommand of(final Versioned<Customer> customer, final String currentPassword, final String newPassword) {
        return new CustomerChangePasswordCommand(customer.getId(), customer.getVersion(), currentPassword, newPassword);
    }

    @Override
    protected TypeReference<Customer> typeReference() {
        return Customer.typeReference();
    }

    @Override
    public HttpRequestIntent httpRequestIntent() {
        return HttpRequestIntent.of(POST, "/customers/password/", JsonUtils.toJson(this));
    }

    public String getId() {
        return id;
    }

    public long getVersion() {
        return version;
    }

    public String getCurrentPassword() {
        return currentPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }
}
