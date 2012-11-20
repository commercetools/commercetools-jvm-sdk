package de.commercetools.sphere.client.shop.model;

import de.commercetools.sphere.client.model.Money;
import de.commercetools.sphere.client.model.Reference;

/** Single product in a {@link Cart} with a quantity. */
public class LineItem {
    private String id;
    private String productId;
    private String name;
    private int quantity;
    private Variant variant;
    private Reference<Catalog> catalog;

    // for JSON deserializer
    private LineItem() {}

    /** Unique id of this line item. */
    public String getId() {
        return id;
    }
    /** Unique if of the product. */
    public String getProductId() {
        return productId;
    }

    /** Name of the product. */
    public String getName() {
        return name;
    }
    /** Number of products. */
    public int getQuantity() {
        return quantity;
    }

    /** Variant of the product. */
    public Variant getVariant() {
        return variant;
    }

    /** The catalog the variant is associated to. */
    public Reference<Catalog> getCatalog() {
        return catalog;
    }

    /** The price of the line item. */
    public Money getPrice() {
        return variant.getPrice();
    }
}
