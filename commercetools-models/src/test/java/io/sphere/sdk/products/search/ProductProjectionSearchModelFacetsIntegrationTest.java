package io.sphere.sdk.products.search;

import io.sphere.sdk.products.ProductProjection;
import io.sphere.sdk.search.*;
import io.sphere.sdk.search.model.RangeStats;
import org.junit.Ignore;
import org.junit.Test;

import java.util.List;
import java.util.function.Consumer;

import static io.sphere.sdk.test.SphereTestUtils.*;
import static java.math.BigDecimal.valueOf;
import static org.assertj.core.api.Assertions.assertThat;

import static io.sphere.sdk.products.ProductsScenario1Fixtures.*;

@Ignore
public class ProductProjectionSearchModelFacetsIntegrationTest extends ProductProjectionSearchModelIntegrationTest {

    public static final ProductProjectionFacetSearchModel FACET = ProductProjectionSearchModel.of().facet();

    @Ignore
    @Test
    public void onBooleanAttributes() throws Exception {
        testTermStats(FACET.allVariants().attribute().ofBoolean(ATTR_NAME_BOOLEAN).allTerms(),
                termStats -> assertThat(termStats).containsOnly(
                        TermStats.of(BOOL_TRUE, 1L),
                        TermStats.of(BOOL_FALSE, 1L)));
    }

    @Ignore
    @Test
    public void onTextAttributes() throws Exception {
        testTermStats(FACET.allVariants().attribute().ofString(ATTR_NAME_TEXT).allTerms(),
                termStats -> assertThat(termStats).containsOnly(
                        TermStats.of(TEXT_FOO, 1L),
                        TermStats.of(TEXT_BAR, 1L)));
    }

    @Ignore
    @Test
    public void onLocTextAttributes() throws Exception {
        testTermStats(FACET.allVariants().attribute().ofLocalizedString(ATTR_NAME_LOC_TEXT).locale(ENGLISH).allTerms(),
                termStats -> assertThat(termStats).containsOnly(
                        TermStats.of(LOC_TEXT_FOO.get(ENGLISH), 1L),
                        TermStats.of(LOC_TEXT_BAR.get(ENGLISH), 1L)));
    }

    @Ignore
    @Test
    public void onEnumKeyAttributes() throws Exception {
        testTermStats(FACET.allVariants().attribute().ofEnum(ATTR_NAME_ENUM).key().allTerms(),
                termStats -> assertThat(termStats).containsOnly(
                        TermStats.of(ENUM_TWO.getKey(), 1L),
                        TermStats.of(ENUM_THREE.getKey(), 1L)));
    }

    @Ignore
    @Test
    public void onEnumLabelAttributes() throws Exception {
        testTermStats(FACET.allVariants().attribute().ofEnum(ATTR_NAME_ENUM).label().allTerms(),
                termStats -> assertThat(termStats).containsOnly(
                        TermStats.of(ENUM_TWO.getLabel(), 1L),
                        TermStats.of(ENUM_THREE.getLabel(), 1L)));
    }

    @Ignore
    @Test
    public void onLocEnumKeyAttributes() throws Exception {
        testTermStats(FACET.allVariants().attribute().ofLocalizedEnum(ATTR_NAME_LOC_ENUM).key().allTerms(),
                termStats -> assertThat(termStats).containsOnly(
                        TermStats.of(LOC_ENUM_TWO.getKey(), 1L),
                        TermStats.of(LOC_ENUM_THREE.getKey(), 1L)));
    }

    @Ignore
    @Test
    public void onLocEnumLabelAttributes() throws Exception {
        testTermStats(FACET.allVariants().attribute().ofLocalizedEnum(ATTR_NAME_LOC_ENUM).label().locale(GERMAN).allTerms(),
                termStats -> assertThat(termStats).containsOnly(
                        TermStats.of(LOC_ENUM_TWO.getLabel().get(GERMAN), 1L),
                        TermStats.of(LOC_ENUM_THREE.getLabel().get(GERMAN), 1L)));
    }

    @Ignore
    @Test
    public void onNumberAttributes() throws Exception {
        testTermStats(FACET.allVariants().attribute().ofNumber(ATTR_NAME_NUMBER).allTerms(),
                termStats -> assertThat(termStats).containsOnly(
                        TermStats.of("5.0", 1L),
                        TermStats.of("10.0", 1L)));
    }

