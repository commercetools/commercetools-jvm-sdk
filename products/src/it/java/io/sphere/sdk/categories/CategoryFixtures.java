package io.sphere.sdk.categories;

import io.sphere.sdk.categories.*;
import io.sphere.sdk.categories.commands.CategoryCreateCommand;
import io.sphere.sdk.categories.commands.CategoryDeleteByIdCommand;
import io.sphere.sdk.categories.queries.CategoryQuery;
import io.sphere.sdk.client.TestClient;
import io.sphere.sdk.queries.PagedQueryResult;
import io.sphere.sdk.utils.SphereInternalLogger;

import java.util.Locale;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static io.sphere.sdk.test.SphereTestUtils.*;

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
            client.execute(new CategoryDeleteByIdCommand(category));
            LOGGER.debug(() -> "deleted category " + category.getId());
        }
    }
}
