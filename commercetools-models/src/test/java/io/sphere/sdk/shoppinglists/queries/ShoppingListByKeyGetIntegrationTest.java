package io.sphere.sdk.shoppinglists.queries;

import io.sphere.sdk.shoppinglists.ShoppingList;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import static io.sphere.sdk.shoppinglists.ShoppingListFixtures.withShoppingList;
import static org.assertj.core.api.Assertions.assertThat;

public class ShoppingListByKeyGetIntegrationTest extends IntegrationTest
{
    @Test
    public void byKeyGet() throws Exception {
        withShoppingList(client(), shoppingList -> {
            final ShoppingList fetchedShoppingList = client().executeBlocking(ShoppingListByKeyGet.of(shoppingList.getKey()));

            assertThat(fetchedShoppingList.getId()).isEqualTo(shoppingList.getId());
            assertThat(fetchedShoppingList.getKey()).isEqualTo(shoppingList.getKey());

            return shoppingList;
        });
    }
}
