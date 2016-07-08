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

final class ProductByIdGetImpl extends MetaModelGetDslImpl<Product, Product, ProductByIdGet, ProductExpansionModel<Product>> implements ProductByIdGet {
    ProductByIdGetImpl(final String id) {
        super(id, ProductEndpoint.ENDPOINT, ProductExpansionModel.of(), ProductByIdGetImpl::new);
    }

    public ProductByIdGetImpl(MetaModelGetDslBuilder<Product, Product, ProductByIdGet, ProductExpansionModel<Product>> builder) {
        super(builder);
    }

    @Override
    public ProductByIdGet withPriceSelection(@Nullable final PriceSelection priceSelection) {
        final List<NameValuePair> resultingParameters = getQueryParametersWithPriceSelection(priceSelection, additionalQueryParameters());
        return withAdditionalQueryParameters(resultingParameters);
    }

    @Nullable
    @Override
    public PriceSelection getPriceSelection() {
        return extractPriceSelectionFromHttpQueryParameters(additionalQueryParameters());
    }
}
