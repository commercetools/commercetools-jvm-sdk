package io.sphere.sdk.products;

import io.sphere.sdk.attributes.*;
import io.sphere.sdk.client.SphereRequest;
import io.sphere.sdk.models.LocalizedStrings;
import io.sphere.sdk.products.commands.ProductCreateCommand;
import io.sphere.sdk.products.commands.ProductDeleteByIdCommand;
import io.sphere.sdk.products.queries.ProductProjectionQuery;
import io.sphere.sdk.products.search.ProductProjectionSearch;
import io.sphere.sdk.products.search.ProductProjectionSearchModel;
import io.sphere.sdk.producttypes.ProductTypeDraft;
import io.sphere.sdk.producttypes.ProductType;
import io.sphere.sdk.producttypes.commands.ProductTypeCreateCommand;
import io.sphere.sdk.producttypes.commands.ProductTypeDeleteByIdCommand;
import io.sphere.sdk.producttypes.queries.ProductTypeQuery;
import io.sphere.sdk.search.*;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import static io.sphere.sdk.models.DefaultCurrencyUnits.EUR;
import static io.sphere.sdk.products.ProductProjectionType.STAGED;
import static io.sphere.sdk.products.search.VariantSearchSortDirection.*;
import static io.sphere.sdk.test.SphereTestUtils.*;
import static java.math.BigDecimal.*;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;
import static org.fest.assertions.Assertions.assertThat;
import static org.fest.assertions.Fail.fail;

public class ProductProjectionSearchIntegrationTest extends IntegrationTest {
    private static final ProductProjectionSearchModel MODEL = ProductProjectionSearch.model();

    private static Product testProduct1;
    private static Product testProduct2;
    private static Product testProduct3;
    private static ProductType productType;
    public static final String TEST_CLASS_NAME = ProductProjectionSearchIntegrationTest.class.getSimpleName();
    public static final String COLOR = ("Color" + TEST_CLASS_NAME).substring(0, Math.min(25, TEST_CLASS_NAME.length()));
    public static final String SIZE = ("Size" + TEST_CLASS_NAME).substring(0, Math.min(25, TEST_CLASS_NAME.length()));

