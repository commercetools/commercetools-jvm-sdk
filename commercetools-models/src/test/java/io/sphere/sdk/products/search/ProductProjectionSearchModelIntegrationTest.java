package io.sphere.sdk.products.search;

import io.sphere.sdk.products.Product;
import io.sphere.sdk.products.ProductProjection;
import io.sphere.sdk.producttypes.ProductType;
import io.sphere.sdk.search.PagedSearchResult;
import io.sphere.sdk.test.IntegrationTest;
import io.sphere.sdk.test.RetryIntegrationTest;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.slf4j.LoggerFactory;

import javax.money.MonetaryAmount;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static io.sphere.sdk.products.ProductsScenario1Fixtures.Data;
import static io.sphere.sdk.products.ProductsScenario1Fixtures.createScenario;
import static io.sphere.sdk.products.ProductsScenario1Fixtures.destroy;
import static io.sphere.sdk.test.SphereTestUtils.asList;

public abstract class ProductProjectionSearchModelIntegrationTest extends IntegrationTest {
    protected static Data data;
    protected static Product product1;
    protected static Product product2;
    protected static Product productA;
    protected static Product productB;
    protected static ProductType productType;

    private static final boolean expectAggregations = Optional.ofNullable(System.getenv("EXPECT_AGGREGATIONS"))
            .map("true"::equals).orElse(true);

    @Rule
    public RetryIntegrationTest retry = new RetryIntegrationTest(10, 10000, LoggerFactory.getLogger(this.getClass()));

    @BeforeClass
    public static void setupProducts() {
        data = createScenario(client());
        productType = data.getProductType();
        productA = data.getReferencedProductA();
        productB = data.getReferencedProductB();
        product1 = data.getProduct1();
        product2 = data.getProduct2();
    }

    @AfterClass
    public static void clean() {
        destroy(client(), data);
    }

    protected static Long toCents(final MonetaryAmount money) {
        return money.getNumber().numberValueExact(BigDecimal.class).movePointRight(2).longValue();
    }

    protected static PagedSearchResult<ProductProjection> executeSearch(final ProductProjectionSearch search) {
        final List<String> ids = asList(product1.getId(), product2.getId());
        final ProductProjectionSearch sphereRequest = search.plusQueryFilters(productModel -> productModel.id().isIn(ids));
        return client().executeBlocking(sphereRequest);
    }

    protected static <T> WithAggregations<T> withAggregations(final T expectedWithAggregations) {
        return new WithAggregations<>(expectedWithAggregations);
    }

    protected List<String> getAllIds() {
        return Stream.of(product1, product2, productA, productB).map(p -> p.getId()).collect(Collectors.toList());
    }

    protected static class WithAggregations<T> {
        private final T expectedWithAggregations;

        WithAggregations(final T expectedWithAggregations) {
            this.expectedWithAggregations = expectedWithAggregations;
        }

        T otherwise(final T expectedOtherwise) {
            return expectAggregations ? expectedWithAggregations : expectedOtherwise;
        }
    }
}
