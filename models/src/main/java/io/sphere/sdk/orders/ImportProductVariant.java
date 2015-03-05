package io.sphere.sdk.orders;

import io.sphere.sdk.attributes.Attribute;
import io.sphere.sdk.models.Image;
import io.sphere.sdk.products.Price;

import java.util.List;
import java.util.Optional;

public interface ImportProductVariant {
    Optional<List<Attribute>> getAttributes();

    Optional<Integer> getId();

    Optional<List<Image>> getImages();

    Optional<List<Price>> getPrices();

    Optional<String> getSku();
}
