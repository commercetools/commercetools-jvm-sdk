package io.sphere.sdk.producttypes;

import io.sphere.sdk.models.Base;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

final class ProductTypeLocalRepositoryImpl extends Base implements ProductTypeLocalRepository {
    final Map<String, ProductType> idToProductTypeMap = new HashMap<>();

    public ProductTypeLocalRepositoryImpl(final Collection<ProductType> productTypes) {
        productTypes.forEach(productType -> idToProductTypeMap.put(productType.getId(), productType));
    }

    @Override
    public Optional<ProductType> findById(final String id) {
        return Optional.ofNullable(idToProductTypeMap.get(id));
    }
}
