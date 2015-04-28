package io.sphere.sdk.products.search;

import com.fasterxml.jackson.core.type.TypeReference;
import io.sphere.sdk.products.ProductProjection;
import io.sphere.sdk.products.ProductProjectionType;
import io.sphere.sdk.search.PagedSearchResult;
import io.sphere.sdk.search.SearchDslImpl;
import io.sphere.sdk.queries.QueryParameter;

import static java.util.Arrays.asList;

/**
 * Searches for products.
 *
 * Consult the documentation for <a href="{@docRoot}/io/sphere/sdk/meta/ProductSearchDocumentation.html">Product Search API</a> for more information.
 */
public class ProductProjectionSearch extends SearchDslImpl<ProductProjection> {

    private static final TypeReference<PagedSearchResult<ProductProjection>> TYPE_REFERENCE =
            new TypeReference<PagedSearchResult<ProductProjection>>() {
        @Override
        public String toString() {
            return "TypeReference<PagedSearchResult<ProductProjection>>";
        }
    };

    private ProductProjectionSearch(final ProductProjectionType type) {
        super("/product-projections/search", TYPE_REFERENCE, asList(QueryParameter.of("staged", type.isStaged().toString())));
    }

    public static ProductProjectionSearch ofStaged() {
        return of(ProductProjectionType.STAGED);
    }

    public static ProductProjectionSearch ofCurrent() {
        return of(ProductProjectionType.CURRENT);
    }

    public static ProductProjectionSearch of(final ProductProjectionType type) {
        return new ProductProjectionSearch(type);
    }

    /**
     * Gets the EXPERIMENTAL model to easily build product projection search requests.
     * Being it experimental, it can be modified in future releases therefore introducing breaking changes.
     * @return the experimental model for product projection search requests.
     */
    public static ExperimentalProductProjectionSearchModel model() {
        return ExperimentalProductProjectionSearchModel.get();
    }
}
