package io.sphere.sdk.products.queries;

import io.sphere.sdk.products.ProductProjection;
import io.sphere.sdk.products.ProductProjectionType;
import io.sphere.sdk.queries.ByIdFetchImpl;
import io.sphere.sdk.http.UrlQueryBuilder;

public class ProductProjectionByIdFetch extends ByIdFetchImpl<ProductProjection> {
    private final ProductProjectionType projectionType;

    private ProductProjectionByIdFetch(final String id, final ProductProjectionType projectionType) {
        super(id, ProductProjectionsEndpoint.ENDPOINT);
        this.projectionType = projectionType;
    }

    @Override
    protected UrlQueryBuilder additionalQueryParameters() {
        final String value = projectionType == ProductProjectionType.STAGED ? "true" : "false";
        return super.additionalQueryParameters().add("staged", value, false);
    }

    public static ProductProjectionByIdFetch of(final String id, final ProductProjectionType projectionType) {
        return new ProductProjectionByIdFetch(id, projectionType);
    }
}
