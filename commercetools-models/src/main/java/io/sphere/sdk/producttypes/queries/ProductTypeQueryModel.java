package io.sphere.sdk.producttypes.queries;

import io.sphere.sdk.producttypes.ProductType;
import io.sphere.sdk.queries.ResourceQueryModel;
import io.sphere.sdk.queries.StringQuerySortingModel;

public interface ProductTypeQueryModel extends ResourceQueryModel<ProductType> {
    StringQuerySortingModel<ProductType> name();

    StringQuerySortingModel<ProductType> key();

    AttributeDefinitionQueryModel<ProductType> attributes();

    static ProductTypeQueryModel of() {
        return new ProductTypeQueryModelImpl(null, null);
    }
}
