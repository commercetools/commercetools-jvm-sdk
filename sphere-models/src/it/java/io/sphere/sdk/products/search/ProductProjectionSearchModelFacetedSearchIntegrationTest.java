package io.sphere.sdk.products.search;

import io.sphere.sdk.products.ProductProjection;
import io.sphere.sdk.search.*;
import io.sphere.sdk.search.model.SimpleRangeStats;
import org.junit.Test;

import java.util.List;
import java.util.function.Consumer;

import static io.sphere.sdk.search.model.FilterRange.atLeast;
import static io.sphere.sdk.search.model.FilterRange.atMost;
import static io.sphere.sdk.test.SphereTestUtils.*;
import static org.assertj.core.api.Assertions.assertThat;
import static io.sphere.sdk.products.ProductsScenario1Fixtures.*;

public class ProductProjectionSearchModelFacetedSearchIntegrationTest extends ProductProjectionSearchModelIntegrationTest {

    private static final ProductProjectionFacetAndFilterSearchModel FACETED_SEARCH = ProductProjectionSearchModel.of().facetedSearch();

    @Test
    public void facetedSearchOnBooleanAttributes() throws Exception {
        testResultWithTerms(FACETED_SEARCH.allVariants().attribute().ofBoolean(ATTR_NAME_BOOLEAN).by(BOOL_TRUE),
                ids -> assertThat(ids).containsOnly(product1.getId()),
                termStats -> assertThat(termStats).containsOnly(
                        TermStats.of(BOOL_TRUE, 1L),
                        TermStats.of(BOOL_FALSE, 1L)));
    }

    @Test
    public void facetedSearchOnTextAttributes() throws Exception {
        testResultWithTerms(FACETED_SEARCH.allVariants().attribute().ofString(ATTR_NAME_TEXT).by(TEXT_FOO),
                ids -> assertThat(ids).containsOnly(product1.getId()),
                termStats -> assertThat(termStats).containsOnly(
                        TermStats.of(TEXT_FOO, 1L),
                        TermStats.of(TEXT_BAR, 1L)));
    }

    @Test
    public void facetedSearchOnLocTextAttributes() throws Exception {
        testResultWithTerms(FACETED_SEARCH.allVariants().attribute().ofLocalizedString(ATTR_NAME_LOC_TEXT).locale(ENGLISH).by(LOC_TEXT_FOO.get(ENGLISH)),
                ids -> assertThat(ids).containsOnly(product1.getId()),
                termStats -> assertThat(termStats).containsOnly(
                        TermStats.of(LOC_TEXT_FOO.get(ENGLISH), 1L),
                        TermStats.of(LOC_TEXT_BAR.get(ENGLISH), 1L)));
    }

    @Test
    public void facetedSearchOnEnumKeyAttributes() throws Exception {
        testResultWithTerms(FACETED_SEARCH.allVariants().attribute().ofEnum(ATTR_NAME_ENUM).key().by(ENUM_TWO.getKey()),
                ids -> assertThat(ids).containsOnly(product1.getId()),
                termStats -> assertThat(termStats).containsOnly(
                        TermStats.of(ENUM_TWO.getKey(), 1L),
                        TermStats.of(ENUM_THREE.getKey(), 1L)));
    }

    @Test
    public void facetedSearchOnEnumLabelAttributes() throws Exception {
        testResultWithTerms(FACETED_SEARCH.allVariants().attribute().ofEnum(ATTR_NAME_ENUM).label().by(ENUM_TWO.getLabel()),
                ids -> assertThat(ids).containsOnly(product1.getId()),
                termStats -> assertThat(termStats).containsOnly(
                        TermStats.of(ENUM_TWO.getLabel(), 1L),
                        TermStats.of(ENUM_THREE.getLabel(), 1L)));
    }

    @Test
    public void facetedSearchOnLocEnumKeyAttributes() throws Exception {
        testResultWithTerms(FACETED_SEARCH.allVariants().attribute().ofLocalizedEnum(ATTR_NAME_LOC_ENUM).key().by(LOC_ENUM_TWO.getKey()),
                ids -> assertThat(ids).containsOnly(product1.getId()),
                termStats -> assertThat(termStats).containsOnly(
                        TermStats.of(LOC_ENUM_TWO.getKey(), 1L),
                        TermStats.of(LOC_ENUM_THREE.getKey(), 1L)));
    }

