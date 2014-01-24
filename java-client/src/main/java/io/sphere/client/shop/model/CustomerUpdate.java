package io.sphere.client.shop.model;

import com.google.common.base.Strings;
import io.sphere.internal.command.CustomerCommands;
import io.sphere.internal.command.Update;

/** CustomerUpdate is used to update a customer in the backend. */
public class CustomerUpdate extends Update<CustomerCommands.CustomerUpdateAction> {

    /** Sets customer's name. */
    public CustomerUpdate setName(CustomerName name) {
        if (Strings.isNullOrEmpty(name.getFirstName()) && Strings.isNullOrEmpty(name.getLastName()))
            throw new IllegalArgumentException("First name and last name can't be empty when updating a customer.");
        add(new CustomerCommands.ChangeName(name));
        return this;
    }

    /** Sets/updates customer's email. */
    public CustomerUpdate setEmail(String email) { 
        add(new CustomerCommands.ChangeEmail(email));
        return this;
    }

    /** Adds an address to the customer's address list.
     *
     *  <p>You can call this method multiple times to add multiple addresses. All the actions will be
     *  executed as part of one HTTP request. */
    public CustomerUpdate addAddress(Address address) { 
        add(new CustomerCommands.AddAddress(address));
        return this;
    }

    /** Replaces the address with the given id with a new address. */
    public CustomerUpdate changeAddress(String addressId, Address address) {
        add(new CustomerCommands.ChangeAddress(addressId, address));
        return this;
    }

    /** Removes the address with given id from the customer's address list. */
    public CustomerUpdate removeAddress(String addressId) {
        add(new CustomerCommands.RemoveAddress(addressId));
        return this;
    }

    /** Sets one of customer's addresses as the default shipping address. */
    public CustomerUpdate setDefaultShippingAddress(String addressId) {
        add(new CustomerCommands.SetDefaultShippingAddress(addressId));
        return this;
    }

    /** Clears the default shipping address id. */
    public CustomerUpdate clearDefaultShippingAddress() {
        add(new CustomerCommands.SetDefaultShippingAddress(null));
        return this;
    }

    /** Sets one of customer's addresses as the default billing address. */
    public CustomerUpdate setDefaultBillingAddress(String addressId) {
        add(new CustomerCommands.SetDefaultBillingAddress(addressId));
        return this;
    }

    /** Clears the default billing address id. */
    public CustomerUpdate clearDefaultBillingAddress() {
        add(new CustomerCommands.SetDefaultBillingAddress(null));
        return this;
    }

    public CustomerUpdate setExternalId(final String externalId) {
        add(new CustomerCommands.SetExternalId(externalId));
        return this;
    }
}
