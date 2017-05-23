package io.sphere.sdk.products.commands;

import io.sphere.sdk.products.Image;
import io.sphere.sdk.products.Product;
import io.sphere.sdk.products.commands.updateactions.AddExternalImage;
import io.sphere.sdk.test.IntegrationTest;
import io.sphere.sdk.test.SphereTestUtils;
import org.junit.Test;

import static io.sphere.sdk.products.ProductFixtures.createExternalImage;
import static io.sphere.sdk.products.ProductFixtures.withUpdateableProduct;
import static io.sphere.sdk.test.SphereTestUtils.MASTER_VARIANT_ID;
import static org.assertj.core.api.Assertions.assertThat;

public class ProductImageUploadCommandIntegrationTest extends IntegrationTest {


    @Test
    public void upload() {
        withUpdateableProduct(client(), (Product product) -> {
            assertThat(product.getMasterData().getStaged().getMasterVariant().getImages()).hasSize(0);

            final Image image = createExternalImage();
            final Product updatedProduct = client().executeBlocking(ProductUpdateCommand.of(product, AddExternalImage.of(image, MASTER_VARIANT_ID)));

            assertThat(updatedProduct.getMasterData().getStaged().getMasterVariant().getImages()).isEqualTo(SphereTestUtils.asList(image));
            return updatedProduct;
        });
    }
}