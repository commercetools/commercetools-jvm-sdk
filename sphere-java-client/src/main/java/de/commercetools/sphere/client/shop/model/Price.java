package de.commercetools.sphere.client.shop.model;

import java.util.Locale;

import de.commercetools.sphere.client.model.Money;
import de.commercetools.sphere.client.model.Reference;

/** Product variant price.
 *
 * A price has a value and optional country and customer group.
 *
 * Examples:
 *  price1 = (10EUR, DE, staff)
 *  price2 = (15EUR, DE)
 *  price3 = (20EUR)
 *
 *  The first price of 15EUR is defined for staff customer group from germany. The second
 *  price is defined for germany, while the third price is defined for all countries.
 * */
public class Price {
    private Money value;
    private String country;
    private Reference<CustomerGroup> customerGroup;

    //for JSON deserializer
    private Price() {}

    public Price(Money value, String country, Reference<CustomerGroup> customerGroup) {
        this.value = value;
        this.country = country;
        this.customerGroup = customerGroup;
    }

    /* The monetary value of the price. */
    public Money getValue() { return value; }

    /** The country used for price calculations. */
    public Locale getCountry() { return new Locale(country); }

    /** The customer group of the price. */
    public Reference<CustomerGroup> getCustomerGroup() { return customerGroup; }
}
