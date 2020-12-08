package io.sphere.sdk.customers.commands;

import com.fasterxml.jackson.databind.JavaType;
import io.sphere.sdk.client.HttpRequestIntent;
import io.sphere.sdk.commands.CommandImpl;
import io.sphere.sdk.customers.CustomerToken;
import io.sphere.sdk.json.SphereJsonUtils;

import javax.annotation.Nullable;

import static io.sphere.sdk.http.HttpMethod.POST;

import static io.sphere.sdk.client.SphereRequestUtils.urlEncode;

public final class CustomerInStoreCreatePasswordTokenCommand extends CommandImpl<CustomerToken> {

    private final String email;
    @Nullable
    private final Long ttlMinutes;

    private final String storeKey;

    private CustomerInStoreCreatePasswordTokenCommand(final String storeKey, final String customerEmail, @Nullable final Long ttlMinutes) {
        this.email = customerEmail;
        this.ttlMinutes = ttlMinutes;
        this.storeKey = storeKey;
    }

    public static CustomerInStoreCreatePasswordTokenCommand of(final String storeKey, final String customerEmail) {
        return new CustomerInStoreCreatePasswordTokenCommand(storeKey, customerEmail, null);
    }

    public static CustomerInStoreCreatePasswordTokenCommand of(final String storeKey, final String customerEmail, @Nullable final Long ttlMinutes) {
        return new CustomerInStoreCreatePasswordTokenCommand(storeKey, customerEmail, ttlMinutes);
    }

    @Override
    protected JavaType jacksonJavaType() {
        return SphereJsonUtils.convertToJavaType(CustomerToken.class);
    }

    @Override
    public HttpRequestIntent httpRequestIntent() {
        return HttpRequestIntent.of(POST, "/in-store/key=" + urlEncode(storeKey) + "/customers/password-token", SphereJsonUtils.toJsonString(this));
    }

    public String getEmail() {
        return email;
    }

    @Nullable
    public Long getTtlMinutes() {
        return ttlMinutes;
    }

}
