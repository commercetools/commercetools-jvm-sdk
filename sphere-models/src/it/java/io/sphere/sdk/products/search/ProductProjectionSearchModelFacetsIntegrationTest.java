package io.sphere.sdk.products.search;

import io.sphere.sdk.products.ProductProjection;
import io.sphere.sdk.search.FacetExpression;
import io.sphere.sdk.search.PagedSearchResult;
import io.sphere.sdk.search.RangeStats;
import io.sphere.sdk.search.TermStats;
import org.junit.Test;

import java.util.List;
import java.util.function.Consumer;

import static io.sphere.sdk.test.SphereTestUtils.*;
import static java.math.BigDecimal.valueOf;
import static org.assertj.core.api.Assertions.assertThat;

public class ProductProjectionSearchModelFacetsIntegrationTest extends ProductProjectionSearchModelIntegrationTest {

    @Test
    public void facetedOnBooleanAttributes() throws Exception {
        testTermStats(model().allVariants().attribute().ofBoolean(ATTR_NAME_BOOLEAN).faceted().byAllTerms(),
                termStats -> assertThat(termStats).containsOnly(
                        TermStats.of(BOOL_TRUE, 1),
                        TermStats.of(BOOL_FALSE, 1)));
    }

    @Test
    public void facetedOnTextAttributes() throws Exception {
        testTermStats(model().allVariants().attribute().ofString(ATTR_NAME_TEXT).faceted().byAllTerms(),
                termStats -> assertThat(termStats).containsOnly(
                        TermStats.of(TEXT_FOO, 1),
                        TermStats.of(TEXT_BAR, 1)));
    }

    @Test
    public void facetedOnLocTextAttributes() throws Exception {
        testTermStats(model().allVariants().attribute().ofLocalizedString(ATTR_NAME_LOC_TEXT).locale(ENGLISH).faceted().byAllTerms(),
                termStats -> assertThat(termStats).containsOnly(
                        TermStats.of(LOC_TEXT_FOO.get(ENGLISH), 1),
                        TermStats.of(LOC_TEXT_BAR.get(ENGLISH), 1)));
    }

    @Test
    public void facetedOnEnumKeyAttributes() throws Exception {
        testTermStats(model().allVariants().attribute().ofEnum(ATTR_NAME_ENUM).key().faceted().byAllTerms(),
                termStats -> assertThat(termStats).containsOnly(
                        TermStats.of(ENUM_TWO.getKey(), 1),
                        TermStats.of(ENUM_THREE.getKey(), 1)));
    }

    @Test
    public void facetedOnEnumLabelAttributes() throws Exception {
        testTermStats(model().allVariants().attribute().ofEnum(ATTR_NAME_ENUM).label().faceted().byAllTerms(),
                termStats -> assertThat(termStats).containsOnly(
                        TermStats.of(ENUM_TWO.getLabel(), 1),
                        TermStats.of(ENUM_THREE.getLabel(), 1)));
    }

    @Test
    public void facetedOnLocEnumKeyAttributes() throws Exception {
        testTermStats(model().allVariants().attribute().ofLocalizableEnum(ATTR_NAME_LOC_ENUM).key().faceted().byAllTerms(),
                termStats -> assertThat(termStats).containsOnly(
                        TermStats.of(LOC_ENUM_TWO.getKey(), 1),
                        TermStats.of(LOC_ENUM_THREE.getKey(), 1)));
    }

    @Test
    public void facetedOnLocEnumLabelAttributes() throws Exception {
        testTermStats(model().allVariants().attribute().ofLocalizableEnum(ATTR_NAME_LOC_ENUM).label().locale(GERMAN).faceted().byAllTerms(),
                termStats -> assertThat(termStats).containsOnly(
                        TermStats.of(LOC_ENUM_TWO.getLabel().get(GERMAN), 1),
                        TermStats.of(LOC_ENUM_THREE.getLabel().get(GERMAN), 1)));
    }

