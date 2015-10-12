package io.sphere.sdk.products.search;

import io.sphere.sdk.products.ProductProjection;
import io.sphere.sdk.search.*;
import org.junit.Test;

import java.util.List;
import java.util.function.Consumer;

import static io.sphere.sdk.search.FilterRange.atLeast;
import static io.sphere.sdk.search.FilterRange.atMost;
import static io.sphere.sdk.test.SphereTestUtils.*;
import static org.assertj.core.api.Assertions.assertThat;

public class ProductProjectionSearchModelFacetedSearchIntegrationTest extends ProductProjectionSearchModelIntegrationTest {

    @Test
    public void facetedSearchOnBooleanAttributes() throws Exception {
        testResultWithTerms(model().allVariants().attribute().ofBoolean(ATTR_NAME_BOOLEAN).facetedAndFiltered().by(BOOL_TRUE),
                ids -> assertThat(ids).containsOnly(product1.getId()),
                termStats -> assertThat(termStats).containsOnly(
                        TermStats.of(BOOL_TRUE, 1),
                        TermStats.of(BOOL_FALSE, 1)));
    }

    @Test
    public void facetedSearchOnTextAttributes() throws Exception {
        testResultWithTerms(model().allVariants().attribute().ofString(ATTR_NAME_TEXT).facetedAndFiltered().by(TEXT_FOO),
                ids -> assertThat(ids).containsOnly(product1.getId()),
                termStats -> assertThat(termStats).containsOnly(
                        TermStats.of(TEXT_FOO, 1),
                        TermStats.of(TEXT_BAR, 1)));
    }

    @Test
    public void facetedSearchOnLocTextAttributes() throws Exception {
        testResultWithTerms(model().allVariants().attribute().ofLocalizedString(ATTR_NAME_LOC_TEXT).locale(ENGLISH).facetedAndFiltered().by(LOC_TEXT_FOO.get(ENGLISH)),
                ids -> assertThat(ids).containsOnly(product1.getId()),
                termStats -> assertThat(termStats).containsOnly(
                        TermStats.of(LOC_TEXT_FOO.get(ENGLISH), 1),
                        TermStats.of(LOC_TEXT_BAR.get(ENGLISH), 1)));
    }

    @Test
    public void facetedSearchOnEnumKeyAttributes() throws Exception {
        testResultWithTerms(model().allVariants().attribute().ofEnum(ATTR_NAME_ENUM).key().facetedAndFiltered().by(ENUM_TWO.getKey()),
                ids -> assertThat(ids).containsOnly(product1.getId()),
                termStats -> assertThat(termStats).containsOnly(
                        TermStats.of(ENUM_TWO.getKey(), 1),
                        TermStats.of(ENUM_THREE.getKey(), 1)));
    }

    @Test
    public void facetedSearchOnEnumLabelAttributes() throws Exception {
        testResultWithTerms(model().allVariants().attribute().ofEnum(ATTR_NAME_ENUM).label().facetedAndFiltered().by(ENUM_TWO.getLabel()),
                ids -> assertThat(ids).containsOnly(product1.getId()),
                termStats -> assertThat(termStats).containsOnly(
                        TermStats.of(ENUM_TWO.getLabel(), 1),
                        TermStats.of(ENUM_THREE.getLabel(), 1)));
    }

    @Test
    public void facetedSearchOnLocEnumKeyAttributes() throws Exception {
        testResultWithTerms(model().allVariants().attribute().ofLocalizableEnum(ATTR_NAME_LOC_ENUM).key().facetedAndFiltered().by(LOC_ENUM_TWO.getKey()),
                ids -> assertThat(ids).containsOnly(product1.getId()),
                termStats -> assertThat(termStats).containsOnly(
                        TermStats.of(LOC_ENUM_TWO.getKey(), 1),
                        TermStats.of(LOC_ENUM_THREE.getKey(), 1)));
    }

    @Test
    public void facetedSearchOnLocEnumLabelAttributes() throws Exception {
        testResultWithTerms(model().allVariants().attribute().ofLocalizableEnum(ATTR_NAME_LOC_ENUM).label().locale(GERMAN).facetedAndFiltered().by(LOC_ENUM_TWO.getLabel().get(GERMAN)),
                ids -> assertThat(ids).containsOnly(product1.getId()),
                termStats -> assertThat(termStats).containsOnly(
                        TermStats.of(LOC_ENUM_TWO.getLabel().get(GERMAN), 1),
                        TermStats.of(LOC_ENUM_THREE.getLabel().get(GERMAN), 1)));
    }

