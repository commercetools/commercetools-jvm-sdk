package io.sphere.sdk.shoppinglists;

import io.sphere.sdk.client.BlockingSphereClient;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.products.Product;
import io.sphere.sdk.shoppinglists.commands.ShoppingListCreateCommand;
import io.sphere.sdk.shoppinglists.commands.ShoppingListDeleteCommand;
import io.sphere.sdk.shoppinglists.queries.ShoppingListQuery;
import io.sphere.sdk.shoppinglists.queries.ShoppingListQueryModel;
import io.sphere.sdk.stores.StoreFixtures;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

import static io.sphere.sdk.products.ProductFixtures.withTaxedProduct;
import static io.sphere.sdk.test.SphereTestUtils.*;

public class ShoppingListFixtures {

    public static void withPersistentShoppingList(final BlockingSphereClient client, final Consumer<ShoppingList> consumer) {
        final String name = randomString();
        final Optional<ShoppingList> fetchedShoppingList = client.executeBlocking(ShoppingListQuery.of()
                .withPredicates(ShoppingListQueryModel.of().name().lang(Locale.ENGLISH).is(name))).head();

        final ShoppingList shoppingList = fetchedShoppingList.orElseGet(() -> createShoppingList(client, name));
        consumer.accept(shoppingList);
    }

    public static ShoppingListDraftBuilder newShoppingListDraftBuilder() {
        final LocalizedString name = en(randomString());
        return ShoppingListDraftBuilder.of(name);
    }

    public static ShoppingListDraftDsl newShoppingListDraftWithLineItem(final Product product, final Long quantity) {
        final List<LineItemDraft> lineItemDrafts = asList(
                LineItemDraftBuilder.of(product.getId()).quantity(quantity).build());

        final ShoppingListDraftDsl shoppingListDraft = newShoppingListDraftBuilder().lineItems(lineItemDrafts).build();
        return shoppingListDraft;
    }

    public static ShoppingListDraftDsl newShoppingListDraftWithTextLineItem(final Long quantity) {
        final List<TextLineItemDraft> textLineItemDrafts = asList(
                TextLineItemDraftBuilder.of(en(randomString()), quantity).build());

        final ShoppingListDraftDsl shoppingListDraft = newShoppingListDraftBuilder().textLineItems(textLineItemDrafts).build();
        return shoppingListDraft;
    }


    public static ShoppingListDraftDsl newShoppingListDraftWithTextLineItems() {
        final List<TextLineItemDraft> textLineItemDrafts = asList(
                TextLineItemDraftBuilder.of(en(randomString()), 1L).build(),
                TextLineItemDraftBuilder.of(en(randomString()), 2L).build(),
                TextLineItemDraftBuilder.of(en(randomString()), 3L).build());

        final ShoppingListDraftDsl shoppingListDraft = newShoppingListDraftBuilder().textLineItems(textLineItemDrafts).build();
        return shoppingListDraft;
    }
    public static ShoppingListDraftDsl newShoppingListDraftWithLineItems(final Product product) {
        final List<LineItemDraft> lineItemDrafts = asList(
                LineItemDraftBuilder.of(product.getId()).quantity(1L).build(),
                LineItemDraftBuilder.of(product.getId()).quantity(2L).build(),
                LineItemDraftBuilder.of(product.getId()).quantity(3L).build());
        final ShoppingListDraftDsl shoppingListDraft = newShoppingListDraftBuilder().lineItems(lineItemDrafts).build();
        return shoppingListDraft;
    }

    public static void withShoppingList(final BlockingSphereClient client, final Function<ShoppingList, ShoppingList> f) {
        final ShoppingList shoppingList = createShoppingList(client, randomString());
        final ShoppingList possiblyUpdatedShoppingList = f.apply(shoppingList);
        client.executeBlocking(ShoppingListDeleteCommand.of(possiblyUpdatedShoppingList));
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
                .key(randomKey())
                .build();
        return client.executeBlocking(ShoppingListCreateCommand.of(draft));
    }

    public static void withUpdateableShoppingListInStore(final BlockingSphereClient client, final Function<ShoppingList, ShoppingList> f) {
        StoreFixtures.withStore(client, store -> {
            final ShoppingListDraft draft = newShoppingListDraftBuilder()
                    .name(en(randomString()))
                    .description(en(randomString()))
                    .key(randomKey())
                    .slug(randomSlug())
                    .store(store.toResourceIdentifier())
                    .build();
            final ShoppingList shoppingList = client.executeBlocking(ShoppingListCreateCommand.of(draft));
            final ShoppingList possiblyUpdatedShoppingList = f.apply(shoppingList);
            client.executeBlocking(ShoppingListDeleteCommand.of(possiblyUpdatedShoppingList));
        });
    }

    public static void withShoppingListInStore(final BlockingSphereClient client, final Function<ShoppingList, ShoppingList> f) {
        StoreFixtures.withStore(client, store -> {
            final ShoppingListDraft draft = newShoppingListDraftBuilder()
                    .name(en(randomString()))
                    .description(en(randomString()))
                    .key(randomKey())
                    .slug(randomSlug())
                    .store(store.toResourceIdentifier())
                    .build();
            final ShoppingList shoppingList = client.executeBlocking(ShoppingListCreateCommand.of(draft));

            final ShoppingList shoppingListToDelete = f.apply(shoppingList);

            client.executeBlocking(ShoppingListDeleteCommand.of(shoppingListToDelete));
        });
    }
}
