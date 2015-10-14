package io.sphere.sdk.products.search;

import io.sphere.sdk.products.ProductProjection;
import io.sphere.sdk.search.FilterExpression;
import org.junit.Test;

import java.util.List;
import java.util.function.Consumer;

import static io.sphere.sdk.test.SphereTestUtils.*;
import static org.assertj.core.api.Assertions.assertThat;

public class ProductProjectionSearchModelFiltersIntegrationTest extends ProductProjectionSearchModelIntegrationTest {

    public static final ProductProjectionFilterSearchModel FILTER = ProductProjectionSearchModel.of().filter();

    @Test
    public void filteredOnBooleanAttributes() throws Exception {
        testProductIds(FILTER.allVariants().attribute().ofBoolean(ATTR_NAME_BOOLEAN).by(true),
                ids -> assertThat(ids).containsOnly(product1.getId()));
    }

    @Test
    public void filteredOnTextAttributes() throws Exception {
        testProductIds(FILTER.allVariants().attribute().ofString(ATTR_NAME_TEXT).by(TEXT_FOO),
                ids -> assertThat(ids).containsOnly(product1.getId()));
    }

    @Test
    public void filteredOnLocTextAttributes() throws Exception {
        testProductIds(FILTER.allVariants().attribute().ofLocalizedString(ATTR_NAME_LOC_TEXT).locale(ENGLISH).by(LOC_TEXT_FOO.get(ENGLISH)),
                ids -> assertThat(ids).containsOnly(product1.getId()));
    }

    @Test
    public void filteredOnEnumKeyAttributes() throws Exception {
        testProductIds(FILTER.allVariants().attribute().ofEnum(ATTR_NAME_ENUM).key().by(ENUM_TWO.getKey()),
                ids -> assertThat(ids).containsOnly(product1.getId()));
    }

    @Test
    public void filteredOnEnumLabelAttributes() throws Exception {
        testProductIds(FILTER.allVariants().attribute().ofEnum(ATTR_NAME_ENUM).label().by(ENUM_TWO.getLabel()),
                ids -> assertThat(ids).containsOnly(product1.getId()));
    }

    @Test
    public void filteredOnLocEnumKeyAttributes() throws Exception {
        testProductIds(FILTER.allVariants().attribute().ofLocalizableEnum(ATTR_NAME_LOC_ENUM).key().by(LOC_ENUM_TWO.getKey()),
                ids -> assertThat(ids).containsOnly(product1.getId()));
    }

    @Test
    public void filteredOnLocEnumLabelAttributes() throws Exception {
        testProductIds(FILTER.allVariants().attribute().ofLocalizableEnum(ATTR_NAME_LOC_ENUM).label().locale(GERMAN).by(LOC_ENUM_TWO.getLabel().get(GERMAN)),
                ids -> assertThat(ids).containsOnly(product1.getId()));
    }

    @Test
    public void filteredOnNumberAttributes() throws Exception {
        testProductIds(FILTER.allVariants().attribute().ofNumber(ATTR_NAME_NUMBER).by(NUMBER_5),
                ids -> assertThat(ids).containsOnly(product1.getId()));
    }

    @Test
    public void filteredOnNumberRangedAttributes() throws Exception {
        testProductIds(FILTER.allVariants().attribute().ofNumber(ATTR_NAME_NUMBER).byLessThanOrEqualTo(NUMBER_5),
                ids -> assertThat(ids).containsOnly(product1.getId()));
    }

    @Test
    public void filteredOnMoneyAmountAttributes() throws Exception {
        testProductIds(FILTER.allVariants().attribute().ofMoney(ATTR_NAME_MONEY).centAmount().by(toCents(MONEY_500_EUR)),
                ids -> assertThat(ids).containsOnly(product1.getId()));
    }

    @Test
    public void filteredOnMoneyAmountRangedAttributes() throws Exception {
        testProductIds(FILTER.allVariants().attribute().ofMoney(ATTR_NAME_MONEY).centAmount().byLessThanOrEqualTo(toCents(MONEY_500_EUR)),
                ids -> assertThat(ids).containsOnly(product1.getId()));
    }

    @Test
    public void filteredOnMoneyCurrencyAttributes() throws Exception {
        testProductIds(FILTER.allVariants().attribute().ofMoney(ATTR_NAME_MONEY).currency().by(MONEY_500_EUR.getCurrency()),
                ids -> assertThat(ids).containsOnly(product1.getId()));
    }