    @Test
    public void facetedSearchOnNumberAttributes() throws Exception {
        testResultWithTerms(model().allVariants().attribute().ofNumber(ATTR_NAME_NUMBER).facetedAndFiltered().by(NUMBER_5.toPlainString()),
                ids -> assertThat(ids).containsOnly(product1.getId()),
                termStats -> assertThat(termStats).containsOnly(
                        TermStats.of("5.0", 1),
                        TermStats.of("10.0", 1)));
    }

    @Test
    public void facetedSearchOnNumberRangedAttributes() throws Exception {
        testResultWithRange(model().allVariants().attribute().ofNumber(ATTR_NAME_NUMBER).facetedAndFiltered().byRange(atMost(NUMBER_5.toPlainString())),
                ids -> assertThat(ids).containsOnly(product1.getId()),
                rangeStats -> {
                    assertThat(rangeStats.getLowerEndpoint()).isEqualTo("0.0");
                    assertThat(rangeStats.getUpperEndpoint()).isEqualTo(null);
                    assertThat(rangeStats.getCount()).isEqualTo(2L);
                    assertThat(rangeStats.getMin()).isEqualTo("5.0");
                    assertThat(rangeStats.getMax()).isEqualTo("10.0");
                    assertThat(rangeStats.getSum()).isEqualTo("15.0");
                    assertThat(rangeStats.getMean()).isEqualTo(7.50D);
                });
    }

    @Test
    public void facetedSearchOnMoneyAmountAttributes() throws Exception {
        testResultWithTerms(model().allVariants().attribute().ofMoney(ATTR_NAME_MONEY).centAmount().facetedAndFiltered().by(toCents(MONEY_500_EUR).toString()),
                ids -> assertThat(ids).containsOnly(product1.getId()),
                termStats -> assertThat(termStats).containsOnly(
                        TermStats.of("50000", 1),
                        TermStats.of("100000", 1)));
    }

    @Test
    public void facetedSearchOnMoneyAmountRangedAttributes() throws Exception {
        testResultWithRange(model().allVariants().attribute().ofMoney(ATTR_NAME_MONEY).centAmount().facetedAndFiltered().byRange(atMost(toCents(MONEY_500_EUR).toString())),
                ids -> assertThat(ids).containsOnly(product1.getId()),
                rangeStats -> {
                    assertThat(rangeStats.getLowerEndpoint()).isEqualTo("0.0");
                    assertThat(rangeStats.getUpperEndpoint()).isEqualTo(null);
                    assertThat(rangeStats.getCount()).isEqualTo(2L);
                    assertThat(rangeStats.getMin()).isEqualTo("50000.0");
                    assertThat(rangeStats.getMax()).isEqualTo("100000.0");
                    assertThat(rangeStats.getSum()).isEqualTo("150000.0");
                    assertThat(rangeStats.getMean()).isEqualTo(75000D);
                });
    }

    @Test
    public void facetedSearchOnMoneyCurrencyAttributes() throws Exception {
        testResultWithTerms(model().allVariants().attribute().ofMoney(ATTR_NAME_MONEY).currency().facetedAndFiltered().by(MONEY_500_EUR.getCurrency().getCurrencyCode()),
                ids -> assertThat(ids).containsOnly(product1.getId()),
                termStats -> assertThat(termStats).containsOnly(
                        TermStats.of("EUR", 1),
                        TermStats.of("USD", 1)));
    }

    @Test
    public void facetedSearchOnDateAttributes() throws Exception {
        testResultWithTerms(model().allVariants().attribute().ofDate(ATTR_NAME_DATE).facetedAndFiltered().by(DATE_2001.toString()),
                ids -> assertThat(ids).containsOnly(product1.getId()),
                termStats -> assertThat(termStats).containsOnly(
                        TermStats.of("2001-09-11", 1),
                        TermStats.of("2002-10-12", 1)));
    }

    @Test
    public void facetedSearchOnDateRangedAttributes() throws Exception {
        testResultWithRange(model().allVariants().attribute().ofDate(ATTR_NAME_DATE).facetedAndFiltered().byRange(atMost(DATE_2001.toString())),
                ids -> assertThat(ids).containsOnly(product1.getId()),
                rangeStats -> {
                    assertThat(rangeStats.getLowerEndpoint()).isEqualTo("0.0");
                    assertThat(rangeStats.getUpperEndpoint()).isEqualTo(null);
                    assertThat(rangeStats.getCount()).isEqualTo(2L);
                    assertThat(rangeStats.getMin()).isEqualTo("1.0001664E12");
                    assertThat(rangeStats.getMax()).isEqualTo("1.0343808E12");
                    assertThat(rangeStats.getSum()).isEqualTo("2.0345472E12");
                    assertThat(rangeStats.getMean()).isEqualTo(1.0172736E12D);
                });
    }

