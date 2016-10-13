package io.sphere.sdk.orders.commands;

import io.sphere.sdk.carts.*;
import io.sphere.sdk.messages.queries.MessageQuery;
import io.sphere.sdk.models.Address;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.models.Referenceable;
import io.sphere.sdk.orders.*;
import io.sphere.sdk.orders.commands.updateactions.*;
import io.sphere.sdk.orders.commands.updateactions.AddPayment;
import io.sphere.sdk.orders.commands.updateactions.RemovePayment;
import io.sphere.sdk.orders.commands.updateactions.SetBillingAddress;
import io.sphere.sdk.orders.commands.updateactions.SetCustomerEmail;
import io.sphere.sdk.orders.commands.updateactions.SetShippingAddress;
import io.sphere.sdk.orders.messages.*;
import io.sphere.sdk.orders.queries.OrderByIdGet;
import io.sphere.sdk.orders.queries.OrderQuery;
import io.sphere.sdk.payments.Payment;
import io.sphere.sdk.queries.PagedQueryResult;
import io.sphere.sdk.queries.Query;
import io.sphere.sdk.states.State;
import io.sphere.sdk.states.StateType;
import io.sphere.sdk.test.IntegrationTest;
import io.sphere.sdk.test.SphereTestUtils;
import net.jcip.annotations.NotThreadSafe;
import org.assertj.core.api.Condition;
import org.junit.Test;

import java.time.ZonedDateTime;
import java.util.*;

import static com.neovisionaries.i18n.CountryCode.DE;
import static io.sphere.sdk.channels.ChannelFixtures.withOrderExportChannel;
import static io.sphere.sdk.orders.OrderFixtures.*;
import static io.sphere.sdk.payments.PaymentFixtures.withPayment;
import static io.sphere.sdk.states.StateFixtures.withStandardStates;
import static io.sphere.sdk.states.StateFixtures.withStateByBuilder;
import static io.sphere.sdk.test.SphereTestUtils.*;
import static io.sphere.sdk.utils.SphereInternalUtils.asSet;
import static io.sphere.sdk.utils.SphereInternalUtils.setOf;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.condition.Not.not;

@NotThreadSafe
public class OrderUpdateCommandIntegrationTest extends IntegrationTest {

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
            final Order updatedOrder = client().executeBlocking(OrderUpdateCommand.of(order, ChangeOrderState.of(OrderState.COMPLETE)));
            assertThat(updatedOrder.getOrderState()).isEqualTo(OrderState.COMPLETE);