    @Test
    public void facetedSearchOnLocEnumLabelAttributes() throws Exception {
        testResultWithTerms(FACETED_SEARCH.allVariants().attribute().ofLocalizedEnum(ATTR_NAME_LOC_ENUM).label().locale(GERMAN).by(LOC_ENUM_TWO.getLabel().get(GERMAN)),
                ids -> assertThat(ids).containsOnly(product1.getId()),
                termStats -> assertThat(termStats).containsOnly(
                        TermStats.of(LOC_ENUM_TWO.getLabel().get(GERMAN), 1L),
                        TermStats.of(LOC_ENUM_THREE.getLabel().get(GERMAN), 1L)));
    }

    @Test
    public void facetedSearchOnNumberAttributes() throws Exception {
        testResultWithTerms(FACETED_SEARCH.allVariants().attribute().ofNumber(ATTR_NAME_NUMBER).by(NUMBER_5.toPlainString()),
                ids -> assertThat(ids).containsOnly(product1.getId()),
                termStats -> assertThat(termStats).containsOnly(
                        TermStats.of("5.0", 1L),
                        TermStats.of("10.0", 1L)));
    }

    @Test
    public void facetedSearchOnNumberRangedAttributes() throws Exception {
        testResultWithRange(FACETED_SEARCH.allVariants().attribute().ofNumber(ATTR_NAME_NUMBER).byRange(atMost(NUMBER_5.toPlainString())),
                ids -> assertThat(ids).containsOnly(product1.getId()),
                rangeStats -> {
                    assertThat(rangeStats.getLowerEndpoint()).isEqualTo(null);
                    assertThat(rangeStats.getUpperEndpoint()).isEqualTo(null);
                    assertThat(rangeStats.getCount()).isEqualTo(2L);
                    assertThat(rangeStats.getMin()).isEqualTo("5.0");
                    assertThat(rangeStats.getMax()).isEqualTo("10.0");
                });
    }

    @Test
    public void facetedSearchOnMoneyAmountAttributes() throws Exception {
        testResultWithTerms(FACETED_SEARCH.allVariants().attribute().ofMoney(ATTR_NAME_MONEY).centAmount().by(toCents(MONEY_500_EUR).toString()),
                ids -> assertThat(ids).containsOnly(product1.getId()),
                termStats -> assertThat(termStats).containsOnly(
                        TermStats.of("50000", 1L),
                        TermStats.of("100000", 1L)));
    }

    @Test
    public void facetedSearchOnMoneyAmountRangedAttributes() throws Exception {
        testResultWithRange(FACETED_SEARCH.allVariants().attribute().ofMoney(ATTR_NAME_MONEY).centAmount().byRange(atMost(toCents(MONEY_500_EUR).toString())),
                ids -> assertThat(ids).containsOnly(product1.getId()),
                rangeStats -> {
                    assertThat(rangeStats.getLowerEndpoint()).isEqualTo(null);
                    assertThat(rangeStats.getUpperEndpoint()).isEqualTo(null);
                    assertThat(rangeStats.getCount()).isEqualTo(2L);
                    assertThat(rangeStats.getMin()).isEqualTo("50000.0");
                    assertThat(rangeStats.getMax()).isEqualTo("100000.0");
                });
    }

    @Test
    public void facetedSearchOnMoneyCurrencyAttributes() throws Exception {
        testResultWithTerms(FACETED_SEARCH.allVariants().attribute().ofMoney(ATTR_NAME_MONEY).currency().by(MONEY_500_EUR.getCurrency().getCurrencyCode()),
                ids -> assertThat(ids).containsOnly(product1.getId()),
                termStats -> assertThat(termStats).containsOnly(
                        TermStats.of("EUR", 1L),
                        TermStats.of("USD", 1L)));
    }

    @Test
    public void facetedSearchOnDateAttributes() throws Exception {
        testResultWithTerms(FACETED_SEARCH.allVariants().attribute().ofDate(ATTR_NAME_DATE).by(DATE_2001.toString()),
                ids -> assertThat(ids).containsOnly(product1.getId()),
                termStats -> assertThat(termStats).containsOnly(
                        TermStats.of("2001-09-11", 1L),
                        TermStats.of("2002-10-12", 1L)));
    }

