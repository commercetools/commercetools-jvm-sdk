package io.sphere.sdk.products;

import io.sphere.sdk.attributes.*;
import io.sphere.sdk.models.LocalizedStrings;
import io.sphere.sdk.products.commands.ProductCreateCommand;
import io.sphere.sdk.products.queries.ProductQuery;
import io.sphere.sdk.products.search.ExperimentalProductProjectionSearchModel;
import io.sphere.sdk.products.search.ProductProjectionSearch;
import io.sphere.sdk.producttypes.ProductTypeDraft;
import io.sphere.sdk.producttypes.ProductType;
import io.sphere.sdk.producttypes.commands.ProductTypeCreateCommand;
import io.sphere.sdk.producttypes.queries.ProductTypeQuery;
import io.sphere.sdk.queries.Query;
import io.sphere.sdk.search.*;
import io.sphere.sdk.test.IntegrationTest;
import io.sphere.sdk.test.RetryIntegrationTest;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

import static io.sphere.sdk.models.DefaultCurrencyUnits.EUR;
import static io.sphere.sdk.products.search.VariantSearchSortDirection.*;
import static io.sphere.sdk.test.SphereTestUtils.*;
import static java.lang.Math.max;
import static java.lang.Math.min;
import static java.math.BigDecimal.*;
import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

public class ProductProjectionSearchIntegrationTest extends IntegrationTest {
    private static final String EVIL_CHARACTER_WORD = "öóßàç";

    private static Product product1;
    private static Product product2;
    private static Product product3;
    private static ProductType productType;

    private static Product evilProduct1;
    private static Product evilProduct2;
    private static ProductType evilProductType;

    public static final String TEST_CLASS_NAME = ProductProjectionSearchIntegrationTest.class.getSimpleName();
    private static final String EVIL_PRODUCT_TYPE_NAME = "Evil" + TEST_CLASS_NAME;
    private static final String SKU_A = EVIL_PRODUCT_TYPE_NAME + "-skuA";
    private static final String SKU_B = EVIL_PRODUCT_TYPE_NAME + "-skuB";
    private static final String PRODUCT_TYPE_NAME = TEST_CLASS_NAME;
    private static final String SKU1 = PRODUCT_TYPE_NAME + "-sku1";
    private static final String SKU2 = PRODUCT_TYPE_NAME + "-sku2";
    private static final String SKU3 = PRODUCT_TYPE_NAME + "-sku3";
    public static final String ATTR_NAME_COLOR = ("Color" + TEST_CLASS_NAME).substring(0, min(20, TEST_CLASS_NAME.length()));
    public static final String ATTR_NAME_SIZE = ("Size" + TEST_CLASS_NAME).substring(0, min(20, TEST_CLASS_NAME.length()));
    public static final String ATTR_NAME_EVIL = ("Evil" + TEST_CLASS_NAME).substring(0, min(20, TEST_CLASS_NAME.length()));

    @Rule
    public RetryIntegrationTest retry = new RetryIntegrationTest(10, 0, LoggerFactory.getLogger(this.getClass()));

    @BeforeClass
    public static void setupProducts() {
        productType = execute(ProductTypeQuery.of().byName(PRODUCT_TYPE_NAME)).head()
                .orElseGet(() -> createProductType());

        evilProductType = execute(ProductTypeQuery.of().byName(EVIL_PRODUCT_TYPE_NAME)).head()
                .orElseGet(() -> createEvilProductType());

        final Query<Product> query = ProductQuery.of()
                .withPredicates(m -> m.masterData().staged().masterVariant().sku().isIn(SKU1, SKU2, SKU3, SKU_A, SKU_B));
        final List<Product> products = execute(query).getResults();

        final Function<String, Optional<Product>> findBySku =
                sku -> products.stream().filter(p -> sku.equals(p.getMasterData().getStaged().getMasterVariant().getSku())).findFirst();

        product1 = findBySku.apply(SKU1).orElseGet(() -> createTestProduct(productType, "Schuh", "shoe", "blue", 38, 46, SKU1));
        product2 = findBySku.apply(SKU2).orElseGet(() -> createTestProduct(productType, "Hemd", "shirt", "red", 36, 44, SKU2));
        product3 = findBySku.apply(SKU3).orElseGet(() -> createTestProduct(productType, "Kleider", "dress", "blue", 40, 42, SKU3));
        evilProduct1 = findBySku.apply(SKU_A).orElseGet(() -> createEvilTestProduct(evilProductType, EVIL_CHARACTER_WORD, EVIL_PRODUCT_TYPE_NAME + "foo", SKU_A));
        evilProduct2 = findBySku.apply(SKU_B).orElseGet(() -> createEvilTestProduct(evilProductType, EVIL_PRODUCT_TYPE_NAME + "bar", EVIL_CHARACTER_WORD, SKU_B));
    }

