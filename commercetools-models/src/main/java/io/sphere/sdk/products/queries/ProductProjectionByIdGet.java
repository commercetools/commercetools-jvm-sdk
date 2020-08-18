package io.sphere.sdk.products.queries;

import io.sphere.sdk.expansion.ExpansionPath;
import io.sphere.sdk.products.ProductIdentifiable;
import io.sphere.sdk.products.ProductProjection;
import io.sphere.sdk.products.ProductProjectionType;
import io.sphere.sdk.products.expansion.ProductProjectionExpansionModel;
import io.sphere.sdk.selection.LocaleSelectionRequestDsl;
import io.sphere.sdk.products.search.PriceSelectionRequestDsl;
import io.sphere.sdk.selection.StoreSelectionRequestDsl;
import io.sphere.sdk.queries.MetaModelGetDsl;

import java.util.List;

public interface ProductProjectionByIdGet extends MetaModelGetDsl<ProductProjection, ProductProjection, ProductProjectionByIdGet, ProductProjectionExpansionModel<ProductProjection>>, PriceSelectionRequestDsl<ProductProjectionByIdGet>, LocaleSelectionRequestDsl<ProductProjectionByIdGet>, StoreSelectionRequestDsl<ProductProjectionByIdGet> {

    static ProductProjectionByIdGet of(final ProductIdentifiable product, final ProductProjectionType projectionType) {
        return of(product.getId(), projectionType);
    }

    static ProductProjectionByIdGet ofCurrent(final ProductIdentifiable product) {
        return of(product.getId(), ProductProjectionType.CURRENT);
    }

    static ProductProjectionByIdGet ofStaged(final ProductIdentifiable product) {
        return of(product.getId(), ProductProjectionType.STAGED);
    }

    static ProductProjectionByIdGet of(final String id, final ProductProjectionType projectionType) {
        return new ProductProjectionByIdGetImpl(id, projectionType);
    }

    static ProductProjectionByIdGet ofStaged(final String id) {
        return of(id, ProductProjectionType.STAGED);
    }

    static ProductProjectionByIdGet ofCurrent(final String id) {
        return of(id, ProductProjectionType.CURRENT);
    }

    @Override
    List<ExpansionPath<ProductProjection>> expansionPaths();

    @Override
    ProductProjectionByIdGet plusExpansionPaths(final ExpansionPath<ProductProjection> expansionPath);

    @Override
    ProductProjectionByIdGet withExpansionPaths(final ExpansionPath<ProductProjection> expansionPath);

    @Override
    ProductProjectionByIdGet withExpansionPaths(final List<ExpansionPath<ProductProjection>> expansionPaths);
}
