package io.sphere.client.shop.model;

import java.util.ArrayList;
import java.util.List;

import io.sphere.client.model.EmptyReference;
import io.sphere.client.model.Money;
import io.sphere.client.model.Reference;

import com.neovisionaries.i18n.CountryCode;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.joda.time.DateTime;

/** Superclass of {@link Cart} and {@link Order}. */
@JsonIgnoreProperties("type")
public abstract class LineItemContainer {
    private String id;
    private int version;
    private List<LineItem> lineItems = new ArrayList<LineItem>();  // initialize to prevent NPEs
    private String customerId;
    private DateTime lastModifiedAt;
    private DateTime createdAt;
    @JsonProperty("amountTotal") protected Money totalPrice;
    private Address shippingAddress;
    private Address billingAddress;
    private CountryCode country;
    private Reference<CustomerGroup> customerGroup = EmptyReference.create("customerGroup");

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

    /** Version that increases when the cart is modified. */
    public int getVersion() { return version; }

    /** Returns the items in this cart or order. Does not fire a query to the backend. */
    public List<LineItem> getLineItems() { return lineItems; }

    /** Date and time when this object was last modified. */
    public DateTime getLastModifiedAt() { return lastModifiedAt; }

    /** Date and time when this object was created. */
    public DateTime getCreatedAt() { return createdAt; }

    /** The shipping address of this cart or order. */
    public Address getShippingAddress() { return shippingAddress; }
    
    /** The billing address of this cart or order. */
    public Address getBillingAddress() { return billingAddress; }

    /** The customer to who this cart or order belongs. */
    public String getCustomerId() { return customerId; }

    /** The sum of prices of line items. */
    public Money getTotalPrice() { return totalPrice; }

    /** The customer group of the customer used for price calculations. */
    public Reference<CustomerGroup> getCustomerGroup() { return customerGroup; }

    /** The country used for price calculations. */
    public CountryCode getCountry() { return country; }
}
