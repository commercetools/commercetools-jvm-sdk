package io.sphere.client.shop.model;

import com.neovisionaries.i18n.CountryCode;

/** A tax rate of a product. */
public class TaxRate {
    private String name;
    private double amount;
    private boolean includedInPrice;
    private CountryCode country;
    private String state = "";
    private String id;

    //for JSON deserializer
    private TaxRate() {}

    /** The name of the tax rate. */
    public String getName() { return name; }

    /** The amount of the tax rate in the range 0 to 1. */
    public double getAmount() { return amount; }

    /** True if the tax is included in price. */
    public boolean isIncludedInPrice() { return includedInPrice; }

    /** The country for which this tax rate is defined.  */
    public CountryCode getCountry() { return country; }

    /** The state for which this tax rate is defined.  */
    public String getState() { return state; }

    public String getId() { return id; }
}
