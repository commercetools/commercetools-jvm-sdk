package io.sphere.sdk.shoppinglists.commands;

import io.sphere.sdk.discountcodes.commands.DiscountCodeDeleteCommand;
import io.sphere.sdk.discountcodes.queries.DiscountCodeQuery;
import io.sphere.sdk.shoppinglists.ShoppingList;
import io.sphere.sdk.shoppinglists.ShoppingListDraftDsl;
import io.sphere.sdk.shoppinglists.ShoppingListFixtures;
import io.sphere.sdk.shoppinglists.queries.ShoppingListQuery;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Locale;

import static io.sphere.sdk.test.SphereTestUtils.en;
import static org.assertj.core.api.Assertions.assertThat;

public class ShoppingListCreateCommandIntegrationTest extends IntegrationTest
{

    @Test
    public void execution()
    {
        ShoppingListDraftDsl draft = ShoppingListFixtures.newShoppingListDraftBuilder().note(en("demo-shopping-list")).build();
        ShoppingList shoppingList = client().executeBlocking(ShoppingListCreateCommand.of(draft));

        assertThat(shoppingList).isNotNull();
    }
}
