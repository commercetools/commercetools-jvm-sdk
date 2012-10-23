package de.commercetools.sphere.client.shop.model;

/**
 * Represents a postal address.
 */
public class Address {
    private String fullAddress;

    // for JSON deserializer
    private Address() {}

    public String getFullAddress() {
        return fullAddress;
    }
}
