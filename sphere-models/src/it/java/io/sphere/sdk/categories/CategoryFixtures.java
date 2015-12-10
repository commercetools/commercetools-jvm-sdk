package io.sphere.sdk.categories;

import io.sphere.sdk.categories.commands.CategoryCreateCommand;
import io.sphere.sdk.categories.commands.CategoryDeleteCommand;
import io.sphere.sdk.categories.queries.CategoryQuery;
import io.sphere.sdk.client.BlockingSphereClient;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.queries.PagedQueryResult;
import io.sphere.sdk.utils.SphereInternalLogger;

import java.util.Locale;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static io.sphere.sdk.test.SphereTestUtils.*;
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

    public static void withCategory(final BlockingSphereClient client, final Supplier<CategoryDraft> creator, final Consumer<Category> user) {
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

    public static void withCategoryAndParentCategory(final BlockingSphereClient client, final BiConsumer<Category, Category> consumer) {
        withCategory(client, parent ->
            withCategory(client, CategoryDraftBuilder.of(randomSlug(), randomSlug()).parent(parent), category -> {
                consumer.accept(category, parent);
            })
        );
    }

    public static void withCategory(final BlockingSphereClient client, final Consumer<Category> consumer) {
        final LocalizedString slug = randomSlug();
        final CategoryDraftBuilder catSupplier = CategoryDraftBuilder.of(en(slug.get(ENGLISH) + " name"), slug).externalId(randomKey());
        CategoryFixtures.withCategory(client, catSupplier, consumer);
    }

    public static Category createCategory(final BlockingSphereClient client) {
        final CategoryDraft categoryDraft = CategoryDraftBuilder.of(randomSlug(), randomSlug()).build();
        return client.executeBlocking(CategoryCreateCommand.of(categoryDraft));
    }
}
