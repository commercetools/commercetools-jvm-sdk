package io.sphere.sdk.shoppinglists.commands;

import io.sphere.sdk.products.Product;
import io.sphere.sdk.products.commands.ProductUpdateCommand;
import io.sphere.sdk.products.commands.updateactions.Publish;
import io.sphere.sdk.products.commands.updateactions.SetSku;
import io.sphere.sdk.shoppinglists.*;
import io.sphere.sdk.shoppinglists.queries.ShoppingListQuery;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static io.sphere.sdk.products.ProductFixtures.withUpdateableProduct;
import static io.sphere.sdk.test.SphereTestUtils.en;
import static io.sphere.sdk.test.SphereTestUtils.randomKey;
import static org.assertj.core.api.Assertions.assertThat;

public class ShoppingListCreateCommandIntegrationTest extends IntegrationTest {
    private static final String DEMO_SHOPPING_LIST_KEY = "demo-shopping-list-key";

    @Before
    @After
    public void clean() {
        List<ShoppingList> results = client().executeBlocking(ShoppingListQuery.of()
                .withPredicates(l -> l.key().is(DEMO_SHOPPING_LIST_KEY)))
                .getResults();
        results.forEach(shoppingList -> client().executeBlocking(ShoppingListDeleteCommand.of(shoppingList)));
    }

    @Test
    public void execution() {
        final int deleteDaysAfterLastModification = 1;
        final ShoppingListDraftDsl draft = ShoppingListFixtures.newShoppingListDraftBuilder()
                .key(DEMO_SHOPPING_LIST_KEY)
                .description(en("Demo shopping list description."))
                .slug(en("demo-shopping-list-slug"))
                .deleteDaysAfterLastModification(deleteDaysAfterLastModification)
                .build();
        final ShoppingList shoppingList = client().executeBlocking(ShoppingListCreateCommand.of(draft));

        assertThat(shoppingList).isNotNull();
        assertThat(shoppingList.getId()).isNotNull();
        assertThat(shoppingList.getKey()).isEqualTo(DEMO_SHOPPING_LIST_KEY);
        assertThat(shoppingList.getDeleteDaysAfterLastModification()).isEqualTo(deleteDaysAfterLastModification);
    }

    @Test
    public void withLineItemBySku() {
        withUpdateableProduct(client(), product -> {
           final String sku = randomKey();
            final Product productWithSku = client().executeBlocking(ProductUpdateCommand.of(product, Arrays.asList(SetSku.of(1, sku), Publish.of())));
            final LineItemDraftDsl lineItemBySku = LineItemDraftBuilder.ofSku(sku, 1L).build();
            final ShoppingListDraft shoppingListDraft = ShoppingListFixtures.newShoppingListDraftBuilder()
                    .key(DEMO_SHOPPING_LIST_KEY)
                    .plusLineItems(lineItemBySku)
                    .build();
            final ShoppingList shoppingList = client().executeBlocking(ShoppingListCreateCommand.of(shoppingListDraft).withExpansionPaths(m -> m.lineItems()));

            assertThat(shoppingList).isNotNull();
            assertThat(shoppingList.getLineItems())
                    .hasSize(1);
            client().executeBlocking(ShoppingListDeleteCommand.of(shoppingList));

            return productWithSku;
        });
    }
}
