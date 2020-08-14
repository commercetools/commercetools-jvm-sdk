package io.sphere.sdk.products.search;

import com.fasterxml.jackson.core.type.TypeReference;
import io.sphere.sdk.expansion.ExpansionPath;
import io.sphere.sdk.expansion.ExpansionPathContainer;
import io.sphere.sdk.models.LocalizedStringEntry;
import io.sphere.sdk.products.ProductProjection;
import io.sphere.sdk.products.ProductProjectionType;
import io.sphere.sdk.products.ProductVariant;
import io.sphere.sdk.products.expansion.ProductProjectionExpansionModel;
import io.sphere.sdk.search.*;
import io.sphere.sdk.selection.LocaleSelection;
import io.sphere.sdk.selection.LocaleSelectionRequestDsl;
import io.sphere.sdk.selection.StoreSelection;
import io.sphere.sdk.selection.StoreSelectionRequestDsl;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Locale;
import java.util.function.Function;

/**
 * Searches for products.
 *
 * Consult the documentation for <a href="{@docRoot}/io/sphere/sdk/meta/SearchDocumentation.html">Product Search API</a> for more information.
 */
public interface ProductProjectionSearch extends MetaModelSearchDsl<ProductProjection, ProductProjectionSearch, ProductProjectionSortSearchModel,
        ProductProjectionFilterSearchModel, ProductProjectionFacetSearchModel, ProductProjectionExpansionModel<ProductProjection>>, PriceSelectionRequestDsl<ProductProjectionSearch>, LocaleSelectionRequestDsl<ProductProjectionSearch>, StoreSelectionRequestDsl<ProductProjectionSearch> {

    /**
     * Creates a container which contains the full Java type information to deserialize the query result (NOT this class) from JSON.
     *
     * @see io.sphere.sdk.json.SphereJsonUtils#readObject(byte[], TypeReference)
     * @see io.sphere.sdk.json.SphereJsonUtils#readObject(String, TypeReference)
     * @see io.sphere.sdk.json.SphereJsonUtils#readObject(com.fasterxml.jackson.databind.JsonNode, TypeReference)
     * @see io.sphere.sdk.json.SphereJsonUtils#readObjectFromResource(String, TypeReference)
     *
     * @return type reference
     */
    static TypeReference<PagedSearchResult<ProductProjection>> resultTypeReference() {
        return new TypeReference<PagedSearchResult<ProductProjection>>(){
            @Override
            public String toString() {
                return "TypeReference<PagedSearchResult<ProductProjection>>";
            }
        };
    }

    /**
     * Creates a search request object for the staged data of a product.
     * @return search request for staged data
     */
    static ProductProjectionSearch ofStaged() {
        return of(ProductProjectionType.STAGED);
    }

    /**
     * Creates a search request object for the current data of a product.
     * @return search request for current data
     */
    static ProductProjectionSearch ofCurrent() {
        return of(ProductProjectionType.CURRENT);
    }

    /**
     * Creates a search request object with the product projection type specified by {@code productProjectionType}.
     * @param productProjectionType the desired projection type for the search results
     * @see #ofStaged()
     * @see #ofCurrent()
     * @return search request for current data
     */
    static ProductProjectionSearch of(final ProductProjectionType productProjectionType) {
        return new ProductProjectionSearchImpl(productProjectionType);
    }

    default ProductProjectionSearch bySlug(final Locale locale, final String slug) {
        return withQueryFilters(m -> m.slug().locale(locale).is(slug));
    }
    
    default ProductProjectionSearch bySku(final String sku) {
        return withQueryFilters(m -> m.allVariants().sku().is(sku));
    }
    
    default ProductProjectionSearch bySku(final List<String> skus) {
        return withQueryFilters(m -> m.allVariants().sku().isIn(skus));
    }

    @Override
    ProductProjectionSearch withText(final LocalizedStringEntry text);

    @Override
    ProductProjectionSearch withText(final Locale locale, final String text);

    @Override
    ProductProjectionSearch withFuzzy(final Boolean fuzzy);

    @Override
    ProductProjectionSearch withFuzzyLevel(final Integer fuzzyLevel);

    @Override
    ProductProjectionSearch withFacets(final List<FacetExpression<ProductProjection>> facets);

    @Override
    ProductProjectionSearch plusFacets(final Function<ProductProjectionFacetSearchModel, FacetExpression<ProductProjection>> m);

    @Override
    ProductProjectionSearch plusResultFilters(final List<FilterExpression<ProductProjection>> filterExpressions);

    @Override
    ProductProjectionSearch plusResultFilters(final Function<ProductProjectionFilterSearchModel, List<FilterExpression<ProductProjection>>> m);

    @Override
    ProductProjectionSearch plusQueryFilters(final List<FilterExpression<ProductProjection>> filterExpressions);

    @Override
    ProductProjectionSearch plusQueryFilters(final Function<ProductProjectionFilterSearchModel, List<FilterExpression<ProductProjection>>> m);

    @Override
    ProductProjectionSearch plusFacetFilters(final List<FilterExpression<ProductProjection>> filterExpressions);

    @Override
    ProductProjectionSearch plusFacetFilters(final Function<ProductProjectionFilterSearchModel, List<FilterExpression<ProductProjection>>> m);

    @Override
    ProductProjectionSearch plusSort(final List<SortExpression<ProductProjection>> sortExpressions);

    @Override
    ProductProjectionSearch plusSort(final SortExpression<ProductProjection> sortExpression);

    @Override
    ProductProjectionSearch plusSort(final Function<ProductProjectionSortSearchModel, SortExpression<ProductProjection>> m);

    @Override
    ProductProjectionSearch withLimit(final Long limit);

    @Override
    ProductProjectionSearch withOffset(final Long offset);

    @Override
    ProductProjectionSearch plusExpansionPaths(final List<ExpansionPath<ProductProjection>> expansionPaths);

    @Override
    ProductProjectionSearch plusExpansionPaths(final ExpansionPath<ProductProjection> expansionPath);

    @Override
    ProductProjectionSearch plusFacetedSearch(final FacetedSearchExpression<ProductProjection> facetedSearchExpression);

    @Override
    ProductProjectionSearch plusFacetedSearch(final List<FacetedSearchExpression<ProductProjection>> facetedSearchExpressions);

    @Override
    ProductProjectionSearch withFacetedSearch(final FacetedSearchExpression<ProductProjection> facetedSearchExpression);

    @Override
    ProductProjectionSearch withFacetedSearch(final List<FacetedSearchExpression<ProductProjection>> facetedSearchExpressions);

    @Override
    ProductProjectionSearch withQueryFilters(final List<FilterExpression<ProductProjection>> filterExpressions);

    @Override
    ProductProjectionSearch withResultFilters(final List<FilterExpression<ProductProjection>> filterExpressions);

    @Override
    ProductProjectionSearch plusExpansionPaths(final Function<ProductProjectionExpansionModel<ProductProjection>, ExpansionPathContainer<ProductProjection>> m);

    @Override
    ProductProjectionSearch withExpansionPaths(final Function<ProductProjectionExpansionModel<ProductProjection>, ExpansionPathContainer<ProductProjection>> m);

    @Override
    ProductProjectionSearch withFacets(final Function<ProductProjectionFacetSearchModel, FacetExpression<ProductProjection>> m);

    @Override
    ProductProjectionSearch withFacetFilters(final Function<ProductProjectionFilterSearchModel, List<FilterExpression<ProductProjection>>> m);

    @Override
    ProductProjectionSearch withQueryFilters(final Function<ProductProjectionFilterSearchModel, List<FilterExpression<ProductProjection>>> m);

    @Override
    ProductProjectionSearch withResultFilters(final Function<ProductProjectionFilterSearchModel, List<FilterExpression<ProductProjection>>> m);

    @Override
    ProductProjectionSearch plusFacets(final FacetExpression<ProductProjection> facetExpression);

    @Override
    ProductProjectionSearch plusFacets(final List<FacetExpression<ProductProjection>> facetExpressions);

    @Override
    ProductProjectionSearch withFacets(final FacetExpression<ProductProjection> facetExpression);

    @Override
    ProductProjectionSearch withSort(final Function<ProductProjectionSortSearchModel, SortExpression<ProductProjection>> m);

    @Override
    ProductProjectionSearch withFacetFilters(final List<FilterExpression<ProductProjection>> filterExpressions);

    @Override
    ProductProjectionSearch withExpansionPaths(final ExpansionPath<ProductProjection> expansionPath);

    @Override
    ProductProjectionSearch withExpansionPaths(final List<ExpansionPath<ProductProjection>> expansionPaths);

    @Override
    ProductProjectionSearch withSort(final SortExpression<ProductProjection> sortExpression);

    @Override
    ProductProjectionSearch withSort(final List<SortExpression<ProductProjection>> sortExpressions);

    /**
     * Uses the cart price selection logic to retrieve the prices for product variants.
     *
     * <p>Example for selecting just a currency</p>
     *
     * {@include.example io.sphere.sdk.products.search.PriceSelectionIntegrationTest#selectAPriceByCurrency()}
     *
     * <p>Examples for different scenarios</p>
     *
     * {@include.example io.sphere.sdk.products.search.PriceSelectionIntegrationTest#verboseTest()}
     *
     * @param priceSelection parameters for the price selection, using null deletes the values
     * @return search request with new parameters
     * @see ProductVariant#getPrice()
     */
    ProductProjectionSearch withPriceSelection(@Nullable final PriceSelection priceSelection);

    /**
     * Flag to control if the {@link ProductVariant#isMatchingVariant()} should be filled.
     *
     * If markMatchingVariants parameter is true those ProductVariants that match the search query have the additional field isMatchingVariant set to true. For the other variants in the same product projection this field is set to false.
     If markMatchingVariants parameter is false the ProductVariants do not contain the field isMatchingVariant.

     * @param markMatchingVariants flag to populate the {@link ProductVariant#isMatchingVariant()} field
     * @return this request with updated marking matching variants parameter
     *
     * {@include.example io.sphere.sdk.products.search.MatchingVariantsFlagSearchIntegrationTest#disableMatchingVariantsFlag()}
     */
    ProductProjectionSearch withMarkingMatchingVariants(final Boolean markMatchingVariants);

    ProductProjectionSearch withLocaleSelection(@Nullable final LocaleSelection localeSelection);

    ProductProjectionSearch plusLocaleSelection(@Nullable final LocaleSelection localeSelection);

    ProductProjectionSearch withStoreSelection(@Nullable final StoreSelection storeSelection);

    @Nullable
    Boolean isMarkingMatchingVariants();

    @Nullable
    PriceSelection getPriceSelection();
}
