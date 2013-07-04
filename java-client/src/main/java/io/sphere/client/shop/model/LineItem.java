package io.sphere.client.shop.model;

import io.sphere.client.model.LocalizedString;
import io.sphere.client.model.Money;
import java.util.Locale;

import org.codehaus.jackson.annotate.JsonProperty;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/** Single product variant in a {@link Cart} or {@link Order}, with a quantity. */
public class LineItem {
    @Nonnull private String id;
    @Nonnull private String productId;
    @Nonnull @JsonProperty("name") private LocalizedString productName = Attribute.defaultLocalizedString;
    @Nonnull @JsonProperty("variant") private Variant variant;
    private int quantity;
    @Nonnull private Price price;
    private TaxRate taxRate;

    // for JSON deserializer
    private LineItem() {}

    /** Unique id of this line item. */
    @Nonnull public String getId() { return id; }

    /** Unique id of the product. */
    @Nonnull public String getProductId() { return productId; }

    /** Name of the product. If there is only one translation it will return this. Otherwise,
        it will return a random one. If there are no translations will return the empty string. */
    @Nonnull public String getProductName() { return productName.get(); }

    /**
     * @param locale
     * @return The product name in the requested locale. It it doesn't exist will return the empty string.
     */
    @Nonnull public String getProductName(Locale locale) { return productName.get(); }

    /** Copy of the product variant from the time when time line item was created. */
    @Nonnull public Variant getVariant() { return variant; }

    /** Number of items ordered. */
    public int getQuantity() { return quantity; }

    /** The total price of this line item, that is price value times quantity. */
    @Nonnull public Money getTotalPrice() { return price.getValue().multiply(quantity); }

    /** The price. */
    @Nonnull public Price getPrice() { return price; }

    /** The tax rate of this line item. Optional.
     *
     *  <p>The tax rate is selected based on the cart's shipping address and is only set when the
     *  shipping address is set. */
    @Nullable public TaxRate getTaxRate() { return taxRate; }
}
