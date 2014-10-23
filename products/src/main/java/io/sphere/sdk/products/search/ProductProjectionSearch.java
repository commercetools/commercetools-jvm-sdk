package io.sphere.sdk.products.search;

import com.fasterxml.jackson.core.type.TypeReference;
import io.sphere.sdk.products.ProductProjection;
import io.sphere.sdk.products.ProductProjectionType;
import io.sphere.sdk.search.PagedSearchResult;
import io.sphere.sdk.search.SearchDslImpl;
import io.sphere.sdk.queries.QueryParameter;

import java.util.Locale;

import static java.util.Arrays.asList;

public class ProductProjectionSearch extends SearchDslImpl<ProductProjection> {

    private static final TypeReference<PagedSearchResult<ProductProjection>> TYPE_REFERENCE =
            new TypeReference<PagedSearchResult<ProductProjection>>() {
        @Override
        public String toString() {
            return "TypeReference<PagedSearchResult<ProductProjection>>";
        }
    };

    public ProductProjectionSearch(final ProductProjectionType type, final Locale lang) {
        super("/product-projections/search", TYPE_REFERENCE, asList(QueryParameter.of("staged", type.isStaged().toString())), lang);
    }
}
