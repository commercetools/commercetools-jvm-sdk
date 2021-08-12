package io.sphere.sdk.shoppinglists.queries;

import io.sphere.sdk.queries.PagedQueryResult;
import io.sphere.sdk.shoppinglists.ShoppingList;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import static io.sphere.sdk.shoppinglists.ShoppingListFixtures.withShoppingListInStore;
import static org.assertj.core.api.Assertions.assertThat;

public class ShoppingListInStoreQueryIntegrationTest extends IntegrationTest {

    @Test
    public void byKey() throws Exception {
        withShoppingListInStore(client(), shoppingList -> {
            final PagedQueryResult<ShoppingList> result = client().executeBlocking(ShoppingListInStoreQuery.of(shoppingList.getStore().getKey())
                    .withPredicates(m -> m.key().is(shoppingList.getKey())));
            assertThat(result.getResults().get(0).getId()).isEqualTo(shoppingList.getId());

            return shoppingList;
        });
    }
}