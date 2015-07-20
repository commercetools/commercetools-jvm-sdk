package io.sphere.sdk.products.queries;

import io.sphere.sdk.queries.QueryModel;

import java.util.Optional;

public class PartialProductVariantQueryModel extends ProductVariantQueryModel<PartialProductVariantQueryModel> {
    private PartialProductVariantQueryModel(final Optional<? extends QueryModel<PartialProductVariantQueryModel>> parent, final Optional<String> pathSegment) {
        super(parent, pathSegment);
    }

    public static PartialProductVariantQueryModel of() {
        return new PartialProductVariantQueryModel(Optional.empty(), Optional.empty());
    }
}
