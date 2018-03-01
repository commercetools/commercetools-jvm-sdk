package io.sphere.sdk.carts.commands;

import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.carts.CartDraft;
import io.sphere.sdk.carts.TaxCalculationMode;
import io.sphere.sdk.carts.commands.updateactions.ChangeTaxCalculationMode;
import io.sphere.sdk.carts.queries.CartQuery;
import io.sphere.sdk.carts.queries.CartQueryModel;
import io.sphere.sdk.queries.PagedQueryResult;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import static io.sphere.sdk.carts.CartFixtures.withCartDraft;
import static io.sphere.sdk.test.SphereTestUtils.EUR;
import static org.assertj.core.api.Assertions.assertThat;

public class TaxCalculationModeIntegrationTest extends IntegrationTest {

    @Test
    public void testPriceWithDifferentTaxCalculationMode() {

        CartDraft cartDraft = CartDraft.of(EUR).withTaxCalculationMode(TaxCalculationMode.UNIT_PRICE_LEVEL);
        withCartDraft(client(), cartDraft, cart -> {
            assertThat(cart.getTaxCalculationMode()).isEqualTo(TaxCalculationMode.UNIT_PRICE_LEVEL);
            Cart updatedCart = client().executeBlocking(CartUpdateCommand.of(cart,ChangeTaxCalculationMode.of(TaxCalculationMode.LINE_ITEM_LEVEL)));
            assertThat(updatedCart.getTaxCalculationMode()).isEqualTo(TaxCalculationMode.LINE_ITEM_LEVEL);

            CartQuery query = CartQuery.of()
                    .plusPredicates(CartQueryModel.of().is(updatedCart))
                    .plusPredicates(CartQueryModel.of().taxCalculationMode().is(TaxCalculationMode.LINE_ITEM_LEVEL));
            PagedQueryResult queryResult = client().executeBlocking(query);
            assertThat(queryResult.head().get()).isEqualTo(updatedCart);
            return updatedCart;
        });
    }
}
