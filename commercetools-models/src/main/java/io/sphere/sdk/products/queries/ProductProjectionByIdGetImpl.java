package io.sphere.sdk.products.queries;

import io.sphere.sdk.http.NameValuePair;
import io.sphere.sdk.products.ProductProjection;
import io.sphere.sdk.products.ProductProjectionType;
import io.sphere.sdk.products.expansion.ProductProjectionExpansionModel;
import io.sphere.sdk.selection.LocaleSelection;
import io.sphere.sdk.products.search.PriceSelection;
import io.sphere.sdk.selection.StoreSelection;
import io.sphere.sdk.queries.MetaModelGetDslBuilder;
import io.sphere.sdk.queries.MetaModelGetDslImpl;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

import static io.sphere.sdk.products.search.PriceSelectionQueryParameters.extractPriceSelectionFromHttpQueryParameters;
import static io.sphere.sdk.products.search.PriceSelectionQueryParameters.getQueryParametersWithPriceSelection;
import static io.sphere.sdk.selection.LocaleSelectionQueryParameters.*;
import static io.sphere.sdk.selection.StoreSelectionQueryParameters.*;

final class ProductProjectionByIdGetImpl extends MetaModelGetDslImpl<ProductProjection, ProductProjection, ProductProjectionByIdGet, ProductProjectionExpansionModel<ProductProjection>> implements ProductProjectionByIdGet {
    ProductProjectionByIdGetImpl(final String id, final ProductProjectionType projectionType) {
        super(ProductProjectionEndpoint.ENDPOINT, id, ProductProjectionExpansionModel.of(), ProductProjectionByIdGetImpl::new, Collections.singletonList(NameValuePair.of("staged", projectionType.isStaged().toString())));
    }

    public ProductProjectionByIdGetImpl(MetaModelGetDslBuilder<ProductProjection, ProductProjection, ProductProjectionByIdGet, ProductProjectionExpansionModel<ProductProjection>> builder) {
        super(builder);
    }

    @Override
    public ProductProjectionByIdGet withPriceSelection(@Nullable final PriceSelection priceSelection) {
        final List<NameValuePair> resultingParameters = getQueryParametersWithPriceSelection(priceSelection, additionalQueryParameters());
        return withAdditionalQueryParameters(resultingParameters);
    }

    @Nullable
    @Override
    public PriceSelection getPriceSelection() {
        return extractPriceSelectionFromHttpQueryParameters(additionalQueryParameters());
    }

    @Override
    public ProductProjectionByIdGet withLocaleSelection(@Nullable final LocaleSelection localeSelection) {
        final List<NameValuePair> resultingParameters = getQueryParametersWithLocaleSelection(localeSelection, additionalQueryParameters());
        return withAdditionalQueryParameters(resultingParameters);
    }

    @Nullable
    @Override
    public LocaleSelection getLocaleSelection() {
        return extractLocaleSelectionFromHttpQueryParameters(additionalQueryParameters());
    }

    @Override
    public ProductProjectionByIdGet withStoreSelection(@Nullable final StoreSelection storeSelection) {
        final List<NameValuePair> resultingParameters = getQueryParametersWithStoreSelection(storeSelection, additionalQueryParameters());
        return withAdditionalQueryParameters(resultingParameters);
    }

    @Nullable
    @Override
    public StoreSelection getStoreSelection() {
        return extractStoreSelectionFromHttpQueryParameters(additionalQueryParameters());
    }
}
