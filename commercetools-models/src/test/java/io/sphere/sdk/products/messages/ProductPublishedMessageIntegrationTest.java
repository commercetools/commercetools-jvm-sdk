package io.sphere.sdk.products.messages;

import io.sphere.sdk.messages.queries.MessageQuery;
import io.sphere.sdk.products.Product;
import io.sphere.sdk.products.ProductFixtures;
import io.sphere.sdk.products.commands.ProductUpdateCommand;
import io.sphere.sdk.products.commands.updateactions.Publish;
import io.sphere.sdk.queries.PagedQueryResult;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ProductPublishedMessageIntegrationTest extends IntegrationTest {
    @Test
    public void message() {
        ProductFixtures.withUpdateableProduct(client(), product -> {
            assertThat(product.getMasterData().isPublished()).isFalse();
            final Product publishedProduct = client().executeBlocking(ProductUpdateCommand.of(product, Publish.of()));
            final PagedQueryResult<ProductPublishedMessage> queryResult = client().executeBlocking(MessageQuery.of()
                    .withPredicates(m -> m.resource().is(product))
                    .withSort(m -> m.createdAt().sort().desc())
                    .withExpansionPaths(m -> m.resource())
                    .withLimit(1L)
                    .forMessageType(ProductPublishedMessage.MESSAGE_HINT));
            assertThat(queryResult.head()).isPresent();
            final ProductPublishedMessage message = queryResult.head().get();
            assertThat(message.getResource().getId()).isEqualTo(product.getId());
            assertThat(message.getProductProjection().getMasterVariant()).isEqualTo(publishedProduct.getMasterData().getCurrent().getMasterVariant());
            assertThat(message.getResource().getObj().getMasterData().getCurrent().getSlug()).isEqualTo(message.getProductProjection().getSlug());
            return publishedProduct;
        });
    }
}