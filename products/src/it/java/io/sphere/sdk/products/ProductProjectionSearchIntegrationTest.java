package io.sphere.sdk.products;

import com.google.common.collect.Lists;
import io.sphere.sdk.attributes.*;
import io.sphere.sdk.http.ClientRequest;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.products.commands.ProductCreateCommand;
import io.sphere.sdk.products.commands.ProductDeleteByIdCommand;
import io.sphere.sdk.products.queries.ProductProjectionQuery;
import io.sphere.sdk.products.search.ProductProjectionSearch;
import io.sphere.sdk.producttypes.NewProductType;
import io.sphere.sdk.producttypes.ProductType;
import io.sphere.sdk.producttypes.commands.ProductTypeCreateCommand;
import io.sphere.sdk.producttypes.commands.ProductTypeDeleteByIdCommand;
import io.sphere.sdk.producttypes.queries.ProductTypeQuery;
import io.sphere.sdk.search.*;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import play.Logger;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.function.Predicate;

import static io.sphere.sdk.products.ProductProjectionType.STAGED;
import static io.sphere.sdk.test.SphereTestUtils.*;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;
import static org.fest.assertions.Assertions.assertThat;
import static org.fest.assertions.Fail.fail;

public class ProductProjectionSearchIntegrationTest extends IntegrationTest {

    private static Product testProduct1;
    private static Product testProduct2;
    private static ProductType productType;
    public static final String TEST_CLASS_NAME = ProductProjectionSearchIntegrationTest.class.getSimpleName();
    public static final String COLOR = ("Color" + TEST_CLASS_NAME).substring(0, Math.min(25, TEST_CLASS_NAME.length()));
    public static final String SIZE = ("Size" + TEST_CLASS_NAME).substring(0, Math.min(25, TEST_CLASS_NAME.length()));
    public static final String COLOR_ATTRIBUTE_KEY = "variants.attributes." + COLOR;
    public static final String SIZE_ATTRIBUTE_KEY = "variants.attributes." + SIZE;
    public static final String PRICE_ATTRIBUTE_KEY = "variants.price.centAmount";

