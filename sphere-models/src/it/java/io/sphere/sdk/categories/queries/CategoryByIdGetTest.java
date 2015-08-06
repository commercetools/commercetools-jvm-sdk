package io.sphere.sdk.categories.queries;

import io.sphere.sdk.categories.Category;
import io.sphere.sdk.categories.CategoryFixtures;
import io.sphere.sdk.categories.expansion.CategoryExpansionModel;
import io.sphere.sdk.queries.Get;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import static org.assertj.core.api.Assertions.*;

public class CategoryByIdGetTest extends IntegrationTest {
    @Test
    public void execution() throws Exception {
        CategoryFixtures.withCategoryAndParentCategory(client(), (category, parent) -> {
            final Get<Category> fetch = CategoryByIdGet.of(category)
                    .withExpansionPaths(CategoryExpansionModel.of().parent());
            final Category loadedCategory = execute(fetch);
            assertThat(loadedCategory.getId()).isEqualTo(category.getId());
            assertThat(loadedCategory.getParent().getObj().getId()).isEqualTo(parent.getId());
        });
    }
}