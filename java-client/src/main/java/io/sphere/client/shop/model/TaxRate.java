package io.sphere.client.shop.model;

import com.neovisionaries.i18n.CountryCode;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import javax.annotation.Nonnull;

/** A tax rate of a {@link LineItem}. */
@JsonIgnoreProperties("id")  // technical id used for managing project tax rates
public class TaxRate {
    @Nonnull private String name = "";
    private double amount;
    private boolean includedInPrice;
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
    public boolean isIncludedInPrice() { return includedInPrice; }

    /** The country where this tax rate applies. */
    @Nonnull public CountryCode getCountry() { return country; }

    /** The state where this tax rate applies. */
    public String getState() { return state; }
}
