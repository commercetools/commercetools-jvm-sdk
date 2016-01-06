package io.sphere.sdk.products.search;

import io.sphere.sdk.products.ProductProjection;
import io.sphere.sdk.search.FilterExpression;
import org.junit.Test;

import java.util.List;
import java.util.function.Consumer;

import static io.sphere.sdk.test.SphereTestUtils.*;
import static org.assertj.core.api.Assertions.assertThat;

import static io.sphere.sdk.products.ProductsScenario1Fixtures.*;

public class ProductProjectionSearchModelFiltersIntegrationTest extends ProductProjectionSearchModelIntegrationTest {

    public static final ProductProjectionFilterSearchModel FILTER = ProductProjectionSearchModel.of().filter();

    @Test
    public void onBooleanAttributes() throws Exception {
        testProductIds(FILTER.allVariants().attribute().ofBoolean(ATTR_NAME_BOOLEAN).by(true),
                ids -> assertThat(ids).containsOnly(product1.getId()));
    }

    @Test
    public void onTextAttributes() throws Exception {
        testProductIds(FILTER.allVariants().attribute().ofString(ATTR_NAME_TEXT).by(TEXT_FOO),
                ids -> assertThat(ids).containsOnly(product1.getId()));
    }

    @Test
    public void onLocTextAttributes() throws Exception {
        testProductIds(FILTER.allVariants().attribute().ofLocalizedString(ATTR_NAME_LOC_TEXT).locale(ENGLISH).by(LOC_TEXT_FOO.get(ENGLISH)),
                ids -> assertThat(ids).containsOnly(product1.getId()));
    }

    @Test
    public void onEnumKeyAttributes() throws Exception {
        testProductIds(FILTER.allVariants().attribute().ofEnum(ATTR_NAME_ENUM).key().by(ENUM_TWO.getKey()),
                ids -> assertThat(ids).containsOnly(product1.getId()));
    }

    @Test
    public void onEnumLabelAttributes() throws Exception {
        testProductIds(FILTER.allVariants().attribute().ofEnum(ATTR_NAME_ENUM).label().by(ENUM_TWO.getLabel()),
                ids -> assertThat(ids).containsOnly(product1.getId()));
    }

    @Test
    public void onLocEnumKeyAttributes() throws Exception {
        testProductIds(FILTER.allVariants().attribute().ofLocalizedEnum(ATTR_NAME_LOC_ENUM).key().by(LOC_ENUM_TWO.getKey()),
                ids -> assertThat(ids).containsOnly(product1.getId()));
    }

    @Test
    public void onLocEnumLabelAttributes() throws Exception {
        testProductIds(FILTER.allVariants().attribute().ofLocalizedEnum(ATTR_NAME_LOC_ENUM).label().locale(GERMAN).by(LOC_ENUM_TWO.getLabel().get(GERMAN)),
                ids -> assertThat(ids).containsOnly(product1.getId()));
    }

    @Test
    public void onNumberAttributes() throws Exception {
        testProductIds(FILTER.allVariants().attribute().ofNumber(ATTR_NAME_NUMBER).by(NUMBER_5),
                ids -> assertThat(ids).containsOnly(product1.getId()));
    }

    @Test
    public void onNumberRangedAttributes() throws Exception {
        testProductIds(FILTER.allVariants().attribute().ofNumber(ATTR_NAME_NUMBER).byLessThanOrEqualTo(NUMBER_5),
                ids -> assertThat(ids).containsOnly(product1.getId()));
    }

    @Test
    public void onMoneyAmountAttributes() throws Exception {
        testProductIds(FILTER.allVariants().attribute().ofMoney(ATTR_NAME_MONEY).centAmount().by(toCents(MONEY_500_EUR)),
                ids -> assertThat(ids).containsOnly(product1.getId()));
    }

    @Test
    public void onMoneyAmountRangedAttributes() throws Exception {
        testProductIds(FILTER.allVariants().attribute().ofMoney(ATTR_NAME_MONEY).centAmount().byLessThanOrEqualTo(toCents(MONEY_500_EUR)),
                ids -> assertThat(ids).containsOnly(product1.getId()));
    }

    @Test
    public void onMoneyCurrencyAttributes() throws Exception {
        testProductIds(FILTER.allVariants().attribute().ofMoney(ATTR_NAME_MONEY).currency().by(MONEY_500_EUR.getCurrency()),
                ids -> assertThat(ids).containsOnly(product1.getId()));
    }

