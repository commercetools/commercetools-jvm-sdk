package io.sphere.sdk.orders;

import com.neovisionaries.i18n.CountryCode;
import io.sphere.sdk.carts.RoundingMode;
import io.sphere.sdk.carts.TaxedPrice;
import io.sphere.sdk.customergroups.CustomerGroup;
import io.sphere.sdk.models.Address;
import io.sphere.sdk.models.Base;
import io.sphere.sdk.models.Reference;

import javax.annotation.Nullable;
import javax.money.MonetaryAmount;
import java.time.ZonedDateTime;
import java.util.List;

final class OrderImportDraftImpl extends Base implements OrderImportDraft {
    @Nullable
    private final String orderNumber;
    @Nullable
    private final String customerId;
    @Nullable
    private final String customerEmail;
    private final List<LineItemImportDraft> lineItems;
    private final List<CustomLineItemImportDraft> customLineItems;
    private final MonetaryAmount totalPrice;
    @Nullable
    private final TaxedPrice taxedPrice;
    @Nullable
    private final Address shippingAddress;
    @Nullable
    private final Address billingAddress;
    @Nullable
    private final Reference<CustomerGroup> customerGroup;
    @Nullable
    private final CountryCode country;
    private final OrderState orderState;
    @Nullable
    private final ShipmentState shipmentState;
    @Nullable
    private final PaymentState paymentState;
    @Nullable
    private final OrderShippingInfo shippingInfo;
    @Nullable
    private final ZonedDateTime completedAt;
    @Nullable
    private final RoundingMode taxRoundingMode;

    public OrderImportDraftImpl(@Nullable final Address billingAddress, @Nullable final String orderNumber, @Nullable final String customerId, @Nullable final String customerEmail, final List<LineItemImportDraft> lineItems, final List<CustomLineItemImportDraft> customLineItems, final MonetaryAmount totalPrice, @Nullable final TaxedPrice taxedPrice, @Nullable final Address shippingAddress, @Nullable final Reference<CustomerGroup> customerGroup, @Nullable final CountryCode country, final OrderState orderState, @Nullable final ShipmentState shipmentState, @Nullable final PaymentState paymentState, @Nullable final OrderShippingInfo shippingInfo, @Nullable final ZonedDateTime completedAt, @Nullable final RoundingMode taxRoundingMode) {
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
        this.taxRoundingMode = taxRoundingMode;
    }

    @Override
    @Nullable
    public Address getBillingAddress() {
        return billingAddress;
    }

    @Nullable
    @Override
    public ZonedDateTime getCompletedAt() {
        return completedAt;
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
    public List<CustomLineItemImportDraft> getCustomLineItems() {
        return customLineItems;
    }

    @Override
    public List<LineItemImportDraft> getLineItems() {
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
    @Nullable
    public PaymentState getPaymentState() {
        return paymentState;
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
    @Nullable
    public OrderShippingInfo getShippingInfo() {
        return shippingInfo;
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
    public RoundingMode getTaxRoundingMode() {
        return taxRoundingMode;
    }
}
