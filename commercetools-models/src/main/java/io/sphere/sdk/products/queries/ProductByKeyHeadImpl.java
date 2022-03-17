package io.sphere.sdk.products.queries;

import io.sphere.sdk.http.NameValuePair;
import io.sphere.sdk.products.Product;
import io.sphere.sdk.products.expansion.ProductExpansionModel;
import io.sphere.sdk.products.search.PriceSelection;
import io.sphere.sdk.queries.MetaModelGetDslImpl;
import io.sphere.sdk.queries.MetaModelHeadDsl;
import io.sphere.sdk.queries.MetaModelHeadDslBuilder;
import io.sphere.sdk.queries.MetaModelHeadDslImpl;

import javax.annotation.Nullable;
import java.util.List;

import static io.sphere.sdk.client.SphereRequestUtils.urlEncode;
import static io.sphere.sdk.products.search.PriceSelectionQueryParameters.extractPriceSelectionFromHttpQueryParameters;
import static io.sphere.sdk.products.search.PriceSelectionQueryParameters.getQueryParametersWithPriceSelection;

final class ProductByKeyHeadImpl extends MetaModelHeadDslImpl<Product, Product, ProductByKeyHead, ProductExpansionModel<Product>> implements ProductByKeyHead {
    ProductByKeyHeadImpl(final String key) {
        super("key=" + urlEncode(key), ProductEndpoint.ENDPOINT, ProductExpansionModel.of(), ProductByKeyHeadImpl::new);
    }

    public ProductByKeyHeadImpl(final MetaModelHeadDslBuilder<Product, Product, ProductByKeyHead, ProductExpansionModel<Product>> builder) {
        super(builder);
    }

    @Override
    public ProductByKeyHead withPriceSelection(@Nullable final PriceSelection priceSelection) {
        final List<NameValuePair> resultingParameters = getQueryParametersWithPriceSelection(priceSelection, additionalQueryParameters());
        return withAdditionalQueryParameters(resultingParameters);
    }

    @Nullable
    @Override
    public PriceSelection getPriceSelection() {
        return extractPriceSelectionFromHttpQueryParameters(additionalQueryParameters());
    }
}
