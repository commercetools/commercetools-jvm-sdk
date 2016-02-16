package io.sphere.sdk.categories.queries;

import io.sphere.sdk.categories.Category;
import io.sphere.sdk.categories.CategoryFixtures;
import io.sphere.sdk.queries.Get;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CategoryByIdGetIntegrationTest extends IntegrationTest {
    @Test
    public void execution() throws Exception {
        CategoryFixtures.withCategoryAndParentCategory(client(), (category, parent) -> {
            final String id = category.getId();
            final Get<Category> fetch = CategoryByIdGet.of(id)
                    .withExpansionPaths(m -> m.parent());
            final Category loadedCategory = client().executeBlocking(fetch);
            assertThat(loadedCategory.getId()).isEqualTo(id);
            assertThat(loadedCategory.getParent()).is(expanded(parent));
        });
    }
}