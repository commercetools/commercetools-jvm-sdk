package io.sphere.sdk.products;

import com.google.common.base.Optional;

import java.util.List;

public interface NewProductVariant {
    Optional<String> getSku();

    List<Price> getPrices();

    List<Attribute> getAttributes();
}
