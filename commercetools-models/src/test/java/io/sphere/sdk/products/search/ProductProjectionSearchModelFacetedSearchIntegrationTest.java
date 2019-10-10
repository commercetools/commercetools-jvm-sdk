package io.sphere.sdk.products.search;

import io.sphere.sdk.products.ProductProjection;
import io.sphere.sdk.search.*;
import io.sphere.sdk.search.model.SimpleRangeStats;
import org.junit.Ignore;
import org.junit.Test;

import java.util.List;
import java.util.function.Consumer;

import static io.sphere.sdk.search.model.FilterRange.atLeast;
import static io.sphere.sdk.search.model.FilterRange.atMost;
import static io.sphere.sdk.test.SphereTestUtils.*;
import static org.assertj.core.api.Assertions.assertThat;
import static io.sphere.sdk.products.ProductsScenario1Fixtures.*;

@Ignore
public class ProductProjectionSearchModelFacetedSearchIntegrationTest extends ProductProjectionSearchModelIntegrationTest {

    private static final ProductProjectionFacetedSearchSearchModel PRODUCT_MODEL = ProductProjectionSearchModel.of().facetedSearch();

    @Ignore
    @Test
    public void facetedSearchOnBooleanAttributes() throws Exception {
        testResultWithTerms(PRODUCT_MODEL.allVariants().attribute().ofBoolean(ATTR_NAME_BOOLEAN).is(BOOL_TRUE),
                ids -> assertThat(ids).containsOnly(product1.getId()),
                termStats -> assertThat(termStats).containsOnly(
                        TermStats.of(BOOL_TRUE, 1L),
                        TermStats.of(BOOL_FALSE, 1L)));
    }

    @Ignore
    @Test
    public void facetedSearchOnTextAttributes() throws Exception {
        testResultWithTerms(PRODUCT_MODEL.allVariants().attribute().ofString(ATTR_NAME_TEXT).is(TEXT_FOO),
                ids -> assertThat(ids).containsOnly(product1.getId()),
                termStats -> assertThat(termStats).containsOnly(
                        TermStats.of(TEXT_FOO, 1L),
                        TermStats.of(TEXT_BAR, 1L)));
    }

    @Ignore
    @Test
    public void facetedSearchOnLocTextAttributes() throws Exception {
        testResultWithTerms(PRODUCT_MODEL.allVariants().attribute().ofLocalizedString(ATTR_NAME_LOC_TEXT).locale(ENGLISH).is(LOC_TEXT_FOO.get(ENGLISH)),
                ids -> assertThat(ids).containsOnly(product1.getId()),
                termStats -> assertThat(termStats).containsOnly(
                        TermStats.of(LOC_TEXT_FOO.get(ENGLISH), 1L),
                        TermStats.of(LOC_TEXT_BAR.get(ENGLISH), 1L)));
    }

    @Ignore
    @Test
    public void facetedSearchOnEnumKeyAttributes() throws Exception {
        testResultWithTerms(PRODUCT_MODEL.allVariants().attribute().ofEnum(ATTR_NAME_ENUM).key().is(ENUM_TWO.getKey()),
                ids -> assertThat(ids).containsOnly(product1.getId()),
                termStats -> assertThat(termStats).containsOnly(
                        TermStats.of(ENUM_TWO.getKey(), 1L),
                        TermStats.of(ENUM_THREE.getKey(), 1L)));
    }

    @Ignore
    @Test
    public void facetedSearchOnEnumLabelAttributes() throws Exception {
        testResultWithTerms(PRODUCT_MODEL.allVariants().attribute().ofEnum(ATTR_NAME_ENUM).label().is(ENUM_TWO.getLabel()),
                ids -> assertThat(ids).containsOnly(product1.getId()),
                termStats -> assertThat(termStats).containsOnly(
                        TermStats.of(ENUM_TWO.getLabel(), 1L),
                        TermStats.of(ENUM_THREE.getLabel(), 1L)));
    }

    @Ignore
    @Test
    public void facetedSearchOnLocEnumKeyAttributes() throws Exception {
        testResultWithTerms(PRODUCT_MODEL.allVariants().attribute().ofLocalizedEnum(ATTR_NAME_LOC_ENUM).key().is(LOC_ENUM_TWO.getKey()),
                ids -> assertThat(ids).containsOnly(product1.getId()),
                termStats -> assertThat(termStats).containsOnly(
                        TermStats.of(LOC_ENUM_TWO.getKey(), 1L),
                        TermStats.of(LOC_ENUM_THREE.getKey(), 1L)));
    }