    private ExperimentalProductProjectionSearchModel model() {
        return ExperimentalProductProjectionSearchModel.of();
    }

    @Test
    public void searchByTextInACertainLanguage() throws Exception {
        final ProductProjectionSearch search = ProductProjectionSearch.ofStaged().withText(ENGLISH, "shoe");
        final PagedSearchResult<ProductProjection> result = executeSearch(search);
        assertThat(toIds(result.getResults())).containsOnly(product1.getId());
    }

    @Test
    public void sortByAttributeAscending() throws Exception {
        final ProductProjectionSearch search = ProductProjectionSearch.ofStaged()
                .withSort(model -> model.allVariants().attribute().ofNumber(ATTR_NAME_SIZE).sort(ASC));
        final PagedSearchResult<ProductProjection> result = executeSearch(search);
        assertThat(resultsToIds(result)).isEqualTo(asList(product2.getId(), product1.getId(), product3.getId()));
    }

    @Test
    public void sortByAttributeDescending() throws Exception {
        final ProductProjectionSearch search = ProductProjectionSearch.ofStaged()
                .withSort(model -> model.allVariants().attribute().ofNumber(ATTR_NAME_SIZE).sort(DESC));
        final PagedSearchResult<ProductProjection> result = executeSearch(search);
        assertThat(resultsToIds(result)).isEqualTo(asList(product1.getId(), product2.getId(), product3.getId()));
    }

    @Test
    public void sortWithAdditionalParameterByAttributeAscending() throws Exception {
        final ProductProjectionSearch search = ProductProjectionSearch.ofStaged()
                .withSort(model -> model.allVariants().attribute().ofNumber(ATTR_NAME_SIZE).sort(ASC_MAX));
        final PagedSearchResult<ProductProjection> result = executeSearch(search);
        assertThat(resultsToIds(result)).isEqualTo(asList(product3.getId(), product2.getId(), product1.getId()));
    }

    @Test
    public void sortWithAdditionalParameterByAttributeDescending() throws Exception {
        final ProductProjectionSearch search = ProductProjectionSearch.ofStaged()
                .withSort(model -> model.allVariants().attribute().ofNumber(ATTR_NAME_SIZE).sort(DESC_MIN));
        final PagedSearchResult<ProductProjection> result = executeSearch(search);
        assertThat(resultsToIds(result)).isEqualTo(asList(product3.getId(), product1.getId(), product2.getId()));
    }

    @Test
    public void sortWithSimpleExpression() {
        final SearchSort<ProductProjection> sort = SearchSort.of("variants.attributes." + ATTR_NAME_SIZE + " asc.max");
        final ProductProjectionSearch search = ProductProjectionSearch.ofStaged().withSort(sort);
        final PagedSearchResult<ProductProjection> result = executeSearch(search);
        assertThat(resultsToIds(result)).isEqualTo(asList(product3.getId(), product2.getId(), product1.getId()));
    }

    @Test
    public void responseContainsRangeFacetsForAttributes() throws Exception {
        final RangeFacetExpression<ProductProjection, BigDecimal> facetExpr = model().allVariants().price().amount().facetOf().greaterThanOrEqualTo(ZERO);
        final ProductProjectionSearch search = ProductProjectionSearch.ofStaged().plusFacets(facetExpr);
        final PagedSearchResult<ProductProjection> result = executeSearch(search);
        assertThat(result.getRangeFacetResult(facetExpr).getRanges().get(0).getCount()).isGreaterThan(0);
    }

    @Test
    public void responseContainsTermFacetsForAttributes() throws Exception {
        final TermFacetExpression<ProductProjection, String> facetExpr = model().allVariants().attribute().ofText(ATTR_NAME_COLOR).facetOf().all();
        final ProductProjectionSearch search = ProductProjectionSearch.ofStaged().plusFacets(facetExpr);
        final PagedSearchResult<ProductProjection> result = executeSearch(search);
        final Predicate<TermStats<String>> isTermBlue = termStat -> termStat.getTerm().equals("blue") && termStat.getCount() > 0;
        assertThat(result.getTermFacetResult(facetExpr).getTerms().stream().anyMatch(isTermBlue)).isTrue();
    }

