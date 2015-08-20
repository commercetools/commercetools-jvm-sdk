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

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

public class ProductUnpublishedMessageTest extends IntegrationTest {
    @Test
    public void message() {
        ProductFixtures.withUpdateableProduct(client(), product -> {
            assertThat(product.getMasterData().isPublished()).isFalse();
            final Product unpublishedProduct = execute(ProductUpdateCommand.of(product, asList(Publish.of(), Unpublish.of())));
            final PagedQueryResult<ProductUnpublishedMessage> queryResult = execute(MessageQuery.of()
                    .withSort(m -> m.createdAt().sort().desc())
                    .withExpansionPaths(m -> m.resource())
                    .withLimit(1)
                    .forMessageType(ProductUnpublishedMessage.MESSAGE_HINT));
            final ProductUnpublishedMessage message = queryResult.head().get();
            assertThat(message.getResource().getId()).isEqualTo(product.getId());
            assertThat(message.getResource().getObj().getMasterData().isPublished()).isFalse();
            return unpublishedProduct;
        });
    }
}