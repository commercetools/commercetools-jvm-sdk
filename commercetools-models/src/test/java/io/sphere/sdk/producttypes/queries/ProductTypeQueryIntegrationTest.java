package io.sphere.sdk.producttypes.queries;

import io.sphere.sdk.producttypes.ProductType;
import io.sphere.sdk.producttypes.ProductTypeFixtures;
import io.sphere.sdk.queries.PagedQueryResult;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import static org.assertj.core.api.Assertions.*;

public class ProductTypeQueryIntegrationTest extends IntegrationTest {
    @Test
    public void execution() {
        ProductTypeFixtures.withProductType(client(), productType -> {
            final PagedQueryResult<ProductType> queryResult = client().executeBlocking(ProductTypeQuery.of().byKey(productType.getKey()));
            assertThat(queryResult.getResults().get(0)).isEqualTo(productType);
        });
    }
}