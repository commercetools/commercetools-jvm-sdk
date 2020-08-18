package io.sphere.sdk.products.queries;

import io.sphere.sdk.http.NameValuePair;
import io.sphere.sdk.products.ProductProjection;
import io.sphere.sdk.products.ProductProjectionType;
import io.sphere.sdk.products.expansion.ProductProjectionExpansionModel;
import io.sphere.sdk.selection.LocaleSelection;
import io.sphere.sdk.products.search.PriceSelection;
import io.sphere.sdk.selection.StoreSelection;
import io.sphere.sdk.queries.MetaModelQueryDslBuilder;
import io.sphere.sdk.queries.MetaModelQueryDslImpl;

import javax.annotation.Nullable;
import java.util.List;

import static io.sphere.sdk.products.search.PriceSelectionQueryParameters.*;
import static io.sphere.sdk.selection.LocaleSelectionQueryParameters.*;
import static io.sphere.sdk.selection.StoreSelectionQueryParameters.*;
import static java.util.Collections.singletonList;

/**
 {@doc.gen summary product projections}
 */
final class ProductProjectionQueryImpl extends MetaModelQueryDslImpl<ProductProjection, ProductProjectionQuery, ProductProjectionQueryModel, ProductProjectionExpansionModel<ProductProjection>> implements ProductProjectionQuery {

    ProductProjectionQueryImpl(final ProductProjectionType productProjectionType){
        super(ProductProjectionEndpoint.ENDPOINT.endpoint(), ProductProjectionQuery.resultTypeReference(), ProductProjectionQueryModel.of(), ProductProjectionExpansionModel.of(), ProductProjectionQueryImpl::new, additionalParametersOf(productProjectionType));
    }

    private ProductProjectionQueryImpl(final MetaModelQueryDslBuilder<ProductProjection, ProductProjectionQuery, ProductProjectionQueryModel, ProductProjectionExpansionModel<ProductProjection>> builder) {
        super(builder);
    }

    private static List<NameValuePair> additionalParametersOf(final ProductProjectionType productProjectionType) {
        return singletonList(NameValuePair.of("staged", productProjectionType.isStaged().toString()));
    }

    @Override
    public ProductProjectionQuery withPriceSelection(@Nullable final PriceSelection priceSelection) {
        final List<NameValuePair> resultingParameters = getQueryParametersWithPriceSelection(priceSelection, additionalHttpQueryParameters());
        return withAdditionalHttpQueryParameters(resultingParameters);
    }

    @Nullable
    @Override
    public PriceSelection getPriceSelection() {
        return extractPriceSelectionFromHttpQueryParameters(additionalHttpQueryParameters());
    }

    @Override
    public ProductProjectionQuery withLocaleSelection(@Nullable final LocaleSelection localeSelection) {
        final List<NameValuePair> resultingParameters = getQueryParametersWithLocaleSelection(localeSelection, additionalHttpQueryParameters());
        return withAdditionalHttpQueryParameters(resultingParameters);
    }

    @Override
    public ProductProjectionQuery plusLocaleSelection(@Nullable final LocaleSelection localeSelection) {
        final List<NameValuePair> resultingParameters = getQueryParametersPlusLocaleSelection(localeSelection, additionalHttpQueryParameters());
        return withAdditionalHttpQueryParameters(resultingParameters);
    }

    @Nullable
    @Override
    public LocaleSelection getLocaleSelection() {
        return extractLocaleSelectionFromHttpQueryParameters(additionalHttpQueryParameters());
    }

    @Override
    public ProductProjectionQuery withStoreSelection(@Nullable final StoreSelection storeSelection) {
        final List<NameValuePair> resultingParameters = getQueryParametersWithStoreSelection(storeSelection, additionalHttpQueryParameters());
        return withAdditionalHttpQueryParameters(resultingParameters);
    }

    @Nullable
    @Override
    public StoreSelection getStoreSelection() {
        return extractStoreSelectionFromHttpQueryParameters(additionalHttpQueryParameters());
    }
}