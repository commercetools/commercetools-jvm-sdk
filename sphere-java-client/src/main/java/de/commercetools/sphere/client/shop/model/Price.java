package de.commercetools.sphere.client.shop.model;

import java.util.Locale;

import com.google.common.base.Strings;
import de.commercetools.sphere.client.model.EmptyReference;
import de.commercetools.sphere.client.model.Money;
import de.commercetools.sphere.client.model.Reference;

import com.google.common.base.Predicate;
import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;

import javax.annotation.Nonnull;

/** Product variant price.
 *
 * A price has a value and optional country and customer group.
 *
 * Examples:
 *  price1 = (10EUR, DE, gold)  - applies to gold customers in Germany
 *  price2 = (15EUR, DE)        - applies to customers in Germany
 *  price3 = (20EUR)            - applies to all customers
 *
 *  See also {@link Variant#getPrice(String, String, de.commercetools.sphere.client.model.Reference)}.
 * */
public class Price {
    @Nonnull private Money value;
    private String country = "";
    private Reference<CustomerGroup> customerGroup = EmptyReference.create("customerGroup");

    @JsonCreator public Price(
            @Nonnull @JsonProperty("value") Money value,
            @JsonProperty("country") String country,
            @JsonProperty("customerGroup") Reference<CustomerGroup> customerGroup) {
        this.value = value;
        this.country = country != null ? country : "";
        this.customerGroup = customerGroup != null ? customerGroup : EmptyReference.<CustomerGroup>create("customerGroup");
    }

    /** The monetary value of the price. */
    public Money getValue() { return value; }

    /** The country where this price is valid, used in price calculations. */
    public Locale getCountry() { return new Locale(country); }

    /** The customer group for which this price is valid, used in price calculations. */
    public Reference<CustomerGroup> getCustomerGroup() { return customerGroup; }

    public boolean matches(String currencyCode, String country, Reference<CustomerGroup> customerGroup) {
        return (value == null || (value != null && value.getCurrencyCode().equals(currencyCode))) &&
               (Strings.isNullOrEmpty(country) ? Strings.isNullOrEmpty(this.country) : country.equals(this.country)) &&
               ((customerGroup == null || customerGroup.isEmpty()) ?
                    this.customerGroup.isEmpty() :
                    !this.customerGroup.isEmpty() && this.customerGroup.getId().equals(customerGroup.getId()));
    }

    // package private
    static final Predicate<Price> matchesP(final String currencyCode, final String country, final Reference<CustomerGroup> customerGroup) {
        return new Predicate<Price>() {
            public boolean apply(Price p) { return p.matches(currencyCode, country, customerGroup); }
        };
    }

    @Override public String toString() {
        String c = Strings.isNullOrEmpty(country) ? "all-countries" : country;
        String g = customerGroup.isEmpty() ? "all-groups" : customerGroup.getId();
        return "(" + value.toString() + ", " + c + ", " + g + ")";
    }
}
