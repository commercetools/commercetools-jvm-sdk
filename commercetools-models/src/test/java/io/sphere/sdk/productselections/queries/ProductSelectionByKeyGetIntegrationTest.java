package io.sphere.sdk.productselections.queries;

import io.sphere.sdk.productselections.ProductSelection;
import io.sphere.sdk.productselections.ProductSelectionType;
import io.sphere.sdk.productselections.commands.ProductSelectionUpdateCommand;
import io.sphere.sdk.productselections.commands.updateactions.SetKey;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import static io.sphere.sdk.productselections.ProductSelectionFixtures.withProductSelection;
import static io.sphere.sdk.stores.StoreFixtures.withStore;
import static io.sphere.sdk.test.SphereTestUtils.randomKey;
import static org.assertj.core.api.Assertions.assertThat;

public class ProductSelectionByKeyGetIntegrationTest extends IntegrationTest {

    @Test
    public void fetchByKeyWithUpdateAction()  {
        withProductSelection(client(), productSelection -> {
            assertThat(productSelection.getKey()).isNull();
            assertThat(productSelection.getType()).isEqualTo(ProductSelectionType.INDIVIDUAL);
            final String key = randomKey();
            final ProductSelection updatedProductSelection = client().executeBlocking(ProductSelectionUpdateCommand.of(productSelection, SetKey.of(key)));

            assertThat(updatedProductSelection).isNotNull();
            assertThat(updatedProductSelection.getType()).isEqualByComparingTo(ProductSelectionType.INDIVIDUAL);

            final ProductSelection fetchedProductSelection = client().executeBlocking(ProductSelectionByKeyGet.of(key));
            assertThat(fetchedProductSelection).isEqualTo(updatedProductSelection);

            return updatedProductSelection;
        });
    }
}
