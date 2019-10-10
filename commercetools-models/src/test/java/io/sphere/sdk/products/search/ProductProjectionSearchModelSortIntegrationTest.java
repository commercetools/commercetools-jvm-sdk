package io.sphere.sdk.products.search;

import io.sphere.sdk.products.ProductProjection;
import io.sphere.sdk.search.PagedSearchResult;
import io.sphere.sdk.search.SortExpression;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

import static io.sphere.sdk.products.ProductsScenario1Fixtures.*;
import static io.sphere.sdk.test.SphereTestUtils.*;
import static org.assertj.core.api.Assertions.assertThat;

@Ignore
public class ProductProjectionSearchModelSortIntegrationTest extends ProductProjectionSearchModelIntegrationTest {

    public static final ProductProjectionSortSearchModel PRODUCT_MODEL = ProductProjectionSearchModel.of().sort();

    @Ignore
    @Test
    public void onBooleanAttributes() throws Exception {
        testProductIds(PRODUCT_MODEL.allVariants().attribute().ofBoolean(ATTR_NAME_BOOLEAN).asc(),
                ids -> assertThat(ids).containsExactly(product2.getId(), product1.getId()));
    }

    @Ignore
    @Test
    public void onTextAttributes() throws Exception {
        testProductIds(PRODUCT_MODEL.allVariants().attribute().ofString(ATTR_NAME_TEXT).asc(),
                ids -> assertThat(ids).containsExactly(product2.getId(), product1.getId()));
    }

    @Ignore
    @Test
    public void onLocTextAttributes() throws Exception {
        testProductIds(PRODUCT_MODEL.allVariants().attribute().ofLocalizedString(ATTR_NAME_LOC_TEXT).locale(ENGLISH).asc(),
                ids -> assertThat(ids).containsExactly(product2.getId(), product1.getId()));
    }

    @Ignore
    @Test
    public void onEnumKeyAttributes() throws Exception {
        testProductIds(PRODUCT_MODEL.allVariants().attribute().ofEnum(ATTR_NAME_ENUM).key().asc(),
                ids -> assertThat(ids).containsExactly(product2.getId(), product1.getId()));
    }

    @Ignore
    @Test
    public void onEnumLabelAttributes() throws Exception {
        testProductIds(PRODUCT_MODEL.allVariants().attribute().ofEnum(ATTR_NAME_ENUM).label().asc(),
                ids -> assertThat(ids).containsExactly(product2.getId(), product1.getId()));
    }

    @Ignore
    @Test
    public void onLocEnumKeyAttributes() throws Exception {
        testProductIds(PRODUCT_MODEL.allVariants().attribute().ofLocalizedEnum(ATTR_NAME_LOC_ENUM).key().asc(),
                ids -> assertThat(ids).containsExactly(product2.getId(), product1.getId()));
    }

    @Ignore
    @Test
    public void onLocEnumLabelAttributes() throws Exception {
        testProductIds(PRODUCT_MODEL.allVariants().attribute().ofLocalizedEnum(ATTR_NAME_LOC_ENUM).label().locale(GERMAN).asc(),
                ids -> assertThat(ids).containsExactly(product2.getId(), product1.getId()));
    }

    @Ignore
    @Test
    public void onNumberAttributes() throws Exception {
        testProductIds(PRODUCT_MODEL.allVariants().attribute().ofNumber(ATTR_NAME_NUMBER).desc(),
                ids -> assertThat(ids).containsExactly(product2.getId(), product1.getId()));
    }

    @Ignore
    @Test
    public void onMoneyAmountAttributes() throws Exception {
        testProductIds(PRODUCT_MODEL.allVariants().attribute().ofMoney(ATTR_NAME_MONEY).centAmount().desc(),
                ids -> assertThat(ids).containsExactly(product2.getId(), product1.getId()));
    }

