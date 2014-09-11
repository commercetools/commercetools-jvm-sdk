package test;

import io.sphere.sdk.models.Identifiable;
import io.sphere.sdk.products.ProductProjection;
import io.sphere.sdk.products.ProductProjectionType;
import io.sphere.sdk.products.queries.FetchProductProjectionById;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;
import static test.ProductReferenceExpansionTest.*;

public class ProductProjectionIntegrationTest extends IntegrationTest {

    @Test
    public void getProductProjectionById() throws Exception {
        withProduct(client(), "ProductProjectionIntegrationTest", product -> {
            final ProductProjectionType projectionType = ProductProjectionType.STAGED;
            final Identifiable<ProductProjection> identifier = product.toProjection(projectionType);
            final ProductProjection productProjection = client().execute(new FetchProductProjectionById(identifier, projectionType)).get();
            final String fetchedProjectionId = productProjection.getId();
            assertThat(fetchedProjectionId).isEqualTo(product.getId());
            assertThat(productProjection.getCategories()).isEqualTo(product.getMasterData().get(projectionType).getCategories());
        });
    }
}
