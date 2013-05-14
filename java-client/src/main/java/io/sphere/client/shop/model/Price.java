package io.sphere.client.shop.model;

import javax.annotation.Nonnull;

import io.sphere.client.model.EmptyReference;
import io.sphere.client.model.Money;
import io.sphere.client.model.Reference;

import com.google.common.base.Predicate;
import com.neovisionaries.i18n.CountryCode;
import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;

/** Price of a product variant.
 *
 * A price has a value and can be optionally restricted to a specific country and customer group.
 *
 * <p>Examples
 * <ul>
 *     <li>(10EUR, DE, gold)  - applies to gold customers in Germany
 *     <li>(15EUR, DE)        - applies to customers in Germany
 *     <li>(20EUR)            - applies to all customers
 * </ul>
 *
 * @see {@link Variant#getPrice(String, com.neovisionaries.i18n.CountryCode, io.sphere.client.model.Reference)}.
 * */
public class Price {
    @Nonnull private Money value;
    private CountryCode country = null;
    @Nonnull private Reference<CustomerGroup> customerGroup = EmptyReference.create("customerGroup");

    @JsonCreator public Price(
            @Nonnull @JsonProperty("value") Money value,
            @JsonProperty("country") CountryCode country,
            @JsonProperty("customerGroup") Reference<CustomerGroup> customerGroup) {
        this.value = value;
        this.country = country;
        this.customerGroup = customerGroup != null ? customerGroup : EmptyReference.<CustomerGroup>create("customerGroup");
    }

    /** The monetary value of the price. */
    public Money getValue() { return value; }

    /** The optional country where this price applies, used in price calculations. Returns null if country is not defined. */
    public CountryCode getCountry() { return country; }

    /** The optional country where this price applies, used in price calculations.
     *
     * @return The ISO 3166-1 (alpha-2) string representation of the country. If country is not defined, returns "" (empty string).
     * */
    public String getCountryString() { return country == null ? "" : country.getAlpha2(); }

    /** The optional customer group for which this price applies, used in price calculations. */
    @Nonnull public Reference<CustomerGroup> getCustomerGroup() { return customerGroup; }

    public boolean matches(String currencyCode, CountryCode country, Reference<CustomerGroup> customerGroup) {
        return (value == null || (value != null && value.getCurrencyCode().equals(currencyCode))) &&
               (country == null ? this.country == null : country.equals(this.country)) &&
               ((customerGroup == null || customerGroup.isEmpty()) ?
                    this.customerGroup.isEmpty() :
                    !this.customerGroup.isEmpty() && this.customerGroup.getId().equals(customerGroup.getId()));
    }

    // package private
    static final Predicate<Price> matchesP(final String currencyCode, final CountryCode country, final Reference<CustomerGroup> customerGroup) {
        return new Predicate<Price>() {
            public boolean apply(Price p) { return p.matches(currencyCode, country, customerGroup); }
        };
    }

    @Override public String toString() {
        String c = country == null ? "all-countries" : country.getAlpha2();
        String g = customerGroup.isEmpty() ? "all-groups" : customerGroup.getId();
        return "(" + value.toString() + ", " + c + ", " + g + ")";
    }
}
