package io.sphere.sdk.categories.queries;

import io.sphere.sdk.categories.Category;
import io.sphere.sdk.categories.CategoryFixtures;
import io.sphere.sdk.categories.expansion.CategoryExpansionModel;
import io.sphere.sdk.queries.Fetch;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import static org.assertj.core.api.Assertions.*;

public class CategoryByIdFetchTest extends IntegrationTest {
    @Test
    public void execution() throws Exception {
        CategoryFixtures.withCategoryAndParentCategory(client(), (category, parent) -> {
            final Fetch<Category> fetch = CategoryByIdFetch.of(category)
                    .withExpansionPath(CategoryExpansionModel.of().parent());
            final Category loadedCategory = execute(fetch).get();
            assertThat(loadedCategory.getId()).isEqualTo(category.getId());
            assertThat(loadedCategory.getParent().get().getObj().get().getId()).isEqualTo(parent.getId());
        });
    }
}