package io.sphere.sdk.categories;

import io.sphere.sdk.categories.commands.CategoryCreateCommand;
import io.sphere.sdk.categories.commands.CategoryDeleteByIdCommand;
import io.sphere.sdk.categories.queries.CategoryQuery;
import io.sphere.sdk.client.TestClient;
import io.sphere.sdk.models.LocalizedStrings;
import io.sphere.sdk.queries.PagedQueryResult;
import io.sphere.sdk.utils.SphereInternalLogger;

import java.util.Locale;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static io.sphere.sdk.test.SphereTestUtils.*;
import static java.util.Locale.ENGLISH;

public class CategoryFixtures {
    private static final SphereInternalLogger LOGGER = SphereInternalLogger.getLogger("categories.fixtures");

    public static void withCategory(final TestClient client, final Supplier<NewCategory> creator, final Consumer<Category> user) {
        final NewCategory newCategory = creator.get();
        final String slug = englishSlugOf(newCategory);
        final PagedQueryResult<Category> pagedQueryResult = client.execute(new CategoryQuery().bySlug(Locale.ENGLISH, slug));
        pagedQueryResult.head().ifPresent(category -> client.execute(new CategoryDeleteByIdCommand(category)));
        final Category category = client.execute(new CategoryCreateCommand(newCategory));
        LOGGER.debug(() -> "created category " + category.getSlug() + " id: " + category.getId());
        try {
            user.accept(category);
        } finally {
            final PagedQueryResult<Category> res = client.execute(new CategoryQuery().byId(category.getId()));
            //need to update because category could be changed
            client.execute(new CategoryDeleteByIdCommand(res.head().get()));
            LOGGER.debug(() -> "deleted category " + category.getId());
        }
    }

    public static void withCategory(final TestClient client, final Consumer<Category> consumer) {
        final LocalizedStrings slug = randomSlug();
        final NewCategoryBuilder catSupplier = NewCategoryBuilder.of(en(slug.get(ENGLISH).get() + " name"), slug);
        CategoryFixtures.withCategory(client, catSupplier, consumer);
    }
}
