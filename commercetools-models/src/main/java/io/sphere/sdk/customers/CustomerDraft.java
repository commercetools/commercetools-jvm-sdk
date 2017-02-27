package io.sphere.sdk.customers;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.annotations.FactoryMethod;
import io.sphere.sdk.annotations.ResourceDraftValue;
import io.sphere.sdk.customergroups.CustomerGroup;
import io.sphere.sdk.models.Address;
import io.sphere.sdk.models.Reference;
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
 *
 */
@JsonDeserialize(as = CustomerDraftDsl.class)
@ResourceDraftValue(factoryMethods = {
        @FactoryMethod(parameterNames = {"email", "password"})},
        additionalDslClassContents = {"public static CustomerDraftDsl of(final CustomerName customerName, final String email, final String password) {\n" +
                "        return CustomerDraftBuilder.of(customerName, email, password).build();\n" +
                "    }",
                "    public CustomerDraftDsl withCart(final io.sphere.sdk.carts.Cart cart) {\n" +
                        "        Objects.requireNonNull(cart);\n" +
                        "        return withAnonymousCartId(cart.getId());\n" +
                        "    }",
        "private static boolean isValidAddressIndex(final List<Address> addresses, final Integer addressIndex) {\n" +
                "        return Optional.ofNullable(addressIndex).map(i -> i < addresses.size() && i >= 0).orElse(true);\n" +
                "    }"},
        abstractBaseClass = true , useBuilderStereotypeDslClass = true)
public interface CustomerDraft extends CustomDraft {
    @Nullable
    String getCustomerNumber();

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

    @Nullable
    String getAnonymousCartId();

    @Nullable
    String getAnonymousId();

    default CustomerName getName() {
        return CustomerName.of(getTitle(), getFirstName(), getMiddleName(), getLastName());
    }

    @Nullable
    String getCompanyName();

    @Nullable
    Reference<CustomerGroup> getCustomerGroup();

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
}