    @Test
    public void facetedSearchOnDateRangedAttributes() throws Exception {
        testResultWithRange(FACETED_SEARCH.allVariants().attribute().ofDate(ATTR_NAME_DATE).byRange(atMost(DATE_2001.toString())),
                ids -> assertThat(ids).containsOnly(product1.getId()),
                rangeStats -> {
                    assertThat(rangeStats.getLowerEndpoint()).isEqualTo(null);
                    assertThat(rangeStats.getUpperEndpoint()).isEqualTo(null);
                    assertThat(rangeStats.getCount()).isEqualTo(2L);
                    assertThat(rangeStats.getMin()).isEqualTo("1.0001664E12");
                    assertThat(rangeStats.getMax()).isEqualTo("1.0343808E12");
                });
    }

    @Test
    public void facetedSearchOnTimeAttributes() throws Exception {
        testResultWithTerms(FACETED_SEARCH.allVariants().attribute().ofTime(ATTR_NAME_TIME).by(TIME_22H.toString()),
                ids -> assertThat(ids).containsOnly(product1.getId()),
                termStats -> assertThat(termStats).containsOnly(
                        TermStats.of("22:05:09.203", 1L),
                        TermStats.of("23:06:10.204", 1L)));
    }

    @Test
    public void facetedSearchOnTimeRangedAttributes() throws Exception {
        testResultWithRange(FACETED_SEARCH.allVariants().attribute().ofTime(ATTR_NAME_TIME).byRange(atMost(TIME_22H.toString())),
                ids -> assertThat(ids).containsOnly(product1.getId()),
                rangeStats -> {
                    assertThat(rangeStats.getLowerEndpoint()).isEqualTo(null);
                    assertThat(rangeStats.getUpperEndpoint()).isEqualTo(null);
                    assertThat(rangeStats.getCount()).isEqualTo(2L);
                    assertThat(rangeStats.getMin()).isEqualTo("7.9509203E7");
                    assertThat(rangeStats.getMax()).isEqualTo("8.3170204E7");
                });
    }

    @Test
    public void facetedSearchOnDateTimeAttributes() throws Exception {
        testResultWithTerms(FACETED_SEARCH.allVariants().attribute().ofDateTime(ATTR_NAME_DATE_TIME).by(DATE_TIME_2001_22H.toString()),
                ids -> assertThat(ids).containsOnly(product1.getId()),
                termStats -> assertThat(termStats).containsOnly(
                        TermStats.of("2002-10-12T23:06:10.204+0000", 1L),
                        TermStats.of("2001-09-11T22:05:09.203+0000", 1L)));
    }

    @Test
    public void facetedSearchOnDateTimeRangedAttributes() throws Exception {
        testResultWithRange(FACETED_SEARCH.allVariants().attribute().ofDateTime(ATTR_NAME_DATE_TIME).byRange(atMost(DATE_TIME_2001_22H.toString())),
                ids -> assertThat(ids).containsOnly(product1.getId()),
                rangeStats -> {
                    assertThat(rangeStats.getLowerEndpoint()).isEqualTo(null);
                    assertThat(rangeStats.getUpperEndpoint()).isEqualTo(null);
                    assertThat(rangeStats.getCount()).isEqualTo(2L);
                    assertThat(rangeStats.getMin()).isEqualTo("1.000245909203E12");
                    assertThat(rangeStats.getMax()).isEqualTo("1.034463970204E12");
                });
    }

    @Test
    public void facetedSearchOnReferenceAttributes() throws Exception {
        testResultWithTerms(FACETED_SEARCH.allVariants().attribute().ofReference(ATTR_NAME_REF).id().by(productSomeId.getId()),
                ids -> assertThat(ids).containsOnly(product1.getId()),
                termStats -> assertThat(termStats).containsOnly(
                        TermStats.of(productSomeId.getId(), 1L),
                        TermStats.of(productOtherId.getId(), 1L)));
    }

    @Test
    public void facetedSearchOnBooleanSetAttributes() throws Exception {
        testResultWithTerms(FACETED_SEARCH.allVariants().attribute().ofBooleanSet(ATTR_NAME_BOOLEAN_SET).by(BOOL_FALSE),
                ids -> assertThat(ids).containsOnly(product1.getId()),
                termStats -> assertThat(termStats).containsExactly(
                        TermStats.of(BOOL_TRUE, 2L),
                        TermStats.of(BOOL_FALSE, 1L)));
    }

