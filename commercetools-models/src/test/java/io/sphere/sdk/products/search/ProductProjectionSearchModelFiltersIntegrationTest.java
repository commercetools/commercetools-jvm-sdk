package io.sphere.sdk.products.search;

import io.sphere.sdk.products.ProductProjection;
import io.sphere.sdk.search.FilterExpression;
import org.junit.Ignore;
import org.junit.Test;

import java.util.List;
import java.util.function.Consumer;

import static io.sphere.sdk.test.SphereTestUtils.*;
import static org.assertj.core.api.Assertions.assertThat;

import static io.sphere.sdk.products.ProductsScenario1Fixtures.*;

@Ignore
public class ProductProjectionSearchModelFiltersIntegrationTest extends ProductProjectionSearchModelIntegrationTest {

    public static final ProductProjectionFilterSearchModel PRODUCT_MODEL = ProductProjectionSearchModel.of().filter();

    @Ignore
    @Test
    public void onBooleanAttributes() throws Exception {
        testProductIds(PRODUCT_MODEL.allVariants().attribute().ofBoolean(ATTR_NAME_BOOLEAN).is(true),
                ids -> assertThat(ids).containsOnly(product1.getId()));
    }

    @Ignore
    @Test
    public void onTextAttributes() throws Exception {
        testProductIds(PRODUCT_MODEL.allVariants().attribute().ofString(ATTR_NAME_TEXT).is(TEXT_FOO),
                ids -> assertThat(ids).containsOnly(product1.getId()));
    }

    @Ignore
    @Test
    public void onLocTextAttributes() throws Exception {
        testProductIds(PRODUCT_MODEL.allVariants().attribute().ofLocalizedString(ATTR_NAME_LOC_TEXT).locale(ENGLISH).is(LOC_TEXT_FOO.get(ENGLISH)),
                ids -> assertThat(ids).containsOnly(product1.getId()));
    }

    @Ignore
    @Test
    public void onEnumKeyAttributes() throws Exception {
        testProductIds(PRODUCT_MODEL.allVariants().attribute().ofEnum(ATTR_NAME_ENUM).key().is(ENUM_TWO.getKey()),
                ids -> assertThat(ids).containsOnly(product1.getId()));
    }

    @Ignore
    @Test
    public void onEnumLabelAttributes() throws Exception {
        testProductIds(PRODUCT_MODEL.allVariants().attribute().ofEnum(ATTR_NAME_ENUM).label().is(ENUM_TWO.getLabel()),
                ids -> assertThat(ids).containsOnly(product1.getId()));
    }

    @Ignore
    @Test
    public void onLocEnumKeyAttributes() throws Exception {
        testProductIds(PRODUCT_MODEL.allVariants().attribute().ofLocalizedEnum(ATTR_NAME_LOC_ENUM).key().is(LOC_ENUM_TWO.getKey()),
                ids -> assertThat(ids).containsOnly(product1.getId()));
    }

    @Ignore
    @Test
    public void onLocEnumLabelAttributes() throws Exception {
        testProductIds(PRODUCT_MODEL.allVariants().attribute().ofLocalizedEnum(ATTR_NAME_LOC_ENUM).label().locale(GERMAN).is(LOC_ENUM_TWO.getLabel().get(GERMAN)),
                ids -> assertThat(ids).containsOnly(product1.getId()));
    }

    @Ignore
    @Test
    public void onNumberAttributes() throws Exception {
        testProductIds(PRODUCT_MODEL.allVariants().attribute().ofNumber(ATTR_NAME_NUMBER).is(NUMBER_5),
                ids -> assertThat(ids).containsOnly(product1.getId()));
    }

    @Ignore
    @Test
    public void onNumberRangedAttributes() throws Exception {
        testProductIds(PRODUCT_MODEL.allVariants().attribute().ofNumber(ATTR_NAME_NUMBER).isLessThanOrEqualTo(NUMBER_5),
                ids -> assertThat(ids).containsOnly(product1.getId()));
    }

    @Ignore
    @Test
    public void onMoneyAmountAttributes() throws Exception {
        testProductIds(PRODUCT_MODEL.allVariants().attribute().ofMoney(ATTR_NAME_MONEY).centAmount().is(toCents(MONEY_500_EUR)),
                ids -> assertThat(ids).containsOnly(product1.getId()));
    }

    @Ignore
    @Test
    public void onMoneyAmountRangedAttributes() throws Exception {
        testProductIds(PRODUCT_MODEL.allVariants().attribute().ofMoney(ATTR_NAME_MONEY).centAmount().isLessThanOrEqualTo(toCents(MONEY_500_EUR)),
                ids -> assertThat(ids).containsOnly(product1.getId()));
    }

