package io.sphere.sdk.categories;

import io.sphere.sdk.categories.commands.CategoryCreateCommand;
import io.sphere.sdk.categories.commands.CategoryDeleteCommand;
import io.sphere.sdk.categories.queries.CategoryQuery;
import io.sphere.sdk.client.BlockingSphereClient;
import io.sphere.sdk.models.*;
import io.sphere.sdk.queries.PagedQueryResult;
import io.sphere.sdk.utils.SphereInternalLogger;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static io.sphere.sdk.test.SphereTestUtils.*;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static java.util.Locale.ENGLISH;

public class CategoryFixtures {
    private static final SphereInternalLogger LOGGER = SphereInternalLogger.getLogger("categories.fixtures");

    public static void withPersistentCategory(final BlockingSphereClient client, final Consumer<Category> user) {
        final String externalId = "persistent-category-id";
        final Optional<Category> fetchedCategory = client.executeBlocking(CategoryQuery.of().byExternalId(externalId)).head();
        final Category category = fetchedCategory.orElseGet(() -> {
            final LocalizedString name = en("name persistent-category-id");
            final CategoryDraftBuilder catSupplier = CategoryDraftBuilder.of(name, name.slugified()).externalId(externalId);
            return client.executeBlocking(CategoryCreateCommand.of(catSupplier.build()));
        });
        user.accept(category);
    }

    public static void withCategory(final BlockingSphereClient client, final Supplier<? extends CategoryDraft> creator, final Consumer<Category> user) {
        final CategoryDraft categoryDraft = creator.get();
        final String slug = englishSlugOf(categoryDraft);
        final PagedQueryResult<Category> pagedQueryResult = client.executeBlocking(CategoryQuery.of().bySlug(Locale.ENGLISH, slug));
        pagedQueryResult.head().ifPresent(category -> client.executeBlocking(CategoryDeleteCommand.of(category)));
        final Category category = client.executeBlocking(CategoryCreateCommand.of(categoryDraft));
        LOGGER.debug(() -> "created category " + category.getSlug() + " id: " + category.getId());
        try {
            user.accept(category);
        } finally {
            final PagedQueryResult<Category> res = client.executeBlocking(CategoryQuery.of().byId(category.getId()));
            //need to update because category could be changed
            client.executeBlocking(CategoryDeleteCommand.of(res.head().get()));
            LOGGER.debug(() -> "deleted category " + category.getId());
        }
    }

    public static void withCategories(final BlockingSphereClient client, final List<Supplier<? extends CategoryDraft>> creator, final Consumer<List<Category>> user) {

        List<Category> categories = creator.stream().map(singleCreator -> {
            final CategoryDraft categoryDraft = singleCreator.get();
            final String slug = englishSlugOf(categoryDraft);
            final PagedQueryResult<Category> pagedQueryResult = client.executeBlocking(CategoryQuery.of().bySlug(Locale.ENGLISH, slug));
            pagedQueryResult.head().ifPresent(
                    category -> client.executeBlocking(CategoryDeleteCommand.of(category))
            );
            final Category category = client.executeBlocking(CategoryCreateCommand.of(categoryDraft));
            return category;
        }).collect(Collectors.toList());
        try {
            user.accept(categories);
        } finally {

            categories.forEach(category -> {
                client.executeBlocking(CategoryDeleteCommand.of(category));
            });
        }
    }

    public static void withCategoryAndParentCategory(final BlockingSphereClient client, final BiConsumer<Category, Category> consumer) {
        withCategory(client, parent ->
            withCategory(client, CategoryDraftBuilder.of(randomSlug(), randomSlug()).key(randomKey()).parent(parent), category -> {
                consumer.accept(category, parent);
            })
        );
    }

    public static void withCategory(final BlockingSphereClient client, final Consumer<Category> consumer) {
        final CategoryDraftBuilder catSupplier = categorySupplier();
        CategoryFixtures.withCategory(client, catSupplier, consumer);
    }

    public static void withCategoryHavingAssets(final BlockingSphereClient client, final Consumer<Category> consumer) {
        final CategoryDraftBuilder catSupplier = categorySupplier();
        catSupplier.assets(asList(getAssetDraft1(), getAssetDraft2()));
        CategoryFixtures.withCategory(client, catSupplier, consumer);
    }

    public static Category createCategory(final BlockingSphereClient client) {
        final CategoryDraft categoryDraft = CategoryDraftBuilder.of(randomSlug(), randomSlug()).build();
        return client.executeBlocking(CategoryCreateCommand.of(categoryDraft));
    }

    public static void deleteAll(final BlockingSphereClient client) {
        delete(client, CategoryQuery.of().byIsRoot());
        delete(client, CategoryQuery.of());
    }

    private static void delete(final BlockingSphereClient client, final CategoryQuery categoryQuery) {
        client.executeBlocking(categoryQuery.withLimit(500)).getResults().forEach(cat -> {
            client.executeBlocking(CategoryDeleteCommand.of(cat));
        });
    }

    private static CategoryDraftBuilder categorySupplier() {
        final LocalizedString slug = randomSlug();
        return CategoryDraftBuilder.of(en(slug.get(ENGLISH) + " name"), slug).externalId(randomKey());
    }

    private static AssetDraft getAssetDraft1() {
        final AssetSource assetSource1 = AssetSourceBuilder.ofUri("https://commercetools.com/binaries/content/gallery/commercetoolswebsite/homepage/cases/rewe.jpg")
                .key(randomKey())
                .contentType("image/jpg")
                .dimensionsOfWidthAndHeight(1934, 1115)
                .build();
        final LocalizedString name = LocalizedString.ofEnglish("REWE show case");
        final LocalizedString description = LocalizedString.ofEnglish("screenshot of the REWE webshop on a mobile and a notebook");
        return AssetDraftBuilder.of(singletonList(assetSource1), name)
                .description(description)
                .key(randomKey())
                .tags("desktop-sized", "jpg-format", "REWE", "awesome")
                .build();
    }

    private static AssetDraft getAssetDraft2() {
        final AssetSource assetSource1 = AssetSourceBuilder.ofUri("https://docs.commercetools.com/assets/img/CT-logo.svg")
                .key(randomKey())
                .contentType("image/svg+xml")
                .build();
        final LocalizedString name = LocalizedString.ofEnglish("commercetools logo");
        return AssetDraftBuilder.of(singletonList(assetSource1), name)
                .tags("desktop-sized", "svg-format", "commercetools", "awesome")
                .key(randomKey())
                .build();
    }
}
