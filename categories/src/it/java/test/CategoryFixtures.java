package test;

import io.sphere.sdk.categories.Category;
import io.sphere.sdk.categories.CategoryCreateCommand;
import io.sphere.sdk.categories.CategoryDeleteByIdCommand;
import io.sphere.sdk.categories.NewCategory;
import io.sphere.sdk.client.TestClient;
import io.sphere.sdk.queries.PagedQueryResult;
import io.sphere.sdk.utils.SphereInternalLogger;

import java.util.Locale;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class CategoryFixtures {
    private static final SphereInternalLogger LOGGER = SphereInternalLogger.getLogger("categories.fixtures");

    public static void withCategory(final TestClient client, final Supplier<NewCategory> creator, final Consumer<Category> categoryUser) {
        final NewCategory newCategory = creator.get();
        final String slug = newCategory.getSlug().get(Locale.ENGLISH).get();
        final PagedQueryResult<Category> pagedQueryResult = client.execute(Category.query().bySlug(Locale.ENGLISH, slug));
        pagedQueryResult.head().ifPresent(category -> client.execute(new CategoryDeleteByIdCommand(category)));
        final Category category = client.execute(new CategoryCreateCommand(newCategory));
        LOGGER.debug(() -> "created category " + category.getSlug() + " id: " + category.getId());
        try {
            categoryUser.accept(category);
        } finally {
            client.execute(new CategoryDeleteByIdCommand(category));
            LOGGER.debug(() -> "deleted category " + category.getId());
        }
    }
}
