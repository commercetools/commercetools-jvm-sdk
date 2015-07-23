package io.sphere.sdk.orders;

import com.neovisionaries.i18n.CountryCode;
import io.sphere.sdk.carts.TaxedPrice;
import io.sphere.sdk.customergroups.CustomerGroup;
import io.sphere.sdk.models.Address;
import io.sphere.sdk.models.Base;
import io.sphere.sdk.models.Reference;

import javax.annotation.Nullable;
import javax.money.MonetaryAmount;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

final class OrderImportDraftImpl extends Base implements OrderImportDraft {
    private final Optional<String> orderNumber;
    private final Optional<String> customerId;
    private final Optional<String> customerEmail;
    private final List<LineItemImportDraft> lineItems;
    private final List<CustomLineItemImportDraft> customLineItems;
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
    @Nullable
    private final ZonedDateTime completedAt;

    public OrderImportDraftImpl(final Optional<Address> billingAddress, final Optional<String> orderNumber, final Optional<String> customerId, final Optional<String> customerEmail, final List<LineItemImportDraft> lineItems, final List<CustomLineItemImportDraft> customLineItems, final MonetaryAmount totalPrice, final Optional<TaxedPrice> taxedPrice, final Optional<Address> shippingAddress, final Optional<Reference<CustomerGroup>> customerGroup, final Optional<CountryCode> country, final OrderState orderState, final Optional<ShipmentState> shipmentState, final Optional<PaymentState> paymentState, final Optional<OrderShippingInfo> shippingInfo, final ZonedDateTime completedAt) {
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
    public ZonedDateTime getCompletedAt() {
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
    public List<CustomLineItemImportDraft> getCustomLineItems() {
        return customLineItems;
    }

    @Override
    public List<LineItemImportDraft> getLineItems() {
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
