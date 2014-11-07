package io.sphere.sdk.products.queries;

import io.sphere.sdk.models.Identifiable;
import io.sphere.sdk.products.ProductProjection;
import io.sphere.sdk.products.ProductProjectionType;
import io.sphere.sdk.queries.FetchByIdImpl;
import io.sphere.sdk.utils.UrlQueryBuilder;

public class ProductProjectionFetchById extends FetchByIdImpl<ProductProjection> {
    private final ProductProjectionType projectionType;

    public ProductProjectionFetchById(final Identifiable<ProductProjection> identifiable, final ProductProjectionType projectionType) {
        super(identifiable, ProductProjectionsEndpoint.ENDPOINT);
        this.projectionType = projectionType;
    }

    @Override
    protected UrlQueryBuilder additionalQueryParameters() {
        final String value = projectionType == ProductProjectionType.STAGED ? "true" : "false";
        return super.additionalQueryParameters().add("staged", value, false);
    }
}