    @Test
    public void facetedSearchOnTimeAttributes() throws Exception {
        testResultWithTerms(model().allVariants().attribute().ofTime(ATTR_NAME_TIME).facetedAndFiltered().by(TIME_22H.toString()),
                ids -> assertThat(ids).containsOnly(product1.getId()),
                termStats -> assertThat(termStats).containsOnly(
                        TermStats.of("22:05:09.203", 1),
                        TermStats.of("23:06:10.204", 1)));
    }

    @Test
    public void facetedSearchOnTimeRangedAttributes() throws Exception {
        testResultWithRange(model().allVariants().attribute().ofTime(ATTR_NAME_TIME).facetedAndFiltered().byRange(atMost(TIME_22H.toString())),
                ids -> assertThat(ids).containsOnly(product1.getId()),
                rangeStats -> {
                    assertThat(rangeStats.getLowerEndpoint()).isEqualTo("0.0");
                    assertThat(rangeStats.getUpperEndpoint()).isEqualTo(null);
                    assertThat(rangeStats.getCount()).isEqualTo(2L);
                    assertThat(rangeStats.getMin()).isEqualTo("7.9509203E7");
                    assertThat(rangeStats.getMax()).isEqualTo("8.3170204E7");
                    assertThat(rangeStats.getSum()).isEqualTo("1.62679407E8");
                    assertThat(rangeStats.getMean()).isEqualTo(8.13397035E7D);
                });
    }

    @Test
    public void facetedSearchOnDateTimeAttributes() throws Exception {
        testResultWithTerms(model().allVariants().attribute().ofDateTime(ATTR_NAME_DATE_TIME).facetedAndFiltered().by(DATE_TIME_2001_22H.toString()),
                ids -> assertThat(ids).containsOnly(product1.getId()),
                termStats -> assertThat(termStats).containsOnly(
                        TermStats.of("2002-10-12T23:06:10.204+0000", 1),
                        TermStats.of("2001-09-11T22:05:09.203+0000", 1)));
    }

    @Test
    public void facetedSearchOnDateTimeRangedAttributes() throws Exception {
        testResultWithRange(model().allVariants().attribute().ofDateTime(ATTR_NAME_DATE_TIME).facetedAndFiltered().byRange(atMost(DATE_TIME_2001_22H.toString())),
                ids -> assertThat(ids).containsOnly(product1.getId()),
                rangeStats -> {
                    assertThat(rangeStats.getLowerEndpoint()).isEqualTo("0.0");
                    assertThat(rangeStats.getUpperEndpoint()).isEqualTo(null);
                    assertThat(rangeStats.getCount()).isEqualTo(2L);
                    assertThat(rangeStats.getMin()).isEqualTo("1.000245909203E12");
                    assertThat(rangeStats.getMax()).isEqualTo("1.034463970204E12");
                    assertThat(rangeStats.getSum()).isEqualTo("2.034709879407E12");
                    assertThat(rangeStats.getMean()).isEqualTo(1.0173549397035E12D);
                });
    }

    @Test
    public void facetedSearchOnReferenceAttributes() throws Exception {
        testResultWithTerms(model().allVariants().attribute().ofReference(ATTR_NAME_REF).id().facetedAndFiltered().by(productSomeId.getId()),
                ids -> assertThat(ids).containsOnly(product1.getId()),
                termStats -> assertThat(termStats).containsOnly(
                        TermStats.of(productSomeId.getId(), 1),
                        TermStats.of(productOtherId.getId(), 1)));
    }

    @Test
    public void facetedSearchOnBooleanSetAttributes() throws Exception {
        testResultWithTerms(model().allVariants().attribute().ofBoolean(ATTR_NAME_BOOLEAN_SET).facetedAndFiltered().by(BOOL_FALSE),
                ids -> assertThat(ids).containsOnly(product1.getId()),
                termStats -> assertThat(termStats).containsExactly(
                        TermStats.of(BOOL_TRUE, 2),
                        TermStats.of(BOOL_FALSE, 1)));
    }

