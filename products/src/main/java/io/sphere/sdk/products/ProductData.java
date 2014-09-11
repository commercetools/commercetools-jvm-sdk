package io.sphere.sdk.products;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * For construction in unit tests use {@link io.sphere.sdk.products.ProductDataBuilder}.
 */
@JsonDeserialize(as=ProductDataImpl.class)
public interface ProductData extends ProductDataLike {

}
