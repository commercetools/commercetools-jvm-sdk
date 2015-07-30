package io.sphere.sdk.products.search;

import io.sphere.sdk.products.ProductProjection;
import io.sphere.sdk.products.ProductProjectionType;
import io.sphere.sdk.search.MetaModelSearchDslBuilder;
import io.sphere.sdk.search.MetaModelSearchDslImpl;
import io.sphere.sdk.http.HttpQueryParameter;

import java.util.List;

import static java.util.Collections.singletonList;

final class ProductProjectionSearchImpl extends MetaModelSearchDslImpl<ProductProjection, ProductProjectionSearch, ExperimentalProductProjectionSearchModel> implements ProductProjectionSearch {

    ProductProjectionSearchImpl(final ProductProjectionType productProjectionType){
        super("/product-projections/search", ProductProjectionSearch.resultTypeReference(), ExperimentalProductProjectionSearchModel.of(), ProductProjectionSearchImpl::new, additionalParametersOf(productProjectionType));
    }

    private ProductProjectionSearchImpl(final MetaModelSearchDslBuilder<ProductProjection, ProductProjectionSearch, ExperimentalProductProjectionSearchModel> builder) {
        super(builder);
    }

    private static List<HttpQueryParameter> additionalParametersOf(final ProductProjectionType productProjectionType) {
        return singletonList(HttpQueryParameter.of("staged", productProjectionType.isStaged().toString()));
    }

}
