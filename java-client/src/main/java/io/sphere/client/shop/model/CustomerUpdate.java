package io.sphere.client.shop.model;

import io.sphere.internal.command.CustomerCommands;
import io.sphere.internal.command.Update;

/**
 * CustomerUpdate is used to update a customer in the backend.
 */
public class CustomerUpdate extends Update<CustomerCommands.CustomerUpdateAction> {

    /**
     * Sets the name fields for the customer (firstName, lastName, middleName, title).
     */
    public CustomerUpdate setName(CustomerName name) {
        if (name.getFirstName() == null || name.getLastName() == null)
            throw new IllegalArgumentException("First name and last name can't be empty when updating a customer.");
        addAction(new CustomerCommands.ChangeName(name));
        return this;
    }

    /** Sets/updates the email. */
    public CustomerUpdate setEmail(String email) { 
        addAction(new CustomerCommands.ChangeEmail(email));
        return this;
    }

    /** An address to be added to the customer's addresses list. It can be called several times to add
     * several addresses. */
    public CustomerUpdate addAddress(Address address) { 
        addAction(new CustomerCommands.AddAddress(address));
        return this;
    }

    /** Replaces the address with the given index with the new address. */
    public CustomerUpdate changeAddress(String addressIndex, Address address) {
        addAction(new CustomerCommands.ChangeAddress(addressIndex, address));
        return this;
    }

    /** Removes the address with the given index from the customer's addresses list. */
    public CustomerUpdate removeAddress(String addressIndex) {
        addAction(new CustomerCommands.RemoveAddress(addressIndex));
        return this;
    }

    /** Sets the default shipping address from the customer's addresses. */
    public CustomerUpdate setDefaultShippingAddress(String addressIndex) {
        addAction(new CustomerCommands.SetDefaultShippingAddress(addressIndex));
        return this;
    }

    /** Unsets the default shipping address. */
    public CustomerUpdate unsetDefaultShippingAddress() {
        addAction(new CustomerCommands.SetDefaultShippingAddress(null));
        return this;
    }

    /** Sets the default billing address from the customer's addresses. */
    public CustomerUpdate setDefaultBillingAddress(String addressIndex) {
        addAction(new CustomerCommands.SetDefaultBillingAddress(addressIndex));
        return this;
    }

    /** Unsets the default billing address. */
    public CustomerUpdate unsetDefaultBillingAddress() {
        addAction(new CustomerCommands.SetDefaultBillingAddress(null));
        return this;
    }
}
