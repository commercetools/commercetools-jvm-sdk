package io.sphere.sdk.products.messages;

import io.sphere.sdk.messages.queries.MessageQuery;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.models.ResourceIdentifiable;
import io.sphere.sdk.products.*;
import io.sphere.sdk.products.commands.ProductCreateCommand;
import io.sphere.sdk.products.commands.ProductDeleteCommand;
import io.sphere.sdk.products.commands.ProductUpdateCommand;
import io.sphere.sdk.products.commands.updateactions.*;
import io.sphere.sdk.products.queries.ProductByKeyGet;
import io.sphere.sdk.producttypes.ProductType;
import io.sphere.sdk.producttypes.ProductTypeFixtures;
import io.sphere.sdk.queries.PagedQueryResult;
import io.sphere.sdk.queries.Query;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import java.util.Random;

import static io.sphere.sdk.products.ProductFixtures.createExternalImage;
import static io.sphere.sdk.products.ProductFixtures.withUpdateableProduct;
import static io.sphere.sdk.producttypes.ProductTypeFixtures.withEmptyProductType;
import static io.sphere.sdk.test.SphereTestUtils.*;
import static io.sphere.sdk.test.SphereTestUtils.randomSlug;
import static org.assertj.core.api.Assertions.assertThat;

public class ProductDeleteMessageIntegrationTest extends IntegrationTest {

    @Test
    public void message() {

        ProductTypeFixtures.withEmptyProductType(client(), productType -> {
            final String key = randomKey();
            final Image image = createExternalImage();
            final ProductDraft productDraft = ProductDraftBuilder.of(productType, randomSlug(), randomSlug(), ProductVariantDraftBuilder.of().images(image).build()).key(key).build();
            final Product product = client().executeBlocking(ProductCreateCommand.of(productDraft));

            final Product deletedProduct = client().executeBlocking(ProductDeleteCommand.ofKey(key, product.getVersion()));
            Query<ProductDeletedMessage> messageQuery = MessageQuery.of()
                    .withPredicates(m -> m.resource().is(deletedProduct))
                    .withSort(m -> m.createdAt().sort().desc())
                    .withExpansionPaths(m -> m.resource())
                    .withLimit(1L)
                    .forMessageType(ProductDeletedMessage.MESSAGE_HINT);

            assertEventually(() -> {
                final PagedQueryResult<ProductDeletedMessage> queryResult = client().executeBlocking(messageQuery);

                assertThat(queryResult.head()).isPresent();
                final ProductDeletedMessage message = queryResult.head().get();
                assertThat(message.getResource().getId()).isEqualTo(product.getId());
                assertThat(message.getRemovedImageUrls()).containsExactly(image.getUrl());
            });
        });
    }
}