    @Ignore
    @Test
    public void facetedSearchOnLocEnumLabelAttributes() throws Exception {
        testResultWithTerms(PRODUCT_MODEL.allVariants().attribute().ofLocalizedEnum(ATTR_NAME_LOC_ENUM).label().locale(GERMAN).is(LOC_ENUM_TWO.getLabel().get(GERMAN)),
                ids -> assertThat(ids).containsOnly(product1.getId()),
                termStats -> assertThat(termStats).containsOnly(
                        TermStats.of(LOC_ENUM_TWO.getLabel().get(GERMAN), 1L),
                        TermStats.of(LOC_ENUM_THREE.getLabel().get(GERMAN), 1L)));
    }

    @Ignore
    @Test
    public void facetedSearchOnNumberAttributes() throws Exception {
        testResultWithTerms(PRODUCT_MODEL.allVariants().attribute().ofNumber(ATTR_NAME_NUMBER).is(NUMBER_5.toPlainString()),
                ids -> assertThat(ids).containsOnly(product1.getId()),
                termStats -> assertThat(termStats).containsOnly(
                        TermStats.of("5.0", 1L),
                        TermStats.of("10.0", 1L)));
    }

    @Ignore
    @Test
    public void facetedSearchOnNumberRangedAttributes() throws Exception {
        testResultWithRange(PRODUCT_MODEL.allVariants().attribute().ofNumber(ATTR_NAME_NUMBER).isBetween(atMost(NUMBER_5.toPlainString())),
                ids -> assertThat(ids).containsOnly(product1.getId()),
                rangeStats -> {
                    assertThat(rangeStats.getLowerEndpoint()).isEqualTo(null);
                    assertThat(rangeStats.getUpperEndpoint()).isEqualTo(null);
                    assertThat(rangeStats.getCount()).isEqualTo(2L);
                    assertThat(rangeStats.getMin()).isEqualTo("5.0");
                    assertThat(rangeStats.getMax()).isEqualTo("10.0");
                });
    }

    @Ignore
    @Test
    public void facetedSearchOnMoneyAmountAttributes() throws Exception {
        testResultWithTerms(PRODUCT_MODEL.allVariants().attribute().ofMoney(ATTR_NAME_MONEY).centAmount().is(toCents(MONEY_500_EUR).toString()),
                ids -> assertThat(ids).containsOnly(product1.getId()),
                termStats -> assertThat(termStats).containsOnly(
                        TermStats.of("50000", 1L),
                        TermStats.of("100000", 1L)));
    }

    @Ignore
    @Test
    public void facetedSearchOnMoneyAmountRangedAttributes() throws Exception {
        testResultWithRange(PRODUCT_MODEL.allVariants().attribute().ofMoney(ATTR_NAME_MONEY).centAmount().isBetween(atMost(toCents(MONEY_500_EUR).toString())),
                ids -> assertThat(ids).containsOnly(product1.getId()),
                rangeStats -> {
                    assertThat(rangeStats.getLowerEndpoint()).isEqualTo(null);
                    assertThat(rangeStats.getUpperEndpoint()).isEqualTo(null);
                    assertThat(rangeStats.getCount()).isEqualTo(2L);
                    assertThat(rangeStats.getMin()).isEqualTo("50000.0");
                    assertThat(rangeStats.getMax()).isEqualTo("100000.0");
                });
    }

    @Ignore
    @Test
    public void facetedSearchOnMoneyCurrencyAttributes() throws Exception {
        testResultWithTerms(PRODUCT_MODEL.allVariants().attribute().ofMoney(ATTR_NAME_MONEY).currency().is(MONEY_500_EUR.getCurrency().getCurrencyCode()),
                ids -> assertThat(ids).containsOnly(product1.getId()),
                termStats -> assertThat(termStats).containsOnly(
                        TermStats.of("EUR", 1L),
                        TermStats.of("USD", 1L)));
    }

    @Ignore
    @Test
    public void facetedSearchOnDateAttributes() throws Exception {
        testResultWithTerms(PRODUCT_MODEL.allVariants().attribute().ofDate(ATTR_NAME_DATE).is(DATE_2001.toString()),
                ids -> assertThat(ids).containsOnly(product1.getId()),
                termStats -> assertThat(termStats).containsOnly(
                        TermStats.of("2001-09-11", 1L),
                        TermStats.of("2002-10-12", 1L)));
    }

