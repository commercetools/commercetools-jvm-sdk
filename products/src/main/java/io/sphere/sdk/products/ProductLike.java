package io.sphere.sdk.products;

import io.sphere.sdk.models.DefaultModelView;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.producttypes.ProductType;
import io.sphere.sdk.taxcategories.TaxCategory;

import java.util.Optional;

public interface ProductLike<T> extends DefaultModelView<T> {
    Reference<ProductType> getProductType();

    Optional<Reference<TaxCategory>> getTaxCategory();
}