    @Test
    public void facetedSearchOnTextSetAttributes() throws Exception {
        testResultWithTerms(FACETED_SEARCH.allVariants().attribute().ofStringSet(ATTR_NAME_TEXT_SET).by(TEXT_BAR),
                ids -> assertThat(ids).containsOnly(product1.getId()),
                termStats -> assertThat(termStats).containsExactly(
                        TermStats.of(TEXT_FOO, 2L),
                        TermStats.of(TEXT_BAR, 1L)));
    }

    @Test
    public void facetedSearchOnLocTextSetAttributes() throws Exception {
        testResultWithTerms(FACETED_SEARCH.allVariants().attribute().ofLocalizedStringSet(ATTR_NAME_LOC_TEXT_SET).locale(ENGLISH).by(LOC_TEXT_BAR.get(ENGLISH)),
                ids -> assertThat(ids).containsOnly(product1.getId()),
                termStats -> assertThat(termStats).containsExactly(
                        TermStats.of(LOC_TEXT_FOO.get(ENGLISH), 2L),
                        TermStats.of(LOC_TEXT_BAR.get(ENGLISH), 1L)));
    }

    @Test
    public void facetedSearchOnEnumKeySetAttributes() throws Exception {
        testResultWithTerms(FACETED_SEARCH.allVariants().attribute().ofEnumSet(ATTR_NAME_ENUM_SET).key().by(ENUM_THREE.getKey()),
                ids -> assertThat(ids).containsOnly(product1.getId()),
                termStats -> assertThat(termStats).containsExactly(
                        TermStats.of(ENUM_TWO.getKey(), 2L),
                        TermStats.of(ENUM_THREE.getKey(), 1L)));
    }

    @Test
    public void facetedSearchOnEnumLabelSetAttributes() throws Exception {
        testResultWithTerms(FACETED_SEARCH.allVariants().attribute().ofEnumSet(ATTR_NAME_ENUM_SET).label().by(ENUM_THREE.getLabel()),
                ids -> assertThat(ids).containsOnly(product1.getId()),
                termStats -> assertThat(termStats).containsExactly(
                        TermStats.of(ENUM_TWO.getLabel(), 2L),
                        TermStats.of(ENUM_THREE.getLabel(), 1L)));
    }

    @Test
    public void facetedSearchOnLocEnumKeySetAttributes() throws Exception {
        testResultWithTerms(FACETED_SEARCH.allVariants().attribute().ofLocalizedEnumSet(ATTR_NAME_LOC_ENUM_SET).key().by(LOC_ENUM_THREE.getKey()),
                ids -> assertThat(ids).containsOnly(product1.getId()),
                termStats -> assertThat(termStats).containsExactly(
                        TermStats.of(LOC_ENUM_TWO.getKey(), 2L),
                        TermStats.of(LOC_ENUM_THREE.getKey(), 1L)));
    }

    @Test
    public void facetedSearchOnLocEnumLabelSetAttributes() throws Exception {
        testResultWithTerms(FACETED_SEARCH.allVariants().attribute().ofLocalizedEnumSet(ATTR_NAME_LOC_ENUM_SET).label().locale(GERMAN).by(LOC_ENUM_THREE.getLabel().get(GERMAN)),
                ids -> assertThat(ids).containsOnly(product1.getId()),
                termStats -> assertThat(termStats).containsExactly(
                        TermStats.of(LOC_ENUM_TWO.getLabel().get(GERMAN), 2L),
                        TermStats.of(LOC_ENUM_THREE.getLabel().get(GERMAN), 1L)));
    }

    @Test
    public void facetedSearchOnNumberSetAttributes() throws Exception {
        testResultWithTerms(FACETED_SEARCH.allVariants().attribute().ofNumberSet(ATTR_NAME_NUMBER_SET).by(NUMBER_10.toPlainString()),
                ids -> assertThat(ids).containsOnly(product1.getId()),
                termStats -> assertThat(termStats).containsExactly(
                        TermStats.of("5.0", 2L),
                        TermStats.of("10.0", 1L)));
    }

