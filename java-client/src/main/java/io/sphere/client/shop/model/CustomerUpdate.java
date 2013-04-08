package io.sphere.client.shop.model;

import io.sphere.internal.command.CustomerCommands;

import java.util.ArrayList;
import java.util.List;

/** Describes changes to be made to a customer. Used in {@link io.sphere.client.shop.CustomerService#update}. */
public class CustomerUpdate {
    private List<CustomerCommands.CustomerUpdateAction> actions = new ArrayList<CustomerCommands.CustomerUpdateAction>();

    /**  Sets the name fields for the customer (firstName, lastName, middleName, title). */
    public void setName(CustomerName name) {
        if (name.getFirstName() == null || name.getLastName() == null)
            throw new IllegalArgumentException("First name and last name can't be empty when updating a customer.");
        this.actions.add(new CustomerCommands.ChangeName(name));
    }

    /** Sets/updates the email. */
    public void setEmail(String email) { this.actions.add(new CustomerCommands.ChangeEmail(email)); }

    /** An address to be added to the customer's addresses list. It can be called several times to add
     * several addresses. */
    public void addAddress(Address address) { this.actions.add(new CustomerCommands.AddAddress(address)); }

    /** Replaces the address with the given index with the new address. */
    public void changeAddress(String addressIndex, Address address) {
        this.actions.add(new CustomerCommands.ChangeAddress(addressIndex, address));
    }

    /** Removes the address with the given index from the customer's addresses list. */
    public void removeAddress(String addressIndex) {
        this.actions.add(new CustomerCommands.RemoveAddress(addressIndex));
    }

    /** Sets the default shipping address from the customer's addresses. */
    public void setDefaultShippingAddress(String addressIndex) {
        this.actions.add(new CustomerCommands.SetDefaultShippingAddress(addressIndex));
    }

    /** Unsets the default shipping address. */
    public void unsetDefaultShippingAddress() {
        this.actions.add(new CustomerCommands.SetDefaultShippingAddress(null));
    }

    /** Sets the default billing address from the customer's addresses. */
    public void setDefaultBillingAddress(String addressIndex) {
        this.actions.add(new CustomerCommands.SetDefaultBillingAddress(addressIndex));
    }

    /** Unsets the default billing address. */
    public void unsetDefaultBillingAddress() {
        this.actions.add(new CustomerCommands.SetDefaultBillingAddress(null));
    }

    /** Internal method, should not be called by the shop developer. */
    public CustomerCommands.UpdateCustomer createCommand(String customerId, int customerVersion) {
        return new CustomerCommands.UpdateCustomer(customerId, customerVersion, actions);
    }
}