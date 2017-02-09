package io.sphere.sdk.shoppinglists;

import io.sphere.sdk.client.BlockingSphereClient;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.products.Product;
import io.sphere.sdk.shoppinglists.commands.ShoppingListCreateCommand;
import io.sphere.sdk.shoppinglists.commands.ShoppingListDeleteCommand;
import io.sphere.sdk.shoppinglists.queries.ShoppingListQuery;
import io.sphere.sdk.shoppinglists.queries.ShoppingListQueryModel;

import java.util.Locale;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

import static io.sphere.sdk.products.ProductFixtures.withTaxedProduct;
import static io.sphere.sdk.test.SphereTestUtils.*;

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

    public static void withShoppingList(final BlockingSphereClient client, final ShoppingListDraft draft, final Function<ShoppingList, ShoppingList> f){
        final ShoppingList shoppingList = client.executeBlocking(ShoppingListCreateCommand.of(draft));
        final ShoppingList possiblyUpdatedShoppingList = f.apply(shoppingList);
        client.executeBlocking(ShoppingListDeleteCommand.of(possiblyUpdatedShoppingList));
    }

    public static void withUpdateableShoppingList(final BlockingSphereClient client, final Function<ShoppingList, ShoppingList> f){
        final ShoppingListDraft draft = newShoppingListDraftBuilder()
                .name(en(randomString()))
                .description(en(randomString()))
                .key(randomKey())
                .slug(randomSlug()).build();
        final ShoppingList shoppingList = client.executeBlocking(ShoppingListCreateCommand.of(draft));
        final ShoppingList possiblyUpdatedShoppingList = f.apply(shoppingList);
        client.executeBlocking(ShoppingListDeleteCommand.of(possiblyUpdatedShoppingList));
    }

    public static void withShoppingListAndTaxedProduct(final BlockingSphereClient client, final BiFunction<ShoppingList, Product, ShoppingList> f) {
        withTaxedProduct(client, product -> {
            final ShoppingList shoppingList = createShoppingList(client, randomString());

            final ShoppingList shoppingListToDelete = f.apply(shoppingList, product);

            client.executeBlocking(ShoppingListDeleteCommand.of(shoppingListToDelete));
        });
    }

    private static ShoppingList createShoppingList(final BlockingSphereClient client, final String name) {
        final ShoppingListDraft draft = newShoppingListDraftBuilder()
                .name(LocalizedString.ofEnglish(name))
                .build();
        return client.executeBlocking(ShoppingListCreateCommand.of(draft));
    }
}