    @Test
    public void onDateAttributes() throws Exception {
        testProductIds(FILTER.allVariants().attribute().ofDate(ATTR_NAME_DATE).by(DATE_2001),
                ids -> assertThat(ids).containsOnly(product1.getId()));
    }

    @Test
    public void onDateRangedAttributes() throws Exception {
        testProductIds(FILTER.allVariants().attribute().ofDate(ATTR_NAME_DATE).byLessThanOrEqualTo(DATE_2001),
                ids -> assertThat(ids).containsOnly(product1.getId()));
    }

    @Test
    public void onTimeAttributes() throws Exception {
        testProductIds(FILTER.allVariants().attribute().ofTime(ATTR_NAME_TIME).by(TIME_22H),
                ids -> assertThat(ids).containsOnly(product1.getId()));
    }

    @Test
    public void onTimeRangedAttributes() throws Exception {
        testProductIds(FILTER.allVariants().attribute().ofTime(ATTR_NAME_TIME).byLessThanOrEqualTo(TIME_22H),
                ids -> assertThat(ids).containsOnly(product1.getId()));
    }

    @Test
    public void onDateTimeAttributes() throws Exception {
        testProductIds(FILTER.allVariants().attribute().ofDateTime(ATTR_NAME_DATE_TIME).by(DATE_TIME_2001_22H),
                ids -> assertThat(ids).containsOnly(product1.getId()));
    }

    @Test
    public void onDateTimeRangedAttributes() throws Exception {
        testProductIds(FILTER.allVariants().attribute().ofDateTime(ATTR_NAME_DATE_TIME).byLessThanOrEqualTo(DATE_TIME_2001_22H),
                ids -> assertThat(ids).containsOnly(product1.getId()));
    }

    @Test
    public void onReferenceAttributes() throws Exception {
        testProductIds(FILTER.allVariants().attribute().ofReference(ATTR_NAME_REF).id().by(productSomeId.getId()),
                ids -> assertThat(ids).containsOnly(product1.getId()));
    }

    @Test
    public void onBooleanSetAttributes() throws Exception {
        testProductIds(FILTER.allVariants().attribute().ofBooleanSet(ATTR_NAME_BOOLEAN_SET).by(false),
                ids -> assertThat(ids).containsOnly(product1.getId()));
    }

    @Test
    public void onTextSetAttributes() throws Exception {
        testProductIds(FILTER.allVariants().attribute().ofStringSet(ATTR_NAME_TEXT_SET).by(TEXT_BAR),
                ids -> assertThat(ids).containsOnly(product1.getId()));
    }

    @Test
    public void onLocTextSetAttributes() throws Exception {
        testProductIds(FILTER.allVariants().attribute().ofLocalizedStringSet(ATTR_NAME_LOC_TEXT_SET).locale(ENGLISH).by(LOC_TEXT_BAR.get(ENGLISH)),
                ids -> assertThat(ids).containsOnly(product1.getId()));
    }

    @Test
    public void onEnumKeySetAttributes() throws Exception {
        testProductIds(FILTER.allVariants().attribute().ofEnumSet(ATTR_NAME_ENUM_SET).key().by(ENUM_THREE.getKey()),
                ids -> assertThat(ids).containsOnly(product1.getId()));
    }

    @Test
    public void onEnumLabelSetAttributes() throws Exception {
        testProductIds(FILTER.allVariants().attribute().ofEnumSet(ATTR_NAME_ENUM_SET).label().by(ENUM_THREE.getLabel()),
                ids -> assertThat(ids).containsOnly(product1.getId()));
    }

    @Test
    public void onLocEnumKeySetAttributes() throws Exception {
        testProductIds(FILTER.allVariants().attribute().ofLocalizedEnumSet(ATTR_NAME_LOC_ENUM_SET).key().by(LOC_ENUM_THREE.getKey()),
                ids -> assertThat(ids).containsOnly(product1.getId()));
    }

    @Test
    public void onLocEnumLabelSetAttributes() throws Exception {
        testProductIds(FILTER.allVariants().attribute().ofLocalizedEnumSet(ATTR_NAME_LOC_ENUM_SET).label().locale(GERMAN).by(LOC_ENUM_THREE.getLabel().get(GERMAN)),
                ids -> assertThat(ids).containsOnly(product1.getId()));
    }

