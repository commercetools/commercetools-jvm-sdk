package io.sphere.sdk.customers;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.customergroups.CustomerGroup;
import io.sphere.sdk.models.Address;
import io.sphere.sdk.models.Referenceable;
import io.sphere.sdk.models.ResourceIdentifier;
import io.sphere.sdk.stores.Store;
import io.sphere.sdk.types.CustomFieldsDraft;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;

public final class CustomerDraftDsl extends CustomerDraftDslBase<CustomerDraftDsl> {

    CustomerDraftDsl(@Nullable final List<Address> addresses, @Nullable final ResourceIdentifier<Cart> anonymousCart, @Nullable final String anonymousCartId, @Nullable final String anonymousId, @Nullable final List<Integer> billingAddresses, @Nullable final String companyName, @Nullable final CustomFieldsDraft custom, @Nullable final ResourceIdentifier<CustomerGroup> customerGroup, @Nullable final String customerNumber, @Nullable final LocalDate dateOfBirth, @Nullable final Integer defaultBillingAddress, @Nullable final Integer defaultShippingAddress, final String email, @JsonProperty("isEmailVerified") final Boolean emailVerified, @Nullable final String externalId, @Nullable final String firstName, @Nullable final String key, @Nullable final String lastName, @Nullable final Locale locale, @Nullable final String middleName, final String password, @Nullable final String salutation , @Nullable final List<Integer> shippingAddresses, @Nullable final List<ResourceIdentifier<Store>> stores, @Nullable final String title, @Nullable final String vatId) {
        super(addresses, anonymousCart, anonymousCartId, anonymousId, billingAddresses, companyName, custom, customerGroup, customerNumber, dateOfBirth, defaultBillingAddress, defaultShippingAddress, email, emailVerified, externalId, firstName, key, lastName, locale, middleName, password, salutation, shippingAddresses, stores, title, vatId);

        if (!isValidAddressIndex(addresses, defaultBillingAddress) || !isValidAddressIndex(addresses, defaultShippingAddress)) {
            throw new IllegalArgumentException("The defaultBillingAddress and defaultShippingAddress cannot contain an index which is not in the address list");
        }
    }

    public static CustomerDraftDsl of(final CustomerName customerName, final String email, final String password) {
        return CustomerDraftBuilder.of(customerName, email, password).build();
    }

    @Deprecated
    public CustomerDraftDsl withCart(final Referenceable<Cart> cart) {
        Objects.requireNonNull(cart);
        return withAnonymousCart(cart.toResourceIdentifier());
    }

    private static boolean isValidAddressIndex(final List<Address> addresses, final Integer addressIndex) {
        return Optional.ofNullable(addressIndex).map(i -> i < addresses.size() && i >= 0).orElse(true);
    }

    public CustomerDraftDsl withCustomerGroup(@Nullable final Referenceable<CustomerGroup> customerGroup) {
        return newBuilder().customerGroup(Optional.ofNullable(customerGroup).map(Referenceable::toReference).orElse(null)).build();
    }
}
