package io.sphere.sdk.products.search;

import io.sphere.sdk.categories.Category;
import io.sphere.sdk.products.ProductProjection;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Ignore;
import org.junit.Test;

import java.util.List;

import static io.sphere.sdk.categories.CategoryFixtures.withCategoryAndParentCategory;
import static io.sphere.sdk.products.ProductFixtures.withProduct;
import static io.sphere.sdk.test.SphereTestUtils.assertEventually;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;

@Ignore
public class CategoriesWithAncestorsIntegrationTest extends IntegrationTest {
    @SuppressWarnings("unchecked")
    @Ignore
    @Test
    public void isInSubtree() {
        withCategoryAndParentCategory(client(), (Category cat, Category parent) -> {
            withProduct(client(), builder -> builder.categoriesAsObjectList(singletonList(cat)), product -> {
                final String parentId = parent.getId();
                assertThat(product.getMasterData().getStaged().getCategories())
                        .contains(cat.toReference())
                        .doesNotContain(parent.toReference());

                assertEventually(() -> {
                    //search for products directly in parent category
                    final ProductProjectionSearch requestForDirectCategories = ProductProjectionSearch.ofStaged()
                            .withQueryFilters(m -> m.categories().id().is(parentId));
                    assertThat(client().executeBlocking(requestForDirectCategories).getResults()).isEmpty();

                    //search for products in parent category or its descendants
                    final ProductProjectionSearch requestForTree = ProductProjectionSearch.ofStaged()
                            .withQueryFilters(m -> m.categories().id().isInSubtree(parentId));
                    final List<ProductProjection> results = client().executeBlocking(requestForTree).getResults();
                    assertThat(results).hasSize(1);
                });
            });
        });
    }
}