    @Ignore
    @Test
    public void facetedSearchOnDateRangedAttributes() throws Exception {
        testResultWithRange(PRODUCT_MODEL.allVariants().attribute().ofDate(ATTR_NAME_DATE).isBetween(atMost(DATE_2001.toString())),
                ids -> assertThat(ids).containsOnly(product1.getId()),
                rangeStats -> {
                    assertThat(rangeStats.getLowerEndpoint()).isEqualTo(null);
                    assertThat(rangeStats.getUpperEndpoint()).isEqualTo(null);
                    assertThat(rangeStats.getCount()).isEqualTo(2L);
                    assertThat(rangeStats.getMin()).isEqualTo("1.0001664E12");
                    assertThat(rangeStats.getMax()).isEqualTo("1.0343808E12");
                });
    }

    @Ignore
    @Test
    public void facetedSearchOnTimeAttributes() throws Exception {
        testResultWithTerms(PRODUCT_MODEL.allVariants().attribute().ofTime(ATTR_NAME_TIME).is(TIME_22H.toString()),
                ids -> assertThat(ids).containsOnly(product1.getId()),
                termStats -> assertThat(termStats).containsOnly(
                        TermStats.of("22:05:09.203", 1L),
                        TermStats.of("23:06:10.204", 1L)));
    }

    @Ignore
    @Test
    public void facetedSearchOnTimeRangedAttributes() throws Exception {
        testResultWithRange(PRODUCT_MODEL.allVariants().attribute().ofTime(ATTR_NAME_TIME).isBetween(atMost(TIME_22H.toString())),
                ids -> assertThat(ids).containsOnly(product1.getId()),
                rangeStats -> {
                    assertThat(rangeStats.getLowerEndpoint()).isEqualTo(null);
                    assertThat(rangeStats.getUpperEndpoint()).isEqualTo(null);
                    assertThat(rangeStats.getCount()).isEqualTo(2L);
                    assertThat(rangeStats.getMin()).isEqualTo("7.9509203E7");
                    assertThat(rangeStats.getMax()).isEqualTo("8.3170204E7");
                });
    }

    @Ignore
    @Test
    public void facetedSearchOnDateTimeAttributes() throws Exception {
        testResultWithTerms(PRODUCT_MODEL.allVariants().attribute().ofDateTime(ATTR_NAME_DATE_TIME).is(DATE_TIME_2001_22H.toString()),
                ids -> assertThat(ids).containsOnly(product1.getId()),
                termStats -> assertThat(termStats).containsOnly(
                        TermStats.of("2002-10-12T23:06:10.204+0000", 1L),
                        TermStats.of("2001-09-11T22:05:09.203+0000", 1L)));
    }

    @Ignore
    @Test
    public void facetedSearchOnDateTimeRangedAttributes() throws Exception {
        testResultWithRange(PRODUCT_MODEL.allVariants().attribute().ofDateTime(ATTR_NAME_DATE_TIME).isBetween(atMost(DATE_TIME_2001_22H.toString())),
                ids -> assertThat(ids).containsOnly(product1.getId()),
                rangeStats -> {
                    assertThat(rangeStats.getLowerEndpoint()).isEqualTo(null);
                    assertThat(rangeStats.getUpperEndpoint()).isEqualTo(null);
                    assertThat(rangeStats.getCount()).isEqualTo(2L);
                    assertThat(rangeStats.getMin()).isEqualTo("1.000245909203E12");
                    assertThat(rangeStats.getMax()).isEqualTo("1.034463970204E12");
                });
    }

    @Ignore
    @Test
    public void facetedSearchOnReferenceAttributes() throws Exception {
        testResultWithTerms(PRODUCT_MODEL.allVariants().attribute().ofReference(ATTR_NAME_REF).id().is(productA.getId()),
                ids -> assertThat(ids).as("expected products in result").containsOnly(product1.getId()),
                termStats -> assertThat(termStats).containsOnly(
                        TermStats.of(productA.getId(), 1L),
                        TermStats.of(productB.getId(), 1L)));
    }

