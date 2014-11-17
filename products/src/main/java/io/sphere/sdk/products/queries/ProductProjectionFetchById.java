package io.sphere.sdk.products.queries;

import io.sphere.sdk.products.ProductProjection;
import io.sphere.sdk.products.ProductProjectionType;
import io.sphere.sdk.queries.FetchByIdImpl;
import io.sphere.sdk.utils.UrlQueryBuilder;

public class ProductProjectionFetchById extends FetchByIdImpl<ProductProjection> {
    private final ProductProjectionType projectionType;

    private ProductProjectionFetchById(final String id, final ProductProjectionType projectionType) {
        super(id, ProductProjectionsEndpoint.ENDPOINT);
        this.projectionType = projectionType;
    }

    @Override
    protected UrlQueryBuilder additionalQueryParameters() {
        final String value = projectionType == ProductProjectionType.STAGED ? "true" : "false";
        return super.additionalQueryParameters().add("staged", value, false);
    }

    public static ProductProjectionFetchById of(final String id, final ProductProjectionType projectionType) {
        return new ProductProjectionFetchById(id, projectionType);
    }
}
