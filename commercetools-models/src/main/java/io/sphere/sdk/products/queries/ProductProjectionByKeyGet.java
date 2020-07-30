package io.sphere.sdk.products.queries;

import io.sphere.sdk.expansion.ExpansionPath;
import io.sphere.sdk.products.ProductProjection;
import io.sphere.sdk.products.ProductProjectionType;
import io.sphere.sdk.products.expansion.ProductProjectionExpansionModel;
import io.sphere.sdk.products.search.PriceSelectionRequestDsl;
import io.sphere.sdk.queries.MetaModelGetDsl;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Retrieves a product projection by a known key.
 *
 * {@include.example io.sphere.sdk.products.queries.ProductProjectionByKeyGetIntegrationTest#execution()}
 */
public interface ProductProjectionByKeyGet extends MetaModelGetDsl<ProductProjection, ProductProjection, ProductProjectionByKeyGet, ProductProjectionExpansionModel<ProductProjection>>, PriceSelectionRequestDsl<ProductProjectionByKeyGet> {

    static ProductProjectionByKeyGet ofStaged(final String key) {
        return of(key, ProductProjectionType.STAGED);
    }

    static ProductProjectionByKeyGet ofCurrent(final String key) {
        return of(key, ProductProjectionType.CURRENT);
    }

    static ProductProjectionByKeyGet of(final String key, final ProductProjectionType projectionType) {
        return new ProductProjectionByKeyGetImpl(key, projectionType);
    }

    @Override
    List<ExpansionPath<ProductProjection>> expansionPaths();

    @Override
    ProductProjectionByKeyGet plusExpansionPaths(final ExpansionPath<ProductProjection> expansionPath);

    @Override
    ProductProjectionByKeyGet withExpansionPaths(final ExpansionPath<ProductProjection> expansionPath);

    @Override
    ProductProjectionByKeyGet withExpansionPaths(final List<ExpansionPath<ProductProjection>> expansionPaths);

    @Nullable
    String getStoreProjection();

    @Nullable
    List<String> getLocaleProjection();
}
