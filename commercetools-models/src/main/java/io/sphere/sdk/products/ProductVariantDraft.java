package io.sphere.sdk.products;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.products.attributes.AttributeDraft;

import javax.annotation.Nullable;
import java.util.List;

/**
 * @see ProductVariantDraftBuilder
 */
@JsonDeserialize(as = ProductVariantDraftImpl.class)
public interface ProductVariantDraft {
    @Nullable
    String getSku();

    List<PriceDraft> getPrices();

    List<AttributeDraft> getAttributes();

    List<Image> getImages();

    @Nullable
    String getKey();
}