    @Ignore
    @Test
    public void onNumberRangedAttributes() throws Exception {
        testRangeStats(FACET.allVariants().attribute().ofNumber(ATTR_NAME_NUMBER).onlyGreaterThanOrEqualTo(valueOf(0)),
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

    @Ignore
    @Test
    public void onMoneyAmountAttributes() throws Exception {
        testTermStats(FACET.allVariants().attribute().ofMoney(ATTR_NAME_MONEY).centAmount().allTerms(),
                termStats -> assertThat(termStats).containsOnly(
                        TermStats.of("50000", 1L),
                        TermStats.of("100000", 1L)));
    }

    @Ignore
    @Test
    public void onMoneyAmountRangedAttributes() throws Exception {
        testRangeStats(FACET.allVariants().attribute().ofMoney(ATTR_NAME_MONEY).centAmount().onlyGreaterThanOrEqualTo(0L),
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

    @Ignore
    @Test
    public void onMoneyCurrencyAttributes() throws Exception {
        testTermStats(FACET.allVariants().attribute().ofMoney(ATTR_NAME_MONEY).currency().allTerms(),
                termStats -> assertThat(termStats).containsOnly(
                        TermStats.of("EUR", 1L),
                        TermStats.of("USD", 1L)));
    }

    @Ignore
    @Test
    public void onDateAttributes() throws Exception {
        testTermStats(FACET.allVariants().attribute().ofDate(ATTR_NAME_DATE).allTerms(),
                termStats -> assertThat(termStats).containsOnly(
                        TermStats.of("2001-09-11", 1L),
                        TermStats.of("2002-10-12", 1L)));
    }

    @Ignore
    @Test
    public void onDateRangedAttributes() throws Exception {
        testRangeStats(FACET.allVariants().attribute().ofDate(ATTR_NAME_DATE).onlyGreaterThanOrEqualTo(DATE_2001),
                rangeStats -> {
                    assertThat(rangeStats.getLowerEndpoint()).isEqualTo("1.0001664E12");
                    assertThat(rangeStats.getUpperEndpoint()).isEqualTo(null);
                    assertThat(rangeStats.getCount()).isEqualTo(2L);
                    assertThat(rangeStats.getMin()).isEqualTo("1.0001664E12");
                    assertThat(rangeStats.getMax()).isEqualTo("1.0343808E12");
                    assertThat(rangeStats.getSum()).isEqualTo("2.0345472E12");
                    assertThat(rangeStats.getMean()).isEqualTo(1.0172736E12D);
                });
    }

    @Ignore
    @Test
    public void onTimeAttributes() throws Exception {
        testTermStats(FACET.allVariants().attribute().ofTime(ATTR_NAME_TIME).allTerms(),
                termStats -> assertThat(termStats).containsOnly(
                        TermStats.of("22:05:09.203", 1L),
                        TermStats.of("23:06:10.204", 1L)));
    }

    @Ignore
    @Test
    public void onTimeRangedAttributes() throws Exception {
        testRangeStats(FACET.allVariants().attribute().ofTime(ATTR_NAME_TIME).onlyGreaterThanOrEqualTo(TIME_22H),
                rangeStats -> {
                    assertThat(rangeStats.getLowerEndpoint()).isEqualTo("7.9509203E7");
                    assertThat(rangeStats.getUpperEndpoint()).isEqualTo(null);
                    assertThat(rangeStats.getCount()).isEqualTo(2L);
                    assertThat(rangeStats.getMin()).isEqualTo("7.9509203E7");
                    assertThat(rangeStats.getMax()).isEqualTo("8.3170204E7");
                    assertThat(rangeStats.getSum()).isEqualTo("1.62679407E8");
                    assertThat(rangeStats.getMean()).isEqualTo(8.13397035E7D);
                });
    }

    @Ignore
    @Test
    public void onDatetimeAttributes() throws Exception {
        testTermStats(FACET.allVariants().attribute().ofDateTime(ATTR_NAME_DATE_TIME).allTerms(),
                termStats -> assertThat(termStats).containsOnly(
                        TermStats.of("2002-10-12T23:06:10.204+0000", 1L),
                        TermStats.of("2001-09-11T22:05:09.203+0000", 1L)));
    }

    @Ignore
    @Test
    public void onDatetimeRangedAttributes() throws Exception {
        testRangeStats(FACET.allVariants().attribute().ofDateTime(ATTR_NAME_DATE_TIME).onlyGreaterThanOrEqualTo(DATE_TIME_2001_22H),
                rangeStats -> {
                    assertThat(rangeStats.getLowerEndpoint()).isEqualTo("1.000245909203E12");
                    assertThat(rangeStats.getUpperEndpoint()).isEqualTo(null);
                    assertThat(rangeStats.getCount()).isEqualTo(2L);
                    assertThat(rangeStats.getMin()).isEqualTo("1.000245909203E12");
                    assertThat(rangeStats.getMax()).isEqualTo("1.034463970204E12");
                    assertThat(rangeStats.getSum()).isEqualTo("2.034709879407E12");
                    assertThat(rangeStats.getMean()).isEqualTo(1.0173549397035E12D);
                });
    }

    @Ignore
    @Test
    public void onReferenceAttributes() throws Exception {
        testTermStats(FACET.allVariants().attribute().ofReference(ATTR_NAME_REF).id().allTerms(),
                termStats -> assertThat(termStats).containsOnly(
                        TermStats.of(productA.getId(), 1L),
                        TermStats.of(productB.getId(), 1L)));
    }

    @Ignore
    @Test
    public void onBooleanSetAttributes() throws Exception {
        testTermStats(FACET.allVariants().attribute().ofBooleanSet(ATTR_NAME_BOOLEAN_SET).allTerms(),
                termStats -> assertThat(termStats).containsExactly(
                        TermStats.of(BOOL_TRUE, 2L),
                        TermStats.of(BOOL_FALSE, 1L)));
    }

    @Ignore
    @Test
    public void onTextSetAttributes() throws Exception {
        testTermStats(FACET.allVariants().attribute().ofStringSet(ATTR_NAME_TEXT_SET).allTerms(),
                termStats -> assertThat(termStats).containsExactly(
                        TermStats.of(TEXT_FOO, 2L),
                        TermStats.of(TEXT_BAR, 1L)));
    }

    @Ignore
    @Test
    public void onLocTextSetAttributes() throws Exception {
        testTermStats(FACET.allVariants().attribute().ofLocalizedStringSet(ATTR_NAME_LOC_TEXT_SET).locale(ENGLISH).allTerms(),
                termStats -> assertThat(termStats).containsExactly(
                        TermStats.of(LOC_TEXT_FOO.get(ENGLISH), 2L),
                        TermStats.of(LOC_TEXT_BAR.get(ENGLISH), 1L)));
    }

    @Ignore
    @Test
    public void onEnumKeySetAttributes() throws Exception {
        testTermStats(FACET.allVariants().attribute().ofEnumSet(ATTR_NAME_ENUM_SET).key().allTerms(),
                termStats -> assertThat(termStats).containsExactly(
                        TermStats.of(ENUM_TWO.getKey(), 2L),
                        TermStats.of(ENUM_THREE.getKey(), 1L)));
    }

    @Ignore
    @Test
    public void onEnumLabelSetAttributes() throws Exception {
        testTermStats(FACET.allVariants().attribute().ofEnumSet(ATTR_NAME_ENUM_SET).label().allTerms(),
                termStats -> assertThat(termStats).containsExactly(
                        TermStats.of(ENUM_TWO.getLabel(), 2L),
                        TermStats.of(ENUM_THREE.getLabel(), 1L)));
    }

    @Ignore
    @Test
    public void onLocEnumKeySetAttributes() throws Exception {
        testTermStats(FACET.allVariants().attribute().ofLocalizedEnumSet(ATTR_NAME_LOC_ENUM_SET).key().allTerms(),
                termStats -> assertThat(termStats).containsExactly(
                        TermStats.of(LOC_ENUM_TWO.getKey(), 2L),
                        TermStats.of(LOC_ENUM_THREE.getKey(), 1L)));
    }

    @Ignore
    @Test
    public void onLocEnumLabelSetAttributes() throws Exception {
        testTermStats(FACET.allVariants().attribute().ofLocalizedEnumSet(ATTR_NAME_LOC_ENUM_SET).label().locale(GERMAN).allTerms(),
                termStats -> assertThat(termStats).containsExactly(
                        TermStats.of(LOC_ENUM_TWO.getLabel().get(GERMAN), 2L),
                        TermStats.of(LOC_ENUM_THREE.getLabel().get(GERMAN), 1L)));
    }

    @Ignore
    @Test
    public void onNumberSetAttributes() throws Exception {
        testTermStats(FACET.allVariants().attribute().ofNumberSet(ATTR_NAME_NUMBER_SET).allTerms(),
                termStats -> assertThat(termStats).containsExactly(
                        TermStats.of("5.0", 2L),
                        TermStats.of("10.0", 1L)));
    }

    @Ignore
    @Test
    public void onNumberRangedSetAttributes() throws Exception {
        testRangeStats(FACET.allVariants().attribute().ofNumberSet(ATTR_NAME_NUMBER_SET).onlyGreaterThanOrEqualTo(valueOf(0)),
                rangeStats -> {
                    assertThat(rangeStats.getLowerEndpoint()).isEqualTo("0.0");
                    assertThat(rangeStats.getUpperEndpoint()).isEqualTo(null);
                    assertThat(rangeStats.getCount()).isEqualTo(2L);
                    assertThat(rangeStats.getMin()).isEqualTo("5.0");
                    assertThat(rangeStats.getMax()).isEqualTo("10.0");
                    assertThat(rangeStats.getSum()).isEqualTo("20.0");
                    assertThat(rangeStats.getMean()).isEqualTo(6.666666666666667D);
                });
    }

    @Ignore
    @Test
    public void onMoneyAmountSetAttributes() throws Exception {
        testTermStats(FACET.allVariants().attribute().ofMoneySet(ATTR_NAME_MONEY_SET).centAmount().allTerms(),
                termStats -> assertThat(termStats).containsExactly(
                        TermStats.of("50000", 2L),
                        TermStats.of("100000", 1L)));
    }

    @Ignore
    @Test
    public void onMoneyAmountRangedSetAttributes() throws Exception {
        testRangeStats(FACET.allVariants().attribute().ofMoneySet(ATTR_NAME_MONEY_SET).centAmount().onlyGreaterThanOrEqualTo(0L),
                rangeStats -> {
                    assertThat(rangeStats.getLowerEndpoint()).isEqualTo("0.0");
                    assertThat(rangeStats.getUpperEndpoint()).isEqualTo(null);
                    assertThat(rangeStats.getCount()).isEqualTo(2L);
                    assertThat(rangeStats.getMin()).isEqualTo("50000.0");
                    assertThat(rangeStats.getMax()).isEqualTo("100000.0");
                    assertThat(rangeStats.getSum()).isEqualTo("200000.0");
                    assertThat(rangeStats.getMean()).isEqualTo(66666.66666666667D);
                });
    }

    @Ignore
    @Test
    public void onMoneyCurrencySetAttributes() throws Exception {
        testTermStats(FACET.allVariants().attribute().ofMoneySet(ATTR_NAME_MONEY_SET).currency().allTerms(),
                termStats -> assertThat(termStats).containsExactly(
                        TermStats.of("EUR", 2L),
                        TermStats.of("USD", 1L)));
    }

    @Ignore
    @Test
    public void onDateSetAttributes() throws Exception {
        testTermStats(FACET.allVariants().attribute().ofDateSet(ATTR_NAME_DATE_SET).allTerms(),
                termStats -> assertThat(termStats).containsExactly(
                        TermStats.of("2001-09-11", 2L),
                        TermStats.of("2002-10-12", 1L)));
    }

    @Ignore
    @Test
    public void onDateRangedSetAttributes() throws Exception {
        testRangeStats(FACET.allVariants().attribute().ofDateSet(ATTR_NAME_DATE_SET).onlyGreaterThanOrEqualTo(DATE_2001),
                rangeStats -> {
                    assertThat(rangeStats.getLowerEndpoint()).isEqualTo("1.0001664E12");
                    assertThat(rangeStats.getUpperEndpoint()).isEqualTo(null);
                    assertThat(rangeStats.getCount()).isEqualTo(2L);
                    assertThat(rangeStats.getMin()).isEqualTo("1.0001664E12");
                    assertThat(rangeStats.getMax()).isEqualTo("1.0343808E12");
                    assertThat(rangeStats.getSum()).isEqualTo("3.0347136E12");
                    assertThat(rangeStats.getMean()).isEqualTo(1.0115712E12D);
                });
    }

    @Ignore
    @Test
    public void onTimeSetAttributes() throws Exception {
        testTermStats(FACET.allVariants().attribute().ofTimeSet(ATTR_NAME_TIME_SET).allTerms(),
                termStats -> assertThat(termStats).containsExactly(
                        TermStats.of("22:05:09.203", 2L),
                        TermStats.of("23:06:10.204", 1L)));
    }

    @Ignore
    @Test
    public void onTimeRangedSetAttributes() throws Exception {
        testRangeStats(FACET.allVariants().attribute().ofTimeSet(ATTR_NAME_TIME_SET).onlyGreaterThanOrEqualTo(TIME_22H),
                rangeStats -> {
                    assertThat(rangeStats.getLowerEndpoint()).isEqualTo("7.9509203E7");
                    assertThat(rangeStats.getUpperEndpoint()).isEqualTo(null);
                    assertThat(rangeStats.getCount()).isEqualTo(2L);
                    assertThat(rangeStats.getMin()).isEqualTo("7.9509203E7");
                    assertThat(rangeStats.getMax()).isEqualTo("8.3170204E7");
                    assertThat(rangeStats.getSum()).isEqualTo("2.4218861E8");
                    assertThat(rangeStats.getMean()).isEqualTo(8.072953666666667E7D);
                });
    }

    @Ignore
    @Test
    public void onDatetimeSetAttributes() throws Exception {
        testTermStats(FACET.allVariants().attribute().ofDateTimeSet(ATTR_NAME_DATE_TIME_SET).allTerms(),
                termStats -> assertThat(termStats).containsExactly(
                        TermStats.of("2001-09-11T22:05:09.203+0000", 2L),
                        TermStats.of("2002-10-12T23:06:10.204+0000", 1L)));
    }

    @Ignore
    @Test
    public void onDatetimeRangedSetAttributes() throws Exception {
        testRangeStats(FACET.allVariants().attribute().ofDateTimeSet(ATTR_NAME_DATE_TIME_SET).onlyGreaterThanOrEqualTo(DATE_TIME_2001_22H),
                rangeStats -> {
                    assertThat(rangeStats.getLowerEndpoint()).isEqualTo("1.000245909203E12");
                    assertThat(rangeStats.getUpperEndpoint()).isEqualTo(null);
                    assertThat(rangeStats.getCount()).isEqualTo(2L);
                    assertThat(rangeStats.getMin()).isEqualTo("1.000245909203E12");
                    assertThat(rangeStats.getMax()).isEqualTo("1.034463970204E12");
                    assertThat(rangeStats.getSum()).isEqualTo("3.03495578861E12");
                    assertThat(rangeStats.getMean()).isEqualTo(1.0116519295366666E12D);
                });
    }

    @Ignore
    @Test
    public void onReferenceSetAttributes() throws Exception {
        testTermStats(FACET.allVariants().attribute().ofReferenceSet(ATTR_NAME_REF_SET).id().allTerms(),
                termStats -> assertThat(termStats).containsExactly(
                        TermStats.of(productA.getId(), 2L),
                        TermStats.of(productB.getId(), 1L)));
    }

    private static void testTermStats(final TermFacetExpression<ProductProjection> facetExpr,
                                      final Consumer<List<TermStats>> test) {
        final List<TermStats> termStats = executeSearchWithFacets(facetExpr).getFacetResult(facetExpr).getTerms();
        test.accept(termStats);
    }

    private static void testRangeStats(final RangeFacetExpression<ProductProjection> facetExpr,
                                       final Consumer<RangeStats> test) {
        final List<RangeStats> ranges = executeSearchWithFacets(facetExpr).getFacetResult(facetExpr).getRanges();
        assertThat(ranges).hasSize(1);
        test.accept(ranges.get(0));
    }

    private static PagedSearchResult<ProductProjection> executeSearchWithFacets(final FacetExpression<ProductProjection> facetExpr) {
        return executeSearch(ProductProjectionSearch.ofStaged().withFacets(facetExpr));
    }
}
