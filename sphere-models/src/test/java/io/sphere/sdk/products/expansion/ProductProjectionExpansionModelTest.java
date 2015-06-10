package io.sphere.sdk.products.expansion;

import io.sphere.sdk.products.ProductProjection;
import io.sphere.sdk.products.ProductProjectionType;
import io.sphere.sdk.products.queries.ProductProjectionByIdFetch;
import io.sphere.sdk.queries.ExpansionPath;
import org.junit.Test;

import static io.sphere.sdk.utils.ListUtils.listOf;
import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.*;

public class ProductProjectionExpansionModelTest {
    @Test
    public void expandCategoriesDemo() throws Exception {
        final ProductProjectionByIdFetch fetch1 = ProductProjectionByIdFetch.of("id", ProductProjectionType.CURRENT)
                .plusExpansionPaths(m -> m.categories());
        assertThat(fetch1.expansionPaths())
                .isEqualTo(asList(ExpansionPath.of("categories[*]")));

        final ProductProjectionByIdFetch fetch2 = fetch1.plusExpansionPaths(m -> m.productType());
        assertThat(fetch2.expansionPaths())
                .isEqualTo(asList(ExpansionPath.of("categories[*]"), ExpansionPath.of("productType")))
                .isEqualTo(listOf(fetch1.expansionPaths(), ExpansionPath.of("productType")));

        //this is equivalent to

        final ExpansionPath<ProductProjection> categoryExpand = ProductProjectionExpansionModel.of().categories();
        final ExpansionPath<ProductProjection> productTypeExpand = ProductProjectionExpansionModel.of().productType();
        final ProductProjectionByIdFetch fetchB = ProductProjectionByIdFetch.of("id", ProductProjectionType.CURRENT)
                        .withExpansionPaths(asList(categoryExpand, productTypeExpand));
        assertThat(fetchB.expansionPaths())
                .isEqualTo(asList(ExpansionPath.of("categories[*]"), ExpansionPath.of("productType")));
    }

    @Test
    public void plusExpansionPathDemo() throws Exception {
        final ProductProjectionByIdFetch fetch = ProductProjectionByIdFetch.of("id", ProductProjectionType.CURRENT);

        assertThat(fetch.expansionPaths()).isEmpty();

        final ProductProjectionByIdFetch fetch2 =
                fetch.plusExpansionPaths(ProductProjectionExpansionModel.of().categories());

        assertThat(fetch.expansionPaths()).overridingErrorMessage("old object is unchanged").isEmpty();
        assertThat(fetch2.expansionPaths()).isEqualTo(asList(ExpansionPath.of("categories[*]")));
        assertThat(fetch2).isNotSameAs(fetch);
    }

    @Test
    public void withExpansionPathDemo() throws Exception {
        final ProductProjectionByIdFetch fetch = ProductProjectionByIdFetch.of("id", ProductProjectionType.CURRENT)
                        .withExpansionPaths(asList(ProductProjectionExpansionModel.of().categories()));

        assertThat(fetch.expansionPaths())
                .isEqualTo(asList(ExpansionPath.of("categories[*]")));

        final ProductProjectionByIdFetch fetch2 =
                fetch.withExpansionPaths(ProductProjectionExpansionModel.of().productType());

        assertThat(fetch.expansionPaths()).overridingErrorMessage("old object is unchanged")
                .isEqualTo(asList(ExpansionPath.of("categories[*]")));
        assertThat(fetch2.expansionPaths()).isEqualTo(asList(ExpansionPath.of("productType")));
        assertThat(fetch2).isNotSameAs(fetch);
    }

    @Test
    public void withExpansionPathDslDemo() throws Exception {
        final ProductProjectionByIdFetch fetch = ProductProjectionByIdFetch.of("id", ProductProjectionType.CURRENT)
                        .plusExpansionPaths(ProductProjectionExpansionModel.of().categories());

        assertThat(fetch.expansionPaths())
                .isEqualTo(asList(ExpansionPath.of("categories[*]")));

        final ProductProjectionByIdFetch fetch2 =
                fetch.withExpansionPaths(m -> m.productType());

        assertThat(fetch.expansionPaths()).overridingErrorMessage("old object is unchanged")
                .isEqualTo(asList(ExpansionPath.of("categories[*]")));
        assertThat(fetch2.expansionPaths()).isEqualTo(asList(ExpansionPath.of("productType")));
        assertThat(fetch2).isNotSameAs(fetch);
    }
}