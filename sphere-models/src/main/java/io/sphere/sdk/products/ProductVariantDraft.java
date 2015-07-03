package io.sphere.sdk.products;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.attributes.AttributeDraft;
import io.sphere.sdk.models.Image;

import java.util.Optional;

import java.util.List;

/**
 * @see ProductVariantDraftBuilder
 */
@JsonDeserialize(as = ProductVariantDraftImpl.class)
public interface ProductVariantDraft {
    Optional<String> getSku();

    List<Price> getPrices();

    List<AttributeDraft> getAttributes();

    List<Image> getImages();
}
