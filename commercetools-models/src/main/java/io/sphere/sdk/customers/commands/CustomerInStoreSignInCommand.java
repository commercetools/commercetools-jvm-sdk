package io.sphere.sdk.customers.commands;

import com.fasterxml.jackson.databind.JavaType;
import io.sphere.sdk.carts.AnonymousCartSignInMode;
import io.sphere.sdk.client.HttpRequestIntent;
import io.sphere.sdk.commands.CommandImpl;
import io.sphere.sdk.customers.CustomerSignInResult;
import io.sphere.sdk.json.SphereJsonUtils;

import javax.annotation.Nullable;

import static io.sphere.sdk.http.HttpMethod.POST;

import static io.sphere.sdk.client.SphereRequestUtils.urlEncode;

public final class CustomerInStoreSignInCommand extends CommandImpl<CustomerSignInResult> {

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

    private final String storeKey;

    private CustomerInStoreSignInCommand(final String storeKey, final String email, final String password, @Nullable final String anonymousCartId, @Nullable final String anonymousId, final AnonymousCartSignInMode anonymousCartSignInMode,
                                  @Nullable final Boolean updateProductData) {
        this.email = email;
        this.password = password;
        this.anonymousCartId = anonymousCartId;
        this.anonymousId = anonymousId;
        this.anonymousCartSignInMode = anonymousCartSignInMode;
        this.updateProductData = updateProductData;
        this.storeKey = storeKey;
    }

    public static CustomerInStoreSignInCommand of(final String storeKey, final String email, final String password) {
        return of(storeKey, email, password, null);
    }

    public static CustomerInStoreSignInCommand of(final String storeKey, final String email, final String password, @Nullable final String anonymousCartId) {
        return new CustomerInStoreSignInCommand(storeKey, email, password, anonymousCartId, null, null, null);
    }

    public CustomerInStoreSignInCommand withAnonymousId(final String storeKey, @Nullable final String anonymousId) {
        return new CustomerInStoreSignInCommand(storeKey, email, password, anonymousCartId, anonymousId, anonymousCartSignInMode, updateProductData);
    }

    public CustomerInStoreSignInCommand withAnonymousCartSignInMode(final String storeKey, @Nullable final AnonymousCartSignInMode anonymousCartSignInMode) {
        return new CustomerInStoreSignInCommand(storeKey, email, password, anonymousCartId, anonymousId, anonymousCartSignInMode, updateProductData);
    }

    public CustomerInStoreSignInCommand withUpdateProductData(final String storeKey, @Nullable final Boolean updateProductData) {
        return new CustomerInStoreSignInCommand(storeKey, email, password, anonymousCartId, anonymousId, anonymousCartSignInMode, updateProductData);
    }

    @Override
    protected JavaType jacksonJavaType() {
        return SphereJsonUtils.convertToJavaType(CustomerSignInResult.typeReference());
    }

    @Override
    public HttpRequestIntent httpRequestIntent() {
        return HttpRequestIntent.of(POST, "/in-store/key=" + urlEncode(storeKey) + "/login", SphereJsonUtils.toJsonString(this));
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
