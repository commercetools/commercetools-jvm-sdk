package io.sphere.sdk.products;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.models.Image;

import java.util.List;
import java.util.Optional;

import static java.util.Objects.requireNonNull;

@JsonDeserialize(as = ProductVariantImpl.class)
public interface ProductVariant extends AttributeContainer {
    int getId();

    Optional<String> getSku();

    List<Price> getPrices();

    List<Image> getImages();

    /**
     * The availability is set if the variant is tracked by the inventory. The field might not contain the latest
     * inventory state (it is eventually consistent) and can be used as an optimization to reduce calls to the inventory service.
     *
     * @return availability
     */
    Optional<ProductVariantAvailability> getAvailability();
}
