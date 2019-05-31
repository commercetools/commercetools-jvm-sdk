package io.sphere.sdk.orders.commands;

import io.sphere.sdk.carts.CartFixtures;
import io.sphere.sdk.carts.commands.CartDeleteCommand;
import io.sphere.sdk.orders.*;
import io.sphere.sdk.orders.commands.updateactions.SetOrderNumber;
import io.sphere.sdk.orders.queries.OrderByIdGet;
import io.sphere.sdk.states.StateType;
import io.sphere.sdk.test.IntegrationTest;
import io.sphere.sdk.test.SphereTestUtils;
import org.junit.Test;

import static io.sphere.sdk.orders.OrderFixtures.withOrder;
import static io.sphere.sdk.states.StateFixtures.withStateByBuilder;
import static io.sphere.sdk.stores.StoreFixtures.withStore;
import static io.sphere.sdk.test.SphereTestUtils.randomString;
import static org.assertj.core.api.Assertions.assertThat;

public class OrderDeleteCommandIntegrationTest extends IntegrationTest {
    @Test
    public void deleteById() throws Exception {
        withOrder(client(), order -> {
            final Order deletedOrder = client().executeBlocking(OrderDeleteCommand.of(order));
            final Order queriedDeletedOrder = client().executeBlocking(OrderByIdGet.of(deletedOrder));
            assertThat(queriedDeletedOrder).isNull();
        });
    }

    @Test
    public void deleteByOrderNumber() throws Exception {
        withOrder(client(), order -> {
            final String orderNumber = randomString();
            final Order orderWithOrderNumber = client().executeBlocking(OrderUpdateCommand.of(order, SetOrderNumber.of(orderNumber)));

            final Order deletedOrder = client().executeBlocking(OrderDeleteCommand.ofOrderNumber(orderNumber, orderWithOrderNumber.getVersion()));
            final Order queriedDeletedOrder = client().executeBlocking(OrderByIdGet.of(deletedOrder));
            assertThat(queriedDeletedOrder).isNull();
        });
    }

    @Test
    public void deleteByIdEraseData() throws Exception {
        withOrder(client(), order -> {
            final Order deletedOrder = client().executeBlocking(OrderDeleteCommand.of(order,true));
            final Order queriedDeletedOrder = client().executeBlocking(OrderByIdGet.of(deletedOrder));
            assertThat(queriedDeletedOrder).isNull();
        });
    }

    @Test
    public void deleteByOrderNumberEraseData() throws Exception {
        withOrder(client(), order -> {
            final String orderNumber = randomString();
            final Order orderWithOrderNumber = client().executeBlocking(OrderUpdateCommand.of(order, SetOrderNumber.of(orderNumber)));

            final Order deletedOrder = client().executeBlocking(OrderDeleteCommand.ofOrderNumber(orderNumber, orderWithOrderNumber.getVersion(),true));
            final Order queriedDeletedOrder = client().executeBlocking(OrderByIdGet.of(deletedOrder));
            assertThat(queriedDeletedOrder).isNull();
        });
    }
    
    @Test
    public void deleteOrderInStoreById() {
        withStateByBuilder(client(), builder -> builder.type(StateType.ORDER_STATE), state -> {
            withStore(client(), store -> {
                CartFixtures.withFilledCartInStore(client(), store, cart -> {
                    final OrderFromCartDraft orderFromCartDraft = OrderFromCartDraftBuilder.of(cart.getId(), cart.getVersion())
                            .shipmentState(ShipmentState.SHIPPED)
                            .state(state.toReference())
                            .orderState(OrderState.CANCELLED)
                            .build();
                    final Order order = client().executeBlocking(OrderFromCartInStoreCreateCommand.of(store.getKey(), orderFromCartDraft)
                            .withExpansionPaths(m -> m.cart()));
                    assertThat(order).isNotNull();
                    assertThat(order.getStore()).isNotNull();
                    assertThat(order.getStore().getKey()).isEqualTo(store.getKey());
                    
                    client().executeBlocking(OrderInStoreDeleteByIdCommand.of(store.getKey(), order, true));
                    client().executeBlocking(CartDeleteCommand.of(order.getCart().getObj()));
                });
            });
        });
    }

    @Test
    public void deleteOrderInStoreByOrderNumber() {
        withStateByBuilder(client(), builder -> builder.type(StateType.ORDER_STATE), state -> {
            withStore(client(), store -> {
                CartFixtures.withFilledCartInStore(client(), store, cart -> {
                    String orderNumber = SphereTestUtils.randomKey();
                    final OrderFromCartDraft orderFromCartDraft = OrderFromCartDraftBuilder.of(cart.getId(), cart.getVersion())
                            .shipmentState(ShipmentState.SHIPPED)
                            .state(state.toReference())
                            .orderState(OrderState.CANCELLED)
                            .orderNumber(orderNumber)
                            .build();
                    final Order order = client().executeBlocking(OrderFromCartInStoreCreateCommand.of(store.getKey(), orderFromCartDraft)
                            .withExpansionPaths(m -> m.cart()));
                    assertThat(order).isNotNull();
                    assertThat(order.getStore()).isNotNull();
                    assertThat(order.getStore().getKey()).isEqualTo(store.getKey());

                    client().executeBlocking(OrderInStoreDeleteByOrderNumberCommand.of(store.getKey(), order.getOrderNumber(), order.getVersion(), true));
                    client().executeBlocking(CartDeleteCommand.of(order.getCart().getObj()));
                });
            });
        });
    }
}