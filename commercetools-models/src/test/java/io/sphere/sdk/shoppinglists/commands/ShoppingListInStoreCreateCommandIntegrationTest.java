package io.sphere.sdk.shoppinglists.commands;

import io.sphere.sdk.shoppinglists.ShoppingList;
import io.sphere.sdk.shoppinglists.ShoppingListDraft;
import io.sphere.sdk.shoppinglists.ShoppingListFixtures;
import io.sphere.sdk.stores.StoreFixtures;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ShoppingListInStoreCreateCommandIntegrationTest extends IntegrationTest {
    
    @Test
    public void createShoppingListWithStore() throws Exception {
        StoreFixtures.withStore(client(), store -> {
            final ShoppingListDraft shoppingListDraft = ShoppingListFixtures.newShoppingListDraftBuilder().build();
            final ShoppingList shoppingList = client().executeBlocking(ShoppingListInStoreCreateCommand.of(store.getKey(), shoppingListDraft));

            assertThat(shoppingList).isNotNull();
            final ShoppingList deletedShoppingList = client().executeBlocking(ShoppingListDeleteCommand.of(shoppingList));
            assertThat(deletedShoppingList).isNotNull();
        });
    }
}