    @Ignore
    @Test
    public void onMoneyCurrencyAttributes() throws Exception {
        testProductIds(PRODUCT_MODEL.allVariants().attribute().ofMoney(ATTR_NAME_MONEY).currency().is(MONEY_500_EUR.getCurrency()),
                ids -> assertThat(ids).containsOnly(product1.getId()));
    }

    @Ignore
    @Test
    public void onDateAttributes() throws Exception {
        testProductIds(PRODUCT_MODEL.allVariants().attribute().ofDate(ATTR_NAME_DATE).is(DATE_2001),
                ids -> assertThat(ids).containsOnly(product1.getId()));
    }

    @Ignore
    @Test
    public void onDateRangedAttributes() throws Exception {
        testProductIds(PRODUCT_MODEL.allVariants().attribute().ofDate(ATTR_NAME_DATE).isLessThanOrEqualTo(DATE_2001),
                ids -> assertThat(ids).containsOnly(product1.getId()));
    }

    @Ignore
    @Test
    public void onTimeAttributes() throws Exception {
        testProductIds(PRODUCT_MODEL.allVariants().attribute().ofTime(ATTR_NAME_TIME).is(TIME_22H),
                ids -> assertThat(ids).containsOnly(product1.getId()));
    }

    @Ignore
    @Test
    public void onTimeRangedAttributes() throws Exception {
        testProductIds(PRODUCT_MODEL.allVariants().attribute().ofTime(ATTR_NAME_TIME).isLessThanOrEqualTo(TIME_22H),
                ids -> assertThat(ids).containsOnly(product1.getId()));
    }

    @Ignore
    @Test
    public void onDateTimeAttributes() throws Exception {
        testProductIds(PRODUCT_MODEL.allVariants().attribute().ofDateTime(ATTR_NAME_DATE_TIME).is(DATE_TIME_2001_22H),
                ids -> assertThat(ids).containsOnly(product1.getId()));
    }

    @Ignore
    @Test
    public void onDateTimeRangedAttributes() throws Exception {
        testProductIds(PRODUCT_MODEL.allVariants().attribute().ofDateTime(ATTR_NAME_DATE_TIME).isLessThanOrEqualTo(DATE_TIME_2001_22H),
                ids -> assertThat(ids).containsOnly(product1.getId()));
    }

    @Ignore
    @Test
    public void onReferenceAttributes() throws Exception {
        testProductIds(PRODUCT_MODEL.allVariants().attribute().ofReference(ATTR_NAME_REF).id().is(productA.getId()),
                ids -> assertThat(ids).containsOnly(product1.getId()));
    }

    @Ignore
    @Test
    public void onBooleanSetAttributes() throws Exception {
        testProductIds(PRODUCT_MODEL.allVariants().attribute().ofBooleanSet(ATTR_NAME_BOOLEAN_SET).is(false),
                ids -> assertThat(ids).containsOnly(product1.getId()));
    }

    @Ignore
    @Test
    public void onTextSetAttributes() throws Exception {
        testProductIds(PRODUCT_MODEL.allVariants().attribute().ofStringSet(ATTR_NAME_TEXT_SET).is(TEXT_BAR),
                ids -> assertThat(ids).containsOnly(product1.getId()));
    }

    @Ignore
    @Test
    public void onLocTextSetAttributes() throws Exception {
        testProductIds(PRODUCT_MODEL.allVariants().attribute().ofLocalizedStringSet(ATTR_NAME_LOC_TEXT_SET).locale(ENGLISH).is(LOC_TEXT_BAR.get(ENGLISH)),
                ids -> assertThat(ids).containsOnly(product1.getId()));
    }

    @Ignore
    @Test
    public void onEnumKeySetAttributes() throws Exception {
        testProductIds(PRODUCT_MODEL.allVariants().attribute().ofEnumSet(ATTR_NAME_ENUM_SET).key().is(ENUM_THREE.getKey()),
                ids -> assertThat(ids).containsOnly(product1.getId()));
    }

    @Ignore
    @Test
    public void onEnumLabelSetAttributes() throws Exception {
        testProductIds(PRODUCT_MODEL.allVariants().attribute().ofEnumSet(ATTR_NAME_ENUM_SET).label().is(ENUM_THREE.getLabel()),
                ids -> assertThat(ids).containsOnly(product1.getId()));
    }

    @Ignore
    @Test
    public void onLocEnumKeySetAttributes() throws Exception {
        testProductIds(PRODUCT_MODEL.allVariants().attribute().ofLocalizedEnumSet(ATTR_NAME_LOC_ENUM_SET).key().is(LOC_ENUM_THREE.getKey()),
                ids -> assertThat(ids).containsOnly(product1.getId()));
    }

