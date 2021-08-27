package io.sphere.sdk.shoppinglists.commands;


import io.sphere.sdk.shoppinglists.ShoppingList;
import io.sphere.sdk.shoppinglists.commands.updateactions.SetKey;
import io.sphere.sdk.shoppinglists.commands.updateactions.SetStore;
import io.sphere.sdk.stores.StoreFixtures;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import static io.sphere.sdk.shoppinglists.ShoppingListFixtures.withUpdateableShoppingListInStore;
import static io.sphere.sdk.test.SphereTestUtils.randomKey;
import static org.assertj.core.api.Assertions.assertThat;

public class ShoppingListInStoreUpdateCommandIntegrationTest extends IntegrationTest {

    @Test
    public void setKey() {
        withUpdateableShoppingListInStore(client(), shoppingList -> {
            final String storeKey = shoppingList.getStore().getKey();
            final String newKey = randomKey();
            assertThat(shoppingList.getKey()).isNotEqualTo(newKey);
            final ShoppingList updatedShoppingList = client().executeBlocking(ShoppingListInStoreUpdateCommand.of(shoppingList, storeKey, SetKey.of(newKey)));
            assertThat(updatedShoppingList.getKey()).isEqualTo(newKey);

            return updatedShoppingList;
        });
    }

    @Test
    public void setStore() throws Exception {
        StoreFixtures.withStore(client(), newStore -> {
            withUpdateableShoppingListInStore(client(), shoppingList -> {
                final ShoppingList updatedShoppingList = client().executeBlocking(
                        ShoppingListUpdateCommand.of(shoppingList, SetStore.of(newStore.toResourceIdentifier())));
                assertThat(updatedShoppingList.getStore().getKey()).isEqualTo(newStore.getKey());

                return updatedShoppingList;
            });
        });
    }
    
}
