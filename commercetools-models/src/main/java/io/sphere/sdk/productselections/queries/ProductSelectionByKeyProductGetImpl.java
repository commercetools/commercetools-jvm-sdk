package io.sphere.sdk.productselections.queries;

import io.sphere.sdk.client.HttpRequestIntent;
import io.sphere.sdk.http.HttpMethod;
import io.sphere.sdk.productselections.ProductSelection;
import io.sphere.sdk.productselections.expansion.ProductSelectionExpansionModel;
import io.sphere.sdk.queries.MetaModelGetDslBuilder;
import io.sphere.sdk.queries.MetaModelGetDslImpl;

import static io.sphere.sdk.client.SphereRequestUtils.urlEncode;

final class ProductSelectionByKeyProductGetImpl extends MetaModelGetDslImpl<ProductSelection, ProductSelection, ProductSelectionByKeyProductGet, ProductSelectionExpansionModel<ProductSelection>> implements ProductSelectionByKeyProductGet {

    private String key;

    ProductSelectionByKeyProductGetImpl(final String key) {
        super("key=" + urlEncode(key), ProductSelectionEndpoint.ENDPOINT, ProductSelectionExpansionModel.of(), ProductSelectionByKeyProductGetImpl::new);
    }

    public ProductSelectionByKeyProductGetImpl(MetaModelGetDslBuilder<ProductSelection, ProductSelection, ProductSelectionByKeyProductGet, ProductSelectionExpansionModel<ProductSelection>> builder) {
        super(builder);
    }

    @Override
    public HttpRequestIntent httpRequestIntent() {
        return HttpRequestIntent.of(HttpMethod.GET, "/product-selections/key=" + urlEncode(key) + "/products");
    }
}