    @Test
    public void facetedOnNumberAttributes() throws Exception {
        testTermStats(model().allVariants().attribute().ofNumber(ATTR_NAME_NUMBER).faceted().byAllTerms(),
                termStats -> assertThat(termStats).containsOnly(
                        TermStats.of("5.0", 1),
                        TermStats.of("10.0", 1)));
    }

    @Test
    public void facetedOnNumberRangedAttributes() throws Exception {
        testRangeStats(model().allVariants().attribute().ofNumber(ATTR_NAME_NUMBER).faceted().byGreaterThanOrEqualTo(valueOf(0)),
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
    public void facetedOnMoneyAmountAttributes() throws Exception {
        testTermStats(model().allVariants().attribute().ofMoney(ATTR_NAME_MONEY).centAmount().faceted().byAllTerms(),
                termStats -> assertThat(termStats).containsOnly(
                        TermStats.of("50000", 1),
                        TermStats.of("100000", 1)));
    }

    @Test
    public void facetedOnMoneyAmountRangedAttributes() throws Exception {
        testRangeStats(model().allVariants().attribute().ofMoney(ATTR_NAME_MONEY).centAmount().faceted().byGreaterThanOrEqualTo(0L),
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
    public void facetedOnMoneyCurrencyAttributes() throws Exception {
        testTermStats(model().allVariants().attribute().ofMoney(ATTR_NAME_MONEY).currency().faceted().byAllTerms(),
                termStats -> assertThat(termStats).containsOnly(
                        TermStats.of("EUR", 1),
                        TermStats.of("USD", 1)));
    }

    @Test
    public void facetedOnDateAttributes() throws Exception {
        testTermStats(model().allVariants().attribute().ofDate(ATTR_NAME_DATE).faceted().byAllTerms(),
                termStats -> assertThat(termStats).containsOnly(
                        TermStats.of("2001-09-11", 1),
                        TermStats.of("2002-10-12", 1)));
    }

    @Test
    public void facetedOnDateRangedAttributes() throws Exception {
        testRangeStats(model().allVariants().attribute().ofDate(ATTR_NAME_DATE).faceted().byGreaterThanOrEqualTo(DATE_2001),
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
    public void facetedOnTimeAttributes() throws Exception {
        testTermStats(model().allVariants().attribute().ofTime(ATTR_NAME_TIME).faceted().byAllTerms(),
                termStats -> assertThat(termStats).containsOnly(
                        TermStats.of("22:05:09.203", 1),
                        TermStats.of("23:06:10.204", 1)));
    }

    @Test
    public void facetedOnTimeRangedAttributes() throws Exception {
        testRangeStats(model().allVariants().attribute().ofTime(ATTR_NAME_TIME).faceted().byGreaterThanOrEqualTo(TIME_22H),
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
    public void facetedOnDatetimeAttributes() throws Exception {
        testTermStats(model().allVariants().attribute().ofDateTime(ATTR_NAME_DATE_TIME).faceted().byAllTerms(),
                termStats -> assertThat(termStats).containsOnly(
                        TermStats.of("2002-10-12T23:06:10.204+0000", 1),
                        TermStats.of("2001-09-11T22:05:09.203+0000", 1)));
    }

    @Test
    public void facetedOnDatetimeRangedAttributes() throws Exception {
        testRangeStats(model().allVariants().attribute().ofDateTime(ATTR_NAME_DATE_TIME).faceted().byGreaterThanOrEqualTo(DATE_TIME_2001_22H),
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
    public void facetedOnReferenceAttributes() throws Exception {
        testTermStats(model().allVariants().attribute().ofReference(ATTR_NAME_REF).id().faceted().byAllTerms(),
                termStats -> assertThat(termStats).containsOnly(
                        TermStats.of(productSomeId.getId(), 1),
                        TermStats.of(productOtherId.getId(), 1)));
    }

    @Test
    public void facetedOnBooleanSetAttributes() throws Exception {
        testTermStats(model().allVariants().attribute().ofString(ATTR_NAME_BOOLEAN_SET).faceted().byAllTerms(),
                termStats -> assertThat(termStats).containsExactly(
                        TermStats.of(BOOL_TRUE, 2),
                        TermStats.of(BOOL_FALSE, 1)));
    }

    @Test
    public void facetedOnTextSetAttributes() throws Exception {
        testTermStats(model().allVariants().attribute().ofString(ATTR_NAME_TEXT_SET).faceted().byAllTerms(),
                termStats -> assertThat(termStats).containsExactly(
                        TermStats.of(TEXT_FOO, 2),
                        TermStats.of(TEXT_BAR, 1)));
    }

    @Test
    public void facetedOnLocTextSetAttributes() throws Exception {
        testTermStats(model().allVariants().attribute().ofLocalizedString(ATTR_NAME_LOC_TEXT_SET).locale(ENGLISH).faceted().byAllTerms(),
                termStats -> assertThat(termStats).containsExactly(
                        TermStats.of(LOC_TEXT_FOO.get(ENGLISH), 2),
                        TermStats.of(LOC_TEXT_BAR.get(ENGLISH), 1)));
    }

    @Test
    public void facetedOnEnumKeySetAttributes() throws Exception {
        testTermStats(model().allVariants().attribute().ofEnum(ATTR_NAME_ENUM_SET).key().faceted().byAllTerms(),
                termStats -> assertThat(termStats).containsExactly(
                        TermStats.of(ENUM_TWO.getKey(), 2),
                        TermStats.of(ENUM_THREE.getKey(), 1)));
    }

    @Test
    public void facetedOnEnumLabelSetAttributes() throws Exception {
        testTermStats(model().allVariants().attribute().ofEnum(ATTR_NAME_ENUM_SET).label().faceted().byAllTerms(),
                termStats -> assertThat(termStats).containsExactly(
                        TermStats.of(ENUM_TWO.getLabel(), 2),
                        TermStats.of(ENUM_THREE.getLabel(), 1)));
    }

    @Test
    public void facetedOnLocEnumKeySetAttributes() throws Exception {
        testTermStats(model().allVariants().attribute().ofLocalizableEnum(ATTR_NAME_LOC_ENUM_SET).key().faceted().byAllTerms(),
                termStats -> assertThat(termStats).containsExactly(
                        TermStats.of(LOC_ENUM_TWO.getKey(), 2),
                        TermStats.of(LOC_ENUM_THREE.getKey(), 1)));
    }

    @Test
    public void facetedOnLocEnumLabelSetAttributes() throws Exception {
        testTermStats(model().allVariants().attribute().ofLocalizableEnum(ATTR_NAME_LOC_ENUM_SET).label().locale(GERMAN).faceted().byAllTerms(),
                termStats -> assertThat(termStats).containsExactly(
                        TermStats.of(LOC_ENUM_TWO.getLabel().get(GERMAN), 2),
                        TermStats.of(LOC_ENUM_THREE.getLabel().get(GERMAN), 1)));
    }

    @Test
    public void facetedOnNumberSetAttributes() throws Exception {
        testTermStats(model().allVariants().attribute().ofNumber(ATTR_NAME_NUMBER_SET).faceted().byAllTerms(),
                termStats -> assertThat(termStats).containsExactly(
                        TermStats.of("5.0", 2),
                        TermStats.of("10.0", 1)));
    }

    @Test
    public void facetedOnNumberRangedSetAttributes() throws Exception {
        testRangeStats(model().allVariants().attribute().ofNumber(ATTR_NAME_NUMBER_SET).faceted().byGreaterThanOrEqualTo(valueOf(0)),
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
    public void facetedOnMoneyAmountSetAttributes() throws Exception {
        testTermStats(model().allVariants().attribute().ofMoney(ATTR_NAME_MONEY_SET).centAmount().faceted().byAllTerms(),
                termStats -> assertThat(termStats).containsExactly(
                        TermStats.of("50000", 2),
                        TermStats.of("100000", 1)));
    }

    @Test
    public void facetedOnMoneyAmountRangedSetAttributes() throws Exception {
        testRangeStats(model().allVariants().attribute().ofMoney(ATTR_NAME_MONEY_SET).centAmount().faceted().byGreaterThanOrEqualTo(0L),
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
    public void facetedOnMoneyCurrencySetAttributes() throws Exception {
        testTermStats(model().allVariants().attribute().ofMoney(ATTR_NAME_MONEY_SET).currency().faceted().byAllTerms(),
                termStats -> assertThat(termStats).containsExactly(
                        TermStats.of("EUR", 2),
                        TermStats.of("USD", 1)));
    }

    @Test
    public void facetedOnDateSetAttributes() throws Exception {
        testTermStats(model().allVariants().attribute().ofDate(ATTR_NAME_DATE_SET).faceted().byAllTerms(),
                termStats -> assertThat(termStats).containsExactly(
                        TermStats.of("2001-09-11", 2),
                        TermStats.of("2002-10-12", 1)));
    }

    @Test
    public void facetedOnDateRangedSetAttributes() throws Exception {
        testRangeStats(model().allVariants().attribute().ofDate(ATTR_NAME_DATE_SET).faceted().byGreaterThanOrEqualTo(DATE_2001),
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
    public void facetedOnTimeSetAttributes() throws Exception {
        testTermStats(model().allVariants().attribute().ofTime(ATTR_NAME_TIME_SET).faceted().byAllTerms(),
                termStats -> assertThat(termStats).containsExactly(
                        TermStats.of("22:05:09.203", 2),
                        TermStats.of("23:06:10.204", 1)));
    }

    @Test
    public void facetedOnTimeRangedSetAttributes() throws Exception {
        testRangeStats(model().allVariants().attribute().ofTime(ATTR_NAME_TIME_SET).faceted().byGreaterThanOrEqualTo(TIME_22H),
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
    public void facetedOnDatetimeSetAttributes() throws Exception {
        testTermStats(model().allVariants().attribute().ofDateTime(ATTR_NAME_DATE_TIME_SET).faceted().byAllTerms(),
                termStats -> assertThat(termStats).containsExactly(
                        TermStats.of("2001-09-11T22:05:09.203+0000", 2),
                        TermStats.of("2002-10-12T23:06:10.204+0000", 1)));
    }

    @Test
    public void facetedOnDatetimeRangedSetAttributes() throws Exception {
        testRangeStats(model().allVariants().attribute().ofDateTime(ATTR_NAME_DATE_TIME_SET).faceted().byGreaterThanOrEqualTo(DATE_TIME_2001_22H),
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
    public void facetedOnReferenceSetAttributes() throws Exception {
        testTermStats(model().allVariants().attribute().ofReference(ATTR_NAME_REF_SET).id().faceted().byAllTerms(),
                termStats -> assertThat(termStats).containsExactly(
                        TermStats.of(productSomeId.getId(), 2),
                        TermStats.of(productOtherId.getId(), 1)));
    }

    private static void testTermStats(final FacetExpression<ProductProjection> facetExpr,
                                      final Consumer<List<TermStats>> test) {
        final List<TermStats> termStats = executeSearchWithFacets(facetExpr).getTermFacetResult(facetExpr).getTerms();
        test.accept(termStats);
    }

    private static void testRangeStats(final FacetExpression<ProductProjection> facetExpr,
                                       final Consumer<RangeStats> test) {
        final List<RangeStats> ranges = executeSearchWithFacets(facetExpr).getRangeFacetResult(facetExpr).getRanges();
        assertThat(ranges).hasSize(1);
        test.accept(ranges.get(0));
    }

    private static PagedSearchResult<ProductProjection> executeSearchWithFacets(final FacetExpression<ProductProjection> facetExpr) {
        return executeSearch(ProductProjectionSearch.ofStaged().withFacets(facetExpr));
    }
}