    @BeforeClass
    public static void setupProducts() {
        removeProducts();
        final TextAttributeDefinition colorAttributeDefinition = TextAttributeDefinitionBuilder
                .of(COLOR, LocalizedString.ofEnglishLocale(COLOR), TextInputHint.SingleLine).build();
        final TextAttributeDefinition sizeAttributeDefinition = TextAttributeDefinitionBuilder
                .of(SIZE, LocalizedString.ofEnglishLocale(SIZE), TextInputHint.SingleLine).build();

        final NewProductType newProductType = NewProductType.of(TEST_CLASS_NAME, "", asList(colorAttributeDefinition, sizeAttributeDefinition));
        final ProductTypeCreateCommand productTypeCreateCommand = new ProductTypeCreateCommand(newProductType);
        productType = execute(productTypeCreateCommand);
        testProduct1 = createTestProduct(productType, "Schuh", "shoe", "red", "M");
        testProduct2 = createTestProduct(productType, "Hemd", "shirt", "blue", "XL");
        final ProductProjectionSearch search = new ProductProjectionSearch(STAGED);
        execute(search, res -> {
            final List<String> ids = toIds(res.getResults());
            return ids.contains(testProduct1.getId()) && ids.contains(testProduct2.getId());
        });
        try {
            Thread.sleep(500); // Wait for elasticsearch synchronization
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static Product createTestProduct(ProductType productType, String germanName, String englishName, String color, final String size) {
        final LocalizedString name = LocalizedString.of(GERMAN, germanName, ENGLISH, englishName);
        final NewProductVariant variant = NewProductVariantBuilder.of()
                .attributes(Attribute.of(COLOR, color), Attribute.of(SIZE, size))
                .price(Price.of(new BigDecimal("23.45"), "EUR"))
                .build();
        final NewProduct newProduct = NewProductBuilder.of(productType, name, name, variant).build();
        return execute(new ProductCreateCommand(newProduct));
    }

    @AfterClass
    public static void removeProducts() {
        List<ProductType> productTypes = execute(new ProductTypeQuery().byName(TEST_CLASS_NAME)).getResults();
        if (!productTypes.isEmpty()) {
            final List<ProductProjection> products = execute(new ProductProjectionQuery(STAGED)
                    .withPredicate(ProductProjectionQuery.model().productType().isAnyOf(productTypes))).getResults();
            products.forEach(p -> execute(new ProductDeleteByIdCommand(p.toProductVersioned())));
            productTypes.forEach(p -> execute(new ProductTypeDeleteByIdCommand(p)));
        }
        testProduct1 = null;
        testProduct2 = null;
        productType = null;
    }

    @Test
    public void searchByTextInACertainLanguage() throws Exception {
        final Search<ProductProjection> search = new ProductProjectionSearch(STAGED).withText(ENGLISH, "shoe");
        final PagedSearchResult<ProductProjection> pagedSearchResult = execute(search);
        //end example parsing here
        assertThat(toIds(pagedSearchResult.getResults())).containsExactly(testProduct1.getId());
    }

    @Test
    public void sortByAnAttribute() throws Exception {
        final List<String> expectedAsc = asList(testProduct2.getId(), testProduct1.getId());
        testSorting("name.en asc", expectedAsc);
        testSorting("name.en desc", Lists.reverse(expectedAsc));
    }

    @Test
    public void responseContainsRangeFacetsForAttributes() throws Exception {
        final String attrKey = PRICE_ATTRIBUTE_KEY;
        final FacetExpression<ProductProjection> rangeFacetExpression = FacetExpression.of(attrKey + ":range(0 to *)");
        final Search<ProductProjection> search = new ProductProjectionSearch(STAGED)
                .plusFacet(rangeFacetExpression);
        final PagedSearchResult<ProductProjection> pagedSearchResult = execute(search);
        final RangeFacetResult rangeFacet = (RangeFacetResult) pagedSearchResult.getFacetsResults().get(attrKey);
        //end example parsing here
        assertThat(rangeFacet.getRanges().get(0).getCount()).isGreaterThan(0);
    }

    @Test
    public void responseContainsTermFacetsForAttributes() throws Exception {
        final String attrKey = COLOR_ATTRIBUTE_KEY;
        final FacetExpression<ProductProjection> termFacetExpression = FacetExpression.of(attrKey);
        final Search<ProductProjection> search = new ProductProjectionSearch(STAGED)
                .plusFacet(termFacetExpression);
        final PagedSearchResult<ProductProjection> pagedSearchResult = execute(search);
        final TermFacetResult facetResult = (TermFacetResult) pagedSearchResult.getFacetsResults().get(attrKey);
        //end example parsing here
        assertThat(facetResult.getTerms().stream()
                .anyMatch(termStat -> termStat.getTerm().equals("blue") && termStat.getCount() > 0))
                .isTrue();
    }

    @Test
    public void resultsAndFacetsAreFilteredByColor() throws Exception {
        final FilterExpression<ProductProjection> filter = FilterExpression.of(COLOR_ATTRIBUTE_KEY + ":\"blue\"");
        final SearchDsl<ProductProjection> search = new ProductProjectionSearch(STAGED)
                .plusFacet(FacetExpression.of(SIZE_ATTRIBUTE_KEY))
                .plusFilterResult(filter)
                .plusFilterFacet(filter);
        final PagedSearchResult<ProductProjection> pagedSearchResult = execute(search);
        final TermFacetResult termFacetResult = (TermFacetResult) pagedSearchResult.getFacetsResults().get(SIZE_ATTRIBUTE_KEY);
        assertThat(termFacetResult.getTerms()).containsExactly(TermStats.of("XL", 1));
        assertThat(pagedSearchResult.size()).isEqualTo(1);
        assertThat(pagedSearchResult.getResults().get(0).getId()).isEqualTo(testProduct2.getId());
    }

    @Test
    public void onlyResultsAreFilteredByColor() throws Exception {
        final FilterExpression<ProductProjection> filter = FilterExpression.of(COLOR_ATTRIBUTE_KEY + ":\"blue\"");
        final SearchDsl<ProductProjection> search = new ProductProjectionSearch(STAGED)
                .plusFacet(FacetExpression.of(SIZE_ATTRIBUTE_KEY))
                .plusFilterResult(filter);
        final PagedSearchResult<ProductProjection> pagedSearchResult = execute(search);
        final TermFacetResult termFacetResult = (TermFacetResult) pagedSearchResult.getFacetsResults().get(SIZE_ATTRIBUTE_KEY);
        assertThat(new HashSet<>(termFacetResult.getTerms())).containsOnly(TermStats.of("XL", 1), TermStats.of("M", 1));
        assertThat(pagedSearchResult.size()).isEqualTo(1);
        assertThat(pagedSearchResult.getResults().get(0).getId()).isEqualTo(testProduct2.getId());
    }

    @Test
    public void onlyFacetsAreFilteredByColor() throws Exception {
        final FilterExpression<ProductProjection> filter = FilterExpression.of(COLOR_ATTRIBUTE_KEY + ":\"blue\"");
        final SearchDsl<ProductProjection> search = new ProductProjectionSearch(STAGED)
                .plusFacet(FacetExpression.of(SIZE_ATTRIBUTE_KEY))
                .plusFilterFacet(filter);
        final PagedSearchResult<ProductProjection> pagedSearchResult = execute(search);
        final TermFacetResult termFacetResult = (TermFacetResult) pagedSearchResult.getFacetsResults().get(SIZE_ATTRIBUTE_KEY);
        assertThat(termFacetResult.getTerms()).containsExactly(TermStats.of("XL", 1));
        final HashSet<String> ids = new HashSet<>(toIds(pagedSearchResult.getResults()));
        assertThat(ids).contains(testProduct2.getId(), testProduct1.getId());
    }

    @Test
    public void resultsArePaginated() throws Exception {
        final FilterExpression<ProductProjection> filterProductType = FilterExpression.of(String.format(COLOR_ATTRIBUTE_KEY + ":\"%s\",\"%s\"", "red", "blue"));
        final SearchDsl<ProductProjection> search = new ProductProjectionSearch(STAGED).plusFilterQuery(filterProductType)
                .withSort(SearchSort.of("name.en asc")).withOffset(1).withLimit(1);
        final PagedSearchResult<ProductProjection> pagedSearchResult = execute(search);
        assertThat(toIds(pagedSearchResult.getResults())).containsExactly(testProduct1.getId());
    }

    @Test
    public void filterQueryFiltersBeforeFacetsAreCalculated() throws Exception {
        final FilterExpression<ProductProjection> filter = FilterExpression.of(COLOR_ATTRIBUTE_KEY + ":\"blue\"");
        final SearchDsl<ProductProjection> search = new ProductProjectionSearch(STAGED)
                .plusFacet(FacetExpression.of(SIZE_ATTRIBUTE_KEY))
                .plusFilterQuery(filter);
        final PagedSearchResult<ProductProjection> pagedSearchResult = execute(search);
        assertThat(pagedSearchResult.size()).isEqualTo(1);
        assertThat(pagedSearchResult.getResults().get(0).getId()).isEqualTo(testProduct2.getId());
        final TermFacetResult termFacetResult = (TermFacetResult) pagedSearchResult.getFacetsResults().get(SIZE_ATTRIBUTE_KEY);
        assertThat(termFacetResult.getTerms()).containsExactly(TermStats.of("XL", 1));
    }

    private void testSorting(String sphereSortExpression, List<String> expected) {
        final SearchDsl<ProductProjection> search = new ProductProjectionSearch(STAGED).withSort(SearchSort.of(sphereSortExpression));
        final PagedSearchResult<ProductProjection> pagedSearchResult = execute(search);
        final List<String> filteredId = toIds(pagedSearchResult.getResults()).stream()
                .filter(id -> id.equals(testProduct1.getId()) || id.equals(testProduct2.getId())).collect(toList());
        assertThat(filteredId).isEqualTo(expected);
    }

    protected static <T> T execute(final ClientRequest<T> clientRequest, final Predicate<T> isOk) {
        return execute(clientRequest, 9, isOk);
    }

    protected static <T> T execute(final ClientRequest<T> clientRequest, final int attemptsLeft, final Predicate<T> isOk) {
        if (attemptsLeft < 1) {
            fail("Could not satisfy the request.");
        }

        T result = execute(clientRequest);
        if (isOk.test(result)) {
            return result;
        } else {
            Logger.info("attempts left " + (attemptsLeft - 1));
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
        final Search<ProductProjection> search = new ProductProjectionSearch(STAGED)
                .withSort(sort)
                .withOffset(50)
                .withLimit(25);
    }

    private SearchSort<ProductProjection> getASortExpressionSomeHow() {
        return null;
    }
}