    @Test
    public void facetedSearchOnNumberRangedSetAttributes() throws Exception {
        testResultWithRange(FACETED_SEARCH.allVariants().attribute().ofNumberSet(ATTR_NAME_NUMBER_SET).byRange(atLeast(NUMBER_10.toPlainString())),
                ids -> assertThat(ids).containsOnly(product1.getId()),
                rangeStats -> {
                    assertThat(rangeStats.getLowerEndpoint()).isEqualTo(null);
                    assertThat(rangeStats.getUpperEndpoint()).isEqualTo(null);
                    assertThat(rangeStats.getCount()).isEqualTo(2L);
                    assertThat(rangeStats.getMin()).isEqualTo("5.0");
                    assertThat(rangeStats.getMax()).isEqualTo("5.0");
                });
    }

    @Test
    public void facetedSearchOnMoneyAmountSetAttributes() throws Exception {
        testResultWithTerms(FACETED_SEARCH.allVariants().attribute().ofMoneySet(ATTR_NAME_MONEY_SET).centAmount().by(toCents(MONEY_1000_USD).toString()),
                ids -> assertThat(ids).containsOnly(product1.getId()),
                termStats -> assertThat(termStats).containsExactly(
                        TermStats.of("50000", 2L),
                        TermStats.of("100000", 1L)));
    }

    @Test
    public void facetedSearchOnMoneyAmountRangedSetAttributes() throws Exception {
        testResultWithRange(FACETED_SEARCH.allVariants().attribute().ofMoneySet(ATTR_NAME_MONEY_SET).centAmount().byRange(atLeast(toCents(MONEY_1000_USD).toString())),
                ids -> assertThat(ids).containsOnly(product1.getId()),
                rangeStats -> {
                    assertThat(rangeStats.getLowerEndpoint()).isEqualTo(null);
                    assertThat(rangeStats.getUpperEndpoint()).isEqualTo(null);
                    assertThat(rangeStats.getCount()).isEqualTo(2L);
                    assertThat(rangeStats.getMin()).isEqualTo("50000.0");
                    assertThat(rangeStats.getMax()).isEqualTo("50000.0");
                });
    }

    @Test
    public void facetedSearchOnMoneyCurrencySetAttributes() throws Exception {
        testResultWithTerms(FACETED_SEARCH.allVariants().attribute().ofMoneySet(ATTR_NAME_MONEY_SET).currency().by(MONEY_1000_USD.getCurrency().getCurrencyCode()),
                ids -> assertThat(ids).containsOnly(product1.getId()),
                termStats -> assertThat(termStats).containsExactly(
                        TermStats.of("EUR", 2L),
                        TermStats.of("USD", 1L)));
    }

    @Test
    public void facetedSearchOnDateSetAttributes() throws Exception {
        testResultWithTerms(FACETED_SEARCH.allVariants().attribute().ofDateSet(ATTR_NAME_DATE_SET).by(DATE_2002.toString()),
                ids -> assertThat(ids).containsOnly(product1.getId()),
                termStats -> assertThat(termStats).containsExactly(
                        TermStats.of("2001-09-11", 2L),
                        TermStats.of("2002-10-12", 1L)));
    }

    @Test
    public void facetedSearchOnDateRangedSetAttributes() throws Exception {
        testResultWithRange(FACETED_SEARCH.allVariants().attribute().ofDateSet(ATTR_NAME_DATE_SET).byRange(atLeast(DATE_2002.toString())),
                ids -> assertThat(ids).containsOnly(product1.getId()),
                rangeStats -> {
                    assertThat(rangeStats.getLowerEndpoint()).isEqualTo(null);
                    assertThat(rangeStats.getUpperEndpoint()).isEqualTo(null);
                    assertThat(rangeStats.getCount()).isEqualTo(2L);
                    assertThat(rangeStats.getMin()).isEqualTo("1.0001664E12");
                    assertThat(rangeStats.getMax()).isEqualTo("1.0001664E12");
                });
    }

    @Test
    public void facetedSearchOnTimeSetAttributes() throws Exception {
        testResultWithTerms(FACETED_SEARCH.allVariants().attribute().ofTimeSet(ATTR_NAME_TIME_SET).by(TIME_23H.toString()),
                ids -> assertThat(ids).containsOnly(product1.getId()),
                termStats -> assertThat(termStats).containsExactly(
                        TermStats.of("22:05:09.203", 2L),
                        TermStats.of("23:06:10.204", 1L)));
    }

