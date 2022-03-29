package io.sphere.sdk.productselections.queries;

import io.sphere.sdk.productselections.ProductSelection;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import static io.sphere.sdk.productselections.ProductSelectionFixtures.withProductSelection;
import static io.sphere.sdk.test.SphereTestUtils.randomKey;
import static org.assertj.core.api.Assertions.assertThat;

public class ProductSelectionByKeyGetIntegrationTest extends IntegrationTest {

    @Test
    public void fetchByKeyWithUpdateAction() {
        withProductSelection(client(), productSelection -> {
            final String key = randomKey();

            final ProductSelection loadedProductSelection = client().executeBlocking(ProductSelectionByKeyGet.of(key));

            assertThat(loadedProductSelection).isEqualTo(productSelection);
        });

    }
}
