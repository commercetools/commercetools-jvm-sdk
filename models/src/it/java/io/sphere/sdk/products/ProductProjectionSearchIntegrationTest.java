package io.sphere.sdk.products;

import io.sphere.sdk.attributes.*;
import io.sphere.sdk.client.SphereRequest;
import io.sphere.sdk.models.LocalizedStrings;
import io.sphere.sdk.models.Versioned;
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
import io.sphere.sdk.test.SphereTestUtils;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import static io.sphere.sdk.models.DefaultCurrencyUnits.EUR;
import static io.sphere.sdk.products.ProductProjectionType.STAGED;
import static io.sphere.sdk.products.search.VariantSearchSortDirection.*;
import static io.sphere.sdk.test.SphereTestUtils.ENGLISH;
import static io.sphere.sdk.test.SphereTestUtils.GERMAN;
import static java.lang.Math.max;
import static java.lang.Math.min;
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
    public static final String COLOR = ("Color" + TEST_CLASS_NAME).substring(0, min(20, TEST_CLASS_NAME.length()));
    public static final String SIZE = ("Size" + TEST_CLASS_NAME).substring(0, min(20, TEST_CLASS_NAME.length()));

    @BeforeClass
    public static void setupProducts() {
        removeProducts();
        final AttributeDefinition sizeAttributeDefinition = TextAttributeDefinitionBuilder
                .of(COLOR, LocalizedStrings.ofEnglishLocale(COLOR), TextInputHint.SINGLE_LINE).isSearchable(true).build();
        final AttributeDefinition colorAttributeDefinition = NumberAttributeDefinitionBuilder
                .of(SIZE, LocalizedStrings.ofEnglishLocale(SIZE)).isSearchable(true).build();

        final ProductTypeDraft productTypeDraft = ProductTypeDraft.of(TEST_CLASS_NAME, "", asList(colorAttributeDefinition, sizeAttributeDefinition));
        final ProductTypeCreateCommand productTypeCreateCommand = ProductTypeCreateCommand.of(productTypeDraft);
        productType = execute(productTypeCreateCommand);
        testProduct1 = createTestProduct(productType, "Schuh", "shoe", "blue", 38, 46);
        testProduct2 = createTestProduct(productType, "Hemd", "shirt", "red", 36, 44);
        testProduct3 = createTestProduct(productType, "Kleider", "dress", "blue", 40, 42);
        final SearchDsl<ProductProjection> search = ProductProjectionSearch.of(STAGED).withSort(MODEL.createdAt().sort(SimpleSearchSortDirection.DESC));
        execute(search, res -> {
            final List<String> ids = SphereTestUtils.toIds(res.getResults());
            return ids.contains(testProduct1.getId()) && ids.contains(testProduct2.getId()) && ids.contains(testProduct3.getId());
        });
        try {
            Thread.sleep(500); // Wait for elasticsearch synchronization
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static Product createTestProduct(final ProductType productType, final String germanName, final String englishName,
                                             final String color, final int size1, final int size2) {
        final LocalizedStrings name = LocalizedStrings.of(GERMAN, germanName, ENGLISH, englishName);
        final ProductVariantDraft masterVariant = ProductVariantDraftBuilder.of()
                .attributes(Attribute.of(SIZE, size1), Attribute.of(COLOR, color))
                .price(Price.of(new BigDecimal("23.45"), EUR))
                .build();
        final ProductVariantDraft variant = ProductVariantDraftBuilder.of()
                .attributes(Attribute.of(SIZE, size2))
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
        assertThat(SphereTestUtils.toIds(result.getResults())).containsExactly(testProduct1.getId());
    }

    @Test
    public void sortByAttributeAscending() throws Exception {
        final SearchSort<ProductProjection> sort = MODEL.variants().attribute().ofNumber(SIZE).sort(ASC);
        final PagedSearchResult<ProductProjection> result = execute(ProductProjectionSearch.of(STAGED).withSort(sort));
        assertThat(resultsToIds(result)).containsExactly(testProduct2.getId(), testProduct1.getId(), testProduct3.getId());
    }

    @Test
    public void sortByAttributeDescending() throws Exception {
        final SearchSort<ProductProjection> sort = MODEL.variants().attribute().ofNumber(SIZE).sort(DESC);
        final PagedSearchResult<ProductProjection> result = execute(ProductProjectionSearch.of(STAGED).withSort(sort));
        assertThat(resultsToIds(result)).containsExactly(testProduct1.getId(), testProduct2.getId(), testProduct3.getId());
    }

    @Test
    public void sortWithAdditionalParameterByAttributeAscending() throws Exception {
        final SearchSort<ProductProjection> sort = MODEL.variants().attribute().ofNumber(SIZE).sort(ASC_MAX);
        final PagedSearchResult<ProductProjection> result = execute(ProductProjectionSearch.of(STAGED).withSort(sort));
        assertThat(resultsToIds(result)).containsExactly(testProduct3.getId(), testProduct2.getId(), testProduct1.getId());
    }

    @Test
    public void sortWithAdditionalParameterByAttributeDescending() throws Exception {
        final SearchSort<ProductProjection> sort = MODEL.variants().attribute().ofNumber(SIZE).sort(DESC_MIN);
        final PagedSearchResult<ProductProjection> result = execute(ProductProjectionSearch.of(STAGED).withSort(sort));
        assertThat(resultsToIds(result)).containsExactly(testProduct3.getId(), testProduct1.getId(), testProduct2.getId());
    }

    @Test
    public void sortWithSimpleExpression() {
        final SearchSort<ProductProjection> sort = SearchSort.of("variants.attributes." + SIZE +" asc.max");
        final PagedSearchResult<ProductProjection> result = execute(ProductProjectionSearch.of(STAGED).withSort(sort));
        assertThat(resultsToIds(result)).containsExactly(testProduct3.getId(), testProduct2.getId(), testProduct1.getId());
    }

    @Test
    public void responseContainsRangeFacetsForAttributes() throws Exception {
        final RangeFacetExpression<ProductProjection, BigDecimal> facetExpression = MODEL.variants().price().amount().facet().onlyGreaterThanOrEqualTo(ZERO);
        final PagedSearchResult<ProductProjection> result = execute(ProductProjectionSearch.of(STAGED).plusFacet(facetExpression));
        assertThat(result.getRangeFacetResult(facetExpression).get().getRanges().get(0).getCount()).isGreaterThan(0);
    }

    @Test
    public void responseContainsTermFacetsForAttributes() throws Exception {
        final TermFacetExpression<ProductProjection, String> facet = MODEL.variants().attribute().ofText(COLOR).facet().all();
        final PagedSearchResult<ProductProjection> result = execute(ProductProjectionSearch.of(STAGED).plusFacet(facet));
        final Predicate<TermStats<String>> isTermBlue = termStat -> termStat.getTerm().equals("blue") && termStat.getCount() > 0;
        assertThat(result.getTermFacetResult(facet).get().getTerms().stream().anyMatch(isTermBlue)).isTrue();
    }

    @Test
    public void resultsAndFacetsAreFilteredByColor() throws Exception {
        final TermFacetExpression<ProductProjection, String> facet = MODEL.variants().attribute().ofText(COLOR).facet().all();
        final FilterExpression<ProductProjection> filter = MODEL.variants().attribute().ofNumber(SIZE).filter().is(valueOf(36));
        final PagedSearchResult<ProductProjection> result = execute(ProductProjectionSearch.of(STAGED)
                .plusFacet(facet)
                .plusFilterResult(filter)
                .plusFilterFacet(filter));
        assertThat(result.size()).isEqualTo(1);
        assertThat(result.getResults().get(0).getId()).isEqualTo(testProduct2.getId());
        assertThat(result.getTermFacetResult(facet).get().getTerms()).containsExactly(TermStats.of("red", 1));
    }

    @Test
    public void onlyResultsAreFilteredByColor() throws Exception {
        final TermFacetExpression<ProductProjection, String> facet = MODEL.variants().attribute().ofText(COLOR).facet().all();
        final FilterExpression<ProductProjection> filter = MODEL.variants().attribute().ofNumber(SIZE).filter().is(valueOf(36));
        final PagedSearchResult<ProductProjection> result = execute(ProductProjectionSearch.of(STAGED)
                .plusFacet(facet)
                .plusFilterResult(filter));
        assertThat(result.size()).isEqualTo(1);
        assertThat(result.getResults().get(0).getId()).isEqualTo(testProduct2.getId());
        assertThat(result.getTermFacetResult(facet).get().getTerms()).containsOnly(TermStats.of("red", 1), TermStats.of("blue", 2));
    }

    @Test
    public void onlyFacetsAreFilteredByColor() throws Exception {
        final TermFacetExpression<ProductProjection, String> facet = MODEL.variants().attribute().ofText(COLOR).facet().all();
        final FilterExpression<ProductProjection> filter = MODEL.variants().attribute().ofNumber(SIZE).filter().is(valueOf(36));
        final PagedSearchResult<ProductProjection> result = execute(ProductProjectionSearch.of(STAGED)
                .plusFacet(facet)
                .plusFilterFacet(filter));
        assertThat(result.getTermFacetResult(facet).get().getTerms()).containsExactly(TermStats.of("red", 1));
        assertThat(resultsToIds(result)).contains(testProduct2.getId(), testProduct1.getId());
    }

    @Test
    public void resultsArePaginated() throws Exception {
        final FilterExpression<ProductProjection> filter = MODEL.variants().attribute().ofText(COLOR).filter().isIn(asList("blue", "red"));
        final SearchSort<ProductProjection> sort = MODEL.name().locale(ENGLISH).sort(SimpleSearchSortDirection.DESC);
        final PagedSearchResult<ProductProjection> result = execute(ProductProjectionSearch.of(STAGED)
                .plusFilterQuery(filter)
                .withSort(sort)
                .withOffset(1)
                .withLimit(1));
        assertThat(resultsToIds(result)).containsExactly(testProduct2.getId());
    }

    @Test
    public void filterQueryFiltersBeforeFacetsAreCalculated() throws Exception {
        final TermFacetExpression<ProductProjection, String> facet = MODEL.variants().attribute().ofText(COLOR).facet().all();
        final FilterExpression<ProductProjection> filter = MODEL.variants().attribute().ofNumber(SIZE).filter().is(valueOf(36));
        final PagedSearchResult<ProductProjection> result = execute(ProductProjectionSearch.of(STAGED)
                .plusFacet(facet)
                .plusFilterQuery(filter));
        assertThat(result.size()).isEqualTo(1);
        assertThat(result.getResults().get(0).getId()).isEqualTo(testProduct2.getId());
        assertThat(result.getTermFacetResult(facet).get().getTerms()).containsExactly(TermStats.of("red", 1));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void simpleFacetsAreParsed() throws Exception {
        final String path = "variants.attributes." + COLOR;
        final FacetExpression<ProductProjection> facet = FacetExpression.of(path);
        final PagedSearchResult<ProductProjection> result = execute(ProductProjectionSearch.of(STAGED).plusFacet(facet));
        final TermFacetResult<String> termFacetResult = (TermFacetResult) result.getFacetResult(path).get();
        assertThat(termFacetResult.getMissing()).isGreaterThanOrEqualTo(3);
        assertThat(termFacetResult.getTotal()).isEqualTo(3);
        assertThat(termFacetResult.getOther()).isEqualTo(0);
        assertThat(termFacetResult.getTerms()).containsExactly(TermStats.of("blue", 2), TermStats.of("red", 1));
    }

    @Test
    public void termFacetsAreParsed() throws Exception {
        final TermFacetExpression<ProductProjection, String> facet = MODEL.variants().attribute().ofText(COLOR).facet().all();
        final PagedSearchResult<ProductProjection> result = execute(ProductProjectionSearch.of(STAGED).plusFacet(facet));
        final TermFacetResult<String> termFacetResult = result.getTermFacetResult(facet).get();
        assertThat(termFacetResult.getMissing()).isGreaterThanOrEqualTo(3);
        assertThat(termFacetResult.getTotal()).isEqualTo(3);
        assertThat(termFacetResult.getOther()).isEqualTo(0);
        assertThat(termFacetResult.getTerms()).containsExactly(TermStats.of("blue", 2), TermStats.of("red", 1));
    }

    @Test
    public void rangeFacetsAreParsed() throws Exception {
        final RangeFacetExpression<ProductProjection, BigDecimal> facet = MODEL.variants().attribute().ofNumber(SIZE).facet().onlyGreaterThanOrEqualTo(ZERO);
        final PagedSearchResult<ProductProjection> result = execute(ProductProjectionSearch.of(STAGED).plusFacet(facet));
        final RangeStats<Double> expectedRange = RangeStats.of(Optional.of(0D), Optional.empty(), 6, 36D, 46D, 246D, 41D);
        assertThat(result.getRangeFacetResult(facet).get().getRanges()).containsExactly(expectedRange);
    }

    @Test
    public void filteredFacetsAreParsed() throws Exception {
        final FilteredFacetExpression<ProductProjection, String> facet = MODEL.variants().attribute().ofText(COLOR).facet().only("blue");
        final PagedSearchResult<ProductProjection> result = execute(ProductProjectionSearch.of(STAGED).plusFacet(facet));
        assertThat(result.getFilteredFacetResult(facet).get().getCount()).isEqualTo(2);
    }

    @Test
    public void paginationExample() {
        final PagedSearchResult<ProductProjection> result = execute(ProductProjectionSearch.of(STAGED).withOffset(50).withLimit(25));
        assertThat(result.getOffset()).isEqualTo(50);
        assertThat(result.getResults().size()).isEqualTo(min(25, max(result.getTotal() - 50, 0)));
    }

    private List<String> resultsToIds(final PagedSearchResult<ProductProjection> result) {
        final Predicate<String> onlyCreatedProducts = id -> id.equals(testProduct1.getId()) || id.equals(testProduct2.getId()) || id.equals(testProduct3.getId());
        return SphereTestUtils.toIds(result.getResults()).stream().filter(onlyCreatedProducts).collect(toList());
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
}
