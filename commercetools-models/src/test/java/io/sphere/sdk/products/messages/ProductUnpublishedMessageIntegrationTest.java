package io.sphere.sdk.products.messages;

import io.sphere.sdk.messages.queries.MessageQuery;
import io.sphere.sdk.products.Product;
import io.sphere.sdk.products.ProductFixtures;
import io.sphere.sdk.products.commands.ProductUpdateCommand;
import io.sphere.sdk.products.commands.updateactions.Publish;
import io.sphere.sdk.products.commands.updateactions.Unpublish;
import io.sphere.sdk.queries.PagedQueryResult;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import static io.sphere.sdk.test.SphereTestUtils.assertEventually;
import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

public class ProductUnpublishedMessageIntegrationTest extends IntegrationTest {
    @Test
    public void message() {
        ProductFixtures.withUpdateableProduct(client(), product -> {
            assertThat(product.getMasterData().isPublished()).isFalse();
            final Product unpublishedProduct = client().executeBlocking(ProductUpdateCommand.of(product, asList(Publish.of(), Unpublish.of())));
            assertEventually(() -> {
                final PagedQueryResult<ProductUnpublishedMessage> queryResult =
                        client().executeBlocking(MessageQuery.of()
                                .withPredicates(m -> m.resource().is(unpublishedProduct))
                                .withSort(m -> m.createdAt().sort().desc())
                                .withExpansionPaths(m -> m.resource())
                                .withLimit(1L)
                                .forMessageType(ProductUnpublishedMessage.MESSAGE_HINT));
                assertThat(queryResult.head()).isPresent();
                final ProductUnpublishedMessage message = queryResult.head().get();
                assertThat(message.getResource().getId()).as("productId").isEqualTo(product.getId());
                assertThat(message.getResource().getObj().getMasterData().isPublished()).isFalse();
            });
            return unpublishedProduct;
        });
    }
}