    @BeforeClass
    public static void setupProducts() {
        removeProducts();
        final TextAttributeDefinition colorAttributeDefinition = TextAttributeDefinitionBuilder
                .of(COLOR, LocalizedStrings.ofEnglishLocale(COLOR), TextInputHint.SINGLE_LINE).isSearchable(true).build();
        final TextAttributeDefinition sizeAttributeDefinition = TextAttributeDefinitionBuilder
                .of(SIZE, LocalizedStrings.ofEnglishLocale(SIZE), TextInputHint.SINGLE_LINE).isSearchable(true).build();

        final ProductTypeDraft productTypeDraft = ProductTypeDraft.of(TEST_CLASS_NAME, "", asList(colorAttributeDefinition, sizeAttributeDefinition));
        final ProductTypeCreateCommand productTypeCreateCommand = ProductTypeCreateCommand.of(productTypeDraft);
        productType = execute(productTypeCreateCommand);
        testProduct1 = createTestProduct(productType, "Schuh", "shoe", "M", "brown", "yellow");
        testProduct2 = createTestProduct(productType, "Hemd", "shirt", "XL", "blue", "white");
        testProduct3 = createTestProduct(productType, "Kleider", "dress", "M", "green", "red");
        final SearchDsl<ProductProjection> search = ProductProjectionSearch.of(STAGED).withSort(MODEL.createdAt().sort(SimpleSearchSortDirection.DESC));
        execute(search, res -> {
            final List<String> ids = toIds(res.getResults());
            return ids.contains(testProduct1.getId()) && ids.contains(testProduct2.getId()) && ids.contains(testProduct3.getId());
        });
        try {
            Thread.sleep(500); // Wait for elasticsearch synchronization
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static Product createTestProduct(final ProductType productType, final String germanName, final String englishName,
                                             final String size, final String color1, final String color2) {
        final LocalizedStrings name = LocalizedStrings.of(GERMAN, germanName, ENGLISH, englishName);
        final ProductVariantDraft masterVariant = ProductVariantDraftBuilder.of()
                .attributes(Attribute.of(COLOR, color1), Attribute.of(SIZE, size))
                .price(Price.of(new BigDecimal("23.45"), EUR))
                .build();
        final ProductVariantDraft variant = ProductVariantDraftBuilder.of()
                .attributes(Attribute.of(COLOR, color2))
                .price(Price.of(new BigDecimal("27.45"), EUR))
                .build();
        final ProductDraft productDraft = ProductDraftBuilder.of(productType, name, name, masterVariant).variants(asList(variant)).build();
        return execute(ProductCreateCommand.of(productDraft));
    }

    @AfterClass
    public static void removeProducts() {
        List<ProductType> productTypes = execute(ProductTypeQuery.of().byName(TEST_CLASS_NAME)).getResults();
        if (!productTypes.isEmpty()) {
            final List<ProductProjection> products = execute(ProductProjectionQuery.of(STAGED)
                    .withPredicate(ProductProjectionQuery.model().productType().isAnyOf(productTypes))).getResults();
            products.forEach(p -> execute(ProductDeleteByIdCommand.of(p.toProductVersioned())));
            productTypes.forEach(p -> execute(ProductTypeDeleteByIdCommand.of(p)));
        }
        testProduct1 = null;
        testProduct2 = null;
        testProduct3 = null;
        productType = null;
    }

    @Test
    public void searchByTextInACertainLanguage() throws Exception {
        final Search<ProductProjection> search = ProductProjectionSearch.of(STAGED).withText(ENGLISH, "shoe");
        final PagedSearchResult<ProductProjection> result = execute(search);
        assertThat(toIds(result.getResults())).containsExactly(testProduct1.getId());
    }

    @Test
    public void sortByAnAttribute() throws Exception {
        testSorting(MODEL.variants().attribute().ofText(COLOR).sort(ASC), asList(testProduct2.getId(), testProduct1.getId(), testProduct3.getId()));
        testSorting(MODEL.variants().attribute().ofText(COLOR).sort(DESC), asList(testProduct1.getId(), testProduct2.getId(), testProduct3.getId()));
    }

    @Test
    public void sortWithAdditionalParameterByAnAttribute() throws Exception {
        testSorting(MODEL.variants().attribute().ofText(COLOR).sort(ASC_MAX), asList(testProduct3.getId(), testProduct2.getId(), testProduct1.getId()));
        testSorting(MODEL.variants().attribute().ofText(COLOR).sort(DESC_MIN), asList(testProduct3.getId(), testProduct1.getId(), testProduct2.getId()));
    }

    @Test
    public void responseContainsRangeFacetsForAttributes() throws Exception {
        final RangeFacetExpression<ProductProjection, BigDecimal> facetExpression = MODEL.variants().price().amount().facet().onlyGreaterThanOrEqualTo(ZERO);
        final Search<ProductProjection> search = ProductProjectionSearch.of(STAGED).plusFacet(facetExpression);
        final PagedSearchResult<ProductProjection> result = execute(search);
        assertThat(result.getRangeFacetResult(facetExpression).get().getRanges().get(0).getCount()).isGreaterThan(0);
    }

    @Test
    public void responseContainsTermFacetsForAttributes() throws Exception {
        final TermFacetExpression<ProductProjection, String> facetExpression = MODEL.variants().attribute().ofText(COLOR).facet().all();
        final Search<ProductProjection> search = ProductProjectionSearch.of(STAGED).plusFacet(facetExpression);
        final PagedSearchResult<ProductProjection> result = execute(search);
        final Predicate<TermStats<String>> isTermBlue = termStat -> termStat.getTerm().equals("blue") && termStat.getCount() > 0;
        assertThat(result.getTermFacetResult(facetExpression).get().getTerms().stream().anyMatch(isTermBlue)).isTrue();
    }

    @Test
    public void resultsAndFacetsAreFilteredByColor() throws Exception {
        final FilterExpression<ProductProjection> filter = MODEL.variants().attribute().ofText(COLOR).filter().is("blue");
        final TermFacetExpression<ProductProjection, String> facet = MODEL.variants().attribute().ofText(SIZE).facet().all();
        final SearchDsl<ProductProjection> search = ProductProjectionSearch.of(STAGED).plusFacet(facet).plusFilterResult(filter).plusFilterFacet(filter);
        final PagedSearchResult<ProductProjection> result = execute(search);
        assertThat(result.size()).isEqualTo(1);
        assertThat(result.getResults().get(0).getId()).isEqualTo(testProduct2.getId());
        assertThat(result.getTermFacetResult(facet).get().getTerms()).containsExactly(TermStats.of("XL", 1));
    }

    @Test
    public void onlyResultsAreFilteredByColor() throws Exception {
        final TermFacetExpression<ProductProjection, String> facet = MODEL.variants().attribute().ofText(SIZE).facet().all();
        final FilterExpression<ProductProjection> filter = MODEL.variants().attribute().ofText(COLOR).filter().is("blue");
        final SearchDsl<ProductProjection> search = ProductProjectionSearch.of(STAGED).plusFacet(facet).plusFilterResult(filter);
        final PagedSearchResult<ProductProjection> result = execute(search);
        assertThat(result.size()).isEqualTo(1);
        assertThat(result.getResults().get(0).getId()).isEqualTo(testProduct2.getId());
        assertThat(result.getTermFacetResult(facet).get().getTerms()).containsOnly(TermStats.of("XL", 1), TermStats.of("M", 2));
    }

    @Test
    public void onlyFacetsAreFilteredByColor() throws Exception {
        final TermFacetExpression<ProductProjection, String> facet = MODEL.variants().attribute().ofText(SIZE).facet().all();
        final FilterExpression<ProductProjection> filter = MODEL.variants().attribute().ofText(COLOR).filter().is("blue");
        final SearchDsl<ProductProjection> search = ProductProjectionSearch.of(STAGED).plusFacet(facet).plusFilterFacet(filter);
        final PagedSearchResult<ProductProjection> result = execute(search);
        assertThat(result.getTermFacetResult(facet).get().getTerms()).containsExactly(TermStats.of("XL", 1));
        final HashSet<String> ids = new HashSet<>(toIds(result.getResults()));
        assertThat(ids).contains(testProduct2.getId(), testProduct1.getId());
    }

    @Test
    public void resultsArePaginated() throws Exception {
        final FilterExpression<ProductProjection> filter = MODEL.variants().attribute().ofText(SIZE).filter().isIn(asList("M", "XL"));
        final SearchSort<ProductProjection> sort = MODEL.name().locale(ENGLISH).sort(SimpleSearchSortDirection.DESC);
        final SearchDsl<ProductProjection> search = ProductProjectionSearch.of(STAGED).plusFilterQuery(filter).withSort(sort).withOffset(1).withLimit(1);
        final PagedSearchResult<ProductProjection> result = execute(search);
        assertThat(toIds(result.getResults())).containsExactly(testProduct2.getId());
    }

    @Test
    public void filterQueryFiltersBeforeFacetsAreCalculated() throws Exception {
        final TermFacetExpression<ProductProjection, String> facet = MODEL.variants().attribute().ofText(SIZE).facet().all();
        final FilterExpression<ProductProjection> filter = MODEL.variants().attribute().ofText(COLOR).filter().is("blue");
        final SearchDsl<ProductProjection> search = ProductProjectionSearch.of(STAGED).plusFacet(facet).plusFilterQuery(filter);
        final PagedSearchResult<ProductProjection> result = execute(search);
        assertThat(result.size()).isEqualTo(1);
        assertThat(result.getResults().get(0).getId()).isEqualTo(testProduct2.getId());
        assertThat(result.getTermFacetResult(facet).get().getTerms()).containsExactly(TermStats.of("XL", 1));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void simpleFacetsAreParsed() throws Exception {
        final String path = "variants.attributes." + SIZE;
        final FacetExpression<ProductProjection> facet = FacetExpression.of(path);
        final SearchDsl<ProductProjection> search = ProductProjectionSearch.of(STAGED).plusFacet(facet);
        final PagedSearchResult<ProductProjection> result = execute(search);
        final TermFacetResult<String> termFacetResult = (TermFacetResult) result.getFacetResult(path).get();
        assertThat(termFacetResult.getMissing()).isEqualTo(3);
        assertThat(termFacetResult.getTotal()).isEqualTo(3);
        assertThat(termFacetResult.getOther()).isEqualTo(0);
        assertThat(termFacetResult.getTerms()).containsExactly(TermStats.of("M", 2), TermStats.of("XL", 1));
    }

    @Test
    public void termFacetsAreParsed() throws Exception {
        final TermFacetExpression<ProductProjection, String> facet = MODEL.variants().attribute().ofText(SIZE).facet().all();
        final SearchDsl<ProductProjection> search = ProductProjectionSearch.of(STAGED).plusFacet(facet);
        final PagedSearchResult<ProductProjection> result = execute(search);
        final TermFacetResult<String> termFacetResult = result.getTermFacetResult(facet).get();
        assertThat(termFacetResult.getMissing()).isEqualTo(3);
        assertThat(termFacetResult.getTotal()).isEqualTo(3);
        assertThat(termFacetResult.getOther()).isEqualTo(0);
        assertThat(termFacetResult.getTerms()).containsExactly(TermStats.of("M", 2), TermStats.of("XL", 1));
    }

    @Test
    public void rangeFacetsAreParsed() throws Exception {
        final RangeFacetExpression<ProductProjection, BigDecimal> facet = MODEL.variants().price().amount().facet().onlyGreaterThanOrEqualTo(ZERO);
        final SearchDsl<ProductProjection> search = ProductProjectionSearch.of(STAGED).plusFacet(facet);
        final PagedSearchResult<ProductProjection> result = execute(search);
        final RangeStats<Double> expectedRange = RangeStats.of(Optional.of(0D), Optional.empty(), 6, 15270D, 2345D, 2745D, 2545D);
        assertThat(result.getRangeFacetResult(facet).get().getRanges()).containsExactly(expectedRange);
    }

    @Test
    public void filteredFacetsAreParsed() throws Exception {
        final FilteredFacetExpression<ProductProjection, String> facet = MODEL.variants().attribute().ofText(SIZE).facet().only("M");
        final SearchDsl<ProductProjection> search = ProductProjectionSearch.of(STAGED).plusFacet(facet);
        final PagedSearchResult<ProductProjection> result = execute(search);
        assertThat(result.getFilteredFacetResult(facet).get().getCount()).isEqualTo(2);
    }

    private void testSorting(SearchSort<ProductProjection> sphereSort, List<String> expected) {
        final SearchDsl<ProductProjection> search = ProductProjectionSearch.of(STAGED).withSort(sphereSort);
        final List<ProductProjection> results = execute(search).getResults();
        final Predicate<String> onlyCreatedProducts = id -> id.equals(testProduct1.getId()) || id.equals(testProduct2.getId()) || id.equals(testProduct3.getId());
        final List<String> filteredId = toIds(results).stream().filter(onlyCreatedProducts).collect(toList());
        assertThat(filteredId).isEqualTo(expected);
    }

    protected static <T> T execute(final SphereRequest<T> clientRequest, final Predicate<T> isOk) {
        return execute(clientRequest, 9, isOk);
    }

    protected static <T> T execute(final SphereRequest<T> clientRequest, final int attemptsLeft, final Predicate<T> isOk) {
        if (attemptsLeft < 1) {
            fail("Could not satisfy the request.");
        }

        T result = execute(clientRequest);
        if (isOk.test(result)) {
            return result;
        } else {
            LoggerFactory.getLogger(ProductProjectionSearchIntegrationTest.class).info("attempts left " + (attemptsLeft - 1));
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return execute(clientRequest, attemptsLeft - 1, isOk);
        }
    }

    private void paginationExample() {
        final SearchSort<ProductProjection> sort = getASortExpressionSomeHow();
        final Search<ProductProjection> search = ProductProjectionSearch.of(STAGED)
                .withSort(sort)
                .withOffset(50)
                .withLimit(25);
    }

    private SearchSort<ProductProjection> getASortExpressionSomeHow() {
        return null;
    }
}
