package io.sphere.sdk.products.queries;

import io.sphere.sdk.http.NameValuePair;
import io.sphere.sdk.products.ProductProjection;
import io.sphere.sdk.products.expansion.ProductProjectionExpansionModel;
import io.sphere.sdk.products.search.PriceSelection;
import io.sphere.sdk.queries.MetaModelQueryDslBuilder;
import io.sphere.sdk.queries.MetaModelQueryDslImpl;


import javax.annotation.Nullable;

import java.util.List;

import static io.sphere.sdk.client.SphereRequestUtils.urlEncode;
import static io.sphere.sdk.products.search.PriceSelectionQueryParameters.extractPriceSelectionFromHttpQueryParameters;
import static io.sphere.sdk.products.search.PriceSelectionQueryParameters.getQueryParametersWithPriceSelection;

class ProductProjectionInStoreQueryImpl extends MetaModelQueryDslImpl<ProductProjection, ProductProjectionInStoreQuery, ProductProjectionQueryModel, ProductProjectionExpansionModel<ProductProjection>> implements ProductProjectionInStoreQuery {

    ProductProjectionInStoreQueryImpl(final String storeKey) {
        super("/in-store/key=" + urlEncode(storeKey) + "/product-projection", ProductProjectionQuery.resultTypeReference(), ProductProjectionQueryModel.of(), ProductProjectionExpansionModel.of(), ProductProjectionInStoreQueryImpl::new);
    }

    private ProductProjectionInStoreQueryImpl(final MetaModelQueryDslBuilder<ProductProjection, ProductProjectionInStoreQuery, ProductProjectionQueryModel, ProductProjectionExpansionModel<ProductProjection>> builder) {
        super(builder);
    }
}
