package io.sphere.sdk.productselections.queries;

import io.sphere.sdk.client.JsonEndpoint;
import io.sphere.sdk.productselections.ProductSelection;
import io.sphere.sdk.productselections.expansion.ProductSelectionExpansionModel;

import io.sphere.sdk.queries.MetaModelGetDslBuilder;
import io.sphere.sdk.queries.MetaModelGetDslImpl;


final class ProductSelectionByIdProductsGetImpl extends MetaModelGetDslImpl<ProductSelection, ProductSelection, ProductSelectionByIdProductsGet, ProductSelectionExpansionModel<ProductSelection>> implements ProductSelectionByIdProductsGet {

    ProductSelectionByIdProductsGetImpl(final String id) {
        super(id, JsonEndpoint.of(ProductSelection.typeReference(), "/product-selections/" + id + "/products"), ProductSelectionExpansionModel.of(), ProductSelectionByIdProductsGetImpl::new);
    }

    public ProductSelectionByIdProductsGetImpl(final MetaModelGetDslBuilder<ProductSelection, ProductSelection, ProductSelectionByIdProductsGet, ProductSelectionExpansionModel<ProductSelection>> builder) {
        super(builder);
    }

}
