package io.sphere.sdk.products.search;

import io.sphere.sdk.products.ProductProjection;
import io.sphere.sdk.search.*;
import io.sphere.sdk.search.model.RangeStats;
import org.junit.Test;

import java.util.List;
import java.util.function.Consumer;

import static io.sphere.sdk.test.SphereTestUtils.*;
import static java.math.BigDecimal.valueOf;
import static org.assertj.core.api.Assertions.assertThat;

public class ProductProjectionSearchModelFacetsIntegrationTest extends ProductProjectionSearchModelIntegrationTest {

    public static final ProductProjectionFacetSearchModel FACET = ProductProjectionSearchModel.of().facet();

    @Test
    public void onBooleanAttributes() throws Exception {
        testTermStats(FACET.allVariants().attribute().ofBoolean(ATTR_NAME_BOOLEAN).allTerms(),
                termStats -> assertThat(termStats).containsOnly(
                        TermStats.of(BOOL_TRUE, 1),
                        TermStats.of(BOOL_FALSE, 1)));
    }

    @Test
    public void onTextAttributes() throws Exception {
        testTermStats(FACET.allVariants().attribute().ofString(ATTR_NAME_TEXT).allTerms(),
                termStats -> assertThat(termStats).containsOnly(
                        TermStats.of(TEXT_FOO, 1),
                        TermStats.of(TEXT_BAR, 1)));
    }

    @Test
    public void onLocTextAttributes() throws Exception {
        testTermStats(FACET.allVariants().attribute().ofLocalizedString(ATTR_NAME_LOC_TEXT).locale(ENGLISH).allTerms(),
                termStats -> assertThat(termStats).containsOnly(
                        TermStats.of(LOC_TEXT_FOO.get(ENGLISH), 1),
                        TermStats.of(LOC_TEXT_BAR.get(ENGLISH), 1)));
    }

    @Test
    public void onEnumKeyAttributes() throws Exception {
        testTermStats(FACET.allVariants().attribute().ofEnum(ATTR_NAME_ENUM).key().allTerms(),
                termStats -> assertThat(termStats).containsOnly(
                        TermStats.of(ENUM_TWO.getKey(), 1),
                        TermStats.of(ENUM_THREE.getKey(), 1)));
    }

    @Test
    public void onEnumLabelAttributes() throws Exception {
        testTermStats(FACET.allVariants().attribute().ofEnum(ATTR_NAME_ENUM).label().allTerms(),
                termStats -> assertThat(termStats).containsOnly(
                        TermStats.of(ENUM_TWO.getLabel(), 1),
                        TermStats.of(ENUM_THREE.getLabel(), 1)));
    }

    @Test
    public void onLocEnumKeyAttributes() throws Exception {
        testTermStats(FACET.allVariants().attribute().ofLocalizableEnum(ATTR_NAME_LOC_ENUM).key().allTerms(),
                termStats -> assertThat(termStats).containsOnly(
                        TermStats.of(LOC_ENUM_TWO.getKey(), 1),
                        TermStats.of(LOC_ENUM_THREE.getKey(), 1)));
    }

    @Test
    public void onLocEnumLabelAttributes() throws Exception {
        testTermStats(FACET.allVariants().attribute().ofLocalizableEnum(ATTR_NAME_LOC_ENUM).label().locale(GERMAN).allTerms(),
                termStats -> assertThat(termStats).containsOnly(
                        TermStats.of(LOC_ENUM_TWO.getLabel().get(GERMAN), 1),
                        TermStats.of(LOC_ENUM_THREE.getLabel().get(GERMAN), 1)));
    }

    @Test
    public void onNumberAttributes() throws Exception {
        testTermStats(FACET.allVariants().attribute().ofNumber(ATTR_NAME_NUMBER).allTerms(),
                termStats -> assertThat(termStats).containsOnly(
                        TermStats.of("5.0", 1),
                        TermStats.of("10.0", 1)));
    }

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