    @Test
    public void resultsAndFacetsAreFilteredByColor() throws Exception {
        final TermFacetExpression<ProductProjection, String> facetExpr = model().allVariants().attribute().ofText(ATTR_NAME_COLOR).facetOf().all();
        final FilterExpression<ProductProjection> filterExpr = model().allVariants().attribute().ofNumber(ATTR_NAME_SIZE).filterBy().exactly(valueOf(36));
        final ProductProjectionSearch search = ProductProjectionSearch.ofStaged()
                .plusFacets(facetExpr)
                .plusResultFilters(filterExpr)
                .plusFacetFilters(filterExpr);
        final PagedSearchResult<ProductProjection> result = executeSearch(search);
        assertThat(resultsToIds(result)).containsOnly(product2.getId());
        assertThat(result.getTermFacetResult(facetExpr).getTerms()).isEqualTo(asList(TermStats.of("red", 1)));
    }

    @Test
    public void onlyResultsAreFilteredByColor() throws Exception {
        final TermFacetExpression<ProductProjection, String> facetExpr = model().allVariants().attribute().ofText(ATTR_NAME_COLOR).facetOf().all();
        final ProductProjectionSearch search = ProductProjectionSearch.ofStaged()
                .plusFacets(facetExpr)
                .plusResultFilters(model -> model.allVariants().attribute().ofNumber(ATTR_NAME_SIZE).filterBy().exactly(valueOf(36)));
        final PagedSearchResult<ProductProjection> result = executeSearch(search);
        assertThat(resultsToIds(result)).containsOnly(product2.getId());
        assertThat(result.getTermFacetResult(facetExpr).getTerms()).isEqualTo(asList(TermStats.of("blue", 2), TermStats.of("red", 1)));
    }


    @Test
    public void onlyFacetsAreFilteredByColor() throws Exception {
        final TermFacetExpression<ProductProjection, String> facetExpr = model().allVariants().attribute().ofText(ATTR_NAME_COLOR).facetOf().all();
        final ProductProjectionSearch search = ProductProjectionSearch.ofStaged()
                .plusFacets(facetExpr)
                .plusFacetFilters(model -> model.allVariants().attribute().ofNumber(ATTR_NAME_SIZE).filterBy().exactly(valueOf(36)));
        final PagedSearchResult<ProductProjection> result = executeSearch(search);
        assertThat(result.getTermFacetResult(facetExpr).getTerms()).isEqualTo(asList(TermStats.of("red", 1)));
        assertThat(resultsToIds(result)).contains(product1.getId(), product2.getId(), product3.getId());
    }

    @Test
    public void filtersByTerm() throws Exception {
        final ProductProjectionSearch search = ProductProjectionSearch.ofStaged()
                .plusQueryFilters(model -> model().allVariants().attribute().ofText(ATTR_NAME_COLOR).filterBy().exactly("red"));
        final PagedSearchResult<ProductProjection> result = executeSearch(search);
        assertThat(resultsToIds(result)).containsOnly(product2.getId());
    }

    @Test
    public void filtersByRange() throws Exception {
        final ProductProjectionSearch search = ProductProjectionSearch.ofStaged()
                .plusQueryFilters(model -> model().allVariants().attribute().ofNumber(ATTR_NAME_SIZE).filterBy().greaterThanOrEqualTo(valueOf(44)));
        final PagedSearchResult<ProductProjection> result = executeSearch(search);
        assertThat(resultsToIds(result)).containsOnly(product1.getId(), product2.getId());
    }

    @Test
    public void simpleFilterByRange() throws Exception {
        final FilterExpression<ProductProjection> filterExpr = FilterExpression.of("variants.attributes." + ATTR_NAME_SIZE + ":range(44 to *)");
        final ProductProjectionSearch search = ProductProjectionSearch.ofStaged().plusQueryFilters(filterExpr);
        final PagedSearchResult<ProductProjection> result = executeSearch(search);
        assertThat(resultsToIds(result)).containsOnly(product1.getId(), product2.getId());
    }

