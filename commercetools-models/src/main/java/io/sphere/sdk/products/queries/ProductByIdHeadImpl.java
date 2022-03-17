package io.sphere.sdk.products.queries;

import io.sphere.sdk.http.NameValuePair;
import io.sphere.sdk.products.Product;
import io.sphere.sdk.products.expansion.ProductExpansionModel;
import io.sphere.sdk.products.search.PriceSelection;
import io.sphere.sdk.queries.MetaModelHeadDslBuilder;
import io.sphere.sdk.queries.MetaModelHeadDslImpl;

import javax.annotation.Nullable;
import java.util.List;

import static io.sphere.sdk.products.search.PriceSelectionQueryParameters.extractPriceSelectionFromHttpQueryParameters;
import static io.sphere.sdk.products.search.PriceSelectionQueryParameters.getQueryParametersWithPriceSelection;

final class ProductByIdHeadImpl extends MetaModelHeadDslImpl<Product, Product, ProductByIdHead, ProductExpansionModel<Product>> implements ProductByIdHead {
    ProductByIdHeadImpl(final String id) {
        super(id, ProductEndpoint.ENDPOINT, ProductExpansionModel.of(), ProductByIdHeadImpl::new);
    }

    public ProductByIdHeadImpl(MetaModelHeadDslBuilder<Product, Product, ProductByIdHead, ProductExpansionModel<Product>> builder) {
        super(builder);
    }

    @Override
    public ProductByIdHead withPriceSelection(@Nullable final PriceSelection priceSelection) {
        final List<NameValuePair> resultingParameters = getQueryParametersWithPriceSelection(priceSelection, additionalQueryParameters());
        return withAdditionalQueryParameters(resultingParameters);
    }

    @Nullable
    @Override
    public PriceSelection getPriceSelection() {
        return extractPriceSelectionFromHttpQueryParameters(additionalQueryParameters());
    }
}
