package io.sphere.sdk.products.queries;

import io.sphere.sdk.queries.QueryModel;

public class PartialProductVariantQueryModel extends ProductVariantQueryModel<PartialProductVariantQueryModel> {
    private PartialProductVariantQueryModel(final QueryModel<PartialProductVariantQueryModel> parent, final String pathSegment) {
        super(parent, pathSegment);
    }

    public static PartialProductVariantQueryModel of() {
        return new PartialProductVariantQueryModel(null, null);
    }
}
