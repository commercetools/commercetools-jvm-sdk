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

final class ProductsHeadImpl extends MetaModelHeadDslImpl<Product, Product, ProductsHead, ProductExpansionModel<Product>> implements ProductsHead {
    ProductsHeadImpl() {
        super(null, ProductEndpoint.ENDPOINT, ProductExpansionModel.of(), ProductsHeadImpl::new);
    }

    public ProductsHeadImpl(MetaModelHeadDslBuilder<Product, Product, ProductsHead, ProductExpansionModel<Product>> builder) {
        super(builder);
    }

    @Override
    public ProductsHead withPriceSelection(@Nullable final PriceSelection priceSelection) {
        final List<NameValuePair> resultingParameters = getQueryParametersWithPriceSelection(priceSelection, additionalQueryParameters());
        return withAdditionalQueryParameters(resultingParameters);
    }

    @Nullable
    @Override
    public PriceSelection getPriceSelection() {
        return extractPriceSelectionFromHttpQueryParameters(additionalQueryParameters());
    }
}
