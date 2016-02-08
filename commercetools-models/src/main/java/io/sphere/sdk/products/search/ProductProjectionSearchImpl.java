package io.sphere.sdk.products.search;

import io.sphere.sdk.products.ProductProjection;
import io.sphere.sdk.products.ProductProjectionType;
import io.sphere.sdk.products.expansion.ProductProjectionExpansionModel;
import io.sphere.sdk.search.MetaModelSearchDslBuilder;
import io.sphere.sdk.search.MetaModelSearchDslImpl;
import io.sphere.sdk.http.NameValuePair;

import java.util.List;

import static java.util.Collections.singletonList;

final class ProductProjectionSearchImpl extends MetaModelSearchDslImpl<ProductProjection, ProductProjectionSearch, ProductProjectionSortSearchModel,
        ProductProjectionFilterSearchModel, ProductProjectionFacetSearchModel, ProductProjectionExpansionModel<ProductProjection>> implements ProductProjectionSearch {

    ProductProjectionSearchImpl(final ProductProjectionType productProjectionType){
        super("/product-projections/search", ProductProjectionSearch.resultTypeReference(), ProductProjectionSearchModel.of().sort(),
                ProductProjectionSearchModel.of().filter(), ProductProjectionSearchModel.of().facet(), ProductProjectionExpansionModel.of(),
                ProductProjectionSearchImpl::new, additionalParametersOf(productProjectionType));
    }

    private ProductProjectionSearchImpl(final MetaModelSearchDslBuilder<ProductProjection, ProductProjectionSearch, ProductProjectionSortSearchModel,
            ProductProjectionFilterSearchModel, ProductProjectionFacetSearchModel, ProductProjectionExpansionModel<ProductProjection>> builder) {
        super(builder);
    }

    private static List<NameValuePair> additionalParametersOf(final ProductProjectionType productProjectionType) {
        return singletonList(NameValuePair.of("staged", productProjectionType.isStaged().toString()));
    }
}
