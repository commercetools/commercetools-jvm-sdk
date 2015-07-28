package io.sphere.sdk.orders;

import io.sphere.sdk.attributes.AttributeImportDraft;
import io.sphere.sdk.products.Image;
import io.sphere.sdk.products.Price;

import javax.annotation.Nullable;
import java.util.List;

/**
 *
 * @see ProductVariantImportDraftBuilder
 */
public interface ProductVariantImportDraft {
    @Nullable
    List<AttributeImportDraft> getAttributes();

    @Nullable
    Integer getId();

    @Nullable
    List<Image> getImages();

    @Nullable
    List<Price> getPrices();

    @Nullable
    String getSku();

    /**
     * Field is not part of the SPHERE.IO API, it is used to initialize {@link LineItemImportDraftBuilder} correctly.
     * @return the product ID
     */
    @Nullable
    String getProductId();
}
