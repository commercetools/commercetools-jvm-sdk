package io.sphere.sdk.shoppinglists.queries;

import io.sphere.sdk.shoppinglists.ShoppingList;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import static io.sphere.sdk.shoppinglists.ShoppingListFixtures.withPersistentShoppingList;
import static org.assertj.core.api.Assertions.assertThat;

public class ShoppingListByIdGetIntegrationTest extends IntegrationTest
{
    @Test
    public void execution() throws Exception {
        withPersistentShoppingList(client(), shoppingList -> {
            final ShoppingList fetchedShoppingList = client().executeBlocking(ShoppingListByIdGet.of(shoppingList.getId()));
            assertThat(fetchedShoppingList.getId()).isEqualTo(shoppingList.getId());
        });
    }
}
