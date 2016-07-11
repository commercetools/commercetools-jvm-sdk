package io.sphere.sdk.products.search;

import io.sphere.sdk.http.NameValuePair;
import io.sphere.sdk.products.ProductProjection;
import io.sphere.sdk.products.ProductProjectionType;
import io.sphere.sdk.products.expansion.ProductProjectionExpansionModel;
import io.sphere.sdk.search.MetaModelSearchDslBuilder;
import io.sphere.sdk.search.MetaModelSearchDslImpl;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static io.sphere.sdk.products.search.PriceSelectionQueryParameters.*;
import static java.util.Collections.singletonList;

final class ProductProjectionSearchImpl extends MetaModelSearchDslImpl<ProductProjection, ProductProjectionSearch, ProductProjectionSortSearchModel,
        ProductProjectionFilterSearchModel, ProductProjectionFacetSearchModel, ProductProjectionExpansionModel<ProductProjection>> implements ProductProjectionSearch {

    ProductProjectionSearchImpl(final ProductProjectionType productProjectionType){
        super("/product-projections/search", ProductProjectionSearch.resultTypeReference(), ProductProjectionSearchModel.of().sort(),
                ProductProjectionSearchModel.of().filter(), ProductProjectionSearchModel.of().facet(), ProductProjectionExpansionModel.of(),
                ProductProjectionSearchImpl::new, additionalParametersOf(productProjectionType));
    }

    private ProductProjectionSearchImpl(final MetaModelSearchDslBuilder<ProductProjection, ProductProjectionSearch, ProductProjectionSortSearchModel,
            ProductProjectionFilterSearchModel, ProductProjectionFacetSearchModel, ProductProjectionExpansionModel<ProductProjection>> builder) {
        super(builder);
    }

    private static List<NameValuePair> additionalParametersOf(final ProductProjectionType productProjectionType) {
        return singletonList(NameValuePair.of("staged", productProjectionType.isStaged().toString()));
    }

    @Override
    public ProductProjectionSearch withPriceSelection(@Nullable final PriceSelection priceSelection) {
        final List<NameValuePair> resultingParameters = getQueryParametersWithPriceSelection(priceSelection, additionalQueryParameters());
        return withAdditionalQueryParameters(resultingParameters);
    }

    @Nullable
    @Override
    public PriceSelection getPriceSelection() {
        return extractPriceSelectionFromHttpQueryParameters(additionalQueryParameters());
    }
}
