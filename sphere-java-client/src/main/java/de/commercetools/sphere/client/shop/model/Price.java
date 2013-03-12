package de.commercetools.sphere.client.shop.model;

import java.util.Locale;

import de.commercetools.sphere.client.model.Money;
import de.commercetools.sphere.client.model.Reference;

import com.google.common.base.Predicate;

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

    public boolean matches(String currencyCode, String country, Reference<CustomerGroup> customerGroup) {
        return (value == null ? false : value.getCurrencyCode().equals(currencyCode)) &&
                (country == null || country.equals("") ?
                    this.country == null || this.country.equals("") : country.equals(this.country)) &&
                (customerGroup == null || customerGroup.isEmpty() ?
                    this.customerGroup == null || this.customerGroup.isEmpty() :
                    (this.customerGroup == null || this.customerGroup.isEmpty() ? false : customerGroup.getId().equals(this.customerGroup.getId())));
    }

    public static final Predicate<Price> matchesP(
            final String currencyCode,
            final String country,
            final Reference<CustomerGroup> customerGroup) {
        return new Predicate<Price>() {
            public boolean apply(Price p) { return p.matches(currencyCode, country, customerGroup); }
        };
    }

    public String toString() {
        String c = country == null || country.isEmpty() ? "all-countries" : country;
        String g = customerGroup == null || customerGroup.isEmpty() ? "all-groups" : customerGroup.getId();
        return "(" + value.toString() + ", " + c + ", " + g + ")";
    }
}
