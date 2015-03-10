package io.sphere.sdk.orders;

import com.neovisionaries.i18n.CountryCode;
import io.sphere.sdk.carts.CustomLineItem;
import io.sphere.sdk.carts.TaxedPrice;
import io.sphere.sdk.customergroups.CustomerGroup;
import io.sphere.sdk.models.*;

import javax.money.MonetaryAmount;
import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class ImportOrderBuilder extends Base implements Builder<ImportOrder> {
    private Optional<String> orderNumber = Optional.empty();
    private Optional<String> customerId = Optional.empty();
    private Optional<String> customerEmail = Optional.empty();
    private List<ImportLineItem> lineItems = Collections.emptyList();
    private List<ImportCustomLineItem> customLineItems = Collections.emptyList();
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
    private Optional<Instant> completedAt = Optional.empty();

    private ImportOrderBuilder(final MonetaryAmount totalPrice, final OrderState orderState) {
        this.totalPrice = totalPrice;
        this.orderState = orderState;
    }

    /**
     * String that unique identifies an order. It can be used to create more human-readable (in contrast to ID) identifier for the order. It should be unique within a project.
     * @param orderNumber
     * @return
     */
    public ImportOrderBuilder orderNumber(final Optional<String> orderNumber) {
        this.orderNumber = orderNumber;
        return this;
    }

    /**
     * String that unique identifies an order. It can be used to create more human-readable (in contrast to ID) identifier for the order. It should be unique within a project.
     * @param orderNumber
     * @return
     */
    public ImportOrderBuilder orderNumber(final String orderNumber) {
        return orderNumber(Optional.of(orderNumber));
    }

    public ImportOrderBuilder customerId(final Optional<String> customerId) {
        this.customerId = customerId;
        return this;
    }

    public ImportOrderBuilder customerId(final String customerId) {
        return customerId(Optional.of(customerId));
    }

    public ImportOrderBuilder customerEmail(final Optional<String> customerEmail) {
        this.customerEmail = customerEmail;
        return this;
    }

    public ImportOrderBuilder customerEmail(final String customerEmail) {
        return customerEmail(Optional.of(customerEmail));
    }

    public ImportOrderBuilder lineItems(final List<ImportLineItem> lineItems) {
        this.lineItems = lineItems;
        return this;
    }

    public ImportOrderBuilder customLineItems(final List<ImportCustomLineItem> customLineItems) {
        this.customLineItems = customLineItems;
        return this;
    }

    public ImportOrderBuilder totalPrice(final MonetaryAmount totalPrice) {
        this.totalPrice = totalPrice;
        return this;
    }

    public ImportOrderBuilder taxedPrice(final TaxedPrice taxedPrice) {
        return taxedPrice(Optional.of(taxedPrice));
    }

    public ImportOrderBuilder taxedPrice(final Optional<TaxedPrice> taxedPrice) {
        this.taxedPrice = taxedPrice;
        return this;
    }

    public ImportOrderBuilder shippingAddress(final Address shippingAddress) {
        return shippingAddress(Optional.of(shippingAddress));
    }

    public ImportOrderBuilder shippingAddress(final Optional<Address> shippingAddress) {
        this.shippingAddress = shippingAddress;
        return this;
    }

    public ImportOrderBuilder billingAddress(final Optional<Address> billingAddress) {
        this.billingAddress = billingAddress;
        return this;
    }

    public ImportOrderBuilder billingAddress(final Address billingAddress) {
        return billingAddress(Optional.of(billingAddress));
    }

    public ImportOrderBuilder customerGroup(final Optional<Reference<CustomerGroup>> customerGroup) {
        this.customerGroup = customerGroup;
        return this;
    }

    public ImportOrderBuilder customerGroup(final Referenceable<CustomerGroup> customerGroup) {
        return customerGroup(Optional.of(customerGroup.toReference()));
    }

    public ImportOrderBuilder country(final Optional<CountryCode> country) {
        this.country = country;
        return this;
    }

    public ImportOrderBuilder country(final CountryCode country) {
        return country(Optional.of(country));
    }

    public ImportOrderBuilder orderState(final OrderState orderState) {
        this.orderState = orderState;
        return this;
    }

    public ImportOrderBuilder shipmentState(final Optional<ShipmentState> shipmentState) {
        this.shipmentState = shipmentState;
        return this;
    }

    public ImportOrderBuilder shipmentState(final ShipmentState shipmentState) {
        return shipmentState(Optional.of(shipmentState));
    }

    public ImportOrderBuilder paymentState(final Optional<PaymentState> paymentState) {
        this.paymentState = paymentState;
        return this;
    }

    public ImportOrderBuilder paymentState(final PaymentState paymentState) {
        return paymentState(Optional.of(paymentState));
    }

    public ImportOrderBuilder shippingInfo(final Optional<OrderShippingInfo> shippingInfo) {
        this.shippingInfo = shippingInfo;
        return this;
    }

    public ImportOrderBuilder shippingInfo(final OrderShippingInfo shippingInfo) {
        return shippingInfo(Optional.of(shippingInfo));
    }

    public ImportOrderBuilder completedAt(final Optional<Instant> completedAt) {
        this.completedAt = completedAt;
        return this;
    }

    public ImportOrderBuilder completedAt(final Instant completedAt) {
        return completedAt(Optional.of(completedAt));
    }

    /**
     * Creates a builder for {@link io.sphere.sdk.orders.ImportOrder} with at least one line item.
     * You can add {@link io.sphere.sdk.carts.CustomLineItem}s with {@link #customLineItems(java.util.List)}.
     *
     * @param totalPrice the total price of the order
     * @param orderState the state of the order
     * @param lineItems a list of line items with at least one element
     * @return a new builder
     */
    public static ImportOrderBuilder ofLineItems(final MonetaryAmount totalPrice, final OrderState orderState, final List<ImportLineItem> lineItems) {
        return new ImportOrderBuilder(totalPrice, orderState).lineItems(lineItems);
    }

    /**
     * Creates a builder for {@link io.sphere.sdk.orders.ImportOrder} with at least one custom line item.
     * You can add {@link io.sphere.sdk.carts.LineItem}s with {@link #lineItems(java.util.List)}.
     *
     * @param totalPrice the total price of the order
     * @param orderState the state of the order
     * @param customLineItems a list of custom line items with at least one element
     * @return
     */
    public static ImportOrderBuilder ofCustomLineItems(final MonetaryAmount totalPrice, final OrderState orderState, final List<ImportCustomLineItem> customLineItems) {
        return new ImportOrderBuilder(totalPrice, orderState).customLineItems(customLineItems);
    }

    @Override
    public ImportOrder build() {
        return new ImportOrderImpl(billingAddress, orderNumber, customerId, customerEmail, lineItems, customLineItems, totalPrice, taxedPrice, shippingAddress, customerGroup, country, orderState, shipmentState, paymentState, shippingInfo, completedAt);
    }
}
