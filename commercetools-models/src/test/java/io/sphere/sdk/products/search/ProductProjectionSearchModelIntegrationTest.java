package io.sphere.sdk.products.search;

import io.sphere.sdk.annotations.NotOSGiCompatible;
import io.sphere.sdk.products.Product;
import io.sphere.sdk.products.ProductProjection;
import io.sphere.sdk.producttypes.ProductType;
import io.sphere.sdk.search.PagedSearchResult;
import io.sphere.sdk.test.IntegrationTest;
import io.sphere.sdk.test.RetryIntegrationTest;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Rule;
import org.slf4j.LoggerFactory;

import javax.money.MonetaryAmount;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static io.sphere.sdk.products.ProductsScenario1Fixtures.*;
import static io.sphere.sdk.test.SphereTestUtils.asList;

@NotOSGiCompatible
@Ignore
public abstract class ProductProjectionSearchModelIntegrationTest extends IntegrationTest {
    protected static Data data;
    protected static Product product1;
    protected static Product product2;
    protected static Product productA;
    protected static Product productB;
    protected static ProductType productType;

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

    protected List<String> getAllIds() {
        return Stream.of(product1, product2, productA, productB).map(p -> p.getId()).collect(Collectors.toList());
    }
}
