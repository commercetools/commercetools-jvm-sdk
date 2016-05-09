package io.sphere.sdk.orders;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.neovisionaries.i18n.CountryCode;
import io.sphere.sdk.carts.*;
import io.sphere.sdk.customergroups.CustomerGroup;
import io.sphere.sdk.discountcodes.DiscountCodeInfo;
import io.sphere.sdk.models.Address;
import io.sphere.sdk.models.ResourceImpl;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.states.State;
import io.sphere.sdk.taxcategories.TaxMode;
import io.sphere.sdk.types.CustomFields;

import javax.annotation.Nullable;
import javax.money.MonetaryAmount;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Set;

final class OrderImpl extends ResourceImpl<Order> implements Order {
    @Nullable
    private final Address billingAddress;
    @Nullable
    private final CountryCode country;
    @Nullable
    private final String customerEmail;
    @Nullable
    private final Reference<CustomerGroup> customerGroup;
    @Nullable
    private final String customerId;
    private final List<CustomLineItem> customLineItems;
    private final InventoryMode inventoryMode;
    private final Long lastMessageSequenceNumber;
    private final List<LineItem> lineItems;
    @Nullable
    private final String orderNumber;
    private final OrderState orderState;
    private final List<ReturnInfo> returnInfo;
    @Nullable
    private final ShipmentState shipmentState;
    @Nullable
    private final Address shippingAddress;
    private final OrderShippingInfo shippingInfo;
    private final Set<SyncInfo> syncInfo;
    @Nullable
    private final TaxedPrice taxedPrice;
    private final MonetaryAmount totalPrice;
    @Nullable
    private final PaymentState paymentState;
    @Nullable
    private final ZonedDateTime completedAt;
    private final List<DiscountCodeInfo> discountCodes;
    @Nullable
    private final Reference<Cart> cart;
    @Nullable
    private final CustomFields custom;
    @Nullable
    private final Reference<State> state;
    @Nullable
    private final PaymentInfo paymentInfo;
    private final TaxMode taxMode;

    @JsonCreator
    protected OrderImpl(final String id, final Long version, final ZonedDateTime createdAt, final ZonedDateTime lastModifiedAt, @Nullable final Address billingAddress, @Nullable final CountryCode country, @Nullable final String customerEmail, @Nullable final Reference<CustomerGroup> customerGroup, @Nullable final String customerId, final List<CustomLineItem> customLineItems, final InventoryMode inventoryMode, final Long lastMessageSequenceNumber, final List<LineItem> lineItems, @Nullable final String orderNumber, final OrderState orderState, final List<ReturnInfo> returnInfo, @Nullable final ShipmentState shipmentState, @Nullable final Address shippingAddress, @Nullable final OrderShippingInfo shippingInfo, final Set<SyncInfo> syncInfo, @Nullable final TaxedPrice taxedPrice, final MonetaryAmount totalPrice, @Nullable final PaymentState paymentState, @Nullable final ZonedDateTime completedAt, final List<DiscountCodeInfo> discountCodes, @Nullable final Reference<Cart> cart, @Nullable final CustomFields custom, @Nullable final Reference<State> state, @Nullable final PaymentInfo paymentInfo, final TaxMode taxMode) {
        super(id, version, createdAt, lastModifiedAt);
        this.billingAddress = billingAddress;
        this.country = country;
        this.customerEmail = customerEmail;
        this.customerGroup = customerGroup;
        this.customerId = customerId;
        this.customLineItems = customLineItems;
        this.inventoryMode = inventoryMode;
        this.lastMessageSequenceNumber = lastMessageSequenceNumber;
        this.lineItems = lineItems;
        this.orderNumber = orderNumber;
        this.orderState = orderState;
        this.returnInfo = returnInfo;
        this.shipmentState = shipmentState;
        this.shippingAddress = shippingAddress;
        this.shippingInfo = shippingInfo;
        this.syncInfo = syncInfo;
        this.taxedPrice = taxedPrice;
        this.totalPrice = totalPrice;
        this.paymentState = paymentState;
        this.completedAt = completedAt;
        this.discountCodes = discountCodes;
        this.cart = cart;
        this.custom = custom;
        this.state = state;
        this.paymentInfo = paymentInfo;
        this.taxMode = taxMode;
    }

    @Override
    @Nullable
    public Address getBillingAddress() {
        return billingAddress;
    }

    @Override
    @Nullable
    public CountryCode getCountry() {
        return country;
    }

    @Override
    @Nullable
    public String getCustomerEmail() {
        return customerEmail;
    }

    @Override
    @Nullable
    public Reference<CustomerGroup> getCustomerGroup() {
        return customerGroup;
    }

    @Override
    @Nullable
    public String getCustomerId() {
        return customerId;
    }

    @Override
    public List<CustomLineItem> getCustomLineItems() {
        return customLineItems;
    }

    @Override
    public InventoryMode getInventoryMode() {
        return inventoryMode;
    }

    @Override
    public Long getLastMessageSequenceNumber() {
        return lastMessageSequenceNumber;
    }

    @Override
    public List<LineItem> getLineItems() {
        return lineItems;
    }

    @Override
    @Nullable
    public String getOrderNumber() {
        return orderNumber;
    }

    @Override
    public OrderState getOrderState() {
        return orderState;
    }

    @Override
    public List<ReturnInfo> getReturnInfo() {
        return returnInfo;
    }

    @Override
    @Nullable
    public ShipmentState getShipmentState() {
        return shipmentState;
    }

    @Override
    @Nullable
    public Address getShippingAddress() {
        return shippingAddress;
    }

    @Override
    public OrderShippingInfo getShippingInfo() {
        return shippingInfo;
    }

    @Override
    public Set<SyncInfo> getSyncInfo() {
        return syncInfo;
    }

    @Override
    @Nullable
    public TaxedPrice getTaxedPrice() {
        return taxedPrice;
    }

    @Override
    public MonetaryAmount getTotalPrice() {
        return totalPrice;
    }

    @Override
    @Nullable
    public PaymentState getPaymentState() {
        return paymentState;
    }

    @Override
    @Nullable
    public ZonedDateTime getCompletedAt() {
        return completedAt;
    }


    @Override
    public List<DiscountCodeInfo> getDiscountCodes() {
        return discountCodes;
    }

    @Nullable
    @Override
    public Reference<Cart> getCart() {
        return cart;
    }

    @Override
    @Nullable
    public CustomFields getCustom() {
        return custom;
    }

    @Override
    @Nullable
    public Reference<State> getState() {
        return state;
    }

    @Override
    @Nullable
    public PaymentInfo getPaymentInfo() {
        return paymentInfo;
    }

    @Override
    public TaxMode getTaxMode() {
        return taxMode;
    }
}
