package io.sphere.sdk.productselections.queries;

import io.sphere.sdk.productselections.ProductSelection;
import io.sphere.sdk.productselections.expansion.ProductSelectionExpansionModel;
import io.sphere.sdk.queries.MetaModelGetDsl;

public interface ProductSelectionByKeyProductsGet extends MetaModelGetDsl<ProductSelection, ProductSelection, ProductSelectionByKeyProductsGet, ProductSelectionExpansionModel<ProductSelection>> {

    static ProductSelectionByKeyProductsGet of(final String id) {
        return new ProductSelectionByKeyProductsGetImpl(id);
    }
}
