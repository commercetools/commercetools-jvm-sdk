package io.sphere.sdk.products.queries;

import io.sphere.sdk.http.HttpQueryParameter;
import io.sphere.sdk.products.ProductProjection;
import io.sphere.sdk.products.ProductProjectionType;
import io.sphere.sdk.products.expansion.ProductProjectionExpansionModel;
import io.sphere.sdk.queries.MetaModelQueryDslBuilder;
import io.sphere.sdk.queries.MetaModelQueryDslImpl;

import java.util.List;

import static java.util.Arrays.asList;

/**
 {@doc.gen summary product projections}
 */
final class ProductProjectionQueryImpl extends MetaModelQueryDslImpl<ProductProjection, ProductProjectionQuery, ProductProjectionQueryModel<ProductProjection>, ProductProjectionExpansionModel<ProductProjection>> implements ProductProjectionQuery {
    ProductProjectionQueryImpl(final ProductProjectionType productProjectionType){
        super(ProductProjectionEndpoint.ENDPOINT.endpoint(), ProductProjectionQuery.resultTypeReference(), ProductProjectionQueryModel.of(), ProductProjectionExpansionModel.of(), ProductProjectionQueryImpl::new, additionalParametersOf(productProjectionType));
    }

    private ProductProjectionQueryImpl(final MetaModelQueryDslBuilder<ProductProjection, ProductProjectionQuery, ProductProjectionQueryModel<ProductProjection>, ProductProjectionExpansionModel<ProductProjection>> builder) {
        super(builder);
    }

    private static List<HttpQueryParameter> additionalParametersOf(final ProductProjectionType productProjectionType) {
        return asList(HttpQueryParameter.of("staged", "" + ProductProjectionType.STAGED.isStaged()));
    }

}