    @Test
    public void facetedSearchOnTextSetAttributes() throws Exception {
        testResultWithTerms(model().allVariants().attribute().ofString(ATTR_NAME_TEXT_SET).facetedAndFiltered().by(TEXT_BAR),
                ids -> assertThat(ids).containsOnly(product1.getId()),
                termStats -> assertThat(termStats).containsExactly(
                        TermStats.of(TEXT_FOO, 2),
                        TermStats.of(TEXT_BAR, 1)));
    }

    @Test
    public void facetedSearchOnLocTextSetAttributes() throws Exception {
        testResultWithTerms(model().allVariants().attribute().ofLocalizedString(ATTR_NAME_LOC_TEXT_SET).locale(ENGLISH).facetedAndFiltered().by(LOC_TEXT_BAR.get(ENGLISH)),
                ids -> assertThat(ids).containsOnly(product1.getId()),
                termStats -> assertThat(termStats).containsExactly(
                        TermStats.of(LOC_TEXT_FOO.get(ENGLISH), 2),
                        TermStats.of(LOC_TEXT_BAR.get(ENGLISH), 1)));
    }

    @Test
    public void facetedSearchOnEnumKeySetAttributes() throws Exception {
        testResultWithTerms(model().allVariants().attribute().ofEnum(ATTR_NAME_ENUM_SET).key().facetedAndFiltered().by(ENUM_THREE.getKey()),
                ids -> assertThat(ids).containsOnly(product1.getId()),
                termStats -> assertThat(termStats).containsExactly(
                        TermStats.of(ENUM_TWO.getKey(), 2),
                        TermStats.of(ENUM_THREE.getKey(), 1)));
    }

    @Test
    public void facetedSearchOnEnumLabelSetAttributes() throws Exception {
        testResultWithTerms(model().allVariants().attribute().ofEnum(ATTR_NAME_ENUM_SET).label().facetedAndFiltered().by(ENUM_THREE.getLabel()),
                ids -> assertThat(ids).containsOnly(product1.getId()),
                termStats -> assertThat(termStats).containsExactly(
                        TermStats.of(ENUM_TWO.getLabel(), 2),
                        TermStats.of(ENUM_THREE.getLabel(), 1)));
    }

    @Test
    public void facetedSearchOnLocEnumKeySetAttributes() throws Exception {
        testResultWithTerms(model().allVariants().attribute().ofLocalizableEnum(ATTR_NAME_LOC_ENUM_SET).key().facetedAndFiltered().by(LOC_ENUM_THREE.getKey()),
                ids -> assertThat(ids).containsOnly(product1.getId()),
                termStats -> assertThat(termStats).containsExactly(
                        TermStats.of(LOC_ENUM_TWO.getKey(), 2),
                        TermStats.of(LOC_ENUM_THREE.getKey(), 1)));
    }

    @Test
    public void facetedSearchOnLocEnumLabelSetAttributes() throws Exception {
        testResultWithTerms(model().allVariants().attribute().ofLocalizableEnum(ATTR_NAME_LOC_ENUM_SET).label().locale(GERMAN).facetedAndFiltered().by(LOC_ENUM_THREE.getLabel().get(GERMAN)),
                ids -> assertThat(ids).containsOnly(product1.getId()),
                termStats -> assertThat(termStats).containsExactly(
                        TermStats.of(LOC_ENUM_TWO.getLabel().get(GERMAN), 2),
                        TermStats.of(LOC_ENUM_THREE.getLabel().get(GERMAN), 1)));
    }

    @Test
    public void facetedSearchOnNumberSetAttributes() throws Exception {
        testResultWithTerms(model().allVariants().attribute().ofNumber(ATTR_NAME_NUMBER_SET).facetedAndFiltered().by(NUMBER_10.toPlainString()),
                ids -> assertThat(ids).containsOnly(product1.getId()),
                termStats -> assertThat(termStats).containsExactly(
                        TermStats.of("5.0", 2),
                        TermStats.of("10.0", 1)));
    }

