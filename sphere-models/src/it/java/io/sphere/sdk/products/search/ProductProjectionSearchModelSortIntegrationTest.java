package io.sphere.sdk.products.search;

import io.sphere.sdk.products.ProductProjection;
import io.sphere.sdk.search.SortExpression;
import org.junit.Test;

import java.util.List;
import java.util.function.Consumer;

import static io.sphere.sdk.test.SphereTestUtils.*;
import static org.assertj.core.api.Assertions.assertThat;

import static io.sphere.sdk.products.ProductsScenario1Fixtures.*;

public class ProductProjectionSearchModelSortIntegrationTest extends ProductProjectionSearchModelIntegrationTest {

    public static final ProductProjectionSortSearchModel SORT = ProductProjectionSearchModel.of().sort();

    @Test
    public void onBooleanAttributes() throws Exception {
        testProductIds(SORT.allVariants().attribute().ofBoolean(ATTR_NAME_BOOLEAN).byAsc(),
                ids -> assertThat(ids).containsExactly(product2.getId(), product1.getId()));
    }

    @Test
    public void onTextAttributes() throws Exception {
        testProductIds(SORT.allVariants().attribute().ofString(ATTR_NAME_TEXT).byAsc(),
                ids -> assertThat(ids).containsExactly(product2.getId(), product1.getId()));
    }

    @Test
    public void onLocTextAttributes() throws Exception {
        testProductIds(SORT.allVariants().attribute().ofLocalizedString(ATTR_NAME_LOC_TEXT).locale(ENGLISH).byAsc(),
                ids -> assertThat(ids).containsExactly(product2.getId(), product1.getId()));
    }

    @Test
    public void onEnumKeyAttributes() throws Exception {
        testProductIds(SORT.allVariants().attribute().ofEnum(ATTR_NAME_ENUM).key().byAsc(),
                ids -> assertThat(ids).containsExactly(product2.getId(), product1.getId()));
    }

    @Test
    public void onEnumLabelAttributes() throws Exception {
        testProductIds(SORT.allVariants().attribute().ofEnum(ATTR_NAME_ENUM).label().byAsc(),
                ids -> assertThat(ids).containsExactly(product2.getId(), product1.getId()));
    }

    @Test
    public void onLocEnumKeyAttributes() throws Exception {
        testProductIds(SORT.allVariants().attribute().ofLocalizedEnum(ATTR_NAME_LOC_ENUM).key().byAsc(),
                ids -> assertThat(ids).containsExactly(product2.getId(), product1.getId()));
    }

    @Test
    public void onLocEnumLabelAttributes() throws Exception {
        testProductIds(SORT.allVariants().attribute().ofLocalizedEnum(ATTR_NAME_LOC_ENUM).label().locale(GERMAN).byAsc(),
                ids -> assertThat(ids).containsExactly(product2.getId(), product1.getId()));
    }

    @Test
    public void onNumberAttributes() throws Exception {
        testProductIds(SORT.allVariants().attribute().ofNumber(ATTR_NAME_NUMBER).byDesc(),
                ids -> assertThat(ids).containsExactly(product2.getId(), product1.getId()));
    }

    @Test
    public void onMoneyAmountAttributes() throws Exception {
        testProductIds(SORT.allVariants().attribute().ofMoney(ATTR_NAME_MONEY).centAmount().byDesc(),
                ids -> assertThat(ids).containsExactly(product2.getId(), product1.getId()));
    }

    @Test
    public void onMoneyCurrencyAttributes() throws Exception {
        testProductIds(SORT.allVariants().attribute().ofMoney(ATTR_NAME_MONEY).currency().byDesc(),
                ids -> assertThat(ids).containsExactly(product2.getId(), product1.getId()));
    }

    @Test
    public void onDateAttributes() throws Exception {
        testProductIds(SORT.allVariants().attribute().ofDate(ATTR_NAME_DATE).byDesc(),
                ids -> assertThat(ids).containsExactly(product2.getId(), product1.getId()));
    }

    @Test
    public void onTimeAttributes() throws Exception {
        testProductIds(SORT.allVariants().attribute().ofTime(ATTR_NAME_TIME).byDesc(),
                ids -> assertThat(ids).containsExactly(product2.getId(), product1.getId()));
    }

    @Test
    public void onDateTimeAttributes() throws Exception {
        testProductIds(SORT.allVariants().attribute().ofDateTime(ATTR_NAME_DATE_TIME).byDesc(),
                ids -> assertThat(ids).containsExactly(product2.getId(), product1.getId()));
    }

