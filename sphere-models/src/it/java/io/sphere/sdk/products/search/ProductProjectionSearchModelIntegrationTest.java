package io.sphere.sdk.products.search;

import io.sphere.sdk.products.Product;
import io.sphere.sdk.products.ProductProjection;
import io.sphere.sdk.products.ProductVariantDraftBuilder;
import io.sphere.sdk.products.queries.ProductQuery;
import io.sphere.sdk.producttypes.ProductType;
import io.sphere.sdk.producttypes.queries.ProductTypeQuery;
import io.sphere.sdk.queries.Query;
import io.sphere.sdk.search.PagedSearchResult;
import io.sphere.sdk.test.IntegrationTest;
import io.sphere.sdk.test.RetryIntegrationTest;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.slf4j.LoggerFactory;

import javax.money.MonetaryAmount;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static io.sphere.sdk.products.ProductsScenario1Fixtures.*;
import static io.sphere.sdk.test.SphereTestUtils.asList;

public class ProductProjectionSearchModelIntegrationTest extends IntegrationTest {
    protected static Product product1;
    protected static Product product2;
    protected static Product productSomeId;
    protected static Product productOtherId;
    protected static ProductType productType;

    @Rule
    public RetryIntegrationTest retry = new RetryIntegrationTest(10, 10000, LoggerFactory.getLogger(this.getClass()));

    @BeforeClass
    public static void setupProducts() {
        productType = client().executeBlocking(ProductTypeQuery.of().byName(PRODUCT_TYPE_NAME)).head()
                .orElseGet(() -> createProductType(client()));

        final Query<Product> query = ProductQuery.of()
                .withPredicates(m -> m.masterData().staged().masterVariant().sku().isIn(asList(SKU1, SKU2, SKU_SOME_ID, SKU_OTHER_ID)));
        final List<Product> products = client().executeBlocking(query).getResults();

        final Function<String, Optional<Product>> findBySku =
                sku -> products.stream().filter(p -> sku.equals(p.getMasterData().getStaged().getMasterVariant().getSku())).findFirst();

        productSomeId = findBySku.apply(SKU_SOME_ID).orElseGet(() -> createTestProduct(client(), "Some Id", ProductVariantDraftBuilder.of().sku(SKU_SOME_ID).build(), productType));
        productOtherId = findBySku.apply(SKU_OTHER_ID).orElseGet(() -> createTestProduct(client(), "Other Id", ProductVariantDraftBuilder.of().sku(SKU_OTHER_ID).build(), productType));
        product1 = findBySku.apply(SKU1).orElseGet(() -> createProduct1(client(), productSomeId, productOtherId, productType));
        product2 = findBySku.apply(SKU2).orElseGet(() -> createProduct2(client(), productSomeId, productOtherId, productType));
    }

    protected static Long toCents(final MonetaryAmount money) {
        return money.getNumber().numberValueExact(BigDecimal.class).movePointRight(2).longValue();
    }

    protected static PagedSearchResult<ProductProjection> executeSearch(final ProductProjectionSearch search) {
        final List<String> ids = asList(product1.getId(), product2.getId());
        final ProductProjectionSearch sphereRequest = search.plusQueryFilters(filter -> filter.id().byAny(ids));
        return client().executeBlocking(sphereRequest);
    }
}
