package io.sphere.sdk.orders.commands;

import io.sphere.sdk.carts.LineItem;
import io.sphere.sdk.orders.*;
import io.sphere.sdk.orders.commands.updateactions.*;
import io.sphere.sdk.test.IntegrationTest;
import org.fest.assertions.Assertions;
import org.junit.Test;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static io.sphere.sdk.channels.ChannelFixtures.*;
import static io.sphere.sdk.orders.OrderFixtures.*;
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
    public static final Instant INSTANT_IN_PAST = Instant.now().minusSeconds(500);

    @Test
    public void changeOrderState() throws Exception {
        withOrder(client(), order -> {
            assertThat(order.getOrderState()).isEqualTo(OrderState.OPEN);
            final Order updatedOrder = execute(OrderUpdateCommand.of(order, ChangeOrderState.of(OrderState.COMPLETE)));
            assertThat(updatedOrder.getOrderState()).isEqualTo(OrderState.COMPLETE);
        });
    }

    @Test
    public void changeShipmentState() throws Exception {
        withOrder(client(), order -> {
            assertThat(order.getShipmentState()).isEqualTo(Optional.empty());
            final Order updatedOrder = execute(OrderUpdateCommand.of(order, ChangeShipmentState.of(ShipmentState.SHIPPED)));
            assertThat(updatedOrder.getShipmentState()).isPresentAs(ShipmentState.SHIPPED);
        });
    }

    @Test
    public void changePaymentState() throws Exception {
        withOrder(client(), order -> {
            assertThat(order.getPaymentState()).isEqualTo(Optional.empty());
            final Order updatedOrder = execute(OrderUpdateCommand.of(order, ChangePaymentState.of(PaymentState.PAID)));
            assertThat(updatedOrder.getPaymentState()).isPresentAs(PaymentState.PAID);
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

    @Test
    public void addParcelToDelivery() throws Exception {
        withOrder(client(), order -> {
            final LineItem lineItem = order.getLineItems().get(0);
            final List<DeliveryItem> items = asList(DeliveryItem.of(lineItem));
            final Order orderWithDelivery = execute(OrderUpdateCommand.of(order, AddDelivery.of(items)));
            final Delivery delivery = orderWithDelivery.getShippingInfo().get().getDeliveries().get(0);
            assertThat(delivery.getParcels()).isEmpty();
            final ParcelDraft parcelDraft = ParcelDraft.of(PARCEL_MEASUREMENTS, TRACKING_DATA);
            final AddParcelToDelivery action = AddParcelToDelivery.of(delivery, parcelDraft);
            final Order updatedOrder = execute(OrderUpdateCommand.of(orderWithDelivery, action));
            final Parcel actual = updatedOrder.getShippingInfo().get().getDeliveries().get(0).getParcels().get(0);
            assertThat(actual.getMeasurements()).isPresentAs(PARCEL_MEASUREMENTS);
            assertThat(actual.getTrackingData()).isPresentAs(TRACKING_DATA);
        });
    }

    @Test
    public void setOrderNumber() throws Exception {
        withOrder(client(), order -> {
            assertThat(order.getOrderNumber()).isAbsent();
            final String orderNumber = randomString();
            final Order updatedOrder = execute(OrderUpdateCommand.of(order, SetOrderNumber.of(orderNumber)));
            assertThat(updatedOrder.getOrderNumber()).isPresentAs(orderNumber);
        });
    }

    @Test
    public void updateSyncInfo() throws Exception {
        withOrderExportChannel(client(), channel ->
            withOrder(client(), order -> {
                assertThat(order.getSyncInfo()).isEmpty();
                final Instant aDateInThePast = INSTANT_IN_PAST;
                final String externalId = "foo";
                final UpdateSyncInfo action = UpdateSyncInfo.of(channel).withExternalId(externalId).withSyncedAt(aDateInThePast);
                final Order updatedOrder = execute(OrderUpdateCommand.of(order, action));
                assertThat(updatedOrder.getSyncInfo()).containsOnly(SyncInfo.of(channel, aDateInThePast, Optional.of(externalId)));
            })
        );
    }

    @Test
    public void addReturnInfo() throws Exception {
        withOrder(client(), order -> {
            Assertions.assertThat(order.getReturnInfo()).isEmpty();
            final String lineItemId = order.getLineItems().get(0).getId();
            final List<ReturnItemDraft> items = asList(ReturnItemDraft.of(1, lineItemId, ReturnShipmentState.RETURNED, "foo bar"));
            final AddReturnInfo action = AddReturnInfo.of(items).withReturnDate(INSTANT_IN_PAST).withReturnTrackingId("trackingId");
            final Order updatedOrder = execute(OrderUpdateCommand.of(order, action));

            final ReturnInfo returnInfo = updatedOrder.getReturnInfo().get(0);
            final ReturnItem returnItem = returnInfo.getItems().get(0);
            assertThat(returnItem.getQuantity()).isEqualTo(1);
            assertThat(returnItem.getLineItemId()).isEqualTo(lineItemId);
            assertThat(returnItem.getShipmentState()).isEqualTo(ReturnShipmentState.RETURNED);
            assertThat(returnItem.getComment()).isPresentAs("foo bar");
            assertThat(returnInfo.getReturnDate()).isPresentAs(INSTANT_IN_PAST);
            assertThat(returnInfo.getReturnTrackingId()).isPresentAs("trackingId");
        });
    }

    @Test
    public void setReturnShipmentState() throws Exception {
        withOrderAndReturnInfo(client(), (order, returnInfo) -> {
            final ReturnItem returnItem = returnInfo.getItems().get(0);
            final ReturnShipmentState newShipmentState = ReturnShipmentState.BACK_IN_STOCK;
            assertThat(returnItem.getShipmentState()).isNotEqualTo(newShipmentState);
            final SetReturnShipmentState action = SetReturnShipmentState.of(returnItem, newShipmentState);
            final Order updatedOrder = execute(OrderUpdateCommand.of(order, action));
            final ReturnShipmentState updatedReturnItem = updatedOrder.getReturnInfo().get(0).getItems().get(0).getShipmentState();
            assertThat(updatedReturnItem).isEqualTo(newShipmentState);
        });
    }

    @Test
    public void setReturnPaymentState() throws Exception {
        withOrderAndReturnInfo(client(), (order, returnInfo) -> {
            final ReturnItem returnItem = returnInfo.getItems().get(0);
            final ReturnPaymentState newPaymentState = ReturnPaymentState.REFUNDED;
            assertThat(returnItem.getPaymentState()).isNotEqualTo(newPaymentState);
            final SetReturnPaymentState action = SetReturnPaymentState.of(returnItem, newPaymentState);
            final Order updatedOrder = execute(OrderUpdateCommand.of(order, action));
            final ReturnPaymentState updatedPaymentState = updatedOrder.getReturnInfo().get(0).getItems().get(0).getPaymentState();
            assertThat(updatedPaymentState).isEqualTo(newPaymentState);
        });
    }
}