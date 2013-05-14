package io.sphere.client.shop.model;

import com.neovisionaries.i18n.CountryCode;
import io.sphere.client.model.EmptyReference;
import io.sphere.client.model.Money;
import io.sphere.client.model.Reference;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.joda.time.DateTime;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

/** Superclass of {@link Cart} and {@link Order}. */
@JsonIgnoreProperties("type")
public abstract class LineItemContainer {
    @Nonnull private String id = "";
    @Nonnull private int version;
    private List<LineItem> lineItems = new ArrayList<LineItem>();  // initialize to prevent NPEs
    private String customerId = "";
    private String customerEmail = "";
    @Nonnull private DateTime lastModifiedAt;
    @Nonnull private DateTime createdAt;
    @Nonnull protected Money totalPrice;
    private TaxedPrice taxedPrice;
    private Address shippingAddress;
    private Address billingAddress;
    private CountryCode country;
    private Reference<CustomerGroup> customerGroup = EmptyReference.create("customerGroup");

    protected LineItemContainer() {}

    /** The sum of quantities of line items. */
    public int getTotalQuantity() {
        int totalQuantity = 0;
        for (LineItem lineItem: this.getLineItems()) {
            totalQuantity += lineItem.getQuantity();
        }
        return totalQuantity;
    }

    // for tests
    protected LineItemContainer(String id, int version) {
        this.id = id;
        this.version = version;
    }

    // --------------------------------------------------------
    // Getters
    // --------------------------------------------------------

    /** The unique id. */
    @Nonnull public String getId() { return id; }

    /** The version. */
    @Nonnull public int getVersion() { return version; }

    /** The items in this cart or order. Does not fire a query to the backend. */
    public List<LineItem> getLineItems() { return lineItems; }

    /** The date and time when this object was last modified. */
    @Nonnull public DateTime getLastModifiedAt() { return lastModifiedAt; }

    /** The date and time when this object was created. */
    @Nonnull public DateTime getCreatedAt() { return createdAt; }

    /** The shipping address. */
    public Address getShippingAddress() { return shippingAddress; }
    
    /** The billing address. */
    public Address getBillingAddress() { return billingAddress; }

    /** The customer to who this Cart or Order belongs. Can be empty if the customer hasn't registered yet. */
    public String getCustomerId() { return customerId; }

    /** The email of the anonymous customer to who this Cart or Order belongs. This field is intended for the case
     * when the customer completes a checkout without registration, only providing email and shipping address. */
    public String getCustomerEmail() { return customerEmail; }

    /** The customer group of the customer, used for price calculations. */
    public Reference<CustomerGroup> getCustomerGroup() { return customerGroup; }

    /** The sum of prices of line items. */
    @Nonnull public Money getTotalPrice() { return totalPrice; }

    /** The currency. */
    public Currency getCurrency() { return Currency.getInstance(totalPrice.getCurrencyCode()); }

    /** The taxed price, defined only when the shipping address is set.
     * Tax rates are determined by the backend based on the country and state of the shipping address. */
    public TaxedPrice getTaxedPrice() { return taxedPrice; }

    /** The country used for price calculations. */
    public CountryCode getCountry() { return country; }
}
