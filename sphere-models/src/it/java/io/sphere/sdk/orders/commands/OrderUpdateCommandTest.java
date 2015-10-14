package io.sphere.sdk.orders.commands;

import io.sphere.sdk.carts.CustomLineItem;
import io.sphere.sdk.carts.ItemState;
import io.sphere.sdk.carts.LineItem;
import io.sphere.sdk.messages.queries.MessageQuery;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.orders.*;
import io.sphere.sdk.orders.commands.updateactions.*;
import io.sphere.sdk.orders.messages.OrderStateTransitionMessage;
import io.sphere.sdk.orders.queries.OrderByIdGet;
import io.sphere.sdk.queries.PagedQueryResult;
import io.sphere.sdk.states.State;
import io.sphere.sdk.states.StateFixtures;
import io.sphere.sdk.states.StateType;
import io.sphere.sdk.test.IntegrationTest;
import io.sphere.sdk.test.SphereTestUtils;
import io.sphere.sdk.utils.MoneyImpl;
import org.assertj.core.api.Assertions;
import org.javamoney.moneta.function.MonetaryUtil;
import org.junit.Test;

import javax.money.MonetaryAmount;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.*;

import static io.sphere.sdk.channels.ChannelFixtures.*;
import static io.sphere.sdk.orders.OrderFixtures.*;
import static io.sphere.sdk.states.StateFixtures.withStandardStates;
import static io.sphere.sdk.states.StateFixtures.withStateByBuilder;
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

            return updatedOrder;
        });
    }

    @Test
    public void changeShipmentState() throws Exception {
        withOrder(client(), order -> {
            final ShipmentState newState = ShipmentState.SHIPPED;
            assertThat(order.getShipmentState()).isNotEqualTo(newState);
            final Order updatedOrder = execute(OrderUpdateCommand.of(order, ChangeShipmentState.of(newState)));
            assertThat(updatedOrder.getShipmentState()).isEqualTo(newState);

            return updatedOrder;
        });
    }

    @Test
    public void changePaymentState() throws Exception {
        withOrder(client(), order -> {
            final PaymentState newState = PaymentState.PAID;
            assertThat(order.getPaymentState()).isNotEqualTo(newState);
            final Order updatedOrder = execute(OrderUpdateCommand.of(order, ChangePaymentState.of(newState)));
            assertThat(updatedOrder.getPaymentState()).isEqualTo(newState);

            return updatedOrder;
        });
    }

    @Test
    public void addDelivery() throws Exception {
        withOrder(client(), order -> {
            assertThat(order.getShippingInfo().getDeliveries()).isEmpty();
            final List<ParcelDraft> parcels = asList(ParcelDraft.of(PARCEL_MEASUREMENTS, TRACKING_DATA));
            final LineItem lineItem = order.getLineItems().get(0);
            final int availableItemsToShip = 1;
            final List<DeliveryItem> items = asList(DeliveryItem.of(lineItem, availableItemsToShip));
            final Order updatedOrder = execute(OrderUpdateCommand.of(order, AddDelivery.of(items, parcels)));
            final Delivery delivery = updatedOrder.getShippingInfo().getDeliveries().get(0);
            assertThat(delivery.getItems()).isEqualTo(items);
            final Parcel parcel = delivery.getParcels().get(0);
            assertThat(parcel.getMeasurements()).isEqualTo(PARCEL_MEASUREMENTS);
            assertThat(parcel.getTrackingData()).isEqualTo(TRACKING_DATA);

            return updatedOrder;
        });
    }

    @Test
    public void addParcelToDelivery() throws Exception {
        withOrder(client(), order -> {
            final LineItem lineItem = order.getLineItems().get(0);
            final List<DeliveryItem> items = asList(DeliveryItem.of(lineItem));
            final Order orderWithDelivery = execute(OrderUpdateCommand.of(order, AddDelivery.of(items)));
            final Delivery delivery = orderWithDelivery.getShippingInfo().getDeliveries().get(0);
            assertThat(delivery.getParcels()).isEmpty();
            final ParcelDraft parcelDraft = ParcelDraft.of(PARCEL_MEASUREMENTS, TRACKING_DATA);
            final AddParcelToDelivery action = AddParcelToDelivery.of(delivery, parcelDraft);
            final Order updatedOrder = execute(OrderUpdateCommand.of(orderWithDelivery, action));
            final Parcel actual = updatedOrder.getShippingInfo().getDeliveries().get(0).getParcels().get(0);
            assertThat(actual.getMeasurements()).isEqualTo(PARCEL_MEASUREMENTS);
            assertThat(actual.getTrackingData()).isEqualTo(TRACKING_DATA);

            return updatedOrder;
        });
    }

    @Test
    public void setOrderNumber() throws Exception {
        withOrder(client(), order -> {
            assertThat(order.getOrderNumber()).isNull();
            final String orderNumber = randomString();
            final Order updatedOrder = execute(OrderUpdateCommand.of(order, SetOrderNumber.of(orderNumber)));
            assertThat(updatedOrder.getOrderNumber()).isEqualTo(orderNumber);

            return updatedOrder;
        });
    }

    @Test
    public void referenceExpansion() throws Exception {
        withOrder(client(), order -> {
            assertThat(order.getOrderNumber()).isNull();
            final String orderNumber = randomString();
            final Order updatedOrder = execute(OrderUpdateCommand.of(order, SetOrderNumber.of(orderNumber)).plusExpansionPaths(m -> m.cart()));
            assertThat(updatedOrder.getCart().getObj()).isNotNull();

            return updatedOrder;
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
                assertThat(updatedOrder.getSyncInfo()).containsOnly(SyncInfo.of(channel, aDateInThePast, externalId));

                //check channel expansion
                final Order loadedOrder = execute(OrderByIdGet.of(order).withExpansionPaths(m -> m.syncInfo().channel()));
                assertThat(new ArrayList<>(loadedOrder.getSyncInfo()).get(0).getChannel().getObj()).isNotNull();

                return updatedOrder;
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
            assertThat(returnInfo.getReturnDate()).isEqualTo(ZonedDateTime_IN_PAST);
            assertThat(returnInfo.getReturnTrackingId()).contains("trackingId");

            return updatedOrder;
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

            return updatedOrder;
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

            return updatedOrder;
        });
    }

    @Test
    public void transitionLineItemState() throws Exception {
        withStandardStates(client(), (State initialState, State nextState) ->
            withOrder(client(), order -> {
                final LineItem lineItem = order.getLineItems().get(0);
                assertThat(lineItem).containsState(initialState).containsNotState(nextState);
                final long quantity = 1;
                final ZonedDateTime actualTransitionDate = ZonedDateTime_IN_PAST;
                final Order updatedOrder = execute(OrderUpdateCommand.of(order, TransitionLineItemState.of(lineItem, quantity, initialState, nextState, actualTransitionDate)));
                assertThat(updatedOrder.getLineItems().get(0)).containsItemStates(ItemState.of(nextState, quantity));

                return updatedOrder;
            })
        );
    }

    @Test
    public void transitionCustomLineItemState() throws Exception {
        withStandardStates(client(), (State initialState, State nextState) ->
            withOrderOfCustomLineItems(client(), order -> {
                final CustomLineItem customLineItem = order.getCustomLineItems().get(0);
                assertThat(customLineItem).containsState(initialState).containsNotState(nextState);
                final long quantity = 1;
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
                final Order loadedOrder = execute(OrderByIdGet.of(order).withExpansionPaths(m -> m.lineItems().state().state()));
                final Reference<State> state = new LinkedList<>(loadedOrder.getLineItems().get(0).getState()).getFirst().getState();
                assertThat(state.getObj()).isNotNull();

                return updatedOrder;
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

    @Test
    public void transitionState() throws Exception {
        withStateByBuilder(client(), builder -> builder.type(StateType.ORDER_STATE), state -> {
            withOrder(client(), order -> {
                final Order updatedOrder = execute(OrderUpdateCommand.of(order, TransitionState.of(state)));

                assertThat(updatedOrder.getState()).isEqualTo(state.toReference());

                final PagedQueryResult<OrderStateTransitionMessage> messageQueryResult = execute(MessageQuery.of()
                        .withPredicates(m -> m.resource().is(order))
                        .forMessageType(OrderStateTransitionMessage.MESSAGE_HINT));

                final OrderStateTransitionMessage message = messageQueryResult.head().get();
                assertThat(message.getState()).isEqualTo(state.toReference());

                return updatedOrder;
            });
        });
    }
}