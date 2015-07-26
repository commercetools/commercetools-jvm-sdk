package io.sphere.sdk.producttypes.queries;

import io.sphere.sdk.producttypes.ProductType;
import io.sphere.sdk.queries.DefaultModelQueryModelImpl;
import io.sphere.sdk.queries.QueryModel;
import io.sphere.sdk.queries.StringQuerySortingModel;

public final class ProductTypeQueryModel extends DefaultModelQueryModelImpl<ProductType> {

    public static ProductTypeQueryModel of() {
        return new ProductTypeQueryModel(null, null);
    }

    private ProductTypeQueryModel(final QueryModel<ProductType> parent, final String pathSegment) {
        super(parent, pathSegment);
    }

    public StringQuerySortingModel<ProductType> name() {
        return stringModel("name");
    }

    public AttributeDefinitionQueryModel<ProductType> attributes() {
        return new AttributeDefinitionQueryModel<>(this, "attributes");
    }
}
