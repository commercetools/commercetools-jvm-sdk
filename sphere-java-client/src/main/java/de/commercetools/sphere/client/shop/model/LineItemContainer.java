package de.commercetools.sphere.client.shop.model;

import java.util.List;
import java.util.ArrayList;

import de.commercetools.sphere.client.model.*;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.joda.time.DateTime;

/** Superclass for {@link Cart} and {@link Order}. */
@JsonIgnoreProperties("type")
public abstract class LineItemContainer {
    private String id;
    private int version;
    private List<LineItem> lineItems = new ArrayList<LineItem>();  // initialize to prevent NPEs
    private String customerId;
    private DateTime lastModifiedAt;
    private DateTime createdAt;
    @JsonProperty("amountTotal")
    protected Money totalPrice;
    private Address shippingAddress;

    /** Sum of quantities of line items. */
    public int getTotalQuantity() {
        int totalQuantity = 0;
        for (LineItem lineItem: this.getLineItems()) {
            totalQuantity += lineItem.getQuantity();
        }
        return totalQuantity;
    }

    protected LineItemContainer() {}

    /** Needed for tests. */
    protected LineItemContainer(String id, int version) {
        this.id = id;
        this.version = version;
    }
    // --------------------------------------------------------
    // Getters
    // --------------------------------------------------------

    /** Unique id of this cart. */
    public String getId() { return id; }

    /** Version that increases when the object is modified. */
    public int getVersion() { return version; }

    /** Returns the items in this cart or order. Does not fire a query to the backend. */
    public List<LineItem> getLineItems() { return lineItems; }

    /** Date and time when this object was last modified. */
    public DateTime getLastModifiedAt() { return lastModifiedAt; }

    /** Date and time when this object was created. */
    public DateTime getCreatedAt() { return createdAt; }

    /** The shipping address of this cart or order. */
    public Address getShippingAddress() { return shippingAddress; }

    /** The customer to who this cart or order belongs. */
    public String getCustomerId() { return customerId; }

    /** The sum of prices of line items. */
    public Money getTotalPrice() { return totalPrice; }
}
