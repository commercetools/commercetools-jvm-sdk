package io.sphere.sdk.shoppinglists.commands;

import io.sphere.sdk.shoppinglists.queries.ShoppingListQuery;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import static io.sphere.sdk.shoppinglists.ShoppingListFixtures.withPersistentShoppingList;
import static org.assertj.core.api.Assertions.assertThat;

public class ShoppingListDeleteCommandIntegrationTest extends IntegrationTest {

    @Test
    public void delete() {
        withPersistentShoppingList(client(), shoppingList -> {
            client().executeBlocking(ShoppingListDeleteCommand.of(shoppingList));

            final ShoppingListQuery shoppingListQuery = ShoppingListQuery.of().withPredicates(m -> m.id().is(shoppingList.getId()));
            assertThat(client().executeBlocking(shoppingListQuery).head()).isEmpty();
        });
    }

    @Test
    public void deleteByKey() {
        withPersistentShoppingList(client(), shoppingList -> {
            client().executeBlocking(ShoppingListDeleteCommand.ofKey(shoppingList.getKey(), shoppingList.getVersion()));

            final ShoppingListQuery shoppingListQuery = ShoppingListQuery.of().withPredicates(m -> m.id().is(shoppingList.getId()));
            assertThat(client().executeBlocking(shoppingListQuery).head()).isEmpty();
        });
    }

    @Test
    public void deleteWithDataErasure() {
        withPersistentShoppingList(client(), shoppingList -> {
            client().executeBlocking(ShoppingListDeleteCommand.of(shoppingList,true));

            final ShoppingListQuery shoppingListQuery = ShoppingListQuery.of().withPredicates(m -> m.id().is(shoppingList.getId()));
            assertThat(client().executeBlocking(shoppingListQuery).head()).isEmpty();
        });
    }

    @Test
    public void deleteByKeyWithDataErasure() {
        withPersistentShoppingList(client(), shoppingList -> {
            client().executeBlocking(ShoppingListDeleteCommand.ofKey(shoppingList.getKey(), shoppingList.getVersion(),true));

            final ShoppingListQuery shoppingListQuery = ShoppingListQuery.of().withPredicates(m -> m.id().is(shoppingList.getId()));
            assertThat(client().executeBlocking(shoppingListQuery).head()).isEmpty();
        });
    }
}