    @Test
    public void onNumberSetAttributes() throws Exception {
        testProductIds(FILTER.allVariants().attribute().ofNumberSet(ATTR_NAME_NUMBER_SET).by(NUMBER_10),
                ids -> assertThat(ids).containsOnly(product1.getId()));
    }

    @Test
    public void onNumberRangedSetAttributes() throws Exception {
        testProductIds(FILTER.allVariants().attribute().ofNumberSet(ATTR_NAME_NUMBER_SET).byGreaterThanOrEqualTo(NUMBER_10),
                ids -> assertThat(ids).containsOnly(product1.getId()));
    }

    @Test
    public void onMoneyAmountSetAttributes() throws Exception {
        testProductIds(FILTER.allVariants().attribute().ofMoneySet(ATTR_NAME_MONEY_SET).centAmount().by(toCents(MONEY_1000_USD)),
                ids -> assertThat(ids).containsOnly(product1.getId()));
    }

    @Test
    public void onMoneyAmountRangedSetAttributes() throws Exception {
        testProductIds(FILTER.allVariants().attribute().ofMoneySet(ATTR_NAME_MONEY_SET).centAmount().byGreaterThanOrEqualTo(toCents(MONEY_1000_USD)),
                ids -> assertThat(ids).containsOnly(product1.getId()));
    }

    @Test
    public void onMoneyCurrencySetAttributes() throws Exception {
        testProductIds(FILTER.allVariants().attribute().ofMoneySet(ATTR_NAME_MONEY_SET).currency().by(MONEY_1000_USD.getCurrency()),
                ids -> assertThat(ids).containsOnly(product1.getId()));
    }

    @Test
    public void onDateSetAttributes() throws Exception {
        testProductIds(FILTER.allVariants().attribute().ofDateSet(ATTR_NAME_DATE_SET).by(DATE_2002),
                ids -> assertThat(ids).containsOnly(product1.getId()));
    }

    @Test
    public void onDateRangedSetAttributes() throws Exception {
        testProductIds(FILTER.allVariants().attribute().ofDateSet(ATTR_NAME_DATE_SET).byGreaterThanOrEqualTo(DATE_2002),
                ids -> assertThat(ids).containsOnly(product1.getId()));
    }

    @Test
    public void onTimeSetAttributes() throws Exception {
        testProductIds(FILTER.allVariants().attribute().ofTimeSet(ATTR_NAME_TIME_SET).by(TIME_23H),
                ids -> assertThat(ids).containsOnly(product1.getId()));
    }

    @Test
    public void onTimeRangedSetAttributes() throws Exception {
        testProductIds(FILTER.allVariants().attribute().ofTimeSet(ATTR_NAME_TIME_SET).byGreaterThanOrEqualTo(TIME_23H),
                ids -> assertThat(ids).containsOnly(product1.getId()));
    }

    @Test
    public void onDateTimeSetAttributes() throws Exception {
        testProductIds(FILTER.allVariants().attribute().ofDateTimeSet(ATTR_NAME_DATE_TIME_SET).by(DATE_TIME_2002_23H),
                ids -> assertThat(ids).containsOnly(product1.getId()));
    }

    @Test
    public void onDateTimeRangedSetAttributes() throws Exception {
        testProductIds(FILTER.allVariants().attribute().ofDateTimeSet(ATTR_NAME_DATE_TIME_SET).byGreaterThanOrEqualTo(DATE_TIME_2002_23H),
                ids -> assertThat(ids).containsOnly(product1.getId()));
    }

    @Test
    public void onReferenceSetAttributes() throws Exception {
        testProductIds(FILTER.allVariants().attribute().ofReferenceSet(ATTR_NAME_REF_SET).id().by(productOtherId.getId()),
                ids -> assertThat(ids).containsOnly(product1.getId()));
    }

    private static void testProductIds(final List<FilterExpression<ProductProjection>> filterExpr,
                                       final Consumer<List<String>> test) {
        final ProductProjectionSearch search = ProductProjectionSearch.ofStaged().withQueryFilters(filterExpr);
        final List<ProductProjection> results = executeSearch(search).getResults();
        test.accept(toIds(results));
    }
}
