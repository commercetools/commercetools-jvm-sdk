package io.sphere.sdk.orders.commands;

import io.sphere.sdk.carts.CustomLineItem;
import io.sphere.sdk.carts.ItemState;
import io.sphere.sdk.carts.LineItem;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.orders.*;
import io.sphere.sdk.orders.commands.updateactions.*;
import io.sphere.sdk.orders.queries.OrderByIdFetch;
import io.sphere.sdk.states.State;
import io.sphere.sdk.test.IntegrationTest;
import io.sphere.sdk.test.SphereTestUtils;
import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.time.ZonedDateTime;
import java.util.*;

import static io.sphere.sdk.channels.ChannelFixtures.*;
import static io.sphere.sdk.orders.OrderFixtures.*;
import static io.sphere.sdk.states.StateFixtures.withStandardStates;
import static io.sphere.sdk.utils.SetUtils.asSet;
import static org.assertj.core.api.Assertions.assertThat;
import static io.sphere.sdk.test.SphereTestUtils.*;
import static io.sphere.sdk.carts.LineItemLikeAssert.assertThat;

public class OrderUpdateCommandTest extends IntegrationTest {

    public static final TrackingData TRACKING_DATA = TrackingData.of()
            .withTrackingId("tracking-id-12")
            .withCarrier("carrier xyz")
            .withProvider("provider foo")
            .withProviderTransaction("prov trans 56");
    public static final ParcelMeasurements PARCEL_MEASUREMENTS = ParcelMeasurements.of(2, 3, 1, 4);
    public static final ZonedDateTime ZonedDateTime_IN_PAST = SphereTestUtils.now().minusSeconds(500);

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
            final ShipmentState newState = ShipmentState.SHIPPED;
            assertThat(order.getShipmentState()).isNotEqualTo(Optional.of(newState));
            final Order updatedOrder = execute(OrderUpdateCommand.of(order, ChangeShipmentState.of(newState)));
            assertThat(updatedOrder.getShipmentState()).contains(newState);
        });
    }

    @Test
    public void changePaymentState() throws Exception {
        withOrder(client(), order -> {
            final PaymentState newState = PaymentState.PAID;
            assertThat(order.getPaymentState()).isNotEqualTo(Optional.of(newState));
            final Order updatedOrder = execute(OrderUpdateCommand.of(order, ChangePaymentState.of(newState)));
            assertThat(updatedOrder.getPaymentState()).contains(newState);
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
            assertThat(parcel.getMeasurements()).contains(PARCEL_MEASUREMENTS);
            assertThat(parcel.getTrackingData()).contains(TRACKING_DATA);
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
            assertThat(actual.getMeasurements()).contains(PARCEL_MEASUREMENTS);
            assertThat(actual.getTrackingData()).contains(TRACKING_DATA);
        });
    }

    @Test
    public void setOrderNumber() throws Exception {
        withOrder(client(), order -> {
            assertThat(order.getOrderNumber()).isEmpty();
            final String orderNumber = randomString();
            final Order updatedOrder = execute(OrderUpdateCommand.of(order, SetOrderNumber.of(orderNumber)));
            assertThat(updatedOrder.getOrderNumber()).contains(orderNumber);
        });
    }

    @Test
    public void updateSyncInfo() throws Exception {
        withOrderExportChannel(client(), channel ->
            withOrder(client(), order -> {
                assertThat(order.getSyncInfo()).isEmpty();
                final ZonedDateTime aDateInThePast = ZonedDateTime_IN_PAST;
                final String externalId = "foo";
                final UpdateSyncInfo action = UpdateSyncInfo.of(channel).withExternalId(externalId).withSyncedAt(aDateInThePast);
                final Order updatedOrder = execute(OrderUpdateCommand.of(order, action));
                assertThat(updatedOrder.getSyncInfo()).containsOnly(SyncInfo.of(channel, aDateInThePast, Optional.of(externalId)));

                //check channel expansion
                final Order loadedOrder = execute(OrderByIdFetch.of(order).withExpansionPaths(m -> m.syncInfo().channel()));
                assertThat(new ArrayList<>(loadedOrder.getSyncInfo()).get(0).getChannel().getObj()).isNotNull();
            })
        );
    }

    @Test
    public void addReturnInfo() throws Exception {
        withOrder(client(), order -> {
            Assertions.assertThat(order.getReturnInfo()).isEmpty();
            final String lineItemId = order.getLineItems().get(0).getId();
            final List<ReturnItemDraft> items = asList(ReturnItemDraft.of(1, lineItemId, ReturnShipmentState.RETURNED, "foo bar"));
            final AddReturnInfo action = AddReturnInfo.of(items).withReturnDate(ZonedDateTime_IN_PAST).withReturnTrackingId("trackingId");
            final Order updatedOrder = execute(OrderUpdateCommand.of(order, action));

            final ReturnInfo returnInfo = updatedOrder.getReturnInfo().get(0);
            final ReturnItem returnItem = returnInfo.getItems().get(0);
            assertThat(returnItem.getQuantity()).isEqualTo(1);
            assertThat(returnItem.getLineItemId()).isEqualTo(lineItemId);
            assertThat(returnItem.getShipmentState()).isEqualTo(ReturnShipmentState.RETURNED);
            assertThat(returnItem.getComment()).contains("foo bar");
            assertThat(returnInfo.getReturnDate()).contains(ZonedDateTime_IN_PAST);
            assertThat(returnInfo.getReturnTrackingId()).contains("trackingId");
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

    @Test
    public void transitionLineItemState() throws Exception {
        withStandardStates(client(), (State initialState, State nextState) ->
                        withOrder(client(), order -> {
                            final LineItem lineItem = order.getLineItems().get(0);
                            assertThat(lineItem).containsState(initialState).containsNotState(nextState);
                            final int quantity = 1;
                            final ZonedDateTime actualTransitionDate = ZonedDateTime_IN_PAST;
                            final Order updatedOrder = execute(OrderUpdateCommand.of(order, TransitionLineItemState.of(lineItem, quantity, initialState, nextState, actualTransitionDate)));
                            assertThat(updatedOrder.getLineItems().get(0)).containsItemStates(ItemState.of(nextState, quantity));
                        })
        );
    }

    @Test
    public void transitionCustomLineItemState() throws Exception {
        withStandardStates(client(), (State initialState, State nextState) ->
            withOrderOfCustomLineItems(client(), order -> {
                final CustomLineItem customLineItem = order.getCustomLineItems().get(0);
                assertThat(customLineItem).containsState(initialState).containsNotState(nextState);
                final int quantity = 1;
                final ZonedDateTime actualTransitionDate = ZonedDateTime_IN_PAST;
                final Order updatedOrder = execute(OrderUpdateCommand.of(order, TransitionCustomLineItemState.of(customLineItem, quantity, initialState, nextState, actualTransitionDate)));
                assertThat(updatedOrder.getCustomLineItems().get(0)).containsItemStates(ItemState.of(nextState, quantity));
            })
        );
    }

    @Test
    public void importLineItemState() throws Exception {
        withStandardStates(client(), (State initialState, State nextState) ->
            withOrder(client(), order -> {
                final LineItem lineItem = order.getLineItems().get(0);
                assertThat(lineItem).containsState(initialState).containsNotState(nextState);
                final Set<ItemState> itemStates = asSet(ItemState.of(nextState, 1), ItemState.of(initialState, lineItem.getQuantity() - 1));
                final Order updatedOrder = execute(OrderUpdateCommand.of(order, ImportLineItemState.of(lineItem, itemStates)));
                assertThat(updatedOrder.getLineItems().get(0)).containsItemStates(itemStates);

                //reference expansion
                final Order loadedOrder = execute(OrderByIdFetch.of(order).withExpansionPaths(m -> m.lineItems().state().state()));
                final Reference<State> state = new LinkedList<>(loadedOrder.getLineItems().get(0).getState()).getFirst().getState();
                assertThat(state.getObj()).isNotNull();
            })
        );
    }

    @Test
    public void importCustomLineItemState() throws Exception {
        withStandardStates(client(), (State initialState, State nextState) ->
            withOrderOfCustomLineItems(client(), order -> {
                final CustomLineItem customLineItem = order.getCustomLineItems().get(0);
                assertThat(customLineItem).containsState(initialState).containsNotState(nextState);
                final Set<ItemState> itemStates = asSet(ItemState.of(nextState, 1), ItemState.of(initialState, customLineItem.getQuantity() - 1));
                final Order updatedOrder = execute(OrderUpdateCommand.of(order, ImportCustomLineItemState.of(customLineItem, itemStates)));
                assertThat(updatedOrder.getCustomLineItems().get(0)).containsItemStates(itemStates);
            })
        );
    }
}