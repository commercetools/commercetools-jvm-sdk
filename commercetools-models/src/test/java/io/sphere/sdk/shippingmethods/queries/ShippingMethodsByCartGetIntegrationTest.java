package io.sphere.sdk.shippingmethods.queries;

import io.sphere.sdk.cartdiscounts.CartPredicate;
import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.carts.commands.CartUpdateCommand;
import io.sphere.sdk.carts.commands.updateactions.SetCustomerEmail;
import io.sphere.sdk.carts.commands.updateactions.SetShippingAddress;
import io.sphere.sdk.client.SphereRequest;
import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.shippingmethods.ShippingMethod;
import io.sphere.sdk.shippingmethods.ShippingRate;
import io.sphere.sdk.test.IntegrationTest;
import io.sphere.sdk.test.utils.VrapRequestDecorator;
import org.assertj.core.api.Condition;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static io.sphere.sdk.carts.CartFixtures.GERMAN_ADDRESS;
import static io.sphere.sdk.carts.CartFixtures.withCart;
import static io.sphere.sdk.shippingmethods.ShippingMethodFixtures.withShippingMethodForGermany;
import static io.sphere.sdk.shippingmethods.ShippingMethodFixtures.withUpdateableDynamicShippingMethodForGermany;
import static org.assertj.core.api.Assertions.assertThat;


public class ShippingMethodsByCartGetIntegrationTest extends IntegrationTest {
    @Test
    public void execution() throws Exception {
        withShippingMethodForGermany(client(), shippingMethod -> {
            withCart(client(), cart -> {
                final Cart cartWithShippingAddress = client().executeBlocking(CartUpdateCommand.of(cart, SetShippingAddress.of(GERMAN_ADDRESS)));

                final SphereRequest<List<ShippingMethod>> sphereRequest =
                        new VrapRequestDecorator<>(ShippingMethodsByCartGet.of(cartWithShippingAddress).plusExpansionPaths(exp -> exp.taxCategory()), "response", "queryParameter");

                final List<ShippingMethod> shippingMethods =
                        client().executeBlocking(sphereRequest);
                assertThat(shippingMethods).isNotEmpty();

                for (final ShippingMethod cartShippingMethod : shippingMethods) {
                    final List<ShippingRate> shippingRates = cartShippingMethod.getZoneRates().stream()
                            .flatMap(zoneRate -> zoneRate.getShippingRates().stream())
                            .collect(Collectors.toList());

                    assertThat(shippingRates).areExactly(1, new Condition<>(ShippingRate::isMatching, "Shipping rate is matching"));
                    assertThat(cartShippingMethod.getTaxCategory().getObj()).isNotNull();
                }
                return cartWithShippingAddress;
            });
        });
    }

    @Test
    public void shouldReturnShippingMethodWithMatchingPredicate() throws Exception {
        final CartPredicate cartPredicate = CartPredicate.of("customer.email=\"john@example.com\"");
        withUpdateableDynamicShippingMethodForGermany(client(), cartPredicate, shippingMethod -> {
            withCart(client(), cart -> {
                final List<UpdateActionImpl<Cart>> updateActions = Arrays.asList(SetShippingAddress.of(GERMAN_ADDRESS), SetCustomerEmail.of("john@example.com"));
                final Cart updatedCart = client().executeBlocking(CartUpdateCommand.of(cart, updateActions));

                final SphereRequest<List<ShippingMethod>> sphereRequest =
                        new VrapRequestDecorator<>(ShippingMethodsByCartGet.of(updatedCart), "response", "queryParameter");

                final List<ShippingMethod> shippingMethods =
                        client().executeBlocking(sphereRequest);
                assertThat(shippingMethods).isNotEmpty();

                for (final ShippingMethod cartShippingMethod : shippingMethods) {
                    final List<ShippingRate> shippingRates = cartShippingMethod.getZoneRates().stream()
                            .flatMap(zoneRate -> zoneRate.getShippingRates().stream())
                            .collect(Collectors.toList());

                    assertThat(shippingRates).areExactly(1, new Condition<>(ShippingRate::isMatching, "Shipping rate is matching"));
                }

                return updatedCart;
            });

            return shippingMethod;
        });
    }
}