    @Test
    public void resultsArePaginated() throws Exception {
        final PagedSearchResult<ProductProjection> result = executeSearch(ProductProjectionSearch.ofStaged()
                .plusQueryFilters(model -> model().allVariants().attribute().ofText(ATTR_NAME_COLOR).filterBy().exactly(asList("blue", "red")))
                .withSort(model -> model().name().locale(ENGLISH).sort(SimpleSearchSortDirection.DESC))
                .withOffset(1L)
                .withLimit(1L));
        assertThat(resultsToIds(result)).containsOnly(product2.getId());
    }

    @Test
    public void filterQueryFiltersBeforeFacetsAreCalculated() throws Exception {
        final TermFacetExpression<ProductProjection, String> facetExpr = model().allVariants().attribute().ofText(ATTR_NAME_COLOR).facetOf().all();
        final ProductProjectionSearch search = ProductProjectionSearch.ofStaged()
                .plusFacets(facetExpr)
                .plusQueryFilters(model -> model().allVariants().attribute().ofNumber(ATTR_NAME_SIZE).filterBy().exactly(valueOf(36)));
        final PagedSearchResult<ProductProjection> result = executeSearch(search);
        assertThat(result.size()).isEqualTo(1);
        assertThat(result.getResults().get(0).getId()).isEqualTo(product2.getId());
        assertThat(result.getTermFacetResult(facetExpr).getTerms()).isEqualTo(asList(TermStats.of("red", 1)));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void simpleFacetsAreParsed() throws Exception {
        final String facetPath = "variants.attributes." + ATTR_NAME_COLOR;
        final FacetExpression<ProductProjection> facetExpr = FacetExpression.of(facetPath);
        final ProductProjectionSearch search = ProductProjectionSearch.ofStaged().plusFacets(facetExpr);
        final PagedSearchResult<ProductProjection> result = executeSearch(search);
        final TermFacetResult<String> termFacetResult = (TermFacetResult) result.getFacetResult(facetPath);
        assertThat(termFacetResult.getMissing()).isGreaterThanOrEqualTo(3);
        assertThat(termFacetResult.getTotal()).isEqualTo(3);
        assertThat(termFacetResult.getOther()).isEqualTo(0);
        assertThat(termFacetResult.getTerms()).isEqualTo(asList(TermStats.of("blue", 2), TermStats.of("red", 1)));
    }

    @Test
    public void termFacetsAreParsed() throws Exception {
        final TermFacetExpression<ProductProjection, String> facetExpr = model().allVariants().attribute().ofText(ATTR_NAME_COLOR).facetOf().all();
        final ProductProjectionSearch search = ProductProjectionSearch.ofStaged().plusFacets(facetExpr);
        final PagedSearchResult<ProductProjection> result = executeSearch(search);
        final TermFacetResult<String> termFacetResult = result.getTermFacetResult(facetExpr);
        assertThat(termFacetResult.getMissing()).isGreaterThanOrEqualTo(3);
        assertThat(termFacetResult.getTotal()).isEqualTo(3);
        assertThat(termFacetResult.getOther()).isEqualTo(0);
        assertThat(termFacetResult.getTerms()).isEqualTo(asList(TermStats.of("blue", 2), TermStats.of("red", 1)));
    }

    @Test
    public void rangeFacetsAreParsed() throws Exception {
        final RangeFacetExpression<ProductProjection, BigDecimal> facetExpr = model().allVariants().attribute().ofNumber(ATTR_NAME_SIZE).facetOf().greaterThanOrEqualTo(ZERO);
        final ProductProjectionSearch search = ProductProjectionSearch.ofStaged().plusFacets(facetExpr);
        final PagedSearchResult<ProductProjection> result = executeSearch(search);
        final RangeStats<BigDecimal> rangeStats = result.getRangeFacetResult(facetExpr).getRanges().get(0);
        assertThat(rangeStats.getLowerEndpoint()).isEqualByComparingTo(BigDecimal.ZERO);
        assertThat(rangeStats.getUpperEndpoint()).isNull();
        assertThat(rangeStats.getCount()).isEqualTo(6L);
        assertThat(rangeStats.getMin()).isEqualByComparingTo(valueOf(36));
        assertThat(rangeStats.getMax()).isEqualByComparingTo(valueOf(46));
        assertThat(rangeStats.getSum()).isEqualByComparingTo(valueOf(246));
        assertThat(rangeStats.getMean()).isEqualTo(41.0);
    }

    @Test
    public void filteredFacetsAreParsed() throws Exception {
        final FilteredFacetExpression<ProductProjection, String> facetExpr = model().allVariants().attribute().ofText(ATTR_NAME_COLOR).facetOf().only("blue");
        final ProductProjectionSearch search = ProductProjectionSearch.ofStaged().plusFacets(facetExpr);
        final PagedSearchResult<ProductProjection> result = executeSearch(search);
        assertThat(result.getFilteredFacetResult(facetExpr).getCount()).isEqualTo(2);
    }

    @Test
    public void termFacetsSupportsAlias() throws Exception {
        final String alias = "my-facet";
        final TermFacetExpression<ProductProjection, String> facetExpr = model().allVariants().attribute().ofText(ATTR_NAME_COLOR).facetOf().withAlias(alias).all();
        final ProductProjectionSearch search = ProductProjectionSearch.ofStaged().plusFacets(facetExpr);
        final PagedSearchResult<ProductProjection> result = executeSearch(search);
        final TermFacetResult<String> termFacetResult = result.getTermFacetResult(facetExpr);
        assertThat(facetExpr.resultPath()).isEqualTo(alias);
        assertThat(termFacetResult.getTerms()).isEqualTo(asList(TermStats.of("blue", 2), TermStats.of("red", 1)));
    }

    @Test
    public void rangeFacetsSupportsAlias() throws Exception {
        final String alias = "my-facet";
        final RangeFacetExpression<ProductProjection, BigDecimal> facetExpr = model().allVariants().attribute().ofNumber(ATTR_NAME_SIZE).facetOf().withAlias(alias).greaterThanOrEqualTo(ZERO);
        final ProductProjectionSearch search = ProductProjectionSearch.ofStaged().plusFacets(facetExpr);
        final PagedSearchResult<ProductProjection> result = executeSearch(search);
        assertThat(facetExpr.resultPath()).isEqualTo(alias);
        final RangeStats<BigDecimal> rangeStats = result.getRangeFacetResult(facetExpr).getRanges().get(0);
        assertThat(rangeStats.getLowerEndpoint()).isEqualByComparingTo(BigDecimal.ZERO);
        assertThat(rangeStats.getUpperEndpoint()).isNull();
        assertThat(rangeStats.getCount()).isEqualTo(6L);
        assertThat(rangeStats.getMin()).isEqualByComparingTo(valueOf(36));
        assertThat(rangeStats.getMax()).isEqualByComparingTo(valueOf(46));
        assertThat(rangeStats.getSum()).isEqualByComparingTo(valueOf(246));
        assertThat(rangeStats.getMean()).isEqualTo(41.0);
    }

    @Test
    public void filteredFacetsSupportsAlias() throws Exception {
        final String alias = "my-facet";
        final FilteredFacetExpression<ProductProjection, String> facetExpr = model().allVariants().attribute().ofText(ATTR_NAME_COLOR).facetOf().withAlias(alias).only("blue");
        final ProductProjectionSearch search = ProductProjectionSearch.ofStaged().plusFacets(facetExpr);
        final PagedSearchResult<ProductProjection> result = executeSearch(search);
        assertThat(facetExpr.resultPath()).isEqualTo(alias);
        assertThat(result.getFilteredFacetResult(facetExpr).getCount()).isEqualTo(2);
    }

    @Test
    public void allowsExpansion() throws Exception {
        final ProductProjectionSearch search = ProductProjectionSearch.ofStaged().withLimit(1);
        assertThat(executeSearch(search).getResults().get(0).getProductType().getObj()).isNull();
        final PagedSearchResult<ProductProjection> result = executeSearch(search.withExpansionPaths(model -> model.productType()));
        assertThat(result.getResults().get(0).getProductType().getObj()).isNotNull();
    }

    @Test
    public void paginationExample() {
        final ProductProjectionSearch search = ProductProjectionSearch.ofStaged()
                .withOffset(50L)
                .withLimit(25L);
        final PagedSearchResult<ProductProjection> result = executeSearch(search);
        assertThat(result.getOffset()).isEqualTo(50);
        assertThat(result.getResults().size()).isEqualTo(min(25, max(result.getTotal() - 50, 0)));
    }

    @Test
    public void unicodeExampleForFilter() throws Exception {
        final ProductProjectionSearch search = ProductProjectionSearch.ofStaged()
                .plusQueryFilters(model -> model().allVariants().attribute().ofText(ATTR_NAME_EVIL).filterBy().exactly(EVIL_CHARACTER_WORD));
        final PagedSearchResult<ProductProjection> result = executeEvilSearch(search);
        assertThat(result.getTotal()).isEqualTo(1);
    }

    private static List<String> resultsToIds(final PagedSearchResult<ProductProjection> result) {
        return toIds(result.getResults());
    }

    private static PagedSearchResult<ProductProjection> executeSearch(final ProductProjectionSearch search) {
        final FilterExpression<ProductProjection> onlyCreatedProducts = FilterExpression.of(
                String.format("id:\"%s\",\"%s\",\"%s\"", product1.getId(), product2.getId(), product3.getId()));
        return execute(search.plusQueryFilters(onlyCreatedProducts));
    }

    private static PagedSearchResult<ProductProjection> executeEvilSearch(final ProductProjectionSearch search) {
        final FilterExpression<ProductProjection> onlyCreatedProducts = FilterExpression.of(
                String.format("id:\"%s\",\"%s\"", evilProduct1.getId(), evilProduct2.getId()));
        return execute(search.plusQueryFilters(onlyCreatedProducts));
    }

    private static ProductType createProductType() {
        final AttributeDefinition colorAttrDef = AttributeDefinitionBuilder
                .of(ATTR_NAME_COLOR, LocalizedStrings.ofEnglishLocale(ATTR_NAME_COLOR), TextType.of()).isSearchable(true).build();
        final AttributeDefinition sizeAttrDef = AttributeDefinitionBuilder
                .of(ATTR_NAME_SIZE, LocalizedStrings.ofEnglishLocale(ATTR_NAME_SIZE), NumberType.of()).isSearchable(true).build();
        final ProductTypeDraft productTypeDraft = ProductTypeDraft.of(PRODUCT_TYPE_NAME, "", asList(colorAttrDef, sizeAttrDef));
        final ProductTypeCreateCommand productTypeCreateCommand = ProductTypeCreateCommand.of(productTypeDraft);
        return execute(productTypeCreateCommand);
    }

    private static ProductType createEvilProductType() {
        final AttributeDefinition evilAttrDef = AttributeDefinitionBuilder
                .of(ATTR_NAME_EVIL, LocalizedStrings.ofEnglishLocale(ATTR_NAME_EVIL), TextType.of()).isSearchable(true).build();
        final ProductTypeDraft evilProductTypeDraft = ProductTypeDraft.of(EVIL_PRODUCT_TYPE_NAME, "", asList(evilAttrDef));
        final ProductTypeCreateCommand evilProductTypeCreateCommand = ProductTypeCreateCommand.of(evilProductTypeDraft);
        return execute(evilProductTypeCreateCommand);
    }

    private static Product createTestProduct(final ProductType productType, final String germanName, final String englishName,
                                             final String color, final int size1, final int size2, final String sku) {
        final LocalizedStrings name = LocalizedStrings.of(GERMAN, germanName, ENGLISH, englishName);
        final ProductVariantDraft masterVariant = ProductVariantDraftBuilder.of()
                .attributes(AttributeDraft.of(ATTR_NAME_SIZE, size1), AttributeDraft.of(ATTR_NAME_COLOR, color))
                .price(Price.of(new BigDecimal("23.45"), EUR))
                .sku(sku)
                .build();
        final ProductVariantDraft variant = ProductVariantDraftBuilder.of()
                .attributes(AttributeDraft.of(ATTR_NAME_SIZE, size2))
                .price(Price.of(new BigDecimal("27.45"), EUR))
                .build();
        final ProductDraft productDraft = ProductDraftBuilder.of(productType, name, name.slugifiedUnique(), masterVariant)
                .variants(asList(variant)).build();
        return execute(ProductCreateCommand.of(productDraft));
    }

    private static Product createEvilTestProduct(final ProductType productType, final String germanName, final String evilValue, final String sku) {
        final LocalizedStrings name = LocalizedStrings.of(GERMAN, germanName);
        final ProductVariantDraft masterVariant = ProductVariantDraftBuilder.of()
                .attributes(AttributeDraft.of(ATTR_NAME_EVIL, evilValue))
                .sku(sku)
                .build();
        final ProductDraft productDraft = ProductDraftBuilder.of(productType, name, name.slugifiedUnique(), masterVariant).build();
        return execute(ProductCreateCommand.of(productDraft));
    }
}