    @Ignore
    @Test
    public void onMoneyCurrencyAttributes() throws Exception {
        testProductIds(PRODUCT_MODEL.allVariants().attribute().ofMoney(ATTR_NAME_MONEY).currency().desc(),
                ids -> assertThat(ids).containsExactly(product2.getId(), product1.getId()));
    }

    @Ignore
    @Test
    public void onDateAttributes() throws Exception {
        testProductIds(PRODUCT_MODEL.allVariants().attribute().ofDate(ATTR_NAME_DATE).desc(),
                ids -> assertThat(ids).containsExactly(product2.getId(), product1.getId()));
    }

    @Ignore
    @Test
    public void onTimeAttributes() throws Exception {
        testProductIds(PRODUCT_MODEL.allVariants().attribute().ofTime(ATTR_NAME_TIME).desc(),
                ids -> assertThat(ids).containsExactly(product2.getId(), product1.getId()));
    }

    @Ignore
    @Test
    public void onDateTimeAttributes() throws Exception {
        testProductIds(PRODUCT_MODEL.allVariants().attribute().ofDateTime(ATTR_NAME_DATE_TIME).desc(),
                ids -> assertThat(ids).containsExactly(product2.getId(), product1.getId()));
    }

    @Ignore
    @Test
    public void onBooleanSetAttributes() throws Exception {
        testProductIds(PRODUCT_MODEL.allVariants().attribute().ofBooleanSet(ATTR_NAME_BOOLEAN_SET).asc(),
                ids -> assertThat(ids).containsExactly(product1.getId(), product2.getId()));
    }

    @Ignore
    @Test
    public void onTextSetAttributes() throws Exception {
        testProductIds(PRODUCT_MODEL.allVariants().attribute().ofStringSet(ATTR_NAME_TEXT_SET).asc(),
                ids -> assertThat(ids).containsExactly(product1.getId(), product2.getId()));
    }

    @Ignore
    @Test
    public void onLocTextSetAttributes() throws Exception {
        testProductIds(PRODUCT_MODEL.allVariants().attribute().ofLocalizedStringSet(ATTR_NAME_LOC_TEXT_SET).locale(ENGLISH).asc(),
                ids -> assertThat(ids).containsExactly(product1.getId(), product2.getId()));
    }

    @Ignore
    @Test
    public void onEnumKeySetAttributes() throws Exception {
        testProductIds(PRODUCT_MODEL.allVariants().attribute().ofEnumSet(ATTR_NAME_ENUM_SET).key().asc(),
                ids -> assertThat(ids).containsExactly(product1.getId(), product2.getId()));
    }

    @Ignore
    @Test
    public void onEnumLabelSetAttributes() throws Exception {
        testProductIds(PRODUCT_MODEL.allVariants().attribute().ofEnumSet(ATTR_NAME_ENUM_SET).label().asc(),
                ids -> assertThat(ids).containsExactly(product1.getId(), product2.getId()));
    }

    @Ignore
    @Test
    public void onLocEnumKeySetAttributes() throws Exception {
        testProductIds(PRODUCT_MODEL.allVariants().attribute().ofLocalizedEnumSet(ATTR_NAME_LOC_ENUM_SET).key().asc(),
                ids -> assertThat(ids).containsExactly(product1.getId(), product2.getId()));
    }

    @Ignore
    @Test
    public void onLocEnumLabelSetAttributes() throws Exception {
        testProductIds(PRODUCT_MODEL.allVariants().attribute().ofLocalizedEnumSet(ATTR_NAME_LOC_ENUM_SET).label().locale(GERMAN).asc(),
                ids -> assertThat(ids).containsExactly(product1.getId(), product2.getId()));
    }

    @Ignore
    @Test
    public void onNumberSetAttributes() throws Exception {
        testProductIds(PRODUCT_MODEL.allVariants().attribute().ofNumberSet(ATTR_NAME_NUMBER_SET).desc(),
                ids -> assertThat(ids).containsExactly(product1.getId(), product2.getId()));
    }