    @Test
    public void onBooleanSetAttributes() throws Exception {
        testProductIds(SORT.allVariants().attribute().ofBooleanSet(ATTR_NAME_BOOLEAN_SET).byAsc(),
                ids -> assertThat(ids).containsExactly(product1.getId(), product2.getId()));
    }

    @Test
    public void onTextSetAttributes() throws Exception {
        testProductIds(SORT.allVariants().attribute().ofStringSet(ATTR_NAME_TEXT_SET).byAsc(),
                ids -> assertThat(ids).containsExactly(product1.getId(), product2.getId()));
    }

    @Test
    public void onLocTextSetAttributes() throws Exception {
        testProductIds(SORT.allVariants().attribute().ofLocalizedStringSet(ATTR_NAME_LOC_TEXT_SET).locale(ENGLISH).byAsc(),
                ids -> assertThat(ids).containsExactly(product1.getId(), product2.getId()));
    }

    @Test
    public void onEnumKeySetAttributes() throws Exception {
        testProductIds(SORT.allVariants().attribute().ofEnumSet(ATTR_NAME_ENUM_SET).key().byAsc(),
                ids -> assertThat(ids).containsExactly(product1.getId(), product2.getId()));
    }

    @Test
    public void onEnumLabelSetAttributes() throws Exception {
        testProductIds(SORT.allVariants().attribute().ofEnumSet(ATTR_NAME_ENUM_SET).label().byAsc(),
                ids -> assertThat(ids).containsExactly(product1.getId(), product2.getId()));
    }

    @Test
    public void onLocEnumKeySetAttributes() throws Exception {
        testProductIds(SORT.allVariants().attribute().ofLocalizedEnumSet(ATTR_NAME_LOC_ENUM_SET).key().byAsc(),
                ids -> assertThat(ids).containsExactly(product1.getId(), product2.getId()));
    }

    @Test
    public void onLocEnumLabelSetAttributes() throws Exception {
        testProductIds(SORT.allVariants().attribute().ofLocalizedEnumSet(ATTR_NAME_LOC_ENUM_SET).label().locale(GERMAN).byAsc(),
                ids -> assertThat(ids).containsExactly(product1.getId(), product2.getId()));
    }

    @Test
    public void onNumberSetAttributes() throws Exception {
        testProductIds(SORT.allVariants().attribute().ofNumberSet(ATTR_NAME_NUMBER_SET).byDesc(),
                ids -> assertThat(ids).containsExactly(product1.getId(), product2.getId()));
    }

    @Test
    public void onMoneyAmountSetAttributes() throws Exception {
        testProductIds(SORT.allVariants().attribute().ofMoneySet(ATTR_NAME_MONEY_SET).centAmount().byDesc(),
                ids -> assertThat(ids).containsExactly(product1.getId(), product2.getId()));
    }

    @Test
    public void onMoneyCurrencySetAttributes() throws Exception {
        testProductIds(SORT.allVariants().attribute().ofMoneySet(ATTR_NAME_MONEY_SET).currency().byDesc(),
                ids -> assertThat(ids).containsExactly(product1.getId(), product2.getId()));
    }

    @Test
    public void onDateSetAttributes() throws Exception {
        testProductIds(SORT.allVariants().attribute().ofDateSet(ATTR_NAME_DATE_SET).byDesc(),
                ids -> assertThat(ids).containsExactly(product1.getId(), product2.getId()));
    }

    @Test
    public void onTimeSetAttributes() throws Exception {
        testProductIds(SORT.allVariants().attribute().ofTimeSet(ATTR_NAME_TIME_SET).byDesc(),
                ids -> assertThat(ids).containsExactly(product1.getId(), product2.getId()));
    }

    @Test
    public void onDateTimeSetAttributes() throws Exception {
        testProductIds(SORT.allVariants().attribute().ofDateTimeSet(ATTR_NAME_DATE_TIME_SET).byDesc(),
                ids -> assertThat(ids).containsExactly(product1.getId(), product2.getId()));
    }

    private static void testProductIds(final SortExpression<ProductProjection> sortExpr,
                                       final Consumer<List<String>> test) {
        final ProductProjectionSearch search = ProductProjectionSearch.ofStaged().withSort(sortExpr);
        final List<ProductProjection> results = executeSearch(search).getResults();
        test.accept(toIds(results));
    }
}
