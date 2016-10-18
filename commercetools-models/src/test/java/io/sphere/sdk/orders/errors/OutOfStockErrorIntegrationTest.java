package io.sphere.sdk.orders.errors;

import com.neovisionaries.i18n.CountryCode;
import io.sphere.sdk.carts.*;
import io.sphere.sdk.carts.commands.CartCreateCommand;
import io.sphere.sdk.client.ErrorResponseException;
import io.sphere.sdk.inventory.InventoryEntryDraft;
import io.sphere.sdk.inventory.commands.InventoryEntryCreateCommand;
import io.sphere.sdk.models.Address;
import io.sphere.sdk.models.DefaultCurrencyUnits;
import io.sphere.sdk.orders.commands.OrderFromCartCreateCommand;
import io.sphere.sdk.products.Product;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import java.util.Collections;

import static io.sphere.sdk.products.ProductFixtures.withTaxedProduct;
import static org.assertj.core.api.Assertions.*;

public class OutOfStockErrorIntegrationTest extends IntegrationTest {
    @Test
    public void order() {
        withTaxedProduct(client(), product -> {
            final int itemsLeft = 3;
            final String sku = getSku(product);
            client().executeBlocking(InventoryEntryCreateCommand.of(InventoryEntryDraft.of(sku, itemsLeft)));

            final int itemCountToOrder = 5;
            final CartDraftDsl draft = CartDraft.of(DefaultCurrencyUnits.EUR)
                    .withCountry(CountryCode.DE)
                    .withInventoryMode(InventoryMode.RESERVE_ON_ORDER)
                    .withShippingAddress(Address.of(CountryCode.DE))
                    .withLineItems(Collections.singletonList(LineItemDraft.of(product, 1, itemCountToOrder)));

            final Cart cart = client().executeBlocking(CartCreateCommand.of(draft));

            final Throwable throwable = catchThrowable(() -> client().executeBlocking(OrderFromCartCreateCommand.of(cart)));

            assertThat(throwable).isInstanceOf(ErrorResponseException.class);
            final ErrorResponseException e = (ErrorResponseException) throwable;
            assertThat(e.hasErrorCode(OutOfStockError.CODE));
            final OutOfStockError outOfStockError = e.getErrors().stream()
                    .filter(err -> err.getCode().equals(OutOfStockError.CODE))
                    .findFirst().get().as(OutOfStockError.class);
            assertThat(outOfStockError.getSkus()).containsExactly(sku);
            assertThat(outOfStockError.getLineItems()).containsExactly(cart.getLineItems().get(0).getId());
        });

    }

    private String getSku(final Product product) {
        return product.getMasterData().getStaged().getMasterVariant().getSku();
    }
}