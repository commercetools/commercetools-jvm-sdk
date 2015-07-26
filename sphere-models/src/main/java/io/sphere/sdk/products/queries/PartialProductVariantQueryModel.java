package io.sphere.sdk.products.queries;

import io.sphere.sdk.queries.QueryModel;

import javax.annotation.Nullable;

public class PartialProductVariantQueryModel extends ProductVariantQueryModel<PartialProductVariantQueryModel> {
    private PartialProductVariantQueryModel(@Nullable final QueryModel<PartialProductVariantQueryModel> parent, @Nullable final String pathSegment) {
        super(parent, pathSegment);
    }

    public static PartialProductVariantQueryModel of() {
        return new PartialProductVariantQueryModel(null, null);
    }
}
