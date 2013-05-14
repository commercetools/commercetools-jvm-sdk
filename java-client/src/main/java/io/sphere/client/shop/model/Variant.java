package io.sphere.client.shop.model;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

import io.sphere.client.model.Money;
import io.sphere.client.model.Reference;

import com.google.common.base.Optional;
import com.google.common.collect.FluentIterable;
import com.neovisionaries.i18n.CountryCode;
import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;
import org.joda.time.DateTime;

/** Variant of a product in a product catalog. */
public class Variant {
    private int id;
    private final String sku;
    @Nonnull private final List<Price> prices;
    @Nonnull private final List<Image> images;
    @Nonnull private final List<Attribute> attributes;
    private VariantAvailability availability;

    // also for tests
    @JsonCreator
    Variant(
            @JsonProperty("id") int id,
            @JsonProperty("sku") String sku,
            @JsonProperty("prices") List<Price> prices,
            @JsonProperty("images") List<Image> images,
            @JsonProperty("attributes") List<Attribute> attributes,
            @JsonProperty("availability") VariantAvailability availability) {
        this.id = id;
        this.sku = sku != null ? sku : "";
        this.prices = prices != null ? prices : new ArrayList<Price>();
        this.images = images != null ? images : new ArrayList<Image>();
        this.attributes = attributes != null ? attributes : new ArrayList<Attribute>();
        this.availability = availability;
    }

    /** The main image for this variant - the first image in the {@link #getImages() images} list.
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

    /** Unique id of this variant. */
    public int getId() { return id; }

    /** SKU (Stock Keeping Unit) of this variant. Optional. */
    public String getSKU() { return sku; }

    /** Images attached to this variant. */
    @Nonnull public List<Image> getImages() { return images; }

    /** Custom attributes of this variant. */
    @Nonnull public List<Attribute> getAttributes() { return attributes; }

    /** The inventory status of this variant. Optional. */
    public VariantAvailability getAvailability() { return availability; }

    // --------------------------------------------------------
    // Getters - price
    // --------------------------------------------------------

    /** The first price of this variant. */
    @Nullable public Price getPrice() { return prices.isEmpty() ? null : prices.get(0); }

    /** Price of this variant. */
    @Nonnull public List<Price> getPrices() { return prices; }

    /** Selects price for the given currency, country and customer group.
     *
     * The price for a specific currency is determined in the following order:
     * <ol>
     *   <li>Find a price for the given country and customer group.
     *   <li>If no price matches, find a price for the given customer group for all countries.
     *   <li>If no price matches, find a price for the given country for all customer groups.
     *   <li>If no price matches, find a price for all countries and all customer groups.
     * </ol>
     *
     * @param currencyCode  ISO Currency Code.
     * @param country       ISO Country Code. {@code null} if you want a generic price that applies in all countries.
     * @param customerGroup Customer group. {@link io.sphere.client.model.EmptyReference} or {@code null}
     *                      if you want a generic price that applies to all customer groups.
     * @return The selected price or {@code null} if no matching price exists. */
    @Nullable public Price getPrice(String currencyCode, CountryCode country, Reference<CustomerGroup> customerGroup) {
        FluentIterable<Price> iPrices = FluentIterable.from(prices);
        if (iPrices.isEmpty()) return null;
        return iPrices.firstMatch(Price.matchesP(currencyCode, country, customerGroup)).or(
                iPrices.firstMatch(Price.matchesP(currencyCode, null, customerGroup)).or(
                iPrices.firstMatch(Price.matchesP(currencyCode, country, null)).or(
                iPrices.firstMatch(Price.matchesP(currencyCode, null, null)).or(Optional.<Price>absent())))).orNull();
    }

    /** Selects price for the given currency and country, defined for all customer groups.
     * @see {@link #getPrice(String, com.neovisionaries.i18n.CountryCode, io.sphere.client.model.Reference)}. */
    @Nullable public Price getPrice(String currencyCode, CountryCode country) {
        return getPrice(currencyCode, country, null);
    }

    /** Selects price for the given currency and customer group, defined for all countries.
     * @see {@link #getPrice(String, com.neovisionaries.i18n.CountryCode, io.sphere.client.model.Reference)}. */
    @Nullable public Price getPrice(String currencyCode, Reference<CustomerGroup> customerGroup) {
        return getPrice(currencyCode, null, customerGroup);
    }

    /** Selects price for the given currency, defined for all countries and all customer groups.
     * @see {@link #getPrice(String, com.neovisionaries.i18n.CountryCode, io.sphere.client.model.Reference)}. */
    @Nullable public Price getPrice(String currencyCode) {
        return getPrice(currencyCode, null, null);
    }
}
