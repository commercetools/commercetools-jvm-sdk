package io.sphere.sdk.products.messages;

import io.sphere.sdk.messages.queries.MessageQuery;
import io.sphere.sdk.products.Image;
import io.sphere.sdk.products.Product;
import io.sphere.sdk.products.ProductFixtures;
import io.sphere.sdk.products.commands.ProductUpdateCommand;
import io.sphere.sdk.products.commands.updateactions.AddExternalImage;
import io.sphere.sdk.queries.PagedQueryResult;
import io.sphere.sdk.queries.Query;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import static io.sphere.sdk.products.ProductFixtures.createExternalImage;
import static io.sphere.sdk.test.SphereTestUtils.MASTER_VARIANT_ID;
import static io.sphere.sdk.test.SphereTestUtils.assertEventually;
import static org.assertj.core.api.Assertions.assertThat;

public class ProductImageAddedMessageIntegrationTest extends IntegrationTest {
    @Test
    public void message() {
        ProductFixtures.withUpdateableProduct(client(), product -> {
            final Image image = createExternalImage();

            final Product addExternalImage = client().executeBlocking(ProductUpdateCommand.of(product, AddExternalImage.ofVariantId( MASTER_VARIANT_ID, image)));
            Query<ProductImageAddedMessage> messageQuery = MessageQuery.of()
                    .withPredicates(m -> m.resource().is(product))
                    .withSort(m -> m.createdAt().sort().desc())
                    .withExpansionPaths(m -> m.resource())
                    .withLimit(1L)
                    .forMessageType(ProductImageAddedMessage.MESSAGE_HINT);

            assertEventually(() -> {
                final PagedQueryResult<ProductImageAddedMessage> queryResult = client().executeBlocking(messageQuery);

                assertThat(queryResult.head()).isPresent();
                final ProductImageAddedMessage message = queryResult.head().get();
                assertThat(message.getResource().getId()).isEqualTo(product.getId());
                assertThat(message.getVariantId()).isEqualTo(MASTER_VARIANT_ID);
                assertThat(message.getStaged()).isTrue();
                assertThat(message.getImage()).isEqualTo(image);
            });
            return addExternalImage;
        });
    }
}