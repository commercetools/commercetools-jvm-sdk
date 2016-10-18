package io.sphere.sdk.orders.errors;

import com.neovisionaries.i18n.CountryCode;
import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.carts.CartDraft;
import io.sphere.sdk.carts.CartDraftDsl;
import io.sphere.sdk.carts.LineItemDraft;
import io.sphere.sdk.carts.commands.CartCreateCommand;
import io.sphere.sdk.carts.commands.CartDeleteCommand;
import io.sphere.sdk.client.ErrorResponseException;
import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.models.Address;
import io.sphere.sdk.models.DefaultCurrencyUnits;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.orders.commands.OrderFromCartCreateCommand;
import io.sphere.sdk.products.PriceDraft;
import io.sphere.sdk.products.Product;
import io.sphere.sdk.products.commands.ProductUpdateCommand;
import io.sphere.sdk.products.commands.updateactions.Publish;
import io.sphere.sdk.products.commands.updateactions.SetPrices;
import io.sphere.sdk.shippingmethods.ShippingMethod;
import io.sphere.sdk.shippingmethods.ShippingRate;
import io.sphere.sdk.shippingmethods.ZoneRate;
import io.sphere.sdk.shippingmethods.commands.ShippingMethodUpdateCommand;
import io.sphere.sdk.shippingmethods.commands.updateactions.AddShippingRate;
import io.sphere.sdk.shippingmethods.commands.updateactions.RemoveShippingRate;
import io.sphere.sdk.test.IntegrationTest;
import io.sphere.sdk.test.SphereTestUtils;
import io.sphere.sdk.zones.Zone;
import org.junit.Test;

import java.util.List;

import static io.sphere.sdk.products.ProductFixtures.withTaxedProduct;
import static io.sphere.sdk.shippingmethods.ShippingMethodFixtures.withUpdateableShippingMethodForGermany;
import static io.sphere.sdk.test.SphereTestUtils.EURO_10;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

public class PriceChangedErrorIntegrationTest extends IntegrationTest {
    @Test
    public void order() {
        withTaxedProduct(client(), product -> {
            withUpdateableShippingMethodForGermany(client(), shippingMethod -> {
                final int quantity = 2;
                final int variantId = 1;
                final CartDraftDsl draft = CartDraft.of(DefaultCurrencyUnits.EUR)
                        .withCountry(CountryCode.DE)
                        .withShippingAddress(Address.of(CountryCode.DE))
                        .withLineItems(singletonList(LineItemDraft.of(product, variantId, quantity)))
                        .withShippingMethod(shippingMethod);

                final Cart cart = client().executeBlocking(CartCreateCommand.of(draft));

                //change the product price
                final List<UpdateActionImpl<Product>> productUpdates = asList(
                        SetPrices.ofVariantId(variantId, singletonList(PriceDraft.of(SphereTestUtils.EURO_20))),
                        Publish.of()
                );
                client().executeBlocking(ProductUpdateCommand.of(product, productUpdates));

                //also change the zone rate
                final ZoneRate zoneRate = shippingMethod.getZoneRates().get(0);
                final ShippingRate oldShippingRate = zoneRate.getShippingRates().get(0);
                final Reference<Zone> zone = zoneRate.getZone();
                final ShippingMethod updatedShippingMethod = client().executeBlocking(ShippingMethodUpdateCommand.of(shippingMethod, asList(RemoveShippingRate.of(oldShippingRate, zone), AddShippingRate.of(ShippingRate.of(EURO_10, EURO_10), zone))));

                //see the order creation fail
                final Throwable throwable = catchThrowable(() -> client().executeBlocking(OrderFromCartCreateCommand.of(cart)));
                assertThat(throwable).isInstanceOf(ErrorResponseException.class);
                final ErrorResponseException e = (ErrorResponseException) throwable;
                assertThat(e.hasErrorCode(PriceChangedError.CODE));
                final PriceChangedError error = e.getErrors().stream()
                        .filter(err -> err.getCode().equals(PriceChangedError.CODE))
                        .findFirst().get().as(PriceChangedError.class);
                assertThat(error.getLineItems())
                        .as("the changed line items can be found")
                        .containsExactly(cart.getLineItems().get(0).getId());
                assertThat(error.isShipping())
                        .as("the rate change can be diagnosed")
                        .isTrue();

                //clean up
                client().executeBlocking(CartDeleteCommand.of(cart));
                return updatedShippingMethod;
            });
        });
    }
}