    @Test
    public void filteredOnDateAttributes() throws Exception {
        testProductIds(FILTER.allVariants().attribute().ofDate(ATTR_NAME_DATE).by(DATE_2001),
                ids -> assertThat(ids).containsOnly(product1.getId()));
    }

    @Test
    public void filteredOnDateRangedAttributes() throws Exception {
        testProductIds(FILTER.allVariants().attribute().ofDate(ATTR_NAME_DATE).byLessThanOrEqualTo(DATE_2001),
                ids -> assertThat(ids).containsOnly(product1.getId()));
    }

    @Test
    public void filteredOnTimeAttributes() throws Exception {
        testProductIds(FILTER.allVariants().attribute().ofTime(ATTR_NAME_TIME).by(TIME_22H),
                ids -> assertThat(ids).containsOnly(product1.getId()));
    }

    @Test
    public void filteredOnTimeRangedAttributes() throws Exception {
        testProductIds(FILTER.allVariants().attribute().ofTime(ATTR_NAME_TIME).byLessThanOrEqualTo(TIME_22H),
                ids -> assertThat(ids).containsOnly(product1.getId()));
    }

    @Test
    public void filteredOnDateTimeAttributes() throws Exception {
        testProductIds(FILTER.allVariants().attribute().ofDateTime(ATTR_NAME_DATE_TIME).by(DATE_TIME_2001_22H),
                ids -> assertThat(ids).containsOnly(product1.getId()));
    }

    @Test
    public void filteredOnDateTimeRangedAttributes() throws Exception {
        testProductIds(FILTER.allVariants().attribute().ofDateTime(ATTR_NAME_DATE_TIME).byLessThanOrEqualTo(DATE_TIME_2001_22H),
                ids -> assertThat(ids).containsOnly(product1.getId()));
    }

    @Test
    public void filteredOnReferenceAttributes() throws Exception {
        testProductIds(FILTER.allVariants().attribute().ofReference(ATTR_NAME_REF).id().by(productSomeId.getId()),
                ids -> assertThat(ids).containsOnly(product1.getId()));
    }

    @Test
    public void filteredOnBooleanSetAttributes() throws Exception {
        testProductIds(FILTER.allVariants().attribute().ofBoolean(ATTR_NAME_BOOLEAN_SET).by(false),
                ids -> assertThat(ids).containsOnly(product1.getId()));
    }

    @Test
    public void filteredOnTextSetAttributes() throws Exception {
        testProductIds(FILTER.allVariants().attribute().ofString(ATTR_NAME_TEXT_SET).by(TEXT_BAR),
                ids -> assertThat(ids).containsOnly(product1.getId()));
    }

    @Test
    public void filteredOnLocTextSetAttributes() throws Exception {
        testProductIds(FILTER.allVariants().attribute().ofLocalizedString(ATTR_NAME_LOC_TEXT_SET).locale(ENGLISH).by(LOC_TEXT_BAR.get(ENGLISH)),
                ids -> assertThat(ids).containsOnly(product1.getId()));
    }

    @Test
    public void filteredOnEnumKeySetAttributes() throws Exception {
        testProductIds(FILTER.allVariants().attribute().ofEnum(ATTR_NAME_ENUM_SET).key().by(ENUM_THREE.getKey()),
                ids -> assertThat(ids).containsOnly(product1.getId()));
    }

    @Test
    public void filteredOnEnumLabelSetAttributes() throws Exception {
        testProductIds(FILTER.allVariants().attribute().ofEnum(ATTR_NAME_ENUM_SET).label().by(ENUM_THREE.getLabel()),
                ids -> assertThat(ids).containsOnly(product1.getId()));
    }

    @Test
    public void filteredOnLocEnumKeySetAttributes() throws Exception {
        testProductIds(FILTER.allVariants().attribute().ofLocalizableEnum(ATTR_NAME_LOC_ENUM_SET).key().by(LOC_ENUM_THREE.getKey()),
                ids -> assertThat(ids).containsOnly(product1.getId()));
    }

    @Test
    public void filteredOnLocEnumLabelSetAttributes() throws Exception {
        testProductIds(FILTER.allVariants().attribute().ofLocalizableEnum(ATTR_NAME_LOC_ENUM_SET).label().locale(GERMAN).by(LOC_ENUM_THREE.getLabel().get(GERMAN)),
                ids -> assertThat(ids).containsOnly(product1.getId()));
    }

