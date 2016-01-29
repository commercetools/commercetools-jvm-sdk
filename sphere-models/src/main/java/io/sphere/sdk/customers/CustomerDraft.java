package io.sphere.sdk.customers;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.customergroups.CustomerGroup;
import io.sphere.sdk.models.Address;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.types.CustomDraft;
import io.sphere.sdk.types.CustomFieldsDraft;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.util.List;

/**
 * Input object for a new {@link Customer}.
 *
 * @see CustomerDraftDsl
 * @see CustomerDraftBuilder
 *
 */
@JsonDeserialize(as = CustomerDraftDsl.class)
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

    CustomerName getName();

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
}
