package de.commercetools.sphere.client.shop.model;

import java.util.ArrayList;
import java.util.List;

/**
 * CustomerUpdate object used to update a customer in the backend.
 */
public class CustomerUpdate {
    private CustomerName customerName;
    private String email;
    private List<Address> shippingAddressesToAdd = new ArrayList<Address>();

    public CustomerName getName() { return customerName; }
    public String getEmail() { return email; }
    public List<Address> getShippingAddressesToAdd() { return shippingAddressesToAdd; }

    /**
     * Sets the name fields for the customer (firstName, lastName, middleName, title).
     */
    public void setName(CustomerName name) {
        if (name.getFirstName() == null || name.getLastName() == null)
            throw new IllegalArgumentException("First name and last name can't be empty when updating a customer.");
        this.customerName = name;
    }

    /** Sets/updates the email. */
    public void setEmail(String email) { this.email = email; }

    /** A shipping address to be added to the Customer.shippingAddresses list. It can be called several times to add
     * several shipping addresses. */
    public void addShippingAddress(Address shippingAddress) { this.shippingAddressesToAdd.add(shippingAddress); }
}