    @Ignore
    @Test
    public void facetedSearchOnBooleanSetAttributes() throws Exception {
        testResultWithTerms(PRODUCT_MODEL.allVariants().attribute().ofBooleanSet(ATTR_NAME_BOOLEAN_SET).is(BOOL_FALSE),
                ids -> assertThat(ids).containsOnly(product1.getId()),
                termStats -> assertThat(termStats).containsExactly(
                        TermStats.of(BOOL_TRUE, 2L),
                        TermStats.of(BOOL_FALSE, 1L)));
    }

    @Ignore
    @Test
    public void facetedSearchOnTextSetAttributes() throws Exception {
        testResultWithTerms(PRODUCT_MODEL.allVariants().attribute().ofStringSet(ATTR_NAME_TEXT_SET).is(TEXT_BAR),
                ids -> assertThat(ids).containsOnly(product1.getId()),
                termStats -> assertThat(termStats).containsExactly(
                        TermStats.of(TEXT_FOO, 2L),
                        TermStats.of(TEXT_BAR, 1L)));
    }

    @Ignore
    @Test
    public void facetedSearchOnLocTextSetAttributes() throws Exception {
        testResultWithTerms(PRODUCT_MODEL.allVariants().attribute().ofLocalizedStringSet(ATTR_NAME_LOC_TEXT_SET).locale(ENGLISH).is(LOC_TEXT_BAR.get(ENGLISH)),
                ids -> assertThat(ids).containsOnly(product1.getId()),
                termStats -> assertThat(termStats).containsExactly(
                        TermStats.of(LOC_TEXT_FOO.get(ENGLISH), 2L),
                        TermStats.of(LOC_TEXT_BAR.get(ENGLISH), 1L)));
    }

    @Ignore
    @Test
    public void facetedSearchOnEnumKeySetAttributes() throws Exception {
        testResultWithTerms(PRODUCT_MODEL.allVariants().attribute().ofEnumSet(ATTR_NAME_ENUM_SET).key().is(ENUM_THREE.getKey()),
                ids -> assertThat(ids).containsOnly(product1.getId()),
                termStats -> assertThat(termStats).containsExactly(
                        TermStats.of(ENUM_TWO.getKey(), 2L),
                        TermStats.of(ENUM_THREE.getKey(), 1L)));
    }

    @Ignore
    @Test
    public void facetedSearchOnEnumLabelSetAttributes() throws Exception {
        testResultWithTerms(PRODUCT_MODEL.allVariants().attribute().ofEnumSet(ATTR_NAME_ENUM_SET).label().is(ENUM_THREE.getLabel()),
                ids -> assertThat(ids).containsOnly(product1.getId()),
                termStats -> assertThat(termStats).containsExactly(
                        TermStats.of(ENUM_TWO.getLabel(), 2L),
                        TermStats.of(ENUM_THREE.getLabel(), 1L)));
    }

    @Ignore
    @Test
    public void facetedSearchOnLocEnumKeySetAttributes() throws Exception {
        testResultWithTerms(PRODUCT_MODEL.allVariants().attribute().ofLocalizedEnumSet(ATTR_NAME_LOC_ENUM_SET).key().is(LOC_ENUM_THREE.getKey()),
                ids -> assertThat(ids).containsOnly(product1.getId()),
                termStats -> assertThat(termStats).containsExactly(
                        TermStats.of(LOC_ENUM_TWO.getKey(), 2L),
                        TermStats.of(LOC_ENUM_THREE.getKey(), 1L)));
    }

    @Ignore
    @Test
    public void facetedSearchOnLocEnumLabelSetAttributes() throws Exception {
        testResultWithTerms(PRODUCT_MODEL.allVariants().attribute().ofLocalizedEnumSet(ATTR_NAME_LOC_ENUM_SET).label().locale(GERMAN).is(LOC_ENUM_THREE.getLabel().get(GERMAN)),
                ids -> assertThat(ids).containsOnly(product1.getId()),
                termStats -> assertThat(termStats).containsExactly(
                        TermStats.of(LOC_ENUM_TWO.getLabel().get(GERMAN), 2L),
                        TermStats.of(LOC_ENUM_THREE.getLabel().get(GERMAN), 1L)));
    }

    @Ignore
    @Test
    public void facetedSearchOnNumberSetAttributes() throws Exception {
        testResultWithTerms(PRODUCT_MODEL.allVariants().attribute().ofNumberSet(ATTR_NAME_NUMBER_SET).is(NUMBER_10.toPlainString()),
                ids -> assertThat(ids).containsOnly(product1.getId()),
                termStats -> assertThat(termStats).containsExactly(
                        TermStats.of("5.0", 2L),
                        TermStats.of("10.0", 1L)));
    }

