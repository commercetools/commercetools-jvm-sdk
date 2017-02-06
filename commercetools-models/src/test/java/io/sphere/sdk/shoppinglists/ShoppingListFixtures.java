package io.sphere.sdk.shoppinglists;

import io.sphere.sdk.cartdiscounts.CartDiscountFixtures;
import io.sphere.sdk.client.BlockingSphereClient;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.shoppinglists.commands.ShoppingListCreateCommand;
import io.sphere.sdk.shoppinglists.queries.ShoppingListQuery;
import io.sphere.sdk.shoppinglists.queries.ShoppingListQueryModel;

import java.util.Locale;
import java.util.Optional;
import java.util.function.Consumer;

import static io.sphere.sdk.test.SphereTestUtils.en;

public class ShoppingListFixtures {

    public static void withPersistentShoppingList(final BlockingSphereClient client, final Consumer<ShoppingList> consumer) {
        final LocalizedString name = en("shopping list name");
        final Optional<ShoppingList> fetchedDiscountCode = client.executeBlocking(ShoppingListQuery.of()
                .withPredicates(ShoppingListQueryModel.of().name().lang(Locale.ENGLISH).is("shopping list name"))).head();

        final ShoppingList discountCode = fetchedDiscountCode.orElseGet(() -> createShoppingList(client, "shopping list name"));
        consumer.accept(discountCode);
    }

    public static ShoppingListDraftBuilder newShoppingListDraftBuilder() {
        final LocalizedString name = en("shopping list name");
        return ShoppingListDraftBuilder.of(name);
    }

    public static ShoppingList defaultShoppingList(final BlockingSphereClient client) {
        return createShoppingList(client, CartDiscountFixtures.class.getSimpleName() + "default-4");
    }

    private static ShoppingList createShoppingList(final BlockingSphereClient client, final String name) {
        final ShoppingListDraft draft = newShoppingListDraftBuilder()
                .name(LocalizedString.ofEnglish(name))
                .build();
        return client.executeBlocking(ShoppingListCreateCommand.of(draft));
    }
}