    @Test
    public void facetedSearchOnNumberRangedSetAttributes() throws Exception {
        testResultWithRange(model().allVariants().attribute().ofNumber(ATTR_NAME_NUMBER_SET).facetedAndFiltered().byRange(atLeast(NUMBER_10.toPlainString())),
                ids -> assertThat(ids).containsOnly(product1.getId()),
                rangeStats -> {
                    assertThat(rangeStats.getLowerEndpoint()).isEqualTo("0.0");
                    assertThat(rangeStats.getUpperEndpoint()).isEqualTo(null);
                    assertThat(rangeStats.getCount()).isEqualTo(2L);
                    assertThat(rangeStats.getMin()).isEqualTo("5.0");
                    assertThat(rangeStats.getMax()).isEqualTo("5.0");
                    assertThat(rangeStats.getSum()).isEqualTo("10.0");
                    assertThat(rangeStats.getMean()).isEqualTo(5.0D);
                });
    }

    @Test
    public void facetedSearchOnMoneyAmountSetAttributes() throws Exception {
        testResultWithTerms(model().allVariants().attribute().ofMoney(ATTR_NAME_MONEY_SET).centAmount().facetedAndFiltered().by(toCents(MONEY_1000_USD).toString()),
                ids -> assertThat(ids).containsOnly(product1.getId()),
                termStats -> assertThat(termStats).containsExactly(
                        TermStats.of("50000", 2),
                        TermStats.of("100000", 1)));
    }

    @Test
    public void facetedSearchOnMoneyAmountRangedSetAttributes() throws Exception {
        testResultWithRange(model().allVariants().attribute().ofMoney(ATTR_NAME_MONEY_SET).centAmount().facetedAndFiltered().byRange(atLeast(toCents(MONEY_1000_USD).toString())),
                ids -> assertThat(ids).containsOnly(product1.getId()),
                rangeStats -> {
                    assertThat(rangeStats.getLowerEndpoint()).isEqualTo("0.0");
                    assertThat(rangeStats.getUpperEndpoint()).isEqualTo(null);
                    assertThat(rangeStats.getCount()).isEqualTo(2L);
                    assertThat(rangeStats.getMin()).isEqualTo("50000.0");
                    assertThat(rangeStats.getMax()).isEqualTo("50000.0");
                    assertThat(rangeStats.getSum()).isEqualTo("100000.0");
                    assertThat(rangeStats.getMean()).isEqualTo(50000D);
                });
    }

    @Test
    public void facetedSearchOnMoneyCurrencySetAttributes() throws Exception {
        testResultWithTerms(model().allVariants().attribute().ofMoney(ATTR_NAME_MONEY_SET).currency().facetedAndFiltered().by(MONEY_1000_USD.getCurrency().getCurrencyCode()),
                ids -> assertThat(ids).containsOnly(product1.getId()),
                termStats -> assertThat(termStats).containsExactly(
                        TermStats.of("EUR", 2),
                        TermStats.of("USD", 1)));
    }

    @Test
    public void facetedSearchOnDateSetAttributes() throws Exception {
        testResultWithTerms(model().allVariants().attribute().ofDate(ATTR_NAME_DATE_SET).facetedAndFiltered().by(DATE_2002.toString()),
                ids -> assertThat(ids).containsOnly(product1.getId()),
                termStats -> assertThat(termStats).containsExactly(
                        TermStats.of("2001-09-11", 2),
                        TermStats.of("2002-10-12", 1)));
    }

    @Test
    public void facetedSearchOnDateRangedSetAttributes() throws Exception {
        testResultWithRange(model().allVariants().attribute().ofDate(ATTR_NAME_DATE_SET).facetedAndFiltered().byRange(atLeast(DATE_2002.toString())),
                ids -> assertThat(ids).containsOnly(product1.getId()),
                rangeStats -> {
                    assertThat(rangeStats.getLowerEndpoint()).isEqualTo("0.0");
                    assertThat(rangeStats.getUpperEndpoint()).isEqualTo(null);
                    assertThat(rangeStats.getCount()).isEqualTo(2L);
                    assertThat(rangeStats.getMin()).isEqualTo("1.0001664E12");
                    assertThat(rangeStats.getMax()).isEqualTo("1.0001664E12");
                    assertThat(rangeStats.getSum()).isEqualTo("2.0003328E12");
                    assertThat(rangeStats.getMean()).isEqualTo(1.0001664E12D);
                });
    }

    @Test
    public void facetedSearchOnTimeSetAttributes() throws Exception {
        testResultWithTerms(model().allVariants().attribute().ofTime(ATTR_NAME_TIME_SET).facetedAndFiltered().by(TIME_23H.toString()),
                ids -> assertThat(ids).containsOnly(product1.getId()),
                termStats -> assertThat(termStats).containsExactly(
                        TermStats.of("22:05:09.203", 2),
                        TermStats.of("23:06:10.204", 1)));
    }