    @Test
    public void facetedSearchOnTimeRangedSetAttributes() throws Exception {
        testResultWithRange(FACETED_SEARCH.allVariants().attribute().ofTimeSet(ATTR_NAME_TIME_SET).byRange(atLeast(TIME_23H.toString())),
                ids -> assertThat(ids).containsOnly(product1.getId()),
                rangeStats -> {
                    assertThat(rangeStats.getLowerEndpoint()).isEqualTo(null);
                    assertThat(rangeStats.getUpperEndpoint()).isEqualTo(null);
                    assertThat(rangeStats.getCount()).isEqualTo(2L);
                    assertThat(rangeStats.getMin()).isEqualTo("7.9509203E7");
                    assertThat(rangeStats.getMax()).isEqualTo("7.9509203E7");
                });
    }

    @Test
    public void facetedSearchOnDateTimeSetAttributes() throws Exception {
        testResultWithTerms(FACETED_SEARCH.allVariants().attribute().ofDateTimeSet(ATTR_NAME_DATE_TIME_SET).by(DATE_TIME_2002_23H.toString()),
                ids -> assertThat(ids).containsOnly(product1.getId()),
                termStats -> assertThat(termStats).containsExactly(
                        TermStats.of("2001-09-11T22:05:09.203+0000", 2L),
                        TermStats.of("2002-10-12T23:06:10.204+0000", 1L)));
    }

    @Test
    public void facetedSearchOnDateTimeRangedSetAttributes() throws Exception {
        testResultWithRange(FACETED_SEARCH.allVariants().attribute().ofDateTimeSet(ATTR_NAME_DATE_TIME_SET).byRange(atLeast(DATE_TIME_2002_23H.toString())),
                ids -> assertThat(ids).containsOnly(product1.getId()),
                rangeStats -> {
                    assertThat(rangeStats.getLowerEndpoint()).isEqualTo(null);
                    assertThat(rangeStats.getUpperEndpoint()).isEqualTo(null);
                    assertThat(rangeStats.getCount()).isEqualTo(2L);
                    assertThat(rangeStats.getMin()).isEqualTo("1.000245909203E12");
                    assertThat(rangeStats.getMax()).isEqualTo("1.000245909203E12");
                });
    }

    @Test
    public void facetedSearchOnReferenceSetAttributes() throws Exception {
        testResultWithTerms(FACETED_SEARCH.allVariants().attribute().ofReferenceSet(ATTR_NAME_REF_SET).id().by(productOtherId.getId()),
                ids -> assertThat(ids).containsOnly(product1.getId()),
                termStats -> assertThat(termStats).containsExactly(
                        TermStats.of(productSomeId.getId(), 2L),
                        TermStats.of(productOtherId.getId(), 1L)));
    }

    private static void testResultWithTerms(final TermFacetAndFilterExpression<ProductProjection> facetedSearchExpr,
                                            final Consumer<List<String>> testFilter, final Consumer<List<TermStats>> testTerms) {
        final PagedSearchResult<ProductProjection> result = executeFacetedSearch(facetedSearchExpr, testFilter);
        testTerms.accept(result.getTermFacetResult(facetedSearchExpr.facetExpression()).getTerms());
    }

    private static void testResultWithRange(final RangeFacetAndFilterExpression<ProductProjection> facetedSearchExpr,
                                            final Consumer<List<String>> testFilter, final Consumer<SimpleRangeStats> rangeStats) {
        final PagedSearchResult<ProductProjection> result = executeFacetedSearch(facetedSearchExpr, testFilter);
        rangeStats.accept(result.getRangeStatsOfAllRanges(facetedSearchExpr.facetExpression()));
    }

    private static PagedSearchResult<ProductProjection> executeFacetedSearch(final FacetAndFilterExpression<ProductProjection> facetedSearchExpr,
                                                                             final Consumer<List<String>> testFilter) {
        final PagedSearchResult<ProductProjection> result = executeSearch(ProductProjectionSearch.ofStaged().plusFacetedSearch(facetedSearchExpr));
        testFilter.accept(toIds(result.getResults()));
        return result;
    }
}