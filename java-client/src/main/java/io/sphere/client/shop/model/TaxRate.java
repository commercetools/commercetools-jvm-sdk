package io.sphere.client.shop.model;

import com.neovisionaries.i18n.CountryCode;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import javax.annotation.Nonnull;

/** A tax rate of a {@link LineItem}. */
@JsonIgnoreProperties("id")  // technical id used for managing project tax rates
public class TaxRate {
    @Nonnull private String name = "";
    private double amount;
    @JsonProperty("includedInPrice") private boolean isIncludedInPrice;
    @Nonnull private CountryCode country;
    private String state = "";

    //for JSON deserializer
    private TaxRate() {}

    /** The name of the tax rate, such as 'General tax (19%)'. */
    @Nonnull public String getName() { return name; }

    /** The amount of the tax rate, in the range [0..1]. */
    public double getAmount() { return amount; }

    /** True if the tax is included in product price.
     *
     * <p>If {@code isIncludedInPrice} is true, the product price is specified as a gross price and the tax
     * is included in that price.
     *
     * <p>If {@code isIncludedInPrice} is false, the product price is specified as a net price and the tax
     * is added on top of that price. */
    public boolean isIncludedInPrice() { return isIncludedInPrice; }

    /** The country where this tax rate applies. */
    @Nonnull public CountryCode getCountry() { return country; }

    /** The state where this tax rate applies. */
    public String getState() { return state; }

    @Override
    public String toString() {
        return "TaxRate{" +
                "name='" + name + '\'' +
                ", amount=" + amount +
                ", isIncludedInPrice=" + isIncludedInPrice +
                ", country=" + country +
                ", state='" + state + '\'' +
                '}';
    }
}