    @Test
    public void facetedSearchOnTimeRangedSetAttributes() throws Exception {
        testResultWithRange(model().allVariants().attribute().ofTime(ATTR_NAME_TIME_SET).facetedAndFiltered().byRange(atLeast(TIME_23H.toString())),
                ids -> assertThat(ids).containsOnly(product1.getId()),
                rangeStats -> {
                    assertThat(rangeStats.getLowerEndpoint()).isEqualTo("0.0");
                    assertThat(rangeStats.getUpperEndpoint()).isEqualTo(null);
                    assertThat(rangeStats.getCount()).isEqualTo(2L);
                    assertThat(rangeStats.getMin()).isEqualTo("7.9509203E7");
                    assertThat(rangeStats.getMax()).isEqualTo("7.9509203E7");
                    assertThat(rangeStats.getSum()).isEqualTo("1.59018406E8");
                    assertThat(rangeStats.getMean()).isEqualTo(7.9509203E7D);
                });
    }

    @Test
    public void facetedSearchOnDateTimeSetAttributes() throws Exception {
        testResultWithTerms(model().allVariants().attribute().ofDateTime(ATTR_NAME_DATE_TIME_SET).facetedAndFiltered().by(DATE_TIME_2002_23H.toString()),
                ids -> assertThat(ids).containsOnly(product1.getId()),
                termStats -> assertThat(termStats).containsExactly(
                        TermStats.of("2001-09-11T22:05:09.203+0000", 2),
                        TermStats.of("2002-10-12T23:06:10.204+0000", 1)));
    }

    @Test
    public void facetedSearchOnDateTimeRangedSetAttributes() throws Exception {
        testResultWithRange(model().allVariants().attribute().ofDateTime(ATTR_NAME_DATE_TIME_SET).facetedAndFiltered().byRange(atLeast(DATE_TIME_2002_23H.toString())),
                ids -> assertThat(ids).containsOnly(product1.getId()),
                rangeStats -> {
                    assertThat(rangeStats.getLowerEndpoint()).isEqualTo("0.0");
                    assertThat(rangeStats.getUpperEndpoint()).isEqualTo(null);
                    assertThat(rangeStats.getCount()).isEqualTo(2L);
                    assertThat(rangeStats.getMin()).isEqualTo("1.000245909203E12");
                    assertThat(rangeStats.getMax()).isEqualTo("1.000245909203E12");
                    assertThat(rangeStats.getSum()).isEqualTo("2.000491818406E12");
                    assertThat(rangeStats.getMean()).isEqualTo(1.000245909203E12D);
                });
    }

    @Test
    public void facetedSearchOnReferenceSetAttributes() throws Exception {
        testResultWithTerms(model().allVariants().attribute().ofReference(ATTR_NAME_REF_SET).id().facetedAndFiltered().by(productOtherId.getId()),
                ids -> assertThat(ids).containsOnly(product1.getId()),
                termStats -> assertThat(termStats).containsExactly(
                        TermStats.of(productSomeId.getId(), 2),
                        TermStats.of(productOtherId.getId(), 1)));
    }

    private static void testResultWithTerms(final TermFacetAndFilterSearchExpression<ProductProjection> facetedSearchExpr,
                                            final Consumer<List<String>> testFilter, final Consumer<List<TermStats>> testTerms) {
        final PagedSearchResult<ProductProjection> result = executeFacetedSearch(facetedSearchExpr, testFilter);
        testTerms.accept(result.getTermFacetResult(facetedSearchExpr.facetExpression()).getTerms());
    }

    private static void testResultWithRange(final RangeFacetAndFilterSearchExpression<ProductProjection> facetedSearchExpr,
                                            final Consumer<List<String>> testFilter, final Consumer<RangeStats> rangeStats) {
        final PagedSearchResult<ProductProjection> result = executeFacetedSearch(facetedSearchExpr, testFilter);
        rangeStats.accept(result.getRangeFacetResult(facetedSearchExpr.facetExpression()).getRanges().get(0));
    }

    private static PagedSearchResult<ProductProjection> executeFacetedSearch(final FacetAndFilterSearchExpression<ProductProjection> facetedSearchExpr,
                                                                             final Consumer<List<String>> testFilter) {
        final PagedSearchResult<ProductProjection> result = executeSearch(ProductProjectionSearch.ofStaged().plusFacetedSearch(facetedSearchExpr));
        testFilter.accept(toIds(result.getResults()));
        return result;
    }
}
