package io.sphere.sdk.products;

import io.sphere.sdk.models.Image;
import io.sphere.sdk.products.commands.ExperimentalProductImageUploadCommand;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import java.io.File;
import java.util.Optional;

import static io.sphere.sdk.products.ProductFixtures.withProduct;
import static org.assertj.core.api.Assertions.assertThat;

public class ProductImageUploadTest extends IntegrationTest {

    @Test
    public void execution() throws Exception {
        withProduct(client(), product -> {
            assertThat(product.getMasterData().getStaged().getMasterVariant().getImages())
                    .overridingErrorMessage("no images yet").isEmpty();
            final File bytes = getImage();
            final Product updatedProduct = client().execute(ExperimentalProductImageUploadCommand.of(product, 1, Optional.of("ct.gif"), ProductUpdateScope.ONLY_STAGED, "image/gif", bytes));
            final Image image = updatedProduct.getMasterData().getStaged().getMasterVariant().getImages().get(0);
            assertThat(image.getDimensions().getHeight()).isEqualTo(102);
            assertThat(image.getDimensions().getWidth()).isEqualTo(460);
            assertThat(image.getUrl()).contains("https:");
        });
    }

    private File getImage() {
        return new File(".", "sphere-models/src/it/resources/ct_logo_farbe.gif");
    }

}
