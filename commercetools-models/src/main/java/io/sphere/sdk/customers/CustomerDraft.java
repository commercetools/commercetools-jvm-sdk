package io.sphere.sdk.customers;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.annotations.FactoryMethod;
import io.sphere.sdk.annotations.ResourceDraftValue;
import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.customergroups.CustomerGroup;
import io.sphere.sdk.models.Address;
import io.sphere.sdk.models.ResourceIdentifier;
import io.sphere.sdk.models.WithKey;
import io.sphere.sdk.stores.Store;
import io.sphere.sdk.types.CustomDraft;
import io.sphere.sdk.types.CustomFieldsDraft;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.util.List;
import java.util.Locale;

/**
 * Input object for a new {@link Customer}.
 *
 * @see CustomerDraftDsl
 * @see CustomerDraftBuilder
 */
@JsonDeserialize(as = CustomerDraftDsl.class)
@ResourceDraftValue(
        abstractBuilderClass = true,
        abstractResourceDraftValueClass = true,
        factoryMethods = {
            @FactoryMethod(parameterNames = {"email", "password"})})
public interface CustomerDraft extends CustomDraft, WithKey {
    @Nullable
    String getCustomerNumber();

    @Nullable
    String getKey();

    String getEmail();

    @Nullable
    String getFirstName();

    @Nullable
    String getLastName();

    @Nullable
    String getMiddleName();

    String getPassword();

    @Nullable
    String getTitle();

    @Nullable
    String getExternalId();

    /**
     * @deprecated use {@link CustomerDraft#getAnonymousCart()} instead
     */
    @Deprecated
    @Nullable
    String getAnonymousCartId();

    @Nullable
    ResourceIdentifier<Cart> getAnonymousCart();

    @Nullable
    String getAnonymousId();

    default CustomerName getName() {
        return CustomerName.of(getTitle(), getFirstName(), getMiddleName(), getLastName());
    }

    @Nullable
    String getCompanyName();

    @Nullable
    ResourceIdentifier<CustomerGroup> getCustomerGroup();

    @Nullable
    LocalDate getDateOfBirth();

    @Nullable
    Integer getDefaultBillingAddress();

    @Nullable
    Integer getDefaultShippingAddress();

    @JsonProperty("isEmailVerified")
    Boolean isEmailVerified();

    @Nullable
    String getVatId();

    @Nullable
    List<Address> getAddresses();

    @Nullable
    CustomFieldsDraft getCustom();

    @Nullable
    Locale getLocale();

    @Nullable
    List<Integer> getBillingAddresses();

    @Nullable
    List<Integer> getShippingAddresses();

    @Nullable
    String getSalutation();
    
    @Nullable
    List<ResourceIdentifier<Store>> getStores();
}
