package io.sphere.sdk.orders;

import com.neovisionaries.i18n.CountryCode;
import io.sphere.sdk.carts.CustomLineItem;
import io.sphere.sdk.carts.TaxedPrice;
import io.sphere.sdk.customergroups.CustomerGroup;
import io.sphere.sdk.models.Address;
import io.sphere.sdk.models.Base;
import io.sphere.sdk.models.Reference;

import javax.money.MonetaryAmount;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

final class ImportOrderImpl extends Base implements ImportOrder {
    private final Optional<String> orderNumber;
    private final Optional<String> customerId;
    private final Optional<String> customerEmail;
    private final List<ImportLineItem> lineItems;
    private final List<CustomLineItem> customLineItems;
    private final MonetaryAmount totalPrice;
    private final Optional<TaxedPrice> taxedPrice;
    private final Optional<Address> shippingAddress;
    private final Optional<Address> billingAddress;
    private final Optional<Reference<CustomerGroup>> customerGroup;
    private final Optional<CountryCode> country;
    private final OrderState orderState;
    private final Optional<ShipmentState> shipmentState;
    private final Optional<PaymentState> paymentState;
    private final Optional<OrderShippingInfo> shippingInfo;
    private final Optional<Instant> completedAt;

    public ImportOrderImpl(final Optional<Address> billingAddress, final Optional<String> orderNumber, final Optional<String> customerId, final Optional<String> customerEmail, final List<ImportLineItem> lineItems, final List<CustomLineItem> customLineItems, final MonetaryAmount totalPrice, final Optional<TaxedPrice> taxedPrice, final Optional<Address> shippingAddress, final Optional<Reference<CustomerGroup>> customerGroup, final Optional<CountryCode> country, final OrderState orderState, final Optional<ShipmentState> shipmentState, final Optional<PaymentState> paymentState, final Optional<OrderShippingInfo> shippingInfo, final Optional<Instant> completedAt) {
        this.billingAddress = billingAddress;
        this.orderNumber = orderNumber;
        this.customerId = customerId;
        this.customerEmail = customerEmail;
        this.lineItems = lineItems;
        this.customLineItems = customLineItems;
        this.totalPrice = totalPrice;
        this.taxedPrice = taxedPrice;
        this.shippingAddress = shippingAddress;
        this.customerGroup = customerGroup;
        this.country = country;
        this.orderState = orderState;
        this.shipmentState = shipmentState;
        this.paymentState = paymentState;
        this.shippingInfo = shippingInfo;
        this.completedAt = completedAt;
    }

    @Override
    public Optional<Address> getBillingAddress() {
        return billingAddress;
    }

    @Override
    public Optional<Instant> getCompletedAt() {
        return completedAt;
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
    public List<ImportLineItem> getLineItems() {
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
    public Optional<PaymentState> getPaymentState() {
        return paymentState;
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
    public Optional<TaxedPrice> getTaxedPrice() {
        return taxedPrice;
    }

    @Override
    public MonetaryAmount getTotalPrice() {
        return totalPrice;
    }
}
