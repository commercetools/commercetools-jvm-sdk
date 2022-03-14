package io.sphere.sdk.productselections.queries;

import io.sphere.sdk.productselections.ProductSelection;
import io.sphere.sdk.productselections.expansion.ProductSelectionExpansionModel;
import io.sphere.sdk.queries.MetaModelGetDsl;

public interface ProductSelectionByIdProductsGet extends MetaModelGetDsl<ProductSelection, ProductSelection, ProductSelectionByIdProductsGet, ProductSelectionExpansionModel<ProductSelection>> {

    static ProductSelectionByIdProductsGet of(final String id) {
        return new ProductSelectionByIdProductsGetImpl(id);
    }
}
