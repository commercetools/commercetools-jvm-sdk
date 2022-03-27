package io.sphere.sdk.productselections.queries;


import io.sphere.sdk.productselections.ProductSelection;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import static io.sphere.sdk.productselections.ProductSelectionFixtures.createProductSelectionWithName;
import static org.assertj.core.api.Assertions.assertThat;

public class ProductSelectionByIdGetIntegrationTest extends IntegrationTest {
    
    @Test
    public void fetchById() throws Exception {
        final ProductSelection productSelection = createProductSelectionWithName(client());
        final String id = productSelection.getId();
        final ProductSelection fetchedProductSelection = client().executeBlocking(ProductSelectionByIdGet.of(id));
        assertThat(fetchedProductSelection).isEqualTo(productSelection);
    }
    
}