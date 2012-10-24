package de.commercetools.sphere.client.shop.model;

import java.util.ArrayList;
import java.util.List;

/**
 * CustomerUpdate object is used to update a customer on the server.
 */
public class CustomerUpdate {
    private Name name;
    private String email;
    private List<Address> shippingAddressesToAdd = new ArrayList<Address>();

    public Name getName() { return name; }
    public String getEmail() { return email; }
    public List<Address> getShippingAddressesToAdd() { return shippingAddressesToAdd; }

    /**
     * Sets the name fields of the customer (firstName, lastName, middleName, title). If set, all the name fields
     * will be set. If a name middleName or title is null, the field will be unset on the server.
     */
    public void setName(Name name) {
        if (name.getFirstName() == null || name.getLastName() == null)
            throw new IllegalArgumentException("First name and last name must not be null in CustomerUpdate.");
        this.name = name;
    }

    /** Sets/updates the email. */
    public void setEmail(String email) { this.email = email; }

    /** A shipping address to be added to the Customer.shippingAddresses list. It can be called several times to add
     * several shipping addresses. */
    public void addShippingAddress(Address shippingAddress) { this.shippingAddressesToAdd.add(shippingAddress); }
}
