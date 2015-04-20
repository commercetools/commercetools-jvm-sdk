package io.sphere.sdk.orders;

import io.sphere.sdk.attributes.Attribute;
import io.sphere.sdk.models.Image;
import io.sphere.sdk.products.Price;

import java.util.List;
import java.util.Optional;

/**
 *
 * @see ProductVariantImportDraftBuilder
 */
public interface ProductVariantImportDraft {
    Optional<List<Attribute>> getAttributes();

    Optional<Integer> getId();

    Optional<List<Image>> getImages();

    Optional<List<Price>> getPrices();

    Optional<String> getSku();

    /**
     * Field is not part of the SPHERE.IO API, it is used to initialize {@link LineItemImportDraftBuilder} correctly.
     * @return the product ID
     */
    Optional<String> getProductId();
}
