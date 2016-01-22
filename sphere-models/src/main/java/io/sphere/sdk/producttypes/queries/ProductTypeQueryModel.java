package io.sphere.sdk.producttypes.queries;

import io.sphere.sdk.producttypes.ProductType;
import io.sphere.sdk.queries.ResourceQueryModelImpl;
import io.sphere.sdk.queries.QueryModel;
import io.sphere.sdk.queries.StringQuerySortingModel;

public final class ProductTypeQueryModel extends ResourceQueryModelImpl<ProductType> {

    public static ProductTypeQueryModel of() {
        return new ProductTypeQueryModel(null, null);
    }

    private ProductTypeQueryModel(final QueryModel<ProductType> parent, final String pathSegment) {
        super(parent, pathSegment);
    }

    public StringQuerySortingModel<ProductType> name() {
        return stringModel("name");
    }

    public StringQuerySortingModel<ProductType> key() {
        return stringModel("key");
    }

    public AttributeDefinitionQueryModel<ProductType> attributes() {
        return new AttributeDefinitionQueryModelImpl<>(this, "attributes");
    }
}
