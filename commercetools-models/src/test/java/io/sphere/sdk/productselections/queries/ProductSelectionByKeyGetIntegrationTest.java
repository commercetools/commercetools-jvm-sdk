package io.sphere.sdk.productselections.queries;

import io.sphere.sdk.productselections.ProductSelection;
import io.sphere.sdk.productselections.commands.ProductSelectionUpdateCommand;
import io.sphere.sdk.productselections.commands.updateactions.SetKey;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import static io.sphere.sdk.productselections.ProductSelectionFixtures.withProductSelection;
import static io.sphere.sdk.productselections.ProductSelectionFixtures.withUpdateableProductSelection;
import static io.sphere.sdk.test.SphereTestUtils.randomKey;
import static org.assertj.core.api.Assertions.assertThat;

public class ProductSelectionByKeyGetIntegrationTest extends IntegrationTest {

    @Test
    public void fetchByKeyWithUpdateAction() {
        withUpdateableProductSelection(client(), productSelection -> {
            final String newKey = randomKey();
            final ProductSelection updatedProductSelection =
                    client().executeBlocking(ProductSelectionUpdateCommand.of(productSelection, SetKey.of(newKey)));
            assertThat(updatedProductSelection).isNotNull();

            final ProductSelection loadedProductSelection = client().executeBlocking(ProductSelectionByKeyGet.of(newKey));

            assertThat(loadedProductSelection).isEqualTo(productSelection);

            return updatedProductSelection;
        });

    }
}
