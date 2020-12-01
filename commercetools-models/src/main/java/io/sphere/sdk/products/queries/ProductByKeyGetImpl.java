package io.sphere.sdk.products.queries;

import io.sphere.sdk.http.NameValuePair;
import io.sphere.sdk.products.Product;
import io.sphere.sdk.products.expansion.ProductExpansionModel;
import io.sphere.sdk.products.search.PriceSelection;
import io.sphere.sdk.queries.MetaModelGetDslBuilder;
import io.sphere.sdk.queries.MetaModelGetDslImpl;

import javax.annotation.Nullable;
import java.util.List;

import static io.sphere.sdk.products.search.PriceSelectionQueryParameters.extractPriceSelectionFromHttpQueryParameters;
import static io.sphere.sdk.products.search.PriceSelectionQueryParameters.getQueryParametersWithPriceSelection;

import static io.sphere.sdk.client.SphereRequestUtils.urlEncode;

final class ProductByKeyGetImpl extends MetaModelGetDslImpl<Product, Product, ProductByKeyGet, ProductExpansionModel<Product>> implements ProductByKeyGet {
    ProductByKeyGetImpl(final String key) {
        super("key=" + urlEncode(key), ProductEndpoint.ENDPOINT, ProductExpansionModel.of(), ProductByKeyGetImpl::new);
    }

    public ProductByKeyGetImpl(final MetaModelGetDslBuilder<Product, Product, ProductByKeyGet, ProductExpansionModel<Product>> builder) {
        super(builder);
    }

    @Override
    public ProductByKeyGet withPriceSelection(@Nullable final PriceSelection priceSelection) {
        final List<NameValuePair> resultingParameters = getQueryParametersWithPriceSelection(priceSelection, additionalQueryParameters());
        return withAdditionalQueryParameters(resultingParameters);
    }

    @Nullable
    @Override
    public PriceSelection getPriceSelection() {
        return extractPriceSelectionFromHttpQueryParameters(additionalQueryParameters());
    }
}
