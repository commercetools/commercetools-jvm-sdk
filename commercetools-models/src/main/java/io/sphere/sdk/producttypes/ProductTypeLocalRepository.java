package io.sphere.sdk.producttypes;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface ProductTypeLocalRepository {
    Optional<ProductType> findById(final String id);

    Optional<ProductType> findByKey(final String key);

    List<ProductType> getAll();

    static ProductTypeLocalRepository of(final Collection<ProductType> productTypes) {
        return new ProductTypeLocalRepositoryImpl(productTypes);
    }
}