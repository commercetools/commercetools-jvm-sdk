package io.sphere.sdk.shoppinglists.commands;

import io.sphere.sdk.shoppinglists.ShoppingList;
import io.sphere.sdk.shoppinglists.ShoppingListDraftDsl;
import io.sphere.sdk.shoppinglists.ShoppingListFixtures;
import io.sphere.sdk.shoppinglists.queries.ShoppingListQuery;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

import static io.sphere.sdk.test.SphereTestUtils.en;
import static org.assertj.core.api.Assertions.assertThat;

public class ShoppingListCreateCommandIntegrationTest extends IntegrationTest {
    private static final String DEMO_SHOPPING_LIST_KEY = "demo-shopping-list-key";

    @BeforeClass
    public static void clean() {
        List<ShoppingList> results = client().executeBlocking(ShoppingListQuery.of()
                .withPredicates(l -> l.key().is(DEMO_SHOPPING_LIST_KEY)))
                .getResults();
        results.forEach(shoppingList -> client().executeBlocking(ShoppingListDeleteCommand.of(shoppingList)));
    }

    @Test
    public void execution() {
        ShoppingListDraftDsl draft = ShoppingListFixtures.newShoppingListDraftBuilder()
                .key(DEMO_SHOPPING_LIST_KEY)
                .description(en("Demo shopping list description."))
                .slug(en("demo-shopping-list-slug"))
                .build();
        ShoppingList shoppingList = client().executeBlocking(ShoppingListCreateCommand.of(draft));

        assertThat(shoppingList).isNotNull();
        assertThat(shoppingList.getId()).isNotNull();
        assertThat(shoppingList.getKey()).isEqualTo(DEMO_SHOPPING_LIST_KEY);
    }
}
