package io.sphere.sdk.orders;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.neovisionaries.i18n.CountryCode;
import io.sphere.sdk.carts.CustomLineItem;
import io.sphere.sdk.carts.InventoryMode;
import io.sphere.sdk.carts.LineItem;
import io.sphere.sdk.carts.TaxedPrice;
import io.sphere.sdk.customergroups.CustomerGroup;
import io.sphere.sdk.discountcodes.DiscountCodeInfo;
import io.sphere.sdk.models.Address;
import io.sphere.sdk.models.DefaultModelImpl;
import io.sphere.sdk.models.Reference;

import javax.annotation.Nullable;
import javax.money.MonetaryAmount;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

final class OrderImpl extends DefaultModelImpl<Order> implements Order {
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
    private final long lastMessageSequenceNumber;
    private final List<LineItem> lineItems;
    private final Optional<String> orderNumber;
    private final OrderState orderState;
    private final List<ReturnInfo> returnInfo;
    private final Optional<ShipmentState> shipmentState;
    @Nullable
    private final Address shippingAddress;
    private final Optional<OrderShippingInfo> shippingInfo;
    private final Set<SyncInfo> syncInfo;
    @Nullable
    private final TaxedPrice taxedPrice;
    private final MonetaryAmount totalPrice;
    private final Optional<PaymentState> paymentState;
    private final Optional<ZonedDateTime> completedAt;
    private final List<DiscountCodeInfo> discountCodes;

    @JsonCreator
    protected OrderImpl(final String id, final long version, final ZonedDateTime createdAt, final ZonedDateTime lastModifiedAt, final Address billingAddress, final CountryCode country, final String customerEmail, final Reference<CustomerGroup> customerGroup, final String customerId, final List<CustomLineItem> customLineItems, final InventoryMode inventoryMode, final long lastMessageSequenceNumber, final List<LineItem> lineItems, final Optional<String> orderNumber, final OrderState orderState, final List<ReturnInfo> returnInfo, final Optional<ShipmentState> shipmentState, final Address shippingAddress, final Optional<OrderShippingInfo> shippingInfo, final Set<SyncInfo> syncInfo, final TaxedPrice taxedPrice, final MonetaryAmount totalPrice, final Optional<PaymentState> paymentState, final Optional<ZonedDateTime> completedAt, final List<DiscountCodeInfo> discountCodes) {
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
    public long getLastMessageSequenceNumber() {
        return lastMessageSequenceNumber;
    }

    @Override
    public List<LineItem> getLineItems() {
        return lineItems;
    }

    @Override
    public Optional<String> getOrderNumber() {
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
    public Optional<ShipmentState> getShipmentState() {
        return shipmentState;
    }

    @Override
    @Nullable
    public Address getShippingAddress() {
        return shippingAddress;
    }

    @Override
    public Optional<OrderShippingInfo> getShippingInfo() {
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
    public Optional<PaymentState> getPaymentState() {
        return paymentState;
    }

    @Override
    public Optional<ZonedDateTime> getCompletedAt() {
        return completedAt;
    }


    @Override
    public List<DiscountCodeInfo> getDiscountCodes() {
        return discountCodes;
    }
}
