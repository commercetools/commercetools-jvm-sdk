package io.sphere.sdk.products.search;

import io.sphere.sdk.products.Product;
import io.sphere.sdk.products.ProductProjection;
import io.sphere.sdk.producttypes.ProductType;
import io.sphere.sdk.search.PagedSearchResult;
import io.sphere.sdk.test.IntegrationTest;
import io.sphere.sdk.test.RetryIntegrationTest;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.slf4j.LoggerFactory;

import javax.money.MonetaryAmount;
import java.math.BigDecimal;
import java.util.List;

import static io.sphere.sdk.products.ProductsScenario1Fixtures.Data;
import static io.sphere.sdk.products.ProductsScenario1Fixtures.createScenario;
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
        final Data data = createScenario(client());
        productType = data.getProductType();
        productSomeId = data.getReferencedProductA();
        productOtherId = data.getReferencedProductB();
        product1 = data.getProduct1();
        product2 = data.getProduct2();
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
