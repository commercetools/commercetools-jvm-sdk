package io.sphere.sdk.products.queries;

import io.sphere.sdk.client.HttpRequestIntent;
import io.sphere.sdk.http.HttpMethod;
import io.sphere.sdk.products.Product;
import io.sphere.sdk.products.expansion.ProductExpansionModel;
import io.sphere.sdk.queries.MetaModelGetDslBuilder;
import io.sphere.sdk.queries.MetaModelGetDslImpl;

import static io.sphere.sdk.client.SphereRequestUtils.urlEncode;

final class ProductByKeyProductSelectionGetImpl extends MetaModelGetDslImpl<Product, Product, ProductByKeyProductSelectionGet, ProductExpansionModel<Product>> implements ProductByKeyProductSelectionGet {

    private String key;

    ProductByKeyProductSelectionGetImpl(final String key) {
        super("key=" + urlEncode(key), ProductEndpoint.ENDPOINT, ProductExpansionModel.of(), ProductByKeyProductSelectionGetImpl::new);
    }

    public ProductByKeyProductSelectionGetImpl(MetaModelGetDslBuilder<Product, Product, ProductByKeyProductSelectionGet, ProductExpansionModel<Product>> builder) {
        super(builder);
    }

    @Override
    public HttpRequestIntent httpRequestIntent() {
        return HttpRequestIntent.of(HttpMethod.GET, "/products/key=" + urlEncode(key) + "/product-selections");
    }
}
