package de.commercetools.sphere.client.shop.model;

import de.commercetools.sphere.client.model.Money;
import de.commercetools.sphere.client.model.Reference;
import org.codehaus.jackson.annotate.JsonProperty;

/** Single product variant in a {@link Cart} or {@link Order}, with a quantity. */
public class LineItem {
    private String id;
    private String productId;
    @JsonProperty("name") private String productName;
    @JsonProperty("variant") private Variant variant;
    private int quantity;
    private Reference<Catalog> catalog;

    // for JSON deserializer
    private LineItem() {}

    /** Unique id of this line item. */
    public String getId() { return id; }

    /** Unique id of the product. */
    public String getProductId() { return productId; }

    /** Name of the product. */
    public String getProductName() { return productName; }

    /** Copy of the product variant. */
    public Variant getVariant() { return variant; }

    /** Number of items ordered. */
    public int getQuantity() { return quantity; }

    /** The total price of this line item, that is variant price times quantity. */
    public Money getPrice() { return variant.getPrice().multiply(quantity); }
}
