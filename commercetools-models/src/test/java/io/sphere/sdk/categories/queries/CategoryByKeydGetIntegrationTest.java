package io.sphere.sdk.categories.queries;

import io.sphere.sdk.categories.Category;
import io.sphere.sdk.categories.CategoryFixtures;
import io.sphere.sdk.queries.Get;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CategoryByKeydGetIntegrationTest extends IntegrationTest {
    @Test
    public void execution() throws Exception {
        CategoryFixtures.withCategoryAndParentCategory(client(), (category, parent) -> {
            final String key = category.getKey();
            final Get<Category> fetch = CategoryByKeyGet.of(key)
                    .withExpansionPaths(m -> m.parent());
            final Category loadedCategory = client().executeBlocking(fetch);
            assertThat(loadedCategory.getKey()).isEqualTo(key);
            assertThat(loadedCategory.getParent()).is(expanded(parent));
        });
    }
}