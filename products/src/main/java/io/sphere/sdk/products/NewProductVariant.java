package io.sphere.sdk.products;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.common.base.Optional;

import java.util.List;

@JsonDeserialize(as = NewProductVariantImpl.class)
public interface NewProductVariant {
    Optional<String> getSku();

    List<Price> getPrices();

    List<Attribute> getAttributes();
}
