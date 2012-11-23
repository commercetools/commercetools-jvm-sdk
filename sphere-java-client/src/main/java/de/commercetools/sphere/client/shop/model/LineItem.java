package de.commercetools.sphere.client.shop.model;

import de.commercetools.sphere.client.model.Reference;
import org.codehaus.jackson.annotate.JsonProperty;

/** Single product variant in a {@link Cart} or {@link Order}, with a quantity. */
public class LineItem {
    private String id;
    private String productId;
    @JsonProperty("name") private String productName;
    @JsonProperty("variant") private Variant productVariant;
    private int quantity;
    private Variant variant;
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
    public Variant getProductVariant() { return productVariant; }

    /** Number of items ordered. */
    public int getQuantity() { return quantity; }
}