    @Test
    public void onMoneyAmountAttributes() throws Exception {
        testTermStats(FACET.allVariants().attribute().ofMoney(ATTR_NAME_MONEY).centAmount().allTerms(),
                termStats -> assertThat(termStats).containsOnly(
                        TermStats.of("50000", 1),
                        TermStats.of("100000", 1)));
    }

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

    @Test
    public void onMoneyCurrencyAttributes() throws Exception {
        testTermStats(FACET.allVariants().attribute().ofMoney(ATTR_NAME_MONEY).currency().allTerms(),
                termStats -> assertThat(termStats).containsOnly(
                        TermStats.of("EUR", 1),
                        TermStats.of("USD", 1)));
    }

    @Test
    public void onDateAttributes() throws Exception {
        testTermStats(FACET.allVariants().attribute().ofDate(ATTR_NAME_DATE).allTerms(),
                termStats -> assertThat(termStats).containsOnly(
                        TermStats.of("2001-09-11", 1),
                        TermStats.of("2002-10-12", 1)));
    }

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

    @Test
    public void onTimeAttributes() throws Exception {
        testTermStats(FACET.allVariants().attribute().ofTime(ATTR_NAME_TIME).allTerms(),
                termStats -> assertThat(termStats).containsOnly(
                        TermStats.of("22:05:09.203", 1),
                        TermStats.of("23:06:10.204", 1)));
    }

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

    @Test
    public void onDatetimeAttributes() throws Exception {
        testTermStats(FACET.allVariants().attribute().ofDateTime(ATTR_NAME_DATE_TIME).allTerms(),
                termStats -> assertThat(termStats).containsOnly(
                        TermStats.of("2002-10-12T23:06:10.204+0000", 1),
                        TermStats.of("2001-09-11T22:05:09.203+0000", 1)));
    }

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

    @Test
    public void onReferenceAttributes() throws Exception {
        testTermStats(FACET.allVariants().attribute().ofReference(ATTR_NAME_REF).id().allTerms(),
                termStats -> assertThat(termStats).containsOnly(
                        TermStats.of(productSomeId.getId(), 1),
                        TermStats.of(productOtherId.getId(), 1)));
    }

    @Test
    public void onBooleanSetAttributes() throws Exception {
        testTermStats(FACET.allVariants().attribute().ofBooleanSet(ATTR_NAME_BOOLEAN_SET).allTerms(),
                termStats -> assertThat(termStats).containsExactly(
                        TermStats.of(BOOL_TRUE, 2),
                        TermStats.of(BOOL_FALSE, 1)));
    }

    @Test
    public void onTextSetAttributes() throws Exception {
        testTermStats(FACET.allVariants().attribute().ofStringSet(ATTR_NAME_TEXT_SET).allTerms(),
                termStats -> assertThat(termStats).containsExactly(
                        TermStats.of(TEXT_FOO, 2),
                        TermStats.of(TEXT_BAR, 1)));
    }

    @Test
    public void onLocTextSetAttributes() throws Exception {
        testTermStats(FACET.allVariants().attribute().ofLocalizedStringSet(ATTR_NAME_LOC_TEXT_SET).locale(ENGLISH).allTerms(),
                termStats -> assertThat(termStats).containsExactly(
                        TermStats.of(LOC_TEXT_FOO.get(ENGLISH), 2),
                        TermStats.of(LOC_TEXT_BAR.get(ENGLISH), 1)));
    }

    @Test
    public void onEnumKeySetAttributes() throws Exception {
        testTermStats(FACET.allVariants().attribute().ofEnumSet(ATTR_NAME_ENUM_SET).key().allTerms(),
                termStats -> assertThat(termStats).containsExactly(
                        TermStats.of(ENUM_TWO.getKey(), 2),
                        TermStats.of(ENUM_THREE.getKey(), 1)));
    }

