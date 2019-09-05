package io.sphere.sdk.products.commands;

import com.fasterxml.jackson.databind.JavaType;
import io.sphere.sdk.client.HttpRequestIntent;
import io.sphere.sdk.commands.CommandImpl;
import io.sphere.sdk.http.HttpMethod;
import io.sphere.sdk.http.UrlQueryBuilder;
import io.sphere.sdk.json.SphereJsonUtils;
import io.sphere.sdk.products.ByIdVariantIdentifier;
import io.sphere.sdk.products.Product;

import javax.annotation.Nullable;
import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.apache.commons.lang3.StringUtils.isEmpty;

/**
 * Uploads an Image for a product.
 * <p>
 * <p>Uploads a binary image file to a given {@link io.sphere.sdk.products.ProductVariant}. The supported image formats are JPEG, PNG and GIF.</p>
 * <p>
 * Example usage executing the command:
 * {@include.example io.sphere.sdk.products.commands.ProductImageUploadCommandIntegrationTest#uploadImage()}
 */
public final class ProductImageUploadCommand extends CommandImpl<Product> {
    private final String productId;
    @Nullable
    private final Integer variant;
    @Nullable
    private final String sku;
    @Nullable
    private final String filename;
    @Nullable
    private final Boolean staged;
    private final File body;
    @Nullable
    private final String givenContentType;

    private static final Map<String, String> ACCEPTABLE_IMAGE_FORMATS_MAP;

    static {
        final HashMap<String, String> map = new HashMap();

        map.put(".jpeg", "image/jpeg");
        map.put(".jpg", "image/jpeg");
        map.put(".gif", "image/gif");
        map.put(".png", "image/png");

        ACCEPTABLE_IMAGE_FORMATS_MAP = Collections.unmodifiableMap(map);
    }

    private ProductImageUploadCommand(final File body, final String productId, final Integer variant, final String sku,
                                      final String filename, final Boolean staged, final String givenContentType) {
        this.productId = productId;
        this.variant = variant;
        this.sku = sku;
        this.filename = filename;
        this.staged = staged;
        this.body = body;
        this.givenContentType = givenContentType;
    }

    public static ProductImageUploadCommand ofMasterVariant(final File body, final String productId) {
        return new ProductImageUploadCommand(body, productId, null, null, null, null, null);
    }

    public static ProductImageUploadCommand ofProductIdAndSku(final File body, final String productId, final String sku) {
        return new ProductImageUploadCommand(body, productId, null, sku, null, null, null);
    }

    public static ProductImageUploadCommand ofVariantId(final File body, final ByIdVariantIdentifier variantIdentifier) {
        return new ProductImageUploadCommand(body, variantIdentifier.getProductId(), variantIdentifier.getVariantId(), null, null, null, null);
    }

    public ProductImageUploadCommand withFilename(final String newFilename) {
        return new ProductImageUploadCommand(body, productId, variant, sku, newFilename, staged, givenContentType);
    }

    public ProductImageUploadCommand withStaged(final Boolean newStaged) {
        return new ProductImageUploadCommand(body, productId, variant, sku, filename, newStaged, givenContentType);
    }

    /**
     * Sets the type of the Type of the uploaded image
     *
     * @param contentType must be one of "image/jpeg", "image/png" or "image/gif"
     * @return ProductImageUploadCommand the new constructed productImageUploadCommand
     */
    public ProductImageUploadCommand withContentType(final String contentType) {
        final ProductImageUploadCommand productImageUploadCommand = new ProductImageUploadCommand(body, productId, variant, sku, filename, staged, contentType);

        return productImageUploadCommand;
    }

    @Override
    protected JavaType jacksonJavaType() {
        return SphereJsonUtils.convertToJavaType(Product.typeReference());
    }

    @Override
    public HttpRequestIntent httpRequestIntent() {
        final String contentType;

        if (givenContentType != null) {
            if (!ACCEPTABLE_IMAGE_FORMATS_MAP.values().contains(givenContentType)) {
                throw new IllegalStateException("Content Type " + givenContentType + " is not accepted.");
            } else {
                contentType = givenContentType;
            }
        } else {
            String extension = "";
            int i = body.getName().lastIndexOf('.');
            if (i > 0) {
                extension = body.getName().substring(i);
            }
            contentType = ACCEPTABLE_IMAGE_FORMATS_MAP.get(extension);
            if (contentType == null) {
                throw new IllegalStateException("cannot determine content type for " + body.getName());
            }
        }
        final UrlQueryBuilder builder = UrlQueryBuilder.of();
        if (!isEmpty(sku)) {
            builder.add("sku", sku);
        }
        if (!isEmpty(filename)) {
            builder.add("filename", filename);
        }
        if (null != variant) {
            builder.add("variant", "" + variant);
        }
        if (null != staged) {
            builder.add("staged", "" + staged);
        }
        final String path = String.format("/products/%s/images%s", productId, builder.toStringWithOptionalQuestionMark());
        return HttpRequestIntent.of(HttpMethod.POST, path, body, contentType);
    }
}