    @Ignore
    @Test
    public void onMoneyAmountSetAttributes() throws Exception {
        testProductIds(PRODUCT_MODEL.allVariants().attribute().ofMoneySet(ATTR_NAME_MONEY_SET).centAmount().desc(),
                ids -> assertThat(ids).containsExactly(product1.getId(), product2.getId()));
    }

    @Ignore
    @Test
    public void onMoneyCurrencySetAttributes() throws Exception {
        testProductIds(PRODUCT_MODEL.allVariants().attribute().ofMoneySet(ATTR_NAME_MONEY_SET).currency().desc(),
                ids -> assertThat(ids).containsExactly(product1.getId(), product2.getId()));
    }

    @Ignore
    @Test
    public void onDateSetAttributes() throws Exception {
        testProductIds(PRODUCT_MODEL.allVariants().attribute().ofDateSet(ATTR_NAME_DATE_SET).desc(),
                ids -> assertThat(ids).containsExactly(product1.getId(), product2.getId()));
    }

    @Ignore
    @Test
    public void onTimeSetAttributes() throws Exception {
        testProductIds(PRODUCT_MODEL.allVariants().attribute().ofTimeSet(ATTR_NAME_TIME_SET).desc(),
                ids -> assertThat(ids).containsExactly(product1.getId(), product2.getId()));
    }

    @Ignore
    @Test
    public void onDateTimeSetAttributes() throws Exception {
        testProductIds(PRODUCT_MODEL.allVariants().attribute().ofDateTimeSet(ATTR_NAME_DATE_TIME_SET).desc(),
                ids -> assertThat(ids).containsExactly(product1.getId(), product2.getId()));
    }

    @Ignore
    @Test
    public void onSku() throws Exception {
        testProductIds(PRODUCT_MODEL.allVariants().sku().asc(),
                ids -> assertThat(ids).containsExactly(product1.getId(), product2.getId()));
    }
    @Ignore
    @Test
    public void score() {
        final ProductProjectionSearch baseRequest = ProductProjectionSearch.ofStaged()
                .withQueryFilters(m -> m.id().isIn(getAllIds()));
        final ProductProjectionSearch asc = baseRequest.withSort(m -> m.score().asc());
        assertEventually(() -> {
            final PagedSearchResult<ProductProjection> ascResult = client().executeBlocking(asc);
            assertThat(ascResult.getTotal()).isEqualTo(getAllIds().size());
        });
    }

    @Ignore
    @Test
    public void id() {
        final ProductProjectionSearch baseRequest = ProductProjectionSearch.ofStaged()
                .withQueryFilters(m -> m.id().isIn(getAllIds()));
        final ProductProjectionSearch asc = baseRequest.withSort(m -> m.id().asc());
        final ProductProjectionSearch desc = baseRequest.withSort(m -> m.id().desc());
        assertEventually(() -> {
            final PagedSearchResult<ProductProjection> ascResult = client().executeBlocking(asc);
            assertThat(ascResult.getTotal()).isEqualTo(getAllIds().size());
            final PagedSearchResult<ProductProjection> descResult = client().executeBlocking(desc);
            assertThat(descResult.getTotal()).isEqualTo(getAllIds().size());
            final LinkedList<ProductProjection> reversedDesc = new LinkedList<>(descResult.getResults());
            Collections.reverse(reversedDesc);
            assertThat(ascResult.getResults()).isEqualTo(reversedDesc);
        });
    }

    private static void testProductIds(final SortExpression<ProductProjection> sortExpr,
                                       final Consumer<List<String>> test) {
        final ProductProjectionSearch search = ProductProjectionSearch.ofStaged().withSort(sortExpr);
        final List<ProductProjection> results = executeSearch(search).getResults();
        assertEventually(() -> {
            test.accept(toIds(results));
        });
    }
}
