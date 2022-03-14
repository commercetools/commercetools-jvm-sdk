package io.sphere.sdk.productselections.queries;

import io.sphere.sdk.client.JsonEndpoint;
import io.sphere.sdk.productselections.ProductSelection;
import io.sphere.sdk.productselections.expansion.ProductSelectionExpansionModel;
import io.sphere.sdk.queries.MetaModelGetDslBuilder;
import io.sphere.sdk.queries.MetaModelGetDslImpl;

import static io.sphere.sdk.client.SphereRequestUtils.urlEncode;


final class ProductSelectionByKeyProductsGetImpl extends MetaModelGetDslImpl<ProductSelection, ProductSelection, ProductSelectionByKeyProductsGet, ProductSelectionExpansionModel<ProductSelection>> implements ProductSelectionByKeyProductsGet {

    ProductSelectionByKeyProductsGetImpl(final String key) {
        super(key, JsonEndpoint.of(ProductSelection.typeReference(), "/product-selections/" + "key=" + urlEncode(key) + "/products"), ProductSelectionExpansionModel.of(), ProductSelectionByKeyProductsGetImpl::new);
    }

    public ProductSelectionByKeyProductsGetImpl(final MetaModelGetDslBuilder<ProductSelection, ProductSelection, ProductSelectionByKeyProductsGet, ProductSelectionExpansionModel<ProductSelection>> builder) {
        super(builder);
    }
}
