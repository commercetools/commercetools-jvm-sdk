package io.sphere.sdk.orders.commands;

import com.neovisionaries.i18n.CountryCode;
import io.sphere.sdk.cartdiscounts.CartPredicate;
import io.sphere.sdk.carts.*;
import io.sphere.sdk.carts.commands.CartUpdateCommand;
import io.sphere.sdk.carts.commands.updateactions.SetCustomerEmail;
import io.sphere.sdk.carts.queries.CartByIdGet;
import io.sphere.sdk.client.ErrorResponseException;
import io.sphere.sdk.models.DefaultCurrencyUnits;
import io.sphere.sdk.orders.Order;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import java.util.Arrays;

import static io.sphere.sdk.carts.CartFixtures.*;
import static io.sphere.sdk.products.ProductFixtures.withTaxedProduct;
import static io.sphere.sdk.shippingmethods.ShippingMethodFixtures.withDynamicShippingMethodForGermany;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class OrderFromCartCreateCommandIntegrationTest extends IntegrationTest {
    @Test
    public void execution() throws Exception {
        withFilledCart(client(), cart -> {
            final OrderFromCartCreateCommand createCommand =
                    OrderFromCartCreateCommand.of(cart).plusExpansionPaths(m -> m.cart());
            final Order order = client().executeBlocking(createCommand);
            assertThat(order.getLineItems()).isEqualTo(cart.getLineItems());
            assertThat(order.getCustomLineItems()).isEqualTo(cart.getCustomLineItems());
            assertThat(order.getCart().getId()).isEqualTo(cart.getId());
            assertThat(order.getCart()).is(expanded());
            final Cart orderedCart = order.getCart().getObj();
            assertThat(orderedCart).isNotNull();
            assertThat(orderedCart.getId()).isEqualTo(cart.getId());
            assertThat(orderedCart.getCartState()).isEqualTo(CartState.ORDERED);
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
}