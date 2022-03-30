package io.sphere.sdk.productselections.commands;


import io.sphere.sdk.productselections.ProductSelection;
import io.sphere.sdk.productselections.ProductSelectionFixtures;
import io.sphere.sdk.productselections.commands.updateactions.SetKey;
import io.sphere.sdk.productselections.queries.ProductSelectionQuery;
import io.sphere.sdk.queries.PagedQueryResult;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import static io.sphere.sdk.test.SphereTestUtils.randomKey;
import static org.assertj.core.api.Assertions.assertThat;

public class ProductSelectionDeleteCommandIntegrationTest extends IntegrationTest {

    @Test
    public void deleteById(){
        ProductSelectionFixtures.withProductSelection(client(), productSelection -> {
            client().executeBlocking(ProductSelectionDeleteCommand.of(productSelection));
            PagedQueryResult<ProductSelection> result = client().executeBlocking(ProductSelectionQuery.of().withPredicates(m -> m.id().is(productSelection.getId())));
            assertThat(result.getResults()).isEmpty();
        });
    }

    @Test
    public void deleteByKey(){
        ProductSelectionFixtures.withUpdateableProductSelection(client(), productSelection -> {

            final String newKey = randomKey();
            final ProductSelection updatedProductSelection = client().executeBlocking(ProductSelectionUpdateCommand.of(productSelection, SetKey.of(newKey)));
            assertThat(updatedProductSelection.getKey()).isEqualTo(newKey);

            client().executeBlocking(ProductSelectionDeleteCommand.ofKey(productSelection.getKey(), productSelection.getVersion()));
            PagedQueryResult<ProductSelection> result = client().executeBlocking(ProductSelectionQuery.of().withPredicates(m -> m.id().is(productSelection.getId())));
            assertThat(result.getResults()).isEmpty();

            return updatedProductSelection;
        });
    }
}
