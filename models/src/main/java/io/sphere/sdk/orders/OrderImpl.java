package io.sphere.sdk.orders;

import com.neovisionaries.i18n.CountryCode;
import io.sphere.sdk.carts.CustomLineItem;
import io.sphere.sdk.carts.InventoryMode;
import io.sphere.sdk.carts.LineItem;
import io.sphere.sdk.carts.TaxedPrice;
import io.sphere.sdk.customergroups.CustomerGroup;
import io.sphere.sdk.models.Address;
import io.sphere.sdk.models.DefaultModelImpl;
import io.sphere.sdk.models.Reference;

import javax.money.MonetaryAmount;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.Set;

final class OrderImpl extends DefaultModelImpl<Order> implements Order {

    private final Optional<Address>  billingAddress;
    private final Optional<CountryCode> country;
    private final Optional<String> customerEmail;
    private final Optional<Reference<CustomerGroup>> customerGroup;
    private final Optional<String> customerId;
    private final List<CustomLineItem> customLineItems;
    private final InventoryMode inventoryMode;
    private final long lastMessageSequenceNumber;
    private final List<LineItem> lineItems;
    private final Optional<String> orderNumber;
    private final OrderState orderState;
    private final Set<ReturnInfo> returnInfo;
    private final Optional<ShipmentState> shipmentState;
    private final Optional<Address> shippingAddress;
    private final Optional<OrderShippingInfo> shippingInfo;
    private final Set<SyncInfo> syncInfo;
    private final Optional<TaxedPrice> taxedPrice;
    private final MonetaryAmount totalPrice;
    private final Optional<PaymentState> paymentState;

    protected OrderImpl(final String id, final long version, final Instant createdAt, final Instant lastModifiedAt, final Optional<Address> billingAddress, final Optional<CountryCode> country, final Optional<String> customerEmail, final Optional<Reference<CustomerGroup>> customerGroup, final Optional<String> customerId, final List<CustomLineItem> customLineItems, final InventoryMode inventoryMode, final long lastMessageSequenceNumber, final List<LineItem> lineItems, final Optional<String> orderNumber, final OrderState orderState, final Set<ReturnInfo> returnInfo, final Optional<ShipmentState> shipmentState, final Optional<Address> shippingAddress, final Optional<OrderShippingInfo> shippingInfo, final Set<SyncInfo> syncInfo, final Optional<TaxedPrice> taxedPrice, final MonetaryAmount totalPrice, final Optional<PaymentState> paymentState) {
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
    }

    @Override
    public Optional<Address> getBillingAddress() {
        return billingAddress;
    }

    @Override
    public Optional<CountryCode> getCountry() {
        return country;
    }

    @Override
    public Optional<String> getCustomerEmail() {
        return customerEmail;
    }

    @Override
    public Optional<Reference<CustomerGroup>> getCustomerGroup() {
        return customerGroup;
    }

    @Override
    public Optional<String> getCustomerId() {
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
    public Set<ReturnInfo> getReturnInfo() {
        return returnInfo;
    }

    @Override
    public Optional<ShipmentState> getShipmentState() {
        return shipmentState;
    }

    @Override
    public Optional<Address> getShippingAddress() {
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
    public Optional<TaxedPrice> getTaxedPrice() {
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
}
