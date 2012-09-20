package de.commercetools.sphere.client.shop.model.orders;

import de.commercetools.sphere.client.model.Money;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/** Single product in a {@link Cart} with a quantity. */
@JsonIgnoreProperties(ignoreUnknown=true) // temp until this class fully matches the json returned by the backend
public class LineItem {
    private String id;
    private String productId;
    private String name;
    private Money price;
    private int quantity;

    // for JSON deserializer
    private LineItem() {}

    /** Unique id of this line item. */
    public String getId() { return id; }
    /** Unique if of the product. */
    public String getProductId() { return productId; }
    /** Name of the product. */
    public String getName() { return name; }
    /** Number of products. */
    public int getQuantity() { return quantity; }
    /** Product price times quantity. */
    public Money getPrice() { return price; }
}
