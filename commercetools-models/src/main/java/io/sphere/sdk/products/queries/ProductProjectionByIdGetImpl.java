package io.sphere.sdk.products.queries;

import io.sphere.sdk.http.NameValuePair;
import io.sphere.sdk.products.ProductProjection;
import io.sphere.sdk.products.ProductProjectionType;
import io.sphere.sdk.products.expansion.ProductProjectionExpansionModel;
import io.sphere.sdk.products.search.PriceSelection;
import io.sphere.sdk.queries.MetaModelGetDslBuilder;
import io.sphere.sdk.queries.MetaModelGetDslImpl;
import io.sphere.sdk.stores.Store;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

import static io.sphere.sdk.products.queries.ProductProjectionQueryParameters.*;
import static io.sphere.sdk.products.search.PriceSelectionQueryParameters.extractPriceSelectionFromHttpQueryParameters;
import static io.sphere.sdk.products.search.PriceSelectionQueryParameters.getQueryParametersWithPriceSelection;

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

    @Override
    public ProductProjectionByIdGet withStoreProjection(@Nullable final Store storeProjection) {
        final List<NameValuePair> resultingParameters = getQueryParametersWithStoreProjection(storeProjection, additionalQueryParameters());
        return withAdditionalQueryParameters(resultingParameters);
    }

    @Override
    public ProductProjectionByIdGet withLocaleProjection(@Nullable final List<String> localeProjection) {
        final List<NameValuePair> resultingParameters = getQueryParametersWithLocaleProjection(localeProjection, additionalQueryParameters());
        return withAdditionalQueryParameters(resultingParameters);
    }

    @Nullable
    @Override
    public PriceSelection getPriceSelection() {
        return extractPriceSelectionFromHttpQueryParameters(additionalQueryParameters());
    }

    @Nullable
    @Override
    public ProductProjectionQuery getStoreProjection() {
        return extractStoreProjectionFromHttpQueryParameters(additionalQueryParameters());
    }

    @Nullable
    @Override
    public ProductProjectionQuery getLocaleProjection() {
        return extractLocaleProjectionFromHttpQueryParameters(additionalQueryParameters());
    }
}