            return updatedOrder;
        });
    }

    @Test
    public void changeShipmentState() throws Exception {
        withOrder(client(), order -> {
            final ShipmentState newState = ShipmentState.SHIPPED;
            assertThat(order.getShipmentState()).isNotEqualTo(newState);
            final Order updatedOrder = client().executeBlocking(OrderUpdateCommand.of(order, ChangeShipmentState.of(newState)));
            assertThat(updatedOrder.getShipmentState()).isEqualTo(newState);

            return updatedOrder;
        });
    }

    @Test
    public void changePaymentState() throws Exception {
        withOrder(client(), order -> {
            final PaymentState newState = PaymentState.PAID;
            assertThat(order.getPaymentState()).isNotEqualTo(newState);
            final Order updatedOrder = client().executeBlocking(OrderUpdateCommand.of(order, ChangePaymentState.of(newState)));
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
            final long availableItemsToShip = 1;
            final List<DeliveryItem> items = asList(DeliveryItem.of(lineItem, availableItemsToShip));
            final Order updatedOrder = client().executeBlocking(OrderUpdateCommand.of(order, AddDelivery.of(items, parcels)));
            final Delivery delivery = updatedOrder.getShippingInfo().getDeliveries().get(0);
            assertThat(delivery.getItems()).isEqualTo(items);
            final Parcel parcel = delivery.getParcels().get(0);
            assertThat(parcel.getMeasurements()).isEqualTo(PARCEL_MEASUREMENTS);
            assertThat(parcel.getTrackingData()).isEqualTo(TRACKING_DATA);

            //you can observe a message
            final Query<DeliveryAddedMessage> messageQuery = MessageQuery.of()
                    .withPredicates(m -> m.resource().is(order))
                    .forMessageType(DeliveryAddedMessage.MESSAGE_HINT);
            assertEventually(() -> {
                final Optional<DeliveryAddedMessage> deliveryAddedMessageOptional =
                        client().executeBlocking(messageQuery).head();
                assertThat(deliveryAddedMessageOptional).isPresent();
                final DeliveryAddedMessage deliveryAddedMessage = deliveryAddedMessageOptional.get();
                final Delivery deliveryFromMessage = deliveryAddedMessage.getDelivery();
                assertThat(deliveryFromMessage.getId()).isEqualTo(delivery.getId());
                assertThat(deliveryFromMessage.getCreatedAt()).isEqualTo(delivery.getCreatedAt());
                assertThat(deliveryFromMessage.getParcels())
                        .as("warning initial parcels are not contained in the message!")
                        .isNotEqualTo(delivery.getParcels())
                        .isEmpty();
                assertThat(deliveryFromMessage.getItems()).isEqualTo(delivery.getItems());
            });

            return updatedOrder;
        });
    }

    @Test
    public void addParcelToDelivery() throws Exception {
        withOrder(client(), order -> {
            final LineItem lineItem = order.getLineItems().get(0);
            final List<DeliveryItem> items = asList(DeliveryItem.of(lineItem));
            final Order orderWithDelivery = client().executeBlocking(OrderUpdateCommand.of(order, AddDelivery.of(items)));
            final Delivery delivery = orderWithDelivery.getShippingInfo().getDeliveries().get(0);
            assertThat(delivery.getParcels()).isEmpty();
            final ParcelDraft parcelDraft = ParcelDraft.of(PARCEL_MEASUREMENTS, TRACKING_DATA);
            final AddParcelToDelivery action = AddParcelToDelivery.of(delivery, parcelDraft);
            final Order updatedOrder = client().executeBlocking(OrderUpdateCommand.of(orderWithDelivery, action));
            final Parcel actualParcel = updatedOrder.getShippingInfo().getDeliveries().get(0).getParcels().get(0);
            assertThat(actualParcel.getMeasurements()).isEqualTo(PARCEL_MEASUREMENTS);
            assertThat(actualParcel.getTrackingData()).isEqualTo(TRACKING_DATA);

            //you can observe a message
            final Query<ParcelAddedToDeliveryMessage> messageQuery = MessageQuery.of()
                    .withPredicates(m -> m.resource().is(order))
                    .forMessageType(ParcelAddedToDeliveryMessage.MESSAGE_HINT);
            assertEventually(() -> {
                final Optional<ParcelAddedToDeliveryMessage> parcelAddedToDeliveryMessageOptional = client().executeBlocking(messageQuery).head();
                assertThat(parcelAddedToDeliveryMessageOptional).isPresent();
                final ParcelAddedToDeliveryMessage parcelAddedToDeliveryMessage = parcelAddedToDeliveryMessageOptional.get();
                final Delivery deliveryFromMessage = parcelAddedToDeliveryMessage.getDelivery();
                assertThat(deliveryFromMessage.getId()).isEqualTo(delivery.getId());
                assertThat(deliveryFromMessage.getCreatedAt()).isEqualTo(delivery.getCreatedAt());
                assertThat(deliveryFromMessage.getItems()).isEqualTo(delivery.getItems());
                final Parcel parcelFromMessage = parcelAddedToDeliveryMessage.getParcel();
                assertThat(parcelFromMessage).isEqualTo(actualParcel);
            });

            return updatedOrder;
        });
    }

    @Test
    public void setOrderNumber() throws Exception {
        withOrder(client(), order -> {
            assertThat(order.getOrderNumber()).isNull();
            final String orderNumber = randomString();
            final Order updatedOrder = client().executeBlocking(OrderUpdateCommand.of(order, SetOrderNumber.of(orderNumber)));
            assertThat(updatedOrder.getOrderNumber()).isEqualTo(orderNumber);

            return updatedOrder;
        });
    }

    @Test
    public void referenceExpansion() throws Exception {
        withOrder(client(), order -> {
            assertThat(order.getOrderNumber()).isNull();
            final String orderNumber = randomString();
            final OrderUpdateCommand orderUpdateCommand = OrderUpdateCommand.of(order, SetOrderNumber.of(orderNumber)).plusExpansionPaths(m -> m.cart());
            final Order updatedOrder = client().executeBlocking(orderUpdateCommand);
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
                final Order updatedOrder = client().executeBlocking(OrderUpdateCommand.of(order, action));
                assertThat(updatedOrder.getSyncInfo()).containsOnly(SyncInfo.of(channel, aDateInThePast, externalId));

                //check channel expansion
                final OrderByIdGet orderByIdGet = OrderByIdGet.of(order).withExpansionPaths(m -> m.syncInfo().channel());
                final Order loadedOrder = client().executeBlocking(orderByIdGet);
                assertThat(new ArrayList<>(loadedOrder.getSyncInfo()).get(0).getChannel().getObj()).isNotNull();

                return updatedOrder;
            })
        );
    }

    @Test
    public void addReturnInfo() throws Exception {
        withOrder(client(), order -> {
            assertThat(order.getReturnInfo()).isEmpty();
            final String lineItemId = order.getLineItems().get(0).getId();
            final List<ReturnItemDraft> items = asList(ReturnItemDraft.of(1L, lineItemId, ReturnShipmentState.RETURNED, "foo bar"));
            final AddReturnInfo action = AddReturnInfo.of(items).withReturnDate(ZonedDateTime_IN_PAST).withReturnTrackingId("trackingId");
            final Order updatedOrder = client().executeBlocking(OrderUpdateCommand.of(order, action));

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
            final Order updatedOrder = client().executeBlocking(OrderUpdateCommand.of(order, action));
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
            final Order updatedOrder = client().executeBlocking(OrderUpdateCommand.of(order, action));
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
                assertThat(lineItem).has(state(initialState)).has(not(state(nextState)));
                final long quantity = 1;
                final ZonedDateTime actualTransitionDate = ZonedDateTime_IN_PAST;
                final Order updatedOrder = client().executeBlocking(OrderUpdateCommand.of(order, TransitionLineItemState.of(lineItem, quantity, initialState, nextState, actualTransitionDate)));
                assertThat(updatedOrder.getLineItems().get(0)).has(itemStates(ItemState.of(nextState, quantity)));

                //you can observe a message
                final Query<LineItemLikeStateTransitionMessage> messageQuery = MessageQuery.of()
                        .withPredicates(m -> m.resource().is(order))
                        .forMessageType(LineItemLikeStateTransitionMessage.MESSAGE_HINT);
                assertEventually(() -> {
                    final Optional<LineItemLikeStateTransitionMessage> lineItemStateTransitionMessageOptional = client().executeBlocking(messageQuery).head();
                    assertThat(lineItemStateTransitionMessageOptional).isPresent();

                    final LineItemLikeStateTransitionMessage lineItemStateTransitionMessage = lineItemStateTransitionMessageOptional.get();

                    final String lineItemIdFromMessage = lineItemStateTransitionMessage.getLineItemId();
                    assertThat(lineItemIdFromMessage).isEqualTo(order.getLineItems().get(0).getId());
                    final ZonedDateTime transitionDateFromMessage = lineItemStateTransitionMessage.getTransitionDate();
                    assertThat(transitionDateFromMessage).isEqualTo(actualTransitionDate);
                    final Long quantityFromMessage = lineItemStateTransitionMessage.getQuantity();
                    assertThat(quantityFromMessage).isEqualTo(quantity);
                    final Reference<State> fromStateReference = lineItemStateTransitionMessage.getFromState();
                    assertThat(fromStateReference).isEqualTo(initialState.toReference());
                    final Reference<State> toStateReference = lineItemStateTransitionMessage.getToState();
                    assertThat(toStateReference).isEqualTo(nextState.toReference());
                });

                return updatedOrder;
            })
        );
    }

    private Condition<LineItemLike> state(final Referenceable<State> state) {
        return new Condition<LineItemLike>() {
            @Override
            public boolean matches(final LineItemLike actual) {
                return actual.getState().stream().anyMatch(itemState -> itemState.getState().equals(state.toReference()));
            }
        };
    }

    @Test
    public void transitionCustomLineItemState() throws Exception {
        withStandardStates(client(), (State initialState, State nextState) ->
            withOrderOfCustomLineItems(client(), order -> {
                final CustomLineItem customLineItem = order.getCustomLineItems().get(0);
                assertThat(customLineItem).has(state(initialState)).has(not(state(nextState)));
                final long quantity = 1;
                final ZonedDateTime actualTransitionDate = ZonedDateTime_IN_PAST;
                final Order updatedOrder = client().executeBlocking(OrderUpdateCommand.of(order, TransitionCustomLineItemState.of(customLineItem, quantity, initialState, nextState, actualTransitionDate)));
                assertThat(updatedOrder.getCustomLineItems().get(0)).has(itemStates(ItemState.of(nextState, quantity)));

                //you can observe a message
                final Query<CustomLineItemStateTransitionMessage> messageQuery = MessageQuery.of()
                        .withPredicates(m -> m.resource().is(order))
                        .forMessageType(CustomLineItemStateTransitionMessage.MESSAGE_HINT);
                assertEventually(() -> {
                    final Optional<CustomLineItemStateTransitionMessage> lineItemStateTransitionMessageOptional = client().executeBlocking(messageQuery).head();
                    assertThat(lineItemStateTransitionMessageOptional).isPresent();

                    final CustomLineItemStateTransitionMessage customLineItemStateTransitionMessage = lineItemStateTransitionMessageOptional.get();

                    final String customLineItemIdFromMessage = customLineItemStateTransitionMessage.getCustomLineItemId();
                    assertThat(customLineItemIdFromMessage).isEqualTo(order.getCustomLineItems().get(0).getId());
                    final ZonedDateTime transitionDateFromMessage = customLineItemStateTransitionMessage.getTransitionDate();
                    assertThat(transitionDateFromMessage).isEqualTo(actualTransitionDate);
                    final Long quantityFromMessage = customLineItemStateTransitionMessage.getQuantity();
                    assertThat(quantityFromMessage).isEqualTo(quantity);
                    final Reference<State> fromStateReference = customLineItemStateTransitionMessage.getFromState();
                    assertThat(fromStateReference).isEqualTo(initialState.toReference());
                    final Reference<State> toStateReference = customLineItemStateTransitionMessage.getToState();
                    assertThat(toStateReference).isEqualTo(nextState.toReference());
                });

            })
        );
    }

    private Condition<? super LineItemLike> itemStates(final ItemState itemState, final ItemState ... moreItemStates) {
        final Set<ItemState> itemStates = setOf(itemState, moreItemStates);
        return itemStates(itemStates);
    }

    private Condition<? super LineItemLike> itemStates(final Set<ItemState> itemStates) {
        return new Condition<LineItemLike>(){
            @Override
            public boolean matches(final LineItemLike actual) {
                return actual.getState().stream().anyMatch(state -> itemStates.contains(state));
            }
        }.describedAs("states " + itemStates);
    }


    @Test
    public void importLineItemState() throws Exception {
        withStandardStates(client(), (State initialState, State nextState) ->
            withOrder(client(), order -> {
                final LineItem lineItem = order.getLineItems().get(0);
                assertThat(lineItem).has(state(initialState)).has(not(state(nextState)));
                final Set<ItemState> itemStates = asSet(ItemState.of(nextState, 1L), ItemState.of(initialState, lineItem.getQuantity() - 1));
                final Order updatedOrder = client().executeBlocking(OrderUpdateCommand.of(order, ImportLineItemState.of(lineItem, itemStates)));
                assertThat(updatedOrder.getLineItems().get(0)).has(itemStates(itemStates));

                //reference expansion
                final OrderByIdGet orderByIdGet = OrderByIdGet.of(order).withExpansionPaths(m -> m.lineItems().state().state());
                final Order loadedOrder = client().executeBlocking(orderByIdGet);
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
                assertThat(customLineItem).has(state(initialState)).has(not(state(nextState)));
                final Set<ItemState> itemStates = asSet(ItemState.of(nextState, 1L), ItemState.of(initialState, customLineItem.getQuantity() - 1));
                final Order updatedOrder = client().executeBlocking(OrderUpdateCommand.of(order, ImportCustomLineItemState.of(customLineItem, itemStates)));
                assertThat(updatedOrder.getCustomLineItems().get(0)).has(itemStates(itemStates));
            })
        );
    }

    @Test
    public void transitionState() throws Exception {
        withStateByBuilder(client(), builder -> builder.type(StateType.ORDER_STATE), state -> {
            withOrder(client(), order -> {
                final Order updatedOrder = client().executeBlocking(OrderUpdateCommand.of(order, TransitionState.of(state)));

                assertThat(updatedOrder.getState()).isEqualTo(state.toReference());

                assertEventually(() -> {

                    final PagedQueryResult<OrderStateTransitionMessage> messageQueryResult = client().executeBlocking(MessageQuery.of()
                            .withPredicates(m -> m.resource().is(order))
                            .forMessageType(OrderStateTransitionMessage.MESSAGE_HINT));

                    final Optional<OrderStateTransitionMessage> message = messageQueryResult.head();

                    assertThat(message).isPresent();
                    assertThat(message.get().getState()).isEqualTo(state.toReference());

                    //check query model
                    final OrderQuery orderQuery = OrderQuery.of()
                            .withPredicates(m -> m.id().is(order.getId()).and(m.state().is(state)));
                    final Order orderByState = client().executeBlocking(orderQuery)
                            .head().get();
                    assertThat(orderByState).isEqualTo(updatedOrder);
                });

                return updatedOrder;
            });
        });
    }

    @Test
    public void addPayment() {
        withPayment(client(), payment -> {
            withOrder(client(), order -> {
                //add payment
                final OrderUpdateCommand orderUpdateCommand = OrderUpdateCommand.of(order, AddPayment.of(payment))
                        .withExpansionPaths(m -> m.paymentInfo().payments());
                final Order orderWithPayment = client().executeBlocking(orderUpdateCommand);

                final Reference<Payment> paymentReference = orderWithPayment.getPaymentInfo().getPayments().get(0);
                assertThat(paymentReference).isEqualTo(payment.toReference());
                assertThat(paymentReference).is(expanded(payment));

                //remove payment
                final Order orderWithoutPayment = client().executeBlocking(OrderUpdateCommand.of(orderWithPayment, RemovePayment.of(payment)));

                assertThat(orderWithoutPayment.getPaymentInfo()).isNull();

                return orderWithoutPayment;
            });
            return payment;
        });
    }

    @Test
    public void setShippingAddress() {
        withOrder(client(), order -> {
            assertThat(order.getShippingAddress().getStreetNumber()).isNull();

            final Address newAddress = order.getShippingAddress().withStreetNumber("5");
            final Order updatedOrder = client().executeBlocking(OrderUpdateCommand.of(order, SetShippingAddress.of(newAddress)));

            assertThat(updatedOrder.getShippingAddress().getStreetNumber()).isEqualTo("5");

            //there is also a message
            final Query<OrderShippingAddressSetMessage> messageQuery = MessageQuery.of()
                    .withPredicates(m -> m.resource().is(order))
                    .forMessageType(OrderShippingAddressSetMessage.MESSAGE_HINT);
            assertEventually(() -> {
                final Optional<OrderShippingAddressSetMessage> shippingAddressSetMessageOptional =
                        client().executeBlocking(messageQuery).head();
                assertThat(shippingAddressSetMessageOptional).isPresent();
                final OrderShippingAddressSetMessage orderShippingAddressSetMessage = shippingAddressSetMessageOptional.get();
                assertThat(orderShippingAddressSetMessage.getAddress()).isEqualTo(newAddress);
            });

            return updatedOrder;
        });
    }
    
    @Test
    public void setBillingAddress() {
        withOrder(client(), order -> {
            assertThat(order.getBillingAddress()).isNull();

            final Address newAddress = Address.of(DE).withStreetNumber("5");
            final Order updatedOrder = client().executeBlocking(OrderUpdateCommand.of(order, SetBillingAddress.of(newAddress)));

            assertThat(updatedOrder.getBillingAddress().getStreetNumber()).isEqualTo("5");

            //there is also a message
            final Query<OrderBillingAddressSetMessage> messageQuery = MessageQuery.of()
                    .withPredicates(m -> m.resource().is(order))
                    .forMessageType(OrderBillingAddressSetMessage.MESSAGE_HINT);
            assertEventually(() -> {
                final Optional<OrderBillingAddressSetMessage> billingAddressSetMessageOptional =
                        client().executeBlocking(messageQuery).head();
                assertThat(billingAddressSetMessageOptional).isPresent();
                final OrderBillingAddressSetMessage orderBillingAddressSetMessage = billingAddressSetMessageOptional.get();
                assertThat(orderBillingAddressSetMessage.getAddress()).isEqualTo(newAddress);
            });

            return updatedOrder;
        });
    }

    @Test
    public void setCustomerEmail() throws Exception {
        withOrder(client(), order -> {
            assertThat(order.getCustomerEmail()).isNotEmpty();
            final String email = "info@commercetools.de";
            final Order updatedOrder = client().executeBlocking(OrderUpdateCommand.of(order, SetCustomerEmail.of(email)));

            assertThat(updatedOrder.getCustomerEmail()).contains(email);

            //there is also a message
            final Query<OrderCustomerEmailSetMessage> messageQuery = MessageQuery.of()
                    .withPredicates(m -> m.resource().is(order))
                    .forMessageType(OrderCustomerEmailSetMessage.MESSAGE_HINT);
            assertEventually(() -> {
                final Optional<OrderCustomerEmailSetMessage> customerEmailSetMessageOptional =
                        client().executeBlocking(messageQuery).head();
                assertThat(customerEmailSetMessageOptional).isPresent();
                final OrderCustomerEmailSetMessage orderCustomerEmailSetMessage = customerEmailSetMessageOptional.get();
                assertThat(orderCustomerEmailSetMessage.getEmail()).isEqualTo(email);
            });

            return updatedOrder;
        });

    }

    @Test
    public void locale() {
        withOrder(client(), order -> {
            assertThat(order.getLocale()).isNull();
            final Order updatedOrder = client().executeBlocking(OrderUpdateCommand.of(order, SetLocale.of(Locale.GERMAN)));
            assertThat(updatedOrder.getLocale()).isEqualTo(GERMAN);
            return updatedOrder;
        });
    }
}