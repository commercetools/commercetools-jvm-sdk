package io.sphere.sdk.products;

import com.fasterxml.jackson.core.type.TypeReference;
import io.sphere.sdk.commands.CommandImpl;
import io.sphere.sdk.http.HttpMethod;
import io.sphere.sdk.http.HttpRequest;
import io.sphere.sdk.models.Image;
import io.sphere.sdk.test.IntegrationTest;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

import static io.sphere.sdk.products.ProductFixtures.withProduct;
import static java.lang.String.format;
import static org.fest.assertions.Assertions.assertThat;

public class ProductImageUploadTest extends IntegrationTest {

    @Test
    public void execution() throws Exception {
        withProduct(client(), product -> {
            assertThat(product.getMasterData().getStaged().getMasterVariant().getImages())
                    .overridingErrorMessage("no images yet").isEmpty();
            final byte[] bytes = getImage();
            final Product updatedProduct = client().execute(new ProductImageUploadCommand(product.getId(), 1, Optional.of("ct.gif"), ProductUpdateScope.ONLY_STAGED, "image/gif", bytes));
            final Image image = updatedProduct.getMasterData().getStaged().getMasterVariant().getImages().get(0);
            assertThat(image.getDimensions().getHeight()).isEqualTo(102);
            assertThat(image.getDimensions().getWidth()).isEqualTo(460);
            assertThat(image.getUrl()).contains("https:");
        });
    }

    private byte[] getImage() {
        final InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("ct_logo_farbe.gif");
        try {
            return IOUtils.toByteArray(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static class ProductImageUploadCommand extends CommandImpl<Product> {
        private final String productId;
        private final int variantId;
        private final Optional<String> filename;
        private final ProductUpdateScope productUpdateScope;
        private final byte[] img;
        private final String contentType;

        private ProductImageUploadCommand(final String productId, final int variantId,
                                          final Optional<String> filename,
                                          final ProductUpdateScope productUpdateScope,
                                          final String contentType, final byte[] img) {
            this.productId = productId;
            this.variantId = variantId;
            this.filename = filename;
            this.productUpdateScope = productUpdateScope;
            this.img = img;
            this.contentType = contentType;
        }

        @Override
        protected TypeReference<Product> typeReference() {
            return Product.typeReference();
        }

        @Override
        public HttpRequest httpRequest() {
            final String path = format("/products/%s/images?variant=%d%s&staged=%s", productId, variantId, filename.map(s -> "&filename="+s).orElse(""), productUpdateScope.isOnlyStaged().toString());
            return HttpRequest.of(HttpMethod.POST, path, img, contentType);
        }

        @Override
        public String toString() {
            return new ReflectionToStringBuilder(this).setExcludeFieldNames("img").toString();
        }
    }
}
