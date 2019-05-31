package io.sphere.sdk.orders.commands;

import com.neovisionaries.i18n.CountryCode;
import io.sphere.sdk.cartdiscounts.CartPredicate;
import io.sphere.sdk.carts.*;
import io.sphere.sdk.carts.commands.CartDeleteCommand;
import io.sphere.sdk.carts.commands.CartUpdateCommand;
import io.sphere.sdk.carts.commands.updateactions.SetCustomerEmail;
import io.sphere.sdk.carts.queries.CartByIdGet;
import io.sphere.sdk.client.ErrorResponseException;
import io.sphere.sdk.models.DefaultCurrencyUnits;
import io.sphere.sdk.orders.*;
import io.sphere.sdk.orders.commands.updateactions.TransitionState;
import io.sphere.sdk.orders.expansion.OrderExpansionModel;
import io.sphere.sdk.states.StateType;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import java.util.Arrays;

import static io.sphere.sdk.carts.CartFixtures.*;
import static io.sphere.sdk.products.ProductFixtures.withTaxedProduct;
import static io.sphere.sdk.shippingmethods.ShippingMethodFixtures.withDynamicShippingMethodForGermany;
import static io.sphere.sdk.states.StateFixtures.withStateByBuilder;
import static io.sphere.sdk.stores.StoreFixtures.withStore;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class OrderFromCartCreateCommandIntegrationTest extends IntegrationTest {

    @Test
    public void execution() throws Exception {
        withStateByBuilder(client(), builder -> builder.type(StateType.ORDER_STATE), state -> {
            withFilledCart(client(), cart -> {
                final Order order = client().executeBlocking(OrderFromCartCreateCommand.of(cart));
                final Order updatedOrder = client().executeBlocking(OrderUpdateCommand.of(order, TransitionState.of(state))
                        .plusExpansionPaths(OrderExpansionModel::state)
                        .plusExpansionPaths(OrderExpansionModel::cart)
                );
                assertThat(updatedOrder.getLineItems()).isEqualTo(cart.getLineItems());
                assertThat(updatedOrder.getCustomLineItems()).isEqualTo(cart.getCustomLineItems());
                assertThat(updatedOrder.getCart().getId()).isEqualTo(cart.getId());
                assertThat(updatedOrder.getCart()).is(expanded());
                final Cart orderedCart = updatedOrder.getCart().getObj();
                assertThat(orderedCart).isNotNull();
                assertThat(orderedCart.getId()).isEqualTo(cart.getId());
                assertThat(orderedCart.getCartState()).isEqualTo(CartState.ORDERED);
                assertThat(updatedOrder.getState()).is(expanded());

                //to be able to delete the state transition we have to delete the associated order.
                client().executeBlocking(OrderDeleteCommand.of(updatedOrder));
            });
        });
    }

    @Test
    public void shouldChangeShippingMethodStateWithNonMatchingShippingMethod()  throws Exception {
        withTaxedProduct(client(), product -> {
            withDynamicShippingMethodForGermany(client(), CartPredicate.of("customer.email=\"john@example.com\""), shippingMethod -> {
                final CartDraft cartDraft = CartDraftBuilder.of(DefaultCurrencyUnits.EUR)
                        .country(CountryCode.DE).shippingAddress(GERMAN_ADDRESS)
                        .customerEmail("john@example.com")
                        .shippingMethod(shippingMethod)
                        .lineItems(Arrays.asList(LineItemDraft.of(product.getId(), 1, 1)))
                        .build();

                withCartDraft(client(), cartDraft, cart -> {
                    assertThat(cart.getShippingInfo().getShippingMethodState()).isEqualTo(ShippingMethodState.MATCHES_CART);

                    final Cart updatedCart = client().executeBlocking(CartUpdateCommand.of(cart, SetCustomerEmail.of("jon@doe.com")));

                    final OrderFromCartCreateCommand orderCreateCommand =
                            OrderFromCartCreateCommand.of(updatedCart);
                    assertThatThrownBy(() -> client().executeBlocking(orderCreateCommand))
                            .isInstanceOf(ErrorResponseException.class)
                            .hasMessageContaining("does not match");

                    final Cart cartWitUpdatedState = client().executeBlocking(CartByIdGet.of(updatedCart));

                    assertThat(cartWitUpdatedState.getShippingInfo().getShippingMethodState()).isEqualTo(ShippingMethodState.DOES_NOT_MATCH_CART);

                    return cartWitUpdatedState;
                });
            });
        });
    }


    @Test
    public void orderFromCartDraft() {
        withStateByBuilder(client(), builder -> builder.type(StateType.ORDER_STATE), state -> {
            withFilledCart(client(), cart -> {
                final OrderFromCartDraft orderFromCartDraft = OrderFromCartDraftBuilder.of(cart.getId(), cart.getVersion())
                        .shipmentState(ShipmentState.SHIPPED)
                        .state(state.toReference())
                        .orderState(OrderState.CANCELLED)
                        .build();
                final Order order = client().executeBlocking(OrderFromCartCreateCommand.of(cart).withDraft(orderFromCartDraft));
                assertThat(order.getState().getId()).isEqualTo(state.getId());
                assertThat(order.getOrderState()).isEqualTo(OrderState.CANCELLED);
                assertThat(order).isNotNull();
                assertThat(order.getShipmentState()).isEqualTo(ShipmentState.SHIPPED);

                client().executeBlocking(OrderDeleteCommand.of(order));
            });
        });
    }
    
    @Test
    public void orderFromCartDraftInStore() {
        withStateByBuilder(client(), builder -> builder.type(StateType.ORDER_STATE), state -> {
            withStore(client(), store -> {
                CartFixtures.withFilledCartInStore(client(), store,  cart -> {
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
                    client().executeBlocking(OrderDeleteCommand.of(order));
                    client().executeBlocking(CartDeleteCommand.of(order.getCart().getObj()));
                });
            });
        });
    }
}