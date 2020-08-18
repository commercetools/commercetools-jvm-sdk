package io.sphere.sdk.products.search;

import io.sphere.sdk.http.NameValuePair;
import io.sphere.sdk.products.ProductProjection;
import io.sphere.sdk.products.ProductProjectionType;
import io.sphere.sdk.products.expansion.ProductProjectionExpansionModel;
import io.sphere.sdk.products.queries.ProductProjectionQuery;
import io.sphere.sdk.search.MetaModelSearchDslBuilder;
import io.sphere.sdk.search.MetaModelSearchDslImpl;
import io.sphere.sdk.selection.LocaleSelection;
import io.sphere.sdk.selection.StoreSelection;

import javax.annotation.Nullable;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static io.sphere.sdk.products.search.PriceSelectionQueryParameters.extractPriceSelectionFromHttpQueryParameters;
import static io.sphere.sdk.products.search.PriceSelectionQueryParameters.getQueryParametersWithPriceSelection;
import static io.sphere.sdk.selection.LocaleSelectionQueryParameters.*;
import static io.sphere.sdk.selection.StoreSelectionQueryParameters.*;
import static java.util.Collections.singletonList;

final class ProductProjectionSearchImpl extends MetaModelSearchDslImpl<ProductProjection, ProductProjectionSearch, ProductProjectionSortSearchModel,
        ProductProjectionFilterSearchModel, ProductProjectionFacetSearchModel, ProductProjectionExpansionModel<ProductProjection>> implements ProductProjectionSearch {

    private static final String MARK_MATCHING_VARIANTS = "markMatchingVariants";

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

    @Override
    public ProductProjectionSearch withMarkingMatchingVariants(final Boolean markMatchingVariants) {
        final Stream<NameValuePair> oldQueryParametersStream = additionalQueryParameters().stream()
                .filter(p -> !MARK_MATCHING_VARIANTS.equals(p.getName()));
        final Stream<NameValuePair> parameter = markMatchingVariants == null
                ? Stream.empty()
                : Stream.of(NameValuePair.of(MARK_MATCHING_VARIANTS, markMatchingVariants.toString()));
        final List<NameValuePair> parameters = Stream.concat(oldQueryParametersStream, parameter).collect(Collectors.toList());
        return withAdditionalQueryParameters(parameters);
    }

    @Override
    public ProductProjectionSearch withLocaleSelection(@Nullable final LocaleSelection localeSelection) {
        final List<NameValuePair> resultingParameters = getQueryParametersWithLocaleSelection(localeSelection, additionalQueryParameters());
        return withAdditionalQueryParameters(resultingParameters);
    }

    @Override
    public ProductProjectionSearch plusLocaleSelection(@Nullable final LocaleSelection localeSelection) {
        final List<NameValuePair> resultingParameters = getQueryParametersPlusLocaleSelection(localeSelection, additionalQueryParameters());
        return withAdditionalQueryParameters(resultingParameters);
    }

    @Override
    public ProductProjectionSearch withStoreSelection(@Nullable StoreSelection storeSelection) {
        final List<NameValuePair> resultingParameters = getQueryParametersWithStoreSelection(storeSelection, additionalQueryParameters());
        return withAdditionalQueryParameters(resultingParameters);
    }

    @Nullable
    @Override
    public Boolean isMarkingMatchingVariants() {
        return additionalQueryParameters().stream()
                .filter(p -> MARK_MATCHING_VARIANTS.equals(p.getName()))
                .map(p -> Boolean.parseBoolean(p.getValue()))
                .findFirst()
                .orElse(null);
    }

    @Nullable
    @Override
    public PriceSelection getPriceSelection() {
        return extractPriceSelectionFromHttpQueryParameters(additionalQueryParameters());
    }

    @Nullable
    @Override
    public LocaleSelection getLocaleSelection() {
        return extractLocaleSelectionFromHttpQueryParameters(additionalQueryParameters());
    }

    @Nullable
    @Override
    public StoreSelection getStoreSelection() {
        return extractStoreSelectionFromHttpQueryParameters(additionalQueryParameters());
    }
}
