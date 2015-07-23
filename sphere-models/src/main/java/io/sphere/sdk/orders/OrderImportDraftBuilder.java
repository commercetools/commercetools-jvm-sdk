package io.sphere.sdk.orders;

import com.neovisionaries.i18n.CountryCode;
import io.sphere.sdk.carts.TaxedPrice;
import io.sphere.sdk.customergroups.CustomerGroup;
import io.sphere.sdk.models.*;

import javax.annotation.Nullable;
import javax.money.MonetaryAmount;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class OrderImportDraftBuilder extends Base implements Builder<OrderImportDraft> {
    private Optional<String> orderNumber = Optional.empty();
    private Optional<String> customerId = Optional.empty();
    private Optional<String> customerEmail = Optional.empty();
    private List<LineItemImportDraft> lineItems = Collections.emptyList();
    private List<CustomLineItemImportDraft> customLineItems = Collections.emptyList();
    private MonetaryAmount totalPrice;
    private Optional<TaxedPrice> taxedPrice = Optional.empty();
    private Optional<Address> shippingAddress = Optional.empty();
    private Optional<Address> billingAddress = Optional.empty();
    private Optional<Reference<CustomerGroup>> customerGroup = Optional.empty();
    private Optional<CountryCode> country = Optional.empty();
    private OrderState orderState;
    private Optional<ShipmentState> shipmentState = Optional.empty();
    private Optional<PaymentState> paymentState = Optional.empty();
    private Optional<OrderShippingInfo> shippingInfo = Optional.empty();
    @Nullable
    private ZonedDateTime completedAt;

    private OrderImportDraftBuilder(final MonetaryAmount totalPrice, final OrderState orderState) {
        this.totalPrice = totalPrice;
        this.orderState = orderState;
    }

    /**
     * String that unique identifies an order. It can be used to create more human-readable (in contrast to ID) identifier for the order. It should be unique within a project.
     * @param orderNumber ID to set
     * @return this builder
     */
    public OrderImportDraftBuilder orderNumber(final Optional<String> orderNumber) {
        this.orderNumber = orderNumber;
        return this;
    }

    /**
     * String that unique identifies an order. It can be used to create more human-readable (in contrast to ID) identifier for the order. It should be unique within a project.
     * @param orderNumber ID to set
     * @return this builder
     */
    public OrderImportDraftBuilder orderNumber(final String orderNumber) {
        return orderNumber(Optional.of(orderNumber));
    }

    public OrderImportDraftBuilder customerId(final Optional<String> customerId) {
        this.customerId = customerId;
        return this;
    }

    public OrderImportDraftBuilder customerId(final String customerId) {
        return customerId(Optional.of(customerId));
    }

    public OrderImportDraftBuilder customerEmail(final Optional<String> customerEmail) {
        this.customerEmail = customerEmail;
        return this;
    }

    public OrderImportDraftBuilder customerEmail(final String customerEmail) {
        return customerEmail(Optional.of(customerEmail));
    }

    public OrderImportDraftBuilder lineItems(final List<LineItemImportDraft> lineItems) {
        this.lineItems = lineItems;
        return this;
    }

    public OrderImportDraftBuilder customLineItems(final List<CustomLineItemImportDraft> customLineItems) {
        this.customLineItems = customLineItems;
        return this;
    }

    public OrderImportDraftBuilder totalPrice(final MonetaryAmount totalPrice) {
        this.totalPrice = totalPrice;
        return this;
    }

    public OrderImportDraftBuilder taxedPrice(final TaxedPrice taxedPrice) {
        return taxedPrice(Optional.of(taxedPrice));
    }

    public OrderImportDraftBuilder taxedPrice(final Optional<TaxedPrice> taxedPrice) {
        this.taxedPrice = taxedPrice;
        return this;
    }

    public OrderImportDraftBuilder shippingAddress(final Address shippingAddress) {
        return shippingAddress(Optional.of(shippingAddress));
    }

    public OrderImportDraftBuilder shippingAddress(final Optional<Address> shippingAddress) {
        this.shippingAddress = shippingAddress;
        return this;
    }

    public OrderImportDraftBuilder billingAddress(final Optional<Address> billingAddress) {
        this.billingAddress = billingAddress;
        return this;
    }

    public OrderImportDraftBuilder billingAddress(final Address billingAddress) {
        return billingAddress(Optional.of(billingAddress));
    }

    public OrderImportDraftBuilder customerGroup(final Optional<Reference<CustomerGroup>> customerGroup) {
        this.customerGroup = customerGroup;
        return this;
    }

    public OrderImportDraftBuilder customerGroup(final Referenceable<CustomerGroup> customerGroup) {
        return customerGroup(Optional.of(customerGroup.toReference()));
    }

    public OrderImportDraftBuilder country(final Optional<CountryCode> country) {
        this.country = country;
        return this;
    }

    public OrderImportDraftBuilder country(final CountryCode country) {
        return country(Optional.of(country));
    }

    public OrderImportDraftBuilder orderState(final OrderState orderState) {
        this.orderState = orderState;
        return this;
    }

    public OrderImportDraftBuilder shipmentState(final Optional<ShipmentState> shipmentState) {
        this.shipmentState = shipmentState;
        return this;
    }

    public OrderImportDraftBuilder shipmentState(final ShipmentState shipmentState) {
        return shipmentState(Optional.of(shipmentState));
    }

    public OrderImportDraftBuilder paymentState(final Optional<PaymentState> paymentState) {
        this.paymentState = paymentState;
        return this;
    }

    public OrderImportDraftBuilder paymentState(final PaymentState paymentState) {
        return paymentState(Optional.of(paymentState));
    }

    public OrderImportDraftBuilder shippingInfo(final Optional<OrderShippingInfo> shippingInfo) {
        this.shippingInfo = shippingInfo;
        return this;
    }

    public OrderImportDraftBuilder shippingInfo(final OrderShippingInfo shippingInfo) {
        return shippingInfo(Optional.of(shippingInfo));
    }

    public OrderImportDraftBuilder completedAt(@Nullable final ZonedDateTime completedAt) {
        this.completedAt = completedAt;
        return this;
    }

    /**
     * Creates a builder for {@link OrderImportDraft} with at least one line item.
     * You can add {@link io.sphere.sdk.carts.CustomLineItem}s with {@link #customLineItems(java.util.List)}.
     *
     * @param totalPrice the total price of the order
     * @param orderState the state of the order
     * @param lineItems a list of line items with at least one element
     * @return a new builder
     */
    public static OrderImportDraftBuilder ofLineItems(final MonetaryAmount totalPrice, final OrderState orderState, final List<LineItemImportDraft> lineItems) {
        return new OrderImportDraftBuilder(totalPrice, orderState).lineItems(lineItems);
    }

    /**
     * Creates a builder for {@link OrderImportDraft} with at least one custom line item.
     * You can add {@link io.sphere.sdk.carts.LineItem}s with {@link #lineItems(java.util.List)}.
     *
     * @param totalPrice the total price of the order
     * @param orderState the state of the order
     * @param customLineItems a list of custom line items with at least one element
     * @return a new builder
     */
    public static OrderImportDraftBuilder ofCustomLineItems(final MonetaryAmount totalPrice, final OrderState orderState, final List<CustomLineItemImportDraft> customLineItems) {
        return new OrderImportDraftBuilder(totalPrice, orderState).customLineItems(customLineItems);
    }

    @Override
    public OrderImportDraft build() {
        return new OrderImportDraftImpl(billingAddress, orderNumber, customerId, customerEmail, lineItems, customLineItems, totalPrice, taxedPrice, shippingAddress, customerGroup, country, orderState, shipmentState, paymentState, shippingInfo, completedAt);
    }
}
