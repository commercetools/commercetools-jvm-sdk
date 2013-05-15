package io.sphere.client.shop.model;

import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Strings;
import io.sphere.client.model.EmptyReference;
import io.sphere.client.model.Reference;

import io.sphere.client.model.VersionedId;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/** A customer that exists in the backend. */
@JsonIgnoreProperties({"type", "password"})
public class Customer {
    @Nonnull private String id;
    @JsonProperty("version") private int version;
    private String email = "";
    @JsonProperty("title") private String title = "";
    @JsonProperty("firstName") private String firstName = "";
    @JsonProperty("middleName") private String middleName = "";
    @JsonProperty("lastName") private String lastName = "";
    @Nonnull private List<Address> addresses = new ArrayList<Address>();
    private String defaultShippingAddressId = "";
    private String defaultBillingAddressId = "";
    @JsonProperty("isEmailVerified") private boolean isEmailVerified;
    @Nonnull private Reference<CustomerGroup> customerGroup = EmptyReference.create("customerGroup");

    // for JSON deserializer
    private Customer() {}

    public Customer(String id, int version) {
        this.id = id;
        this.version = version;
    }

    /** The unique id. */
    @Nonnull public String getId() { return id; }

    /** The {@link #getId() id} plus version. */
    @Nonnull public VersionedId getIdAndVersion() { return VersionedId.create(id, version); }

    /** Email address of the customer. */
    public String getEmail() { return email; }

    /** Customer's name. */
    @Nonnull public CustomerName getName() {
        return new CustomerName(title,  firstName, middleName, lastName);
    }

    /** A list of customer's addresses. */
    @Nonnull public List<Address> getAddresses() { return addresses; }

    /** Finds an address by id in the {@link #getAddresses() addresses} list.
     *  @returns The address with given id or null if no such address exists. */
    @Nullable public Address getAddressById(String addressId) {
        if (Strings.isNullOrEmpty(addressId)) return null;
        for (Address address: getAddresses()) {
            if (address.getId() != null && address.getId().equals(addressId)) return address;
        }
        return null;
    }

    /** Id of the default shipping address in the {@link #getAddresses() addresses} list. Optional. */
    public String getDefaultShippingAddressId() { return defaultShippingAddressId; }

    /** Id of the default billing address in the {@link #getAddresses() addresses} list. Optional. */
    public String getDefaultBillingAddressId() { return defaultBillingAddressId; }

    /** If true, customer's email address has been verified to be valid. */
    public boolean isEmailVerified() { return isEmailVerified; }

    /** The customer group this customer belongs to. Optional. */
    @Nonnull public Reference<CustomerGroup> getCustomerGroup() { return customerGroup; }
}