    @Ignore
    @Test
    public void facetedSearchOnNumberRangedSetAttributes() throws Exception {
        testResultWithRange(PRODUCT_MODEL.allVariants().attribute().ofNumberSet(ATTR_NAME_NUMBER_SET).isBetween(atLeast(NUMBER_10.toPlainString())),
                ids -> assertThat(ids).containsOnly(product1.getId()),
                rangeStats -> {
                    assertThat(rangeStats.getLowerEndpoint()).isEqualTo(null);
                    assertThat(rangeStats.getUpperEndpoint()).isEqualTo(null);
                    assertThat(rangeStats.getCount()).isEqualTo(2L);
                    assertThat(rangeStats.getMin()).isEqualTo("5.0");
                    assertThat(rangeStats.getMax()).isEqualTo("10.0");
                });
    }

    @Ignore
    @Test
    public void facetedSearchOnMoneyAmountSetAttributes() throws Exception {
        testResultWithTerms(PRODUCT_MODEL.allVariants().attribute().ofMoneySet(ATTR_NAME_MONEY_SET).centAmount().is(toCents(MONEY_1000_USD).toString()),
                ids -> assertThat(ids).containsOnly(product1.getId()),
                termStats -> assertThat(termStats).containsExactly(
                        TermStats.of("50000", 2L),
                        TermStats.of("100000", 1L)));
    }

    @Ignore
    @Test
    public void facetedSearchOnMoneyAmountRangedSetAttributes() throws Exception {
        testResultWithRange(PRODUCT_MODEL.allVariants().attribute().ofMoneySet(ATTR_NAME_MONEY_SET).centAmount().isBetween(atLeast(toCents(MONEY_1000_USD).toString())),
                ids -> assertThat(ids).containsOnly(product1.getId()),
                rangeStats -> {
                    assertThat(rangeStats.getLowerEndpoint()).isEqualTo(null);
                    assertThat(rangeStats.getUpperEndpoint()).isEqualTo(null);
                    assertThat(rangeStats.getCount()).isEqualTo(2L);
                    assertThat(rangeStats.getMin()).isEqualTo("50000.0");
                    assertThat(rangeStats.getMax()).isEqualTo("100000.0");
                });
    }

    @Ignore
    @Test
    public void facetedSearchOnMoneyCurrencySetAttributes() throws Exception {
        testResultWithTerms(PRODUCT_MODEL.allVariants().attribute().ofMoneySet(ATTR_NAME_MONEY_SET).currency().is(MONEY_1000_USD.getCurrency().getCurrencyCode()),
                ids -> assertThat(ids).containsOnly(product1.getId()),
                termStats -> assertThat(termStats).containsExactly(
                        TermStats.of("EUR", 2L),
                        TermStats.of("USD", 1L)));
    }

    @Ignore
    @Test
    public void facetedSearchOnDateSetAttributes() throws Exception {
        testResultWithTerms(PRODUCT_MODEL.allVariants().attribute().ofDateSet(ATTR_NAME_DATE_SET).is(DATE_2002.toString()),
                ids -> assertThat(ids).containsOnly(product1.getId()),
                termStats -> assertThat(termStats).containsExactly(
                        TermStats.of("2001-09-11", 2L),
                        TermStats.of("2002-10-12", 1L)));
    }

    @Ignore
    @Test
    public void facetedSearchOnDateRangedSetAttributes() throws Exception {
        testResultWithRange(PRODUCT_MODEL.allVariants().attribute().ofDateSet(ATTR_NAME_DATE_SET).isBetween(atLeast(DATE_2002.toString())),
                ids -> assertThat(ids).containsOnly(product1.getId()),
                rangeStats -> {
                    assertThat(rangeStats.getLowerEndpoint()).isEqualTo(null);
                    assertThat(rangeStats.getUpperEndpoint()).isEqualTo(null);
                    assertThat(rangeStats.getCount()).isEqualTo(2L);
                    assertThat(rangeStats.getMin()).isEqualTo("1.0001664E12");
                    assertThat(rangeStats.getMax()).isEqualTo("1.0343808E12");
                });
    }

