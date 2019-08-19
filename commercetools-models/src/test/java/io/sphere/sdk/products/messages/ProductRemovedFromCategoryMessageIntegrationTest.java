package io.sphere.sdk.products.messages;

import io.sphere.sdk.categories.Category;
import io.sphere.sdk.messages.queries.MessageQuery;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.products.Product;
import io.sphere.sdk.products.commands.ProductUpdateCommand;
import io.sphere.sdk.products.commands.updateactions.AddToCategory;
import io.sphere.sdk.products.commands.updateactions.RemoveFromCategory;
import io.sphere.sdk.queries.PagedQueryResult;
import io.sphere.sdk.queries.Query;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import static io.sphere.sdk.products.ProductFixtures.withProductAndUnconnectedCategory;
import static io.sphere.sdk.test.SphereTestUtils.assertEventually;
import static org.assertj.core.api.Assertions.assertThat;

public class ProductRemovedFromCategoryMessageIntegrationTest extends IntegrationTest {

    @Test
    public void message() throws Exception {
        withProductAndUnconnectedCategory(client(), (final Product product, final Category category) -> {
            assertThat(product.getMasterData().getStaged().getCategories()).isEmpty();

            final String orderHint = "0.123";
            final Product productWithCategory = client()
                    .executeBlocking(ProductUpdateCommand.of(product, AddToCategory.of(category, orderHint)));

            final Reference<Category> categoryReference = productWithCategory.getMasterData().getStaged().getCategories().stream().findAny().get();
            assertThat(categoryReference.referencesSameResource(category)).isTrue();
            assertThat(productWithCategory.getMasterData().getStaged().getCategoryOrderHints().get(category.getId())).isEqualTo(orderHint);
            
            final Product productWithoutCategory = client()
                    .executeBlocking(ProductUpdateCommand.of(productWithCategory, RemoveFromCategory.of(category)));

            assertThat(productWithoutCategory.getMasterData().getStaged().getCategories()).isEmpty();
            
            Query<ProductRemovedFromCategoryMessage> messageQuery = MessageQuery.of()
                    .withPredicates(m -> m.resource().is(productWithCategory))
                    .withSort(m -> m.createdAt().sort().desc())
                    .withExpansionPaths(m -> m.resource())
                    .withLimit(1L)
                    .forMessageType(ProductRemovedFromCategoryMessage.MESSAGE_HINT);

            assertEventually(() -> {
                final PagedQueryResult<ProductRemovedFromCategoryMessage> queryResult = client().executeBlocking(messageQuery);

                assertThat(queryResult.head()).isPresent();
                final ProductRemovedFromCategoryMessage message = queryResult.head().get();
                assertThat(message.getResource().getId()).isEqualTo(product.getId());
            });

        });
    }
    
}
