package io.sphere.sdk.products.expansion;

import io.sphere.sdk.products.ProductProjection;
import io.sphere.sdk.products.ProductProjectionType;
import io.sphere.sdk.products.queries.ProductProjectionByIdGet;
import io.sphere.sdk.expansion.ExpansionPath;
import io.sphere.sdk.products.queries.ProductProjectionQuery;
import io.sphere.sdk.products.search.ProductProjectionSearch;
import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;

import static io.sphere.sdk.utils.SphereInternalUtils.listOf;
import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.*;

public class ProductProjectionExpansionModelTest {
    @Test
    public void expandCategoriesDemo() throws Exception {
        final ProductProjectionByIdGet fetch1 = ProductProjectionByIdGet.of("id", ProductProjectionType.CURRENT)
                .plusExpansionPaths(m -> m.categories());
        assertThat(fetch1.expansionPaths())
                .isEqualTo(asList(ExpansionPath.of("categories[*]")));

        final ProductProjectionByIdGet fetch2 = fetch1.plusExpansionPaths(m -> m.productType());
        assertThat(fetch2.expansionPaths())
                .isEqualTo(asList(ExpansionPath.of("categories[*]"), ExpansionPath.of("productType")))
                .isEqualTo(listOf(fetch1.expansionPaths(), ExpansionPath.of("productType")));

        //this is equivalent to

        final ExpansionPath<ProductProjection> categoryExpand = ProductProjectionExpansionModel.of().categories().expansionPaths().get(0);
        final ExpansionPath<ProductProjection> productTypeExpand = ProductProjectionExpansionModel.of().productType().expansionPaths().get(0);
        final ProductProjectionByIdGet fetchB = ProductProjectionByIdGet.of("id", ProductProjectionType.CURRENT)
                        .withExpansionPaths(asList(categoryExpand, productTypeExpand));
        assertThat(fetchB.expansionPaths())
                .isEqualTo(asList(ExpansionPath.of("categories[*]"), ExpansionPath.of("productType")));
    }

    @Test
    public void plusExpansionPathDemo() throws Exception {
        final ProductProjectionByIdGet fetch = ProductProjectionByIdGet.of("id", ProductProjectionType.CURRENT);

        assertThat(fetch.expansionPaths()).isEmpty();

        final ProductProjectionByIdGet fetch2 =
                fetch.plusExpansionPaths(ProductProjectionExpansionModel.of().categories());

        assertThat(fetch.expansionPaths()).overridingErrorMessage("old object is unchanged").isEmpty();
        assertThat(fetch2.expansionPaths()).isEqualTo(asList(ExpansionPath.of("categories[*]")));
        assertThat(fetch2).isNotSameAs(fetch);
    }

    @Test
    public void withExpansionPathDemo() throws Exception {
        final ProductProjectionByIdGet fetch = ProductProjectionByIdGet.of("id", ProductProjectionType.CURRENT)
                        .withExpansionPaths(ProductProjectionExpansionModel.of().categories());

        assertThat(fetch.expansionPaths())
                .isEqualTo(asList(ExpansionPath.of("categories[*]")));

        final ProductProjectionByIdGet fetch2 =
                fetch.withExpansionPaths(ProductProjectionExpansionModel.of().productType());

        assertThat(fetch.expansionPaths()).overridingErrorMessage("old object is unchanged")
                .isEqualTo(asList(ExpansionPath.of("categories[*]")));
        assertThat(fetch2.expansionPaths()).isEqualTo(asList(ExpansionPath.of("productType")));
        assertThat(fetch2).isNotSameAs(fetch);
    }

    @Test
    public void withExpansionPathDslDemo() throws Exception {
        final ProductProjectionByIdGet fetch = ProductProjectionByIdGet.of("id", ProductProjectionType.CURRENT)
                        .plusExpansionPaths(ProductProjectionExpansionModel.of().categories());

        assertThat(fetch.expansionPaths())
                .isEqualTo(asList(ExpansionPath.of("categories[*]")));

        final ProductProjectionByIdGet fetch2 =
                fetch.withExpansionPaths(m -> m.productType());

        assertThat(fetch.expansionPaths()).overridingErrorMessage("old object is unchanged")
                .isEqualTo(asList(ExpansionPath.of("categories[*]")));
        assertThat(fetch2.expansionPaths()).isEqualTo(asList(ExpansionPath.of("productType")));
        assertThat(fetch2).isNotSameAs(fetch);
    }

    @Test
    public void variants() {
        final ExpansionPath<ProductProjection> expansionPath =
                ProductProjectionExpansionModel.of().variants().prices().customerGroup().expansionPaths().get(0);
        assertThat(expansionPath.toSphereExpand()).isEqualTo("variants[*].prices[*].customerGroup");
    }

    @Test
    public void allVariants() {
        final List<ExpansionPath<ProductProjection>> expansionPaths =
                ProductProjectionExpansionModel.of().allVariants().prices().customerGroup().expansionPaths();
        assertThat(expansionPaths.stream().map(ExpansionPath::toSphereExpand).collect(Collectors.toList()))
                .containsExactly("masterVariant.prices[*].customerGroup", "variants[*].prices[*].customerGroup");
    }

    @Test
    public void useExpansionPathsOfOtherRequest() {
        final ProductProjectionQuery query = ProductProjectionQuery.ofCurrent().withExpansionPaths(m -> m.categories());
        final ProductProjectionSearch search = ProductProjectionSearch.ofCurrent().withExpansionPaths(query);
        assertThat(query.expansionPaths())
                .hasSize(1)
                .as("reuse expansion spec from other request")
                .isEqualTo(search.expansionPaths());
    }
}