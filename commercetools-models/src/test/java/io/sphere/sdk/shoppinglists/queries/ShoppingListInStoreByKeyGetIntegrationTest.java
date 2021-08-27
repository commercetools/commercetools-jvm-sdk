package io.sphere.sdk.shoppinglists.queries;

import io.sphere.sdk.shoppinglists.ShoppingList;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import static io.sphere.sdk.shoppinglists.ShoppingListFixtures.withShoppingListInStore;
import static org.assertj.core.api.Assertions.assertThat;

public class ShoppingListInStoreByKeyGetIntegrationTest extends IntegrationTest {

    @Test
    public void execute() throws Exception {
        withShoppingListInStore(client(), shoppingList -> {
            final String storeKey = shoppingList.getStore().getKey();
            final ShoppingList queriedShoppingList = client().executeBlocking(ShoppingListInStoreByKeyGet.of(storeKey, shoppingList.getKey()));
            assertThat(queriedShoppingList).isNotNull();
            assertThat(queriedShoppingList.getKey()).isEqualTo(shoppingList.getKey());

            return queriedShoppingList;
        });
    }
    
}