    @Ignore
    @Test
    public void onLocEnumLabelSetAttributes() throws Exception {
        testProductIds(PRODUCT_MODEL.allVariants().attribute().ofLocalizedEnumSet(ATTR_NAME_LOC_ENUM_SET).label().locale(GERMAN).is(LOC_ENUM_THREE.getLabel().get(GERMAN)),
                ids -> assertThat(ids).containsOnly(product1.getId()));
    }

    @Ignore
    @Test
    public void onNumberSetAttributes() throws Exception {
        testProductIds(PRODUCT_MODEL.allVariants().attribute().ofNumberSet(ATTR_NAME_NUMBER_SET).is(NUMBER_10),
                ids -> assertThat(ids).containsOnly(product1.getId()));
    }

    @Ignore
    @Test
    public void onNumberRangedSetAttributes() throws Exception {
        testProductIds(PRODUCT_MODEL.allVariants().attribute().ofNumberSet(ATTR_NAME_NUMBER_SET).isGreaterThanOrEqualTo(NUMBER_10),
                ids -> assertThat(ids).containsOnly(product1.getId()));
    }

    @Ignore
    @Test
    public void onMoneyAmountSetAttributes() throws Exception {
        testProductIds(PRODUCT_MODEL.allVariants().attribute().ofMoneySet(ATTR_NAME_MONEY_SET).centAmount().is(toCents(MONEY_1000_USD)),
                ids -> assertThat(ids).containsOnly(product1.getId()));
    }

    @Ignore
    @Test
    public void onMoneyAmountRangedSetAttributes() throws Exception {
        testProductIds(PRODUCT_MODEL.allVariants().attribute().ofMoneySet(ATTR_NAME_MONEY_SET).centAmount().isGreaterThanOrEqualTo(toCents(MONEY_1000_USD)),
                ids -> assertThat(ids).containsOnly(product1.getId()));
    }

    @Ignore
    @Test
    public void onMoneyCurrencySetAttributes() throws Exception {
        testProductIds(PRODUCT_MODEL.allVariants().attribute().ofMoneySet(ATTR_NAME_MONEY_SET).currency().is(MONEY_1000_USD.getCurrency()),
                ids -> assertThat(ids).containsOnly(product1.getId()));
    }

    @Ignore
    @Test
    public void onDateSetAttributes() throws Exception {
        testProductIds(PRODUCT_MODEL.allVariants().attribute().ofDateSet(ATTR_NAME_DATE_SET).is(DATE_2002),
                ids -> assertThat(ids).containsOnly(product1.getId()));
    }

    @Ignore
    @Test
    public void onDateRangedSetAttributes() throws Exception {
        testProductIds(PRODUCT_MODEL.allVariants().attribute().ofDateSet(ATTR_NAME_DATE_SET).isGreaterThanOrEqualTo(DATE_2002),
                ids -> assertThat(ids).containsOnly(product1.getId()));
    }

    @Ignore
    @Test
    public void onTimeSetAttributes() throws Exception {
        testProductIds(PRODUCT_MODEL.allVariants().attribute().ofTimeSet(ATTR_NAME_TIME_SET).is(TIME_23H),
                ids -> assertThat(ids).containsOnly(product1.getId()));
    }

    @Ignore
    @Test
    public void onTimeRangedSetAttributes() throws Exception {
        testProductIds(PRODUCT_MODEL.allVariants().attribute().ofTimeSet(ATTR_NAME_TIME_SET).isGreaterThanOrEqualTo(TIME_23H),
                ids -> assertThat(ids).containsOnly(product1.getId()));
    }

    @Ignore
    @Test
    public void onDateTimeSetAttributes() throws Exception {
        testProductIds(PRODUCT_MODEL.allVariants().attribute().ofDateTimeSet(ATTR_NAME_DATE_TIME_SET).is(DATE_TIME_2002_23H),
                ids -> assertThat(ids).containsOnly(product1.getId()));
    }

    @Ignore
    @Test
    public void onDateTimeRangedSetAttributes() throws Exception {
        testProductIds(PRODUCT_MODEL.allVariants().attribute().ofDateTimeSet(ATTR_NAME_DATE_TIME_SET).isGreaterThanOrEqualTo(DATE_TIME_2002_23H),
                ids -> assertThat(ids).containsOnly(product1.getId()));
    }

    @Ignore
    @Test
    public void onReferenceSetAttributes() throws Exception {
        testProductIds(PRODUCT_MODEL.allVariants().attribute().ofReferenceSet(ATTR_NAME_REF_SET).id().is(productB.getId()),
                ids -> assertThat(ids).containsOnly(product1.getId()));
    }

    private static void testProductIds(final List<FilterExpression<ProductProjection>> filterExpr,
                                       final Consumer<List<String>> test) {
        final ProductProjectionSearch search = ProductProjectionSearch.ofStaged().withQueryFilters(filterExpr);
        final List<ProductProjection> results = executeSearch(search).getResults();
        test.accept(toIds(results));
    }
}
