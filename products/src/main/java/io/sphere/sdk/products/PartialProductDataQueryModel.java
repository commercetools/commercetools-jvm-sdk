package io.sphere.sdk.products;

import io.sphere.sdk.queries.QueryModel;

import java.util.Optional;

public class PartialProductDataQueryModel extends ProductDataQueryModel<PartialProductDataQueryModel> {
    public PartialProductDataQueryModel(final Optional<? extends QueryModel<io.sphere.sdk.products.PartialProductDataQueryModel>> parent, final Optional<String> pathSegment) {
        super(parent, pathSegment);
    }
}
