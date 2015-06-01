package io.sphere.sdk.products.queries;

import io.sphere.sdk.http.HttpQueryParameter;
import io.sphere.sdk.products.ProductProjection;
import io.sphere.sdk.products.ProductProjectionType;
import io.sphere.sdk.queries.ByIdFetchImpl;

import static java.util.Arrays.asList;

public class ProductProjectionByIdFetch extends ByIdFetchImpl<ProductProjection> {
    private ProductProjectionByIdFetch(final String id, final ProductProjectionType projectionType) {
        super(ProductProjectionsEndpoint.ENDPOINT, id, asList(HttpQueryParameter.of("staged", projectionType.isStaged().toString())));
    }

    public static ProductProjectionByIdFetch of(final String id, final ProductProjectionType projectionType) {
        return new ProductProjectionByIdFetch(id, projectionType);
    }
}
