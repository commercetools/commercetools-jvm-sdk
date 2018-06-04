package io.sphere.sdk.orders;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.products.PriceDraft;
import io.sphere.sdk.products.attributes.AttributeImportDraft;
import io.sphere.sdk.products.Image;
import io.sphere.sdk.products.Price;

import javax.annotation.Nullable;
import java.util.List;

/**
 *
 * @see ProductVariantImportDraftBuilder
 */
@JsonDeserialize(as = ProductVariantImportDraftImpl.class)
public interface ProductVariantImportDraft {
    @Nullable
    List<AttributeImportDraft> getAttributes();

    @Nullable
    Integer getId();

    @Nullable
    List<Image> getImages();

    @Nullable
    List<PriceDraft> getPrices();

    @Nullable
    String getSku();

    /**
     * Field is not part of the platform API, it is used to initialize {@link LineItemImportDraftBuilder} correctly.
     * @return the product ID
     */
    @Nullable
    String getProductId();
}
