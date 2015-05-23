package io.sphere.sdk.categories;

import io.sphere.sdk.categories.commands.CategoryCreateCommand;
import io.sphere.sdk.categories.commands.CategoryDeleteCommand;
import io.sphere.sdk.categories.queries.CategoryQuery;
import io.sphere.sdk.client.TestClient;
import io.sphere.sdk.models.LocalizedStrings;
import io.sphere.sdk.queries.PagedQueryResult;
import io.sphere.sdk.utils.SphereInternalLogger;

import java.util.Locale;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static io.sphere.sdk.test.SphereTestUtils.*;
import static java.util.Locale.ENGLISH;

public class CategoryFixtures {
    private static final SphereInternalLogger LOGGER = SphereInternalLogger.getLogger("categories.fixtures");

    public static void withPersistentCategory(final TestClient client, final Consumer<Category> user) {
        final String externalId = "persistent-category-id";
        final Optional<Category> fetchedCategory = client.execute(CategoryQuery.of().byExternalId(externalId)).head();
        final Category category = fetchedCategory.orElseGet(() -> {
            final LocalizedStrings name = en("name persistent-category-id");
            final CategoryDraftBuilder catSupplier = CategoryDraftBuilder.of(name, name.slugified()).externalId(externalId);
            return client.execute(CategoryCreateCommand.of(catSupplier.build()));
        });
        user.accept(category);
    }

    public static void withCategory(final TestClient client, final Supplier<CategoryDraft> creator, final Consumer<Category> user) {
        final CategoryDraft categoryDraft = creator.get();
        final String slug = englishSlugOf(categoryDraft);
        final PagedQueryResult<Category> pagedQueryResult = client.execute(CategoryQuery.of().bySlug(Locale.ENGLISH, slug));
        pagedQueryResult.head().ifPresent(category -> client.execute(CategoryDeleteCommand.of(category)));
        final Category category = client.execute(CategoryCreateCommand.of(categoryDraft));
        LOGGER.debug(() -> "created category " + category.getSlug() + " id: " + category.getId());
        try {
            user.accept(category);
        } finally {
            final PagedQueryResult<Category> res = client.execute(CategoryQuery.of().byId(category.getId()));
            //need to update because category could be changed
            client.execute(CategoryDeleteCommand.of(res.head().get()));
            LOGGER.debug(() -> "deleted category " + category.getId());
        }
    }

    public static void withCategory(final TestClient client, final Consumer<Category> consumer) {
        final LocalizedStrings slug = randomSlug();
        final CategoryDraftBuilder catSupplier = CategoryDraftBuilder.of(en(slug.get(ENGLISH).get() + " name"), slug).externalId(randomKey());
        CategoryFixtures.withCategory(client, catSupplier, consumer);
    }
}
