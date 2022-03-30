package io.sphere.sdk.productselections.queries;

import io.sphere.sdk.client.HttpRequestIntent;
import io.sphere.sdk.http.HttpMethod;
import io.sphere.sdk.productselections.ProductSelection;
import io.sphere.sdk.productselections.expansion.ProductSelectionExpansionModel;
import io.sphere.sdk.queries.MetaModelGetDslBuilder;
import io.sphere.sdk.queries.MetaModelGetDslImpl;

final class ProductSelectionByIdProductGetImpl extends MetaModelGetDslImpl<ProductSelection, ProductSelection, ProductSelectionByIdProductGet, ProductSelectionExpansionModel<ProductSelection>> implements ProductSelectionByIdProductGet {

    private String id;

    ProductSelectionByIdProductGetImpl(final String id) {
        super(id, ProductSelectionEndpoint.ENDPOINT, ProductSelectionExpansionModel.of(), ProductSelectionByIdProductGetImpl::new);
        this.id = id;
    }

    public ProductSelectionByIdProductGetImpl(MetaModelGetDslBuilder<ProductSelection, ProductSelection, ProductSelectionByIdProductGet, ProductSelectionExpansionModel<ProductSelection>> builder) {
        super(builder);
    }

    @Override
    public HttpRequestIntent httpRequestIntent() {
        return HttpRequestIntent.of(HttpMethod.GET, String.format("/product-selections/%s/products", id));
    }
}
