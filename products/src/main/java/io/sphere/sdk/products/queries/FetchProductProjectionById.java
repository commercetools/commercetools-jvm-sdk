package io.sphere.sdk.products.queries;

import com.fasterxml.jackson.core.type.TypeReference;
import io.sphere.sdk.models.Identifiable;
import io.sphere.sdk.products.ProductProjection;
import io.sphere.sdk.products.ProductProjectionType;
import io.sphere.sdk.queries.FetchImpl;
import io.sphere.sdk.utils.UrlQueryBuilder;

public class FetchProductProjectionById extends FetchImpl<ProductProjection> {
    private final ProductProjectionType projectionType;

    public FetchProductProjectionById(final Identifiable<ProductProjection> identifiable, final ProductProjectionType projectionType) {
        super(identifiable);
        this.projectionType = projectionType;
    }

    @Override
    protected TypeReference<ProductProjection> typeReference() {
        return ProductProjection.typeReference();
    }

    @Override
    protected String baseEndpointWithoutId() {
        return "/product-projections";
    }

    @Override
    protected UrlQueryBuilder additionalQueryParameters() {
        final String value = projectionType == ProductProjectionType.STAGED ? "true" : "false";
        return super.additionalQueryParameters().add("staged", value, false);
    }
}
