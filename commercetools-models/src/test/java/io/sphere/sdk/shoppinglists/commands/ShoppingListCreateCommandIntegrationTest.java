package io.sphere.sdk.shoppinglists.commands;

import io.sphere.sdk.products.Product;
import io.sphere.sdk.products.commands.ProductUpdateCommand;
import io.sphere.sdk.products.commands.updateactions.Publish;
import io.sphere.sdk.products.commands.updateactions.SetSku;
import io.sphere.sdk.shoppinglists.*;
import io.sphere.sdk.shoppinglists.queries.ShoppingListQuery;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static io.sphere.sdk.products.ProductFixtures.withUpdateableProduct;
import static io.sphere.sdk.test.SphereTestUtils.en;
import static io.sphere.sdk.test.SphereTestUtils.randomKey;
import static org.assertj.core.api.Assertions.assertThat;

public class ShoppingListCreateCommandIntegrationTest extends IntegrationTest {

    @Test
    public void execution() {
        final String shoppingCartKey = "demo-shopping-list-key" + UUID.randomUUID().toString();
        final int deleteDaysAfterLastModification = 1;
        final ShoppingListDraftDsl draft = ShoppingListFixtures.newShoppingListDraftBuilder()
                .key(shoppingCartKey)
                .description(en("Demo shopping list description."))
                .slug(en("demo-shopping-list-slug"))
                .deleteDaysAfterLastModification(deleteDaysAfterLastModification)
                .build();
        final ShoppingList shoppingList = client().executeBlocking(ShoppingListCreateCommand.of(draft));

        assertThat(shoppingList).isNotNull();
        assertThat(shoppingList.getId()).isNotNull();
        assertThat(shoppingList.getKey()).isEqualTo(shoppingCartKey);
        assertThat(shoppingList.getDeleteDaysAfterLastModification()).isEqualTo(deleteDaysAfterLastModification);
        clean(shoppingCartKey);
    }

    @Test
    public void withLineItemBySku() {
        final String shoppingCartKey = "demo-shopping-list-key" + UUID.randomUUID().toString();
        withUpdateableProduct(client(), product -> {
           final String sku = randomKey();
            final Product productWithSku = client().executeBlocking(ProductUpdateCommand.of(product, Arrays.asList(SetSku.of(1, sku), Publish.of())));
            final LineItemDraftDsl lineItemBySku = LineItemDraftBuilder.ofSku(sku, 1L).build();
            final ShoppingListDraft shoppingListDraft = ShoppingListFixtures.newShoppingListDraftBuilder()
                    .key(shoppingCartKey)
                    .plusLineItems(lineItemBySku)
                    .build();
            final ShoppingList shoppingList = client().executeBlocking(ShoppingListCreateCommand.of(shoppingListDraft).withExpansionPaths(m -> m.lineItems()));

            assertThat(shoppingList).isNotNull();
            assertThat(shoppingList.getLineItems())
                    .hasSize(1);

            return productWithSku;
        });
        clean(shoppingCartKey);
    }

    private void clean(String key) {
        List<ShoppingList> results = client().executeBlocking(ShoppingListQuery.of()
                .withPredicates(l -> l.key().is(key)))
                .getResults();
        results.forEach(shoppingList -> client().executeBlocking(ShoppingListDeleteCommand.of(shoppingList)));
    }
}
