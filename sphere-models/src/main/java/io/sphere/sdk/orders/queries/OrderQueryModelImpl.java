package io.sphere.sdk.orders.queries;

import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.carts.queries.*;
import io.sphere.sdk.customergroups.CustomerGroup;
import io.sphere.sdk.orders.Order;
import io.sphere.sdk.orders.OrderState;
import io.sphere.sdk.orders.PaymentState;
import io.sphere.sdk.orders.ShipmentState;
import io.sphere.sdk.queries.*;
import io.sphere.sdk.states.State;

final class OrderQueryModelImpl extends CartLikeQueryModel<Order> implements OrderQueryModel {
    OrderQueryModelImpl(final QueryModel<Order> parent, final String pathSegment) {
        super(parent, pathSegment);
    }

    @Override
    public SphereEnumerationQueryModel<Order, OrderState> orderState() {
        return enumerationQueryModel("orderState");
    }

    @Override
    public SphereEnumerationQueryModel<Order, ShipmentState> shipmentState() {
        return enumerationQueryModel("shipmentState");
    }

    @Override
    public SphereEnumerationQueryModel<Order, PaymentState> paymentState() {
        return enumerationQueryModel("paymentState");
    }

    @Override
    public SyncInfoQueryModel<Order> syncInfo() {
        return new SyncInfoQueryModelImpl<>(this, "syncInfo");
    }

    @Override
    public ReferenceQueryModel<Order, Cart> cart() {
        return referenceModel("cart");
    }

    @Override
    public CountryQueryModel<Order> country() {
        return super.country();
    }

    @Override
    public StringQuerySortingModel<Order> customerEmail() {
        return super.customerEmail();
    }

    @Override
    public ReferenceOptionalQueryModel<Order, CustomerGroup> customerGroup() {
        return super.customerGroup();
    }

    @Override
    public StringQuerySortingModel<Order> customerId() {
        return super.customerId();
    }

    @Override
    public StringQuerySortingModel<Order> orderNumber() {
        return stringModel("orderNumber");
    }

    @Override
    public LineItemCollectionQueryModel<Order> lineItems() {
        return super.lineItems();
    }

    @Override
    public TaxedPriceOptionalQueryModel<Order> taxedPrice() {
        return super.taxedPrice();
    }

    @Override
    public MoneyQueryModel<Order> totalPrice() {
        return super.totalPrice();
    }

    @Override
    public ReferenceOptionalQueryModel<Order, State> state() {
        return referenceOptionalModel("state");
    }

    @Override
    public CustomLineItemCollectionQueryModel<Order> customLineItems() {
        return super.customLineItems();
    }

    @Override
    public AddressQueryModel<Order> billingAddress() {
        return super.billingAddress();
    }

    @Override
    public DiscountCodeInfoCollectionQueryModel<Order> discountCodes() {
        return super.discountCodes();
    }

    @Override
    public AddressQueryModel<Order> shippingAddress() {
        return super.shippingAddress();
    }

    @Override
    public CartShippingInfoQueryModel<Order> shippingInfo() {
        return super.shippingInfo();
    }
}
