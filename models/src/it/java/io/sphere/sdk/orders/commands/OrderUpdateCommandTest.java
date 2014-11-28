package io.sphere.sdk.orders.commands;

import io.sphere.sdk.carts.LineItem;
import io.sphere.sdk.orders.*;
import io.sphere.sdk.orders.commands.updateactions.*;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import java.util.List;
import java.util.Optional;

import static io.sphere.sdk.orders.OrderFixtures.withOrder;
import static org.fest.assertions.Assertions.assertThat;
import static io.sphere.sdk.test.OptionalAssert.assertThat;
import static io.sphere.sdk.test.SphereTestUtils.*;

public class OrderUpdateCommandTest extends IntegrationTest {

    public static final TrackingData TRACKING_DATA = TrackingData.of()
            .withTrackingId("tracking-id-12")
            .withCarrier("carrier xyz")
            .withProvider("provider foo")
            .withProviderTransaction("prov trans 56");
    public static final ParcelMeasurements PARCEL_MEASUREMENTS = ParcelMeasurements.of(1, 2, 3, 4);

    @Test
    public void changeOrderState() throws Exception {
        withOrder(client(), order -> {
            assertThat(order.getOrderState()).isEqualTo(OrderState.Open);
            final Order updatedOrder = execute(OrderUpdateCommand.of(order, ChangeOrderState.of(OrderState.Complete)));
            assertThat(updatedOrder.getOrderState()).isEqualTo(OrderState.Complete);
        });
    }

    @Test
    public void changeShipmentState() throws Exception {
        withOrder(client(), order -> {
            assertThat(order.getShipmentState()).isEqualTo(Optional.empty());
            final Order updatedOrder = execute(OrderUpdateCommand.of(order, ChangeShipmentState.of(ShipmentState.Shipped)));
            assertThat(updatedOrder.getShipmentState()).isPresentAs(ShipmentState.Shipped);
        });
    }

    @Test
    public void changePaymentState() throws Exception {
        withOrder(client(), order -> {
            assertThat(order.getPaymentState()).isEqualTo(Optional.empty());
            final Order updatedOrder = execute(OrderUpdateCommand.of(order, ChangePaymentState.of(PaymentState.Paid)));
            assertThat(updatedOrder.getPaymentState()).isPresentAs(PaymentState.Paid);
        });
    }

    @Test
    public void addDelivery() throws Exception {
        withOrder(client(), order -> {
            assertThat(order.getShippingInfo().get().getDeliveries()).isEmpty();
            final List<ParcelDraft> parcels = asList(ParcelDraft.of(PARCEL_MEASUREMENTS, TRACKING_DATA));
            final LineItem lineItem = order.getLineItems().get(0);
            final int availableItemsToShip = 1;
            final List<DeliveryItem> items = asList(DeliveryItem.of(lineItem, availableItemsToShip));
            final Order updatedOrder = execute(OrderUpdateCommand.of(order, AddDelivery.of(items, parcels)));
            final Delivery delivery = updatedOrder.getShippingInfo().get().getDeliveries().get(0);
            assertThat(delivery.getItems()).isEqualTo(items);
            final Parcel parcel = delivery.getParcels().get(0);
            assertThat(parcel.getMeasurements()).isPresentAs(PARCEL_MEASUREMENTS);
            assertThat(parcel.getTrackingData()).isPresentAs(TRACKING_DATA);
        });
    }
}