    @Test
    public void filteredOnNumberSetAttributes() throws Exception {
        testProductIds(FILTER.allVariants().attribute().ofNumber(ATTR_NAME_NUMBER_SET).by(NUMBER_10),
                ids -> assertThat(ids).containsOnly(product1.getId()));
    }

    @Test
    public void filteredOnNumberRangedSetAttributes() throws Exception {
        testProductIds(FILTER.allVariants().attribute().ofNumber(ATTR_NAME_NUMBER_SET).byGreaterThanOrEqualTo(NUMBER_10),
                ids -> assertThat(ids).containsOnly(product1.getId()));
    }

    @Test
    public void filteredOnMoneyAmountSetAttributes() throws Exception {
        testProductIds(FILTER.allVariants().attribute().ofMoney(ATTR_NAME_MONEY_SET).centAmount().by(toCents(MONEY_1000_USD)),
                ids -> assertThat(ids).containsOnly(product1.getId()));
    }

    @Test
    public void filteredOnMoneyAmountRangedSetAttributes() throws Exception {
        testProductIds(FILTER.allVariants().attribute().ofMoney(ATTR_NAME_MONEY_SET).centAmount().byGreaterThanOrEqualTo(toCents(MONEY_1000_USD)),
                ids -> assertThat(ids).containsOnly(product1.getId()));
    }

    @Test
    public void filteredOnMoneyCurrencySetAttributes() throws Exception {
        testProductIds(FILTER.allVariants().attribute().ofMoney(ATTR_NAME_MONEY_SET).currency().by(MONEY_1000_USD.getCurrency()),
                ids -> assertThat(ids).containsOnly(product1.getId()));
    }

    @Test
    public void filteredOnDateSetAttributes() throws Exception {
        testProductIds(FILTER.allVariants().attribute().ofDate(ATTR_NAME_DATE_SET).by(DATE_2002),
                ids -> assertThat(ids).containsOnly(product1.getId()));
    }

    @Test
    public void filteredOnDateRangedSetAttributes() throws Exception {
        testProductIds(FILTER.allVariants().attribute().ofDate(ATTR_NAME_DATE_SET).byGreaterThanOrEqualTo(DATE_2002),
                ids -> assertThat(ids).containsOnly(product1.getId()));
    }

    @Test
    public void filteredOnTimeSetAttributes() throws Exception {
        testProductIds(FILTER.allVariants().attribute().ofTime(ATTR_NAME_TIME_SET).by(TIME_23H),
                ids -> assertThat(ids).containsOnly(product1.getId()));
    }

    @Test
    public void filteredOnTimeRangedSetAttributes() throws Exception {
        testProductIds(FILTER.allVariants().attribute().ofTime(ATTR_NAME_TIME_SET).byGreaterThanOrEqualTo(TIME_23H),
                ids -> assertThat(ids).containsOnly(product1.getId()));
    }

    @Test
    public void filteredOnDateTimeSetAttributes() throws Exception {
        testProductIds(FILTER.allVariants().attribute().ofDateTime(ATTR_NAME_DATE_TIME_SET).by(DATE_TIME_2002_23H),
                ids -> assertThat(ids).containsOnly(product1.getId()));
    }

    @Test
    public void filteredOnDateTimeRangedSetAttributes() throws Exception {
        testProductIds(FILTER.allVariants().attribute().ofDateTime(ATTR_NAME_DATE_TIME_SET).byGreaterThanOrEqualTo(DATE_TIME_2002_23H),
                ids -> assertThat(ids).containsOnly(product1.getId()));
    }

    @Test
    public void filteredOnReferenceSetAttributes() throws Exception {
        testProductIds(FILTER.allVariants().attribute().ofReference(ATTR_NAME_REF_SET).id().by(productOtherId.getId()),
                ids -> assertThat(ids).containsOnly(product1.getId()));
    }

    private static void testProductIds(final List<FilterExpression<ProductProjection>> filterExpr,
                                       final Consumer<List<String>> test) {
        final ProductProjectionSearch search = ProductProjectionSearch.ofStaged().withQueryFilters(filterExpr);
        final List<ProductProjection> results = executeSearch(search).getResults();
        test.accept(toIds(results));
    }
}