    @Ignore
    @Test
    public void facetedSearchOnTimeSetAttributes() throws Exception {
        testResultWithTerms(PRODUCT_MODEL.allVariants().attribute().ofTimeSet(ATTR_NAME_TIME_SET).is(TIME_23H.toString()),
                ids -> assertThat(ids).containsOnly(product1.getId()),
                termStats -> assertThat(termStats).containsExactly(
                        TermStats.of("22:05:09.203", 2L),
                        TermStats.of("23:06:10.204", 1L)));
    }

    @Ignore
    @Test
    public void facetedSearchOnTimeRangedSetAttributes() throws Exception {
        testResultWithRange(PRODUCT_MODEL.allVariants().attribute().ofTimeSet(ATTR_NAME_TIME_SET).isBetween(atLeast(TIME_23H.toString())),
                ids -> assertThat(ids).containsOnly(product1.getId()),
                rangeStats -> {
                    assertThat(rangeStats.getLowerEndpoint()).isEqualTo(null);
                    assertThat(rangeStats.getUpperEndpoint()).isEqualTo(null);
                    assertThat(rangeStats.getCount()).isEqualTo(2L);
                    assertThat(rangeStats.getMin()).isEqualTo("7.9509203E7");
                    assertThat(rangeStats.getMax()).isEqualTo("8.3170204E7");
                });
    }

    @Ignore
    @Test
    public void facetedSearchOnDateTimeSetAttributes() throws Exception {
        testResultWithTerms(PRODUCT_MODEL.allVariants().attribute().ofDateTimeSet(ATTR_NAME_DATE_TIME_SET).is(DATE_TIME_2002_23H.toString()),
                ids -> assertThat(ids).containsOnly(product1.getId()),
                termStats -> assertThat(termStats).containsExactly(
                        TermStats.of("2001-09-11T22:05:09.203+0000", 2L),
                        TermStats.of("2002-10-12T23:06:10.204+0000", 1L)));
    }

    @Ignore
    @Test
    public void facetedSearchOnDateTimeRangedSetAttributes() throws Exception {
        testResultWithRange(PRODUCT_MODEL.allVariants().attribute().ofDateTimeSet(ATTR_NAME_DATE_TIME_SET).isBetween(atLeast(DATE_TIME_2002_23H.toString())),
                ids -> assertThat(ids).containsOnly(product1.getId()),
                rangeStats -> {
                    assertThat(rangeStats.getLowerEndpoint()).isEqualTo(null);
                    assertThat(rangeStats.getUpperEndpoint()).isEqualTo(null);
                    assertThat(rangeStats.getCount()).isEqualTo(2L);
                    assertThat(rangeStats.getMin()).isEqualTo("1.000245909203E12");
                    assertThat(rangeStats.getMax()).isEqualTo("1.034463970204E12");
                });
    }

    @Ignore
    @Test
    public void facetedSearchOnReferenceSetAttributes() throws Exception {
        testResultWithTerms(PRODUCT_MODEL.allVariants().attribute().ofReferenceSet(ATTR_NAME_REF_SET).id().is(productB.getId()),
                ids -> assertThat(ids).as("expected products in result").containsOnly(product1.getId()),
                termStats -> assertThat(termStats).containsExactly(
                        TermStats.of(productA.getId(), 2L),
                        TermStats.of(productB.getId(), 1L)));
    }

    private static void testResultWithTerms(final TermFacetedSearchExpression<ProductProjection> facetedSearchExpr,
                                            final Consumer<List<String>> testFilter, final Consumer<List<TermStats>> testTerms) {
        final PagedSearchResult<ProductProjection> result = executeFacetedSearch(facetedSearchExpr, testFilter);
        testTerms.accept(result.getFacetResult(facetedSearchExpr).getTerms());
    }

    private static void testResultWithRange(final RangeFacetedSearchExpression<ProductProjection> facetedSearchExpr,
                                            final Consumer<List<String>> testFilter, final Consumer<SimpleRangeStats> rangeStats) {
        final PagedSearchResult<ProductProjection> result = executeFacetedSearch(facetedSearchExpr, testFilter);
        rangeStats.accept(result.getRangeStatsOfAllRanges(facetedSearchExpr));
    }

    private static PagedSearchResult<ProductProjection> executeFacetedSearch(final FacetedSearchExpression<ProductProjection> facetedSearchExpr,
                                                                             final Consumer<List<String>> testFilter) {
        final PagedSearchResult<ProductProjection> result = executeSearch(ProductProjectionSearch.ofStaged().plusFacetedSearch(facetedSearchExpr));
        testFilter.accept(toIds(result.getResults()));
        return result;
    }
}