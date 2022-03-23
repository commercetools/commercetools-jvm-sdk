package io.sphere.sdk.products.queries;

import io.sphere.sdk.client.HttpRequestIntent;
import io.sphere.sdk.http.HttpMethod;
import io.sphere.sdk.http.NameValuePair;
import io.sphere.sdk.json.SphereJsonUtils;
import io.sphere.sdk.productdiscounts.queries.MatchingProductDiscountGet;
import io.sphere.sdk.products.Product;
import io.sphere.sdk.products.expansion.ProductExpansionModel;
import io.sphere.sdk.products.search.PriceSelection;
import io.sphere.sdk.queries.MetaModelGetDslBuilder;
import io.sphere.sdk.queries.MetaModelGetDslImpl;

import javax.annotation.Nullable;
import java.util.List;

import static io.sphere.sdk.http.HttpMethod.POST;
import static io.sphere.sdk.products.search.PriceSelectionQueryParameters.extractPriceSelectionFromHttpQueryParameters;
import static io.sphere.sdk.products.search.PriceSelectionQueryParameters.getQueryParametersWithPriceSelection;

final class ProductByIdProductSelectionGetImpl extends MetaModelGetDslImpl<Product, Product, ProductByIdProductSelectionGet, ProductExpansionModel<Product>> implements ProductByIdProductSelectionGet {

    private String id;

    ProductByIdProductSelectionGetImpl(final String id) {
        super(id, ProductEndpoint.ENDPOINT, ProductExpansionModel.of(), ProductByIdProductSelectionGetImpl::new);
        this.id = id;
    }

    public ProductByIdProductSelectionGetImpl(MetaModelGetDslBuilder<Product, Product, ProductByIdProductSelectionGet, ProductExpansionModel<Product>> builder) {
        super(builder);
    }

    @Override
    public HttpRequestIntent httpRequestIntent() {
        return HttpRequestIntent.of(HttpMethod.GET, String.format("/products/%s/product-selections", id));
    }
}