    @Test
    public void onEnumLabelSetAttributes() throws Exception {
        testTermStats(FACET.allVariants().attribute().ofEnumSet(ATTR_NAME_ENUM_SET).label().allTerms(),
                termStats -> assertThat(termStats).containsExactly(
                        TermStats.of(ENUM_TWO.getLabel(), 2),
                        TermStats.of(ENUM_THREE.getLabel(), 1)));
    }

    @Test
    public void onLocEnumKeySetAttributes() throws Exception {
        testTermStats(FACET.allVariants().attribute().ofLocalizableEnumSet(ATTR_NAME_LOC_ENUM_SET).key().allTerms(),
                termStats -> assertThat(termStats).containsExactly(
                        TermStats.of(LOC_ENUM_TWO.getKey(), 2),
                        TermStats.of(LOC_ENUM_THREE.getKey(), 1)));
    }

    @Test
    public void onLocEnumLabelSetAttributes() throws Exception {
        testTermStats(FACET.allVariants().attribute().ofLocalizableEnumSet(ATTR_NAME_LOC_ENUM_SET).label().locale(GERMAN).allTerms(),
                termStats -> assertThat(termStats).containsExactly(
                        TermStats.of(LOC_ENUM_TWO.getLabel().get(GERMAN), 2),
                        TermStats.of(LOC_ENUM_THREE.getLabel().get(GERMAN), 1)));
    }

    @Test
    public void onNumberSetAttributes() throws Exception {
        testTermStats(FACET.allVariants().attribute().ofNumberSet(ATTR_NAME_NUMBER_SET).allTerms(),
                termStats -> assertThat(termStats).containsExactly(
                        TermStats.of("5.0", 2),
                        TermStats.of("10.0", 1)));
    }

