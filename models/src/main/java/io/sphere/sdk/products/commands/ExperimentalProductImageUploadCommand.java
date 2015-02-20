package io.sphere.sdk.products.commands;

import com.fasterxml.jackson.core.type.TypeReference;
import io.sphere.sdk.client.HttpRequestIntent;
import io.sphere.sdk.commands.CommandImpl;
import io.sphere.sdk.http.HttpMethod;
import io.sphere.sdk.models.Identifiable;
import io.sphere.sdk.products.Product;
import io.sphere.sdk.products.ProductUpdateScope;

import java.io.File;
import java.util.Optional;

import static java.lang.String.format;

public class ExperimentalProductImageUploadCommand extends CommandImpl<Product> {
    private final String productId;
    private final int variantId;
    private final Optional<String> filename;
    private final ProductUpdateScope productUpdateScope;
    private final File img;
    private final String contentType;

    private ExperimentalProductImageUploadCommand(final String productId, final int variantId,
                                          final Optional<String> filename,
                                          final ProductUpdateScope productUpdateScope,
                                          final String contentType, final File img) {
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
    public HttpRequestIntent httpRequestIntent() {
        final String path = format("/products/%s/images?variant=%d%s&staged=%s", productId, variantId, filename.map(s -> "&filename="+s).orElse(""), productUpdateScope.isOnlyStaged().toString());
        return HttpRequestIntent.of(HttpMethod.POST, path, img, contentType);
    }

    public static ExperimentalProductImageUploadCommand of(final String productId, final int variantId,
                                                        final Optional<String> filename,
                                                        final ProductUpdateScope productUpdateScope,
                                                        final String contentType, final File img) {
        return new ExperimentalProductImageUploadCommand(productId, variantId, filename, productUpdateScope, contentType, img);
    }

    public static ExperimentalProductImageUploadCommand of(final Identifiable<Product> product, final int variantId,
                                                        final Optional<String> filename,
                                                        final ProductUpdateScope productUpdateScope,
                                                        final String contentType, final File img) {
        return of(product.getId(), variantId, filename, productUpdateScope, contentType, img);
    }
}
