package io.sphere.sdk.products.queries;

import io.sphere.sdk.products.ProductProjection;
import io.sphere.sdk.products.ProductProjectionType;
import io.sphere.sdk.products.VariantIdentifier;
import io.sphere.sdk.queries.Query;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import static io.sphere.sdk.products.ProductFixtures.withProduct;
import static org.assertj.core.api.Assertions.*;

public class ProductProjectionQueryTest extends IntegrationTest {
    @Test
    public void variantIdentifierIsAvailable() throws Exception {
        withProduct(client(), product -> {
            final Query<ProductProjection> query = ProductProjectionQuery.of(ProductProjectionType.STAGED)
                    .withPredicate(ProductProjectionQuery.model().id().is(product.getId()));
            final ProductProjection productProjection = execute(query).head().get();
            final VariantIdentifier identifier = productProjection.getMasterVariant().getIdentifier();
            assertThat(identifier).isEqualTo(VariantIdentifier.of(product.getId(), 1));
        });
    }
}