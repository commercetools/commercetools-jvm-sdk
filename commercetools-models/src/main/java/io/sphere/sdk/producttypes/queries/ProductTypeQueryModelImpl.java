package io.sphere.sdk.producttypes.queries;

import io.sphere.sdk.producttypes.ProductType;
import io.sphere.sdk.queries.ResourceQueryModelImpl;
import io.sphere.sdk.queries.QueryModel;
import io.sphere.sdk.queries.StringQuerySortingModel;

final class ProductTypeQueryModelImpl extends ResourceQueryModelImpl<ProductType> implements ProductTypeQueryModel {

    ProductTypeQueryModelImpl(final QueryModel<ProductType> parent, final String pathSegment) {
        super(parent, pathSegment);
    }

    @Override
    public StringQuerySortingModel<ProductType> name() {
        return stringModel("name");
    }

    @Override
    public StringQuerySortingModel<ProductType> key() {
        return stringModel("key");
    }

    @Override
    public AttributeDefinitionQueryModel<ProductType> attributes() {
        return new AttributeDefinitionQueryModelImpl<>(this, "attributes");
    }
}
