package io.sphere.sdk.customers.commands;

import com.fasterxml.jackson.databind.JavaType;
import io.sphere.sdk.client.HttpRequestIntent;
import io.sphere.sdk.commands.CommandImpl;
import io.sphere.sdk.customers.Customer;
import io.sphere.sdk.customers.CustomerToken;
import io.sphere.sdk.json.SphereJsonUtils;

import javax.annotation.Nullable;

import static io.sphere.sdk.http.HttpMethod.POST;

import static io.sphere.sdk.client.SphereRequestUtils.urlEncode;

public final class CustomerInStoreVerifyEmailCommand extends CommandImpl<Customer>  {

    @Nullable
    private final String id;
    @Nullable
    private final Long version;
    private final String tokenValue;
    private final String storeKey;

    private CustomerInStoreVerifyEmailCommand(final String storeKey, @Nullable final String id, @Nullable final Long version, final String tokenValue) {
        this.id = id;
        this.version = version;
        this.tokenValue = tokenValue;
        this.storeKey = storeKey;
    }

    @Override
    protected JavaType jacksonJavaType() {
        return SphereJsonUtils.convertToJavaType(Customer.typeReference());
    }

    @Override
    public HttpRequestIntent httpRequestIntent() {
        return HttpRequestIntent.of(POST, "/in-store/key=" + urlEncode(storeKey) + "/customers/email/confirm", SphereJsonUtils.toJsonString(this));
    }

    /**
     * Creates a command to verify the email of a customer.
     * @param tokenValue the value of the token which customer email address should be confirmed
     * @return command
     */
    public static CustomerInStoreVerifyEmailCommand ofTokenValue(final String storeKey, final String tokenValue) {
        return new CustomerInStoreVerifyEmailCommand(storeKey, null, null, tokenValue);
    }

    /**
     * Creates a command to verify the email of a customer.
     * @param customerToken the token object
     * @return command
     */
    public static CustomerInStoreVerifyEmailCommand ofCustomerToken(final String storeKey, final CustomerToken customerToken) {
        return ofTokenValue(storeKey, customerToken.getValue());
    }

    public String getTokenValue() {
        return tokenValue;
    }
}
