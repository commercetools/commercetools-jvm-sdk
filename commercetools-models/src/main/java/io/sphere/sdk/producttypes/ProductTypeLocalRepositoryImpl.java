package io.sphere.sdk.producttypes;

import io.sphere.sdk.models.Base;

import java.util.*;

final class ProductTypeLocalRepositoryImpl extends Base implements ProductTypeLocalRepository {
    private final Map<String, ProductType> idToProductTypeMap = new HashMap<>();
    private final Map<String, ProductType> keyToProductTypeMap = new HashMap<>();
    private final List<ProductType> all;

    public ProductTypeLocalRepositoryImpl(final Collection<ProductType> productTypes) {
        all = Collections.unmodifiableList(new ArrayList<>(productTypes));
        productTypes.forEach(productType -> {
            idToProductTypeMap.put(productType.getId(), productType);
            keyToProductTypeMap.put(productType.getKey(), productType);
        });
    }

    @Override
    public Optional<ProductType> findById(final String id) {
        return Optional.ofNullable(idToProductTypeMap.get(id));
    }

    @Override
    public Optional<ProductType> findByKey(final String key) {
        return Optional.ofNullable(keyToProductTypeMap.get(key));
    }

    @Override
    public List<ProductType> getAll() {
        return all;
    }
}
