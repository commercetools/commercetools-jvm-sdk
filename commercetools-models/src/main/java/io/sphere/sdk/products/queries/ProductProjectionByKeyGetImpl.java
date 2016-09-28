package io.sphere.sdk.products.queries;

import io.sphere.sdk.http.NameValuePair;
import io.sphere.sdk.products.ProductProjection;
import io.sphere.sdk.products.ProductProjectionType;
import io.sphere.sdk.products.expansion.ProductProjectionExpansionModel;
import io.sphere.sdk.products.search.PriceSelection;
import io.sphere.sdk.queries.MetaModelGetDslBuilder;
import io.sphere.sdk.queries.MetaModelGetDslImpl;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

import static io.sphere.sdk.products.search.PriceSelectionQueryParameters.extractPriceSelectionFromHttpQueryParameters;
import static io.sphere.sdk.products.search.PriceSelectionQueryParameters.getQueryParametersWithPriceSelection;

final class ProductProjectionByKeyGetImpl extends MetaModelGetDslImpl<ProductProjection, ProductProjection, ProductProjectionByKeyGet, ProductProjectionExpansionModel<ProductProjection>> implements ProductProjectionByKeyGet {
    ProductProjectionByKeyGetImpl(final String key, final ProductProjectionType projectionType) {
        super(ProductProjectionEndpoint.ENDPOINT, "key=" + key, ProductProjectionExpansionModel.of(), ProductProjectionByKeyGetImpl::new, Collections.singletonList(NameValuePair.of("staged", projectionType.isStaged().toString())));
    }

    public ProductProjectionByKeyGetImpl(final MetaModelGetDslBuilder<ProductProjection, ProductProjection, ProductProjectionByKeyGet, ProductProjectionExpansionModel<ProductProjection>> builder) {
        super(builder);
    }

    @Override
    public ProductProjectionByKeyGet withPriceSelection(@Nullable final PriceSelection priceSelection) {
        final List<NameValuePair> resultingParameters = getQueryParametersWithPriceSelection(priceSelection, additionalQueryParameters());
        return withAdditionalQueryParameters(resultingParameters);
    }

    @Nullable
    @Override
    public PriceSelection getPriceSelection() {
        return extractPriceSelectionFromHttpQueryParameters(additionalQueryParameters());
    }
}
