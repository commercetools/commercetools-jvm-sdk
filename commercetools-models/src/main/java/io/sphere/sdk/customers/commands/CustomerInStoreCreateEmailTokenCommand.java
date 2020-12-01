package io.sphere.sdk.customers.commands;

import com.fasterxml.jackson.databind.JavaType;
import io.sphere.sdk.client.HttpRequestIntent;
import io.sphere.sdk.commands.CommandImpl;
import io.sphere.sdk.customers.Customer;
import io.sphere.sdk.customers.CustomerToken;
import io.sphere.sdk.json.SphereJsonUtils;
import io.sphere.sdk.models.Identifiable;
import io.sphere.sdk.models.Versioned;

import javax.annotation.Nullable;

import static io.sphere.sdk.http.HttpMethod.POST;

import static io.sphere.sdk.client.SphereRequestUtils.urlEncode;

public final class CustomerInStoreCreateEmailTokenCommand extends CommandImpl<CustomerToken> {

    private final String id;
    @Nullable
    private final Long version;
    private final Integer ttlMinutes;
    private final String storeKey;

    private CustomerInStoreCreateEmailTokenCommand(final String storeKey, final String id, final Long version, final Integer ttlMinutes) {
        this.id = id;
        this.version = version;
        this.ttlMinutes = ttlMinutes;
        this.storeKey = storeKey;
    }

    /**
     * Creates a command object to create a token to verify a customer's email.
     * No optimistic version control is used.
     * @param id the id belonging to the customer
     * @param timeToLiveInMinutes the time in minutes the token is valid, platform limitation apply
     * @return command
     */
    public static CustomerInStoreCreateEmailTokenCommand ofCustomerId(final String storeKey, final String id, final Integer timeToLiveInMinutes) {
        return new CustomerInStoreCreateEmailTokenCommand(storeKey, id, null, timeToLiveInMinutes);
    }

    /**
     * Creates a command object to create a token to verify a customer's email.
     * No optimistic version control is used.
     * @param customer customer
     * @param timeToLiveInMinutes the time in minutes the token is valid, platform limitation apply
     * @return command
     */
    public static CustomerInStoreCreateEmailTokenCommand of(final String storeKey,final Identifiable<Customer> customer, final Integer timeToLiveInMinutes) {
        final Long version = customer != null && customer instanceof Versioned
                ? ((Versioned<Customer>)customer).getVersion()
                :null;
        return new CustomerInStoreCreateEmailTokenCommand(storeKey, customer.getId(), version, timeToLiveInMinutes);
    }

    public Integer getTtlMinutes() {
        return ttlMinutes;
    }

    public String getId() {
        return id;
    }

    @Nullable
    public Long getVersion() {
        return version;
    }

    @Override
    protected JavaType jacksonJavaType() {
        return SphereJsonUtils.convertToJavaType(CustomerToken.typeReference());
    }

    @Override
    public HttpRequestIntent httpRequestIntent() {
        return HttpRequestIntent.of(POST, "/in-store/key=" + urlEncode(storeKey) + "/customers/email-token", SphereJsonUtils.toJsonString(this));
    }
}
