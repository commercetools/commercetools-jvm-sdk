package io.sphere.sdk.products.search;

import io.sphere.sdk.http.NameValuePair;
import io.sphere.sdk.products.ProductProjection;
import io.sphere.sdk.products.ProductProjectionType;
import io.sphere.sdk.products.expansion.ProductProjectionExpansionModel;
import io.sphere.sdk.search.MetaModelSearchDslBuilder;
import io.sphere.sdk.search.MetaModelSearchDslImpl;

import javax.annotation.Nullable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toList;

final class ProductProjectionSearchImpl extends MetaModelSearchDslImpl<ProductProjection, ProductProjectionSearch, ProductProjectionSortSearchModel,
        ProductProjectionFilterSearchModel, ProductProjectionFacetSearchModel, ProductProjectionExpansionModel<ProductProjection>> implements ProductProjectionSearch {

    private static final String PRICE_CURRENCY = "priceCurrency";
    private static final String PRICE_COUNTRY = "priceCountry";
    private static final String PRICE_CUSTOMER_GROUP = "priceCustomerGroup";
    private static final String PRICE_CHANNEL = "priceChannel";
    private static final List<String> priceSelectionParameterNames = asList(PRICE_CURRENCY, PRICE_COUNTRY, PRICE_CUSTOMER_GROUP, PRICE_CHANNEL);

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
        final List<NameValuePair> currentParameters = additionalQueryParameters();
        final List<NameValuePair> currentParametersWithoutPriceSelectionParameters = currentParameters.stream()
                .filter(pair -> !priceSelectionParameterNames.contains(pair.getName()))
                .collect(toList());
        final List<NameValuePair> resultingParameters = new LinkedList<>(currentParametersWithoutPriceSelectionParameters);
        if (priceSelection != null && priceSelection.getPriceCurrency() != null) {
            addParamIfNotNull(resultingParameters, PRICE_CURRENCY, priceSelection.getPriceCurrency());
            addParamIfNotNull(resultingParameters, PRICE_COUNTRY, priceSelection.getPriceCountry());
            addParamIfNotNull(resultingParameters, PRICE_CUSTOMER_GROUP, priceSelection.getPriceCustomerGroup());
            addParamIfNotNull(resultingParameters, PRICE_CHANNEL, priceSelection.getPriceChannel());
        }
        return withAdditionalQueryParameters(resultingParameters);
    }

    private void addParamIfNotNull(final List<NameValuePair> resultingParameters, final String name, final String value) {
        if (value != null) {
            resultingParameters.add(NameValuePair.of(name, value));
        }
    }

    @Nullable
    @Override
    public PriceSelection getPriceSelection() {
        final List<NameValuePair> priceSelectionCandidates = additionalQueryParameters()
                .stream()
                .filter(pair -> priceSelectionParameterNames.contains(pair.getName()))
                .collect(Collectors.toList());
        final boolean containsPriceSelection = priceSelectionCandidates.stream()
                .anyMatch(pair -> PRICE_CURRENCY.equals(pair.getName()));
        return containsPriceSelection ? extractPriceSelection(priceSelectionCandidates) : null;
    }

    private PriceSelection extractPriceSelection(final List<NameValuePair> priceSelectionCandidates) {
        final Map<String, String> map = NameValuePair.convertToStringMap(priceSelectionCandidates);
        return PriceSelectionBuilder.ofCurrencyCode(map.get(PRICE_CURRENCY))
                .priceCountryCode(map.get(PRICE_COUNTRY))
                .priceCustomerGroupId(map.get(PRICE_CUSTOMER_GROUP))
                .priceChannelId(map.get(PRICE_CHANNEL))
                .build();
    }
}
