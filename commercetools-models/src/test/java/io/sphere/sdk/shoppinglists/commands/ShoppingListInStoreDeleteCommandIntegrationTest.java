package io.sphere.sdk.shoppinglists.commands;


import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.carts.queries.CartByCustomerIdGet;
import io.sphere.sdk.customers.Customer;
import io.sphere.sdk.customers.CustomerDraft;
import io.sphere.sdk.customers.CustomerSignInResult;
import io.sphere.sdk.customers.commands.CustomerCreateCommand;
import io.sphere.sdk.customers.commands.CustomerInStoreDeleteCommand;
import io.sphere.sdk.models.ResourceIdentifier;
import io.sphere.sdk.shoppinglists.ShoppingList;
import io.sphere.sdk.shoppinglists.ShoppingListDraft;
import io.sphere.sdk.shoppinglists.queries.ShoppingListInStoreByIdGet;
import io.sphere.sdk.stores.Store;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static io.sphere.sdk.customers.CustomerFixtures.newCustomerDraft;
import static io.sphere.sdk.shoppinglists.ShoppingListFixtures.*;
import static io.sphere.sdk.stores.StoreFixtures.withStore;
import static io.sphere.sdk.test.SphereTestUtils.randomKey;
import static org.assertj.core.api.Assertions.assertThat;

public class ShoppingListInStoreDeleteCommandIntegrationTest extends IntegrationTest {
    
    @Test
    public void deleteById() throws Exception {
        withStore(client(), store -> {
            final ShoppingListDraft shoppingListDraft = newShoppingListDraftBuilder().store(store.toResourceIdentifier()).build();
            final ShoppingList shoppingList = client().executeBlocking(ShoppingListCreateCommand.of(shoppingListDraft));

            client().executeBlocking(ShoppingListInStoreDeleteCommand.of(store.getKey(), shoppingList));
            final ShoppingList shoppingListDeleted = client().executeBlocking(ShoppingListInStoreByIdGet.of(store.getKey(), shoppingList.getId()));
            assertThat(shoppingListDeleted).isNull();
        });
    }

    @Test
    public void deleteByKey() throws Exception {
        withStore(client(), store -> {
            final ShoppingListDraft shoppingListDraft = newShoppingListDraftBuilder().key(randomKey()).store(store.toResourceIdentifier()).build();
            final ShoppingList shoppingList = client().executeBlocking(ShoppingListCreateCommand.of(shoppingListDraft));

            client().executeBlocking(ShoppingListInStoreDeleteCommand.ofKey(store.getKey(), shoppingList.getKey(), shoppingList.getVersion()));
            final ShoppingList shoppingListDeleted = client().executeBlocking(ShoppingListInStoreByIdGet.of(store.getKey(), shoppingList.getId()));
            assertThat(shoppingListDeleted).isNull();
        });
    }
}