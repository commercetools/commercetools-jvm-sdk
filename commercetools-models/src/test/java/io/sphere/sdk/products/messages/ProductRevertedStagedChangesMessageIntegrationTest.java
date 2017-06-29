package io.sphere.sdk.products.messages;

import io.sphere.sdk.messages.queries.MessageQuery;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.products.Image;
import io.sphere.sdk.products.Product;
import io.sphere.sdk.products.ProductFixtures;
import io.sphere.sdk.products.ProductVariant;
import io.sphere.sdk.products.commands.ProductDeleteCommand;
import io.sphere.sdk.products.commands.ProductUpdateCommand;
import io.sphere.sdk.products.commands.updateactions.*;
import io.sphere.sdk.queries.PagedQueryResult;
import io.sphere.sdk.queries.Query;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import java.util.Random;

import static io.sphere.sdk.products.ProductFixtures.createExternalImage;
import static io.sphere.sdk.products.ProductFixtures.withUpdateableProduct;
import static io.sphere.sdk.test.SphereTestUtils.MASTER_VARIANT_ID;
import static io.sphere.sdk.test.SphereTestUtils.asList;
import static io.sphere.sdk.test.SphereTestUtils.assertEventually;
import static org.assertj.core.api.Assertions.assertThat;

public class ProductRevertedStagedChangesMessageIntegrationTest extends IntegrationTest {

    @Test
    public void message() {

        withUpdateableProduct(client(), product -> {

            final Image image = createExternalImage();
            //changing only staged and not current
            final ProductUpdateCommand cmd = ProductUpdateCommand.of(product, asList(Publish.of(), AddExternalImage.of(image,MASTER_VARIANT_ID)));
            final Product updatedProduct = client().executeBlocking(cmd);

            final Product revertedProduct = client().executeBlocking(ProductUpdateCommand.of(updatedProduct, RevertStagedChanges.of()));

            Query<ProductRevertedStagedChangesMessage> messageQuery = MessageQuery.of()
                    .withPredicates(m -> m.resource().is(product))
                    .withSort(m -> m.createdAt().sort().desc())
                    .withExpansionPaths(m -> m.resource())
                    .withLimit(1L)
                    .forMessageType(ProductRevertedStagedChangesMessage.MESSAGE_HINT);

            assertEventually(() -> {
                final PagedQueryResult<ProductRevertedStagedChangesMessage> queryResult = client().executeBlocking(messageQuery);

                assertThat(queryResult.head()).isPresent();
                final ProductRevertedStagedChangesMessage message = queryResult.head().get();
                assertThat(message.getResource().getId()).isEqualTo(product.getId());
                assertThat(message.getRemovedImageUrls()).containsExactly(image.getUrl());
            });


            return revertedProduct;
        });
    }
}