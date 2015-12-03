package io.sphere.sdk.orders.queries;

import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.carts.queries.CartLikeQueryModel;
import io.sphere.sdk.carts.queries.LineItemCollectionQueryModel;
import io.sphere.sdk.carts.queries.TaxedPriceOptionalQueryModel;
import io.sphere.sdk.customergroups.CustomerGroup;
import io.sphere.sdk.orders.Order;
import io.sphere.sdk.orders.OrderState;
import io.sphere.sdk.orders.PaymentState;
import io.sphere.sdk.orders.ShipmentState;
import io.sphere.sdk.queries.*;
import io.sphere.sdk.states.State;

public class OrderQueryModel extends CartLikeQueryModel<Order> {
    private OrderQueryModel(final QueryModel<Order> parent, final String pathSegment) {
        super(parent, pathSegment);
    }

    public static OrderQueryModel of() {
        return new OrderQueryModel(null, null);
    }

    public SphereEnumerationQueryModel<Order, OrderState> orderState() {
        return enumerationQueryModel("orderState");
    }

    public SphereEnumerationQueryModel<Order, ShipmentState> shipmentState() {
        return enumerationQueryModel("shipmentState");
    }

    public SphereEnumerationQueryModel<Order, PaymentState> paymentState() {
        return enumerationQueryModel("paymentState");
    }

    public SyncInfoQueryModel<Order> syncInfo() {
        return new SyncInfoQueryModelImpl<>(this, "syncInfo");
    }

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

    public ReferenceOptionalQueryModel<Order, State> state() {
        return referenceOptionalModel("state");
    }
}
