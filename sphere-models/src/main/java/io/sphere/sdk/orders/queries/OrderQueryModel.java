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
import io.sphere.sdk.types.queries.WithCustomQueryModel;

public interface OrderQueryModel extends ResourceQueryModel<Order>, WithCustomQueryModel<Order> {
    SphereEnumerationQueryModel<Order, OrderState> orderState();

    SphereEnumerationQueryModel<Order, ShipmentState> shipmentState();

    SphereEnumerationQueryModel<Order, PaymentState> paymentState();

    SyncInfoQueryModel<Order> syncInfo();

    ReferenceQueryModel<Order, Cart> cart();

    CountryQueryModel<Order> country();

    StringQuerySortingModel<Order> customerEmail();

    ReferenceOptionalQueryModel<Order, CustomerGroup> customerGroup();

    StringQuerySortingModel<Order> customerId();

    StringQuerySortingModel<Order> orderNumber();

    LineItemCollectionQueryModel<Order> lineItems();

    TaxedPriceOptionalQueryModel<Order> taxedPrice();

    MoneyQueryModel<Order> totalPrice();

    ReferenceOptionalQueryModel<Order, State> state();

    CustomLineItemCollectionQueryModel<Order> customLineItems();

    AddressQueryModel<Order> billingAddress();

    DiscountCodeInfoCollectionQueryModel<Order> discountCodes();

    AddressQueryModel<Order> shippingAddress();

    CartShippingInfoQueryModel<Order> shippingInfo();

    static OrderQueryModel of() {
        return new OrderQueryModelImpl(null, null);
    }
}
