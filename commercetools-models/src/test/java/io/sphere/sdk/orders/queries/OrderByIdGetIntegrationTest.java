package io.sphere.sdk.orders.queries;

import io.sphere.sdk.carts.CartFixtures;
import io.sphere.sdk.carts.commands.CartDeleteCommand;
import io.sphere.sdk.orders.*;
import io.sphere.sdk.orders.commands.OrderDeleteCommand;
import io.sphere.sdk.orders.commands.OrderFromCartInStoreCreateCommand;
import io.sphere.sdk.states.StateType;
import io.sphere.sdk.test.IntegrationTest;
import io.sphere.sdk.test.SphereTestUtils;
import org.junit.Test;

import static io.sphere.sdk.orders.OrderFixtures.withOrder;
import static io.sphere.sdk.states.StateFixtures.withStateByBuilder;
import static io.sphere.sdk.stores.StoreFixtures.withStore;
import static org.assertj.core.api.Assertions.assertThat;


public class OrderByIdGetIntegrationTest extends IntegrationTest {
    @Test
    public void execution() throws Exception {
        withOrder(client(), order -> {
            final Order loadedOrder = client().executeBlocking(OrderByIdGet.of(order.getId()));
            assertThat(loadedOrder.getId()).isEqualTo(order.getId());
            return order;
        });
    }
    
    @Test
    public void getOrderInStoreById() {
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
                    
                    final Order orderInStore = client().executeBlocking(OrderInStoreByIdGet.of(store.getKey(), order.getId()));
                    assertThat(orderInStore).isNotNull();
                    
                    client().executeBlocking(OrderDeleteCommand.of(order));
                    client().executeBlocking(CartDeleteCommand.of(order.getCart().getObj()));
                });
            });
        });
    }

    @Test
    public void getOrderInStoreByOrderId() {
        withStateByBuilder(client(), builder -> builder.type(StateType.ORDER_STATE), state -> {
            withStore(client(), store -> {
                CartFixtures.withFilledCartInStore(client(), store, cart -> {
                    String randomOrderNumber = SphereTestUtils.randomKey();
                    final OrderFromCartDraft orderFromCartDraft = OrderFromCartDraftBuilder.of(cart.getId(), cart.getVersion())
                            .shipmentState(ShipmentState.SHIPPED)
                            .state(state.toReference())
                            .orderState(OrderState.CANCELLED)
                            .orderNumber(randomOrderNumber)
                            .build();
                    final Order order = client().executeBlocking(OrderFromCartInStoreCreateCommand.of(store.getKey(), orderFromCartDraft)
                            .withExpansionPaths(m -> m.cart()));
                    assertThat(order).isNotNull();
                    assertThat(order.getStore()).isNotNull();
                    assertThat(order.getStore().getKey()).isEqualTo(store.getKey());

                    final Order orderInStore = client().executeBlocking(OrderInStoreByOrderNumberGet.of(store.getKey(), order.getOrderNumber()));
                    assertThat(orderInStore).isNotNull();

                    client().executeBlocking(OrderDeleteCommand.of(order));
                    client().executeBlocking(CartDeleteCommand.of(order.getCart().getObj()));
                });
            });
        });
    }
}