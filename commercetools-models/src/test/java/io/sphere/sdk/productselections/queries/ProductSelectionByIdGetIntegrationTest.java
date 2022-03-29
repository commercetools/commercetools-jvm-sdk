package io.sphere.sdk.productselections.queries;


import io.sphere.sdk.productselections.ProductSelection;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import static io.sphere.sdk.productselections.ProductSelectionFixtures.withProductSelection;
import static org.assertj.core.api.Assertions.assertThat;

public class ProductSelectionByIdGetIntegrationTest extends IntegrationTest {

    @Test
    public void fetchById() {
        withProductSelection(client(), productSelection -> {
            final String productSelectionId = productSelection.getId();

            final ProductSelection loadedProductSelection = client().executeBlocking(ProductSelectionByIdGet.of(productSelectionId));

            assertThat(loadedProductSelection).isEqualTo(productSelection);
        });
    }
}