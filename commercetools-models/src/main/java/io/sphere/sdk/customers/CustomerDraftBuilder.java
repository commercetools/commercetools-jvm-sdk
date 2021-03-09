package io.sphere.sdk.customers;

import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.customergroups.CustomerGroup;
import io.sphere.sdk.models.Address;
import io.sphere.sdk.models.ResourceIdentifier;
import io.sphere.sdk.stores.Store;
import io.sphere.sdk.types.CustomFieldsDraft;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.util.List;
import java.util.Locale;

public final class CustomerDraftBuilder extends CustomerDraftBuilderBase<CustomerDraftBuilder> {

    CustomerDraftBuilder(@Nullable final List<Address> addresses, @Nullable final ResourceIdentifier<Cart> anonymousCart, @Nullable final String anonymousCartId, @Nullable final String anonymousId, @Nullable final List<Integer> billingAddresses, @Nullable final String companyName, @Nullable final CustomFieldsDraft custom, @Nullable final ResourceIdentifier<CustomerGroup> customerGroup, @Nullable final String customerNumber, @Nullable final LocalDate dateOfBirth, @Nullable final Integer defaultBillingAddress, @Nullable final Integer defaultShippingAddress, final String email, final Boolean emailVerified, @Nullable final String externalId, @Nullable final String firstName, @Nullable final String key, @Nullable final String lastName, @Nullable final Locale locale, @Nullable final String middleName, final String password, @Nullable final String salutation, @Nullable final List<Integer> shippingAddresses, final @Nullable List<ResourceIdentifier<Store>> stores, @Nullable final String title, @Nullable final String vatId) {
        super(addresses, anonymousCart, anonymousCartId, anonymousId, billingAddresses, companyName, custom, customerGroup, customerNumber, dateOfBirth, defaultBillingAddress, defaultShippingAddress, email, emailVerified, externalId, firstName, key, lastName, locale, middleName, password,salutation, shippingAddresses, stores, title, vatId);
    }

    public static CustomerDraftBuilder of(final CustomerName customerName, final String email, final String password) {
        return CustomerDraftBuilder.of(email, password)
                .firstName(customerName.getFirstName())
                .middleName(customerName.getMiddleName())
                .lastName(customerName.getLastName())
                .title(customerName.getTitle());
    }
}
