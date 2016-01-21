package io.sphere.sdk.products;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(as = VariantIdentifierImpl.class)
public interface VariantIdentifier {
}
