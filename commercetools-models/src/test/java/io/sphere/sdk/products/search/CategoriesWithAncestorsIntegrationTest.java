package io.sphere.sdk.products.search;

import io.sphere.sdk.products.ProductProjection;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import java.util.HashSet;
import java.util.List;

import static io.sphere.sdk.categories.CategoryFixtures.withCategoryAndParentCategory;
import static io.sphere.sdk.products.ProductFixtures.withProduct;
import static io.sphere.sdk.test.SphereTestUtils.asList;
import static io.sphere.sdk.test.SphereTestUtils.assertEventually;
import static java.util.Collections.singleton;
import static org.assertj.core.api.Assertions.assertThat;

public class CategoriesWithAncestorsIntegrationTest extends IntegrationTest {
    @Test
    public void execute() {
        withCategoryAndParentCategory(client(), (cat, parent) -> {
            withProduct(client(), builder -> builder.categories(singleton(cat.toReference())), product -> {
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
                            .withQueryFilters(m -> m.categoriesWithAncestors().id().is(parentId));
                    final List<ProductProjection> results = client().executeBlocking(requestForTree).getResults();
                    assertThat(results).hasSize(1);
                    assertThat(results.get(0).getCategoriesWithAncestors())
                            .isEqualTo(new HashSet<>(asList(cat.toReference(), parent.toReference())));
                });
            });
        });
    }
}
