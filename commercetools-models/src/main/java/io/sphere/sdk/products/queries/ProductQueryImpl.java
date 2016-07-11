package io.sphere.sdk.products.queries;

import io.sphere.sdk.http.NameValuePair;
import io.sphere.sdk.products.Product;
import io.sphere.sdk.products.expansion.ProductExpansionModel;
import io.sphere.sdk.products.search.PriceSelection;
import io.sphere.sdk.queries.MetaModelQueryDslBuilder;
import io.sphere.sdk.queries.MetaModelQueryDslImpl;

import javax.annotation.Nullable;
import java.util.List;

import static io.sphere.sdk.products.search.PriceSelectionQueryParameters.extractPriceSelectionFromHttpQueryParameters;
import static io.sphere.sdk.products.search.PriceSelectionQueryParameters.getQueryParametersWithPriceSelection;

/**
 {@doc.gen summary products}
 */
final class ProductQueryImpl extends MetaModelQueryDslImpl<Product, ProductQuery, ProductQueryModel, ProductExpansionModel<Product>> implements ProductQuery {
    ProductQueryImpl(){
        super(ProductEndpoint.ENDPOINT.endpoint(), ProductQuery.resultTypeReference(), ProductQueryModel.of(), ProductExpansionModel.of(), ProductQueryImpl::new);
    }

    private ProductQueryImpl(final MetaModelQueryDslBuilder<Product, ProductQuery, ProductQueryModel, ProductExpansionModel<Product>> builder) {
        super(builder);
    }

    @Override
    public ProductQuery withPriceSelection(@Nullable final PriceSelection priceSelection) {
        final List<NameValuePair> resultingParameters = getQueryParametersWithPriceSelection(priceSelection, additionalHttpQueryParameters());
        return withAdditionalHttpQueryParameters(resultingParameters);
    }

    @Nullable
    @Override
    public PriceSelection getPriceSelection() {
        return extractPriceSelectionFromHttpQueryParameters(additionalHttpQueryParameters());
    }
}