    @Test
    public void onNumberRangedSetAttributes() throws Exception {
        testRangeStats(FACET.allVariants().attribute().ofNumberSet(ATTR_NAME_NUMBER_SET).onlyGreaterThanOrEqualTo(valueOf(0)),
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
    public void onMoneyAmountSetAttributes() throws Exception {
        testTermStats(FACET.allVariants().attribute().ofMoneySet(ATTR_NAME_MONEY_SET).centAmount().allTerms(),
                termStats -> assertThat(termStats).containsExactly(
                        TermStats.of("50000", 2),
                        TermStats.of("100000", 1)));
    }

    @Test
    public void onMoneyAmountRangedSetAttributes() throws Exception {
        testRangeStats(FACET.allVariants().attribute().ofMoneySet(ATTR_NAME_MONEY_SET).centAmount().onlyGreaterThanOrEqualTo(0L),
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
    public void onMoneyCurrencySetAttributes() throws Exception {
        testTermStats(FACET.allVariants().attribute().ofMoneySet(ATTR_NAME_MONEY_SET).currency().allTerms(),
                termStats -> assertThat(termStats).containsExactly(
                        TermStats.of("EUR", 2),
                        TermStats.of("USD", 1)));
    }

    @Test
    public void onDateSetAttributes() throws Exception {
        testTermStats(FACET.allVariants().attribute().ofDateSet(ATTR_NAME_DATE_SET).allTerms(),
                termStats -> assertThat(termStats).containsExactly(
                        TermStats.of("2001-09-11", 2),
                        TermStats.of("2002-10-12", 1)));
    }

    @Test
    public void onDateRangedSetAttributes() throws Exception {
        testRangeStats(FACET.allVariants().attribute().ofDateSet(ATTR_NAME_DATE_SET).onlyGreaterThanOrEqualTo(DATE_2001),
                rangeStats -> {
                    assertThat(rangeStats.getLowerEndpoint()).isEqualTo("1.0001664E12");
                    assertThat(rangeStats.getUpperEndpoint()).isEqualTo(null);
                    assertThat(rangeStats.getCount()).isEqualTo(2L);
                    assertThat(rangeStats.getMin()).isEqualTo("1.0001664E12");
                    assertThat(rangeStats.getMax()).isEqualTo("1.0001664E12");
                    assertThat(rangeStats.getSum()).isEqualTo("2.0003328E12");
                    assertThat(rangeStats.getMean()).isEqualTo(1.0001664E12D);
                });
    }

    @Test
    public void onTimeSetAttributes() throws Exception {
        testTermStats(FACET.allVariants().attribute().ofTimeSet(ATTR_NAME_TIME_SET).allTerms(),
                termStats -> assertThat(termStats).containsExactly(
                        TermStats.of("22:05:09.203", 2),
                        TermStats.of("23:06:10.204", 1)));
    }

    @Test
    public void onTimeRangedSetAttributes() throws Exception {
        testRangeStats(FACET.allVariants().attribute().ofTimeSet(ATTR_NAME_TIME_SET).onlyGreaterThanOrEqualTo(TIME_22H),
                rangeStats -> {
                    assertThat(rangeStats.getLowerEndpoint()).isEqualTo("7.9509203E7");
                    assertThat(rangeStats.getUpperEndpoint()).isEqualTo(null);
                    assertThat(rangeStats.getCount()).isEqualTo(2L);
                    assertThat(rangeStats.getMin()).isEqualTo("7.9509203E7");
                    assertThat(rangeStats.getMax()).isEqualTo("7.9509203E7");
                    assertThat(rangeStats.getSum()).isEqualTo("1.59018406E8");
                    assertThat(rangeStats.getMean()).isEqualTo(7.9509203E7D);
                });
    }

    @Test
    public void onDatetimeSetAttributes() throws Exception {
        testTermStats(FACET.allVariants().attribute().ofDateTimeSet(ATTR_NAME_DATE_TIME_SET).allTerms(),
                termStats -> assertThat(termStats).containsExactly(
                        TermStats.of("2001-09-11T22:05:09.203+0000", 2),
                        TermStats.of("2002-10-12T23:06:10.204+0000", 1)));
    }

    @Test
    public void onDatetimeRangedSetAttributes() throws Exception {
        testRangeStats(FACET.allVariants().attribute().ofDateTimeSet(ATTR_NAME_DATE_TIME_SET).onlyGreaterThanOrEqualTo(DATE_TIME_2001_22H),
                rangeStats -> {
                    assertThat(rangeStats.getLowerEndpoint()).isEqualTo("1.000245909203E12");
                    assertThat(rangeStats.getUpperEndpoint()).isEqualTo(null);
                    assertThat(rangeStats.getCount()).isEqualTo(2L);
                    assertThat(rangeStats.getMin()).isEqualTo("1.000245909203E12");
                    assertThat(rangeStats.getMax()).isEqualTo("1.000245909203E12");
                    assertThat(rangeStats.getSum()).isEqualTo("2.000491818406E12");
                    assertThat(rangeStats.getMean()).isEqualTo(1.000245909203E12D);
                });
    }

    @Test
    public void onReferenceSetAttributes() throws Exception {
        testTermStats(FACET.allVariants().attribute().ofReferenceSet(ATTR_NAME_REF_SET).id().allTerms(),
                termStats -> assertThat(termStats).containsExactly(
                        TermStats.of(productSomeId.getId(), 2),
                        TermStats.of(productOtherId.getId(), 1)));
    }

    private static void testTermStats(final TermFacetExpression<ProductProjection> facetExpr,
                                      final Consumer<List<TermStats>> test) {
        final List<TermStats> termStats = executeSearchWithFacets(facetExpr).getTermFacetResult(facetExpr).getTerms();
        test.accept(termStats);
    }

    private static void testRangeStats(final RangeFacetExpression<ProductProjection> facetExpr,
                                       final Consumer<RangeStats> test) {
        final List<RangeStats> ranges = executeSearchWithFacets(facetExpr).getRangeFacetResult(facetExpr).getRanges();
        assertThat(ranges).hasSize(1);
        test.accept(ranges.get(0));
    }

    private static PagedSearchResult<ProductProjection> executeSearchWithFacets(final FacetExpression<ProductProjection> facetExpr) {
        return executeSearch(ProductProjectionSearch.ofStaged().withFacets(facetExpr));
    }
}
