package io.sphere.sdk.products.commands;

import io.sphere.sdk.products.Image;
import io.sphere.sdk.products.*;
import io.sphere.sdk.producttypes.ProductType;
import io.sphere.sdk.producttypes.ProductTypeFixtures;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import static org.assertj.core.api.Assertions.*;
import static io.sphere.sdk.test.SphereTestUtils.*;

public class ProductCreateCommandTest extends IntegrationTest {
    @Test
    public void createProductWithExternalImage() throws Exception {
        final ProductType productType = ProductTypeFixtures.defaultProductType(client());
        final Image image = Image.ofWidthAndHeight("http://www.commercetools.com/assets/img/ct_logo_farbe.gif", 460, 102, "commercetools logo");
        final ProductVariantDraft masterVariant = ProductVariantDraftBuilder.of()
                .images(image)
                .build();
        final ProductDraft productDraft = ProductDraftBuilder.of(productType, en("product with external image"), randomSlug(), masterVariant).build();
        final Product product = execute(ProductCreateCommand.of(productDraft));
        final Image loadedImage = product.getMasterData().getStaged().getMasterVariant().getImages().get(0);
        assertThat(loadedImage).isEqualTo(image);

        //clean up test
        execute(ProductDeleteCommand.of(product));
    }
}