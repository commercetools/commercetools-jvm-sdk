package de.commercetools.sphere.client.shop.model;

import java.util.ArrayList;
import java.util.List;

import de.commercetools.sphere.client.model.Money;
import de.commercetools.sphere.client.model.Reference;

import com.google.common.collect.FluentIterable;
import org.joda.time.DateTime;

/** Variant of a product in a product catalog. */
public class Variant {
    private int id;
    private String sku;
    private List<Price> prices= new ArrayList<Price>();
    private List<Image> images = new ArrayList<Image>();
    private List<Attribute> attributes = new ArrayList<Attribute>();
    private VariantAvailability availability;

    // for JSON deserializer
    private Variant() { }

    // for tests
    Variant(int id, String sku, List<Price> prices, List<Image> images, List<Attribute> attributes) {
        this.id = id;
        this.sku = sku;
        this.prices = prices;
        this.images = images;
        this.attributes = attributes;
    }

    /** The main image for this variant - that is the first image in the {@link #getImages()} list.
     *  @return The image or null if this variant has no images. */
    public Image getFeaturedImage() {
        if (this.images.isEmpty())
            return Image.none();
        return this.images.get(0);
    }

    // --------------------------------------------------------
    // Get attribute
    // --------------------------------------------------------

    /** Returns true if a custom attribute with given name is present. */
    public boolean hasAttribute(String attributeName) {
        return getAttribute(attributeName) != null;
    }

    /** Finds custom attribute with given name. Returns null if no such attribute exists. */
    public Attribute getAttribute(String attributeName) {
        for (Attribute a: attributes) {
            if (a.getName().equals(attributeName)) {
                return a;
            }
        }
        return null;
    }

    /** Returns the value of custom attribute.
     *  @return The value or null if no such attribute is present. */
    public Object get(String attributeName) {
        Attribute a = getAttribute(attributeName);
        return a == null ? null : a.getValue();
    }

    /** Returns the value of a custom string attribute.
     *  @return The value or empty string if no such attribute is present or its value is not a string. */
    public String getString(String attributeName) {
        Attribute a = getAttribute(attributeName);
        return a == null ? Attribute.defaultString : a.getString();
    }

    /** Returns the value of a custom number attribute.
     *  @return The value or 0 if no such attribute is present or its value is not an int. */
    public int getInt(String attributeName) {
        Attribute a = getAttribute(attributeName);
        return a == null ? Attribute.defaultInt : a.getInt();
    }

    /** Returns the value of a custom number attribute.
     *  @return The value or 0.0 if no such attribute is present or its value is not a double. */
    public double getDouble(String attributeName) {
        Attribute a = getAttribute(attributeName);
        return a == null ? Attribute.defaultDouble : a.getDouble();
    }

    /** Returns the value of a custom money attribute.
     *  @return The value or null if no such attribute is present or its value of not a Money instance. */
    public Money getMoney(String attributeName) {
        Attribute a = getAttribute(attributeName);
        return a == null ? Attribute.defaultMoney : a.getMoney();
    }

    /** Returns the value of a custom DateTime attribute.
     *  @return The value or null if no such attribute is present or its value is not a DateTime. */
    public DateTime getDateTime(String attributeName) {
        Attribute a = getAttribute(attributeName);
        return a == null ? Attribute.defaultDateTime : a.getDateTime();
    }

    // --------------------------------------------------------
    // Getters
    // --------------------------------------------------------

    /** Unique id of this variant. An id is never empty. */
    public int getId() { return id; }

    /** SKU (Stock Keeping Unit) of this variant. SKUs are optional. */
    public String getSKU() { return sku; }

    /** The first price of this variant. */
    public Price getPrice() { return prices.isEmpty() ? null : prices.get(0); }

    /** Price of this variant. */
    public List<Price> getPrices() { return prices; }

    /** Selects variants price for the given currency country and customer group.
     *
     * The price for a specific currency is determined in the following order:
     *   1. Find a price for the given country and customer group.
     *   2. If step 1 did not find a price, find a price for the given customer group for all countries.
     *   3. If step 2 did not find a price, find a price for the given country for all customer groups.
     *   4. If step 3 did not find a price, find a price for all countries and all customer groups.
     *
     *   @param currencyCode ISO Currency Code.
     *   @param country ISO Country Code. "" or null for all countries.
     *   @param customerGroup EmptyReference or null for all groups.
     *   @return the selected price.
     *   */
    public Price getPrice(String currencyCode, String country, Reference<CustomerGroup> customerGroup) {
        FluentIterable<Price> iPrices = FluentIterable.from(prices);
        if (iPrices.isEmpty()) return null;
        else {
            FluentIterable<Price> exact = iPrices.filter(Price.matchesP(currencyCode, country, customerGroup));
            if (exact.isEmpty()) {
                FluentIterable<Price> allCountries = iPrices.filter(Price.matchesP(currencyCode, null, customerGroup));
                if (allCountries.isEmpty()) {
                    FluentIterable<Price> allGroups = iPrices.filter(Price.matchesP(currencyCode, country, null));
                    if (allGroups.isEmpty()) {
                        FluentIterable<Price> allCountriesGroups = iPrices.filter(Price.matchesP(currencyCode, null, null));
                        if (allCountriesGroups.isEmpty()) return null; else return allCountriesGroups.get(0);
                    } else return allGroups.get(0);
                } else return allCountries.get(0);
            } else return exact.get(0);
        }
    }

    public Price getPrice(String currencyCode, String country) {
        return getPrice(currencyCode, country, null);
    }

    public Price getPrice(String currencyCode, Reference<CustomerGroup> customerGroup) {
        return getPrice(currencyCode, null, customerGroup);
    }

    public Price getPrice(String currencyCode) {
        return getPrice(currencyCode, null, null);
    }

    /** Images attached to this variant. */
    public List<Image> getImages() { return images; }

    /** Custom attributes of this variant. */
    public List<Attribute> getAttributes() { return attributes; }

    /** The inventory status of this variant. */
    public VariantAvailability getAvailability() { return availability; }

    /** The SKU of the variant. */
    public String getSku() { return sku; }

}
