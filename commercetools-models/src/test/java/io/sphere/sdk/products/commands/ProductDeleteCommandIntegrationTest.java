package io.sphere.sdk.products.commands;

import io.sphere.sdk.products.Product;
import io.sphere.sdk.products.ProductDraft;
import io.sphere.sdk.products.ProductDraftBuilder;
import io.sphere.sdk.products.ProductVariantDraftBuilder;
import io.sphere.sdk.products.queries.ProductByKeyGet;
import io.sphere.sdk.producttypes.ProductTypeFixtures;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import static io.sphere.sdk.test.SphereTestUtils.randomKey;
import static io.sphere.sdk.test.SphereTestUtils.randomSlug;
import static org.assertj.core.api.Assertions.assertThat;

public class ProductDeleteCommandIntegrationTest extends IntegrationTest {
    @Test
    public void byKey() {
        ProductTypeFixtures.withEmptyProductType(client(), productType -> {
            final String key = randomKey();
            final ProductDraft productDraft = ProductDraftBuilder.of(productType, randomSlug(), randomSlug(), ProductVariantDraftBuilder.of().build()).key(key).build();
            final Product product = client().executeBlocking(ProductCreateCommand.of(productDraft));

            final Product deletedProduct = client().executeBlocking(ProductDeleteCommand.ofKey(key, product.getVersion()));

            assertThat(client().executeBlocking(ProductByKeyGet.of(key))).isNull();
        });
    }
}