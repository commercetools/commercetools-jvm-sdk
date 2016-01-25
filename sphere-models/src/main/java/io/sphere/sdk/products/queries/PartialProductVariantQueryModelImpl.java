package io.sphere.sdk.products.queries;

import io.sphere.sdk.queries.QueryModel;

import javax.annotation.Nullable;

final class PartialProductVariantQueryModelImpl extends ProductVariantQueryModelImpl<PartialProductVariantQueryModel> implements PartialProductVariantQueryModel {
    PartialProductVariantQueryModelImpl(@Nullable final QueryModel<PartialProductVariantQueryModel> parent, @Nullable final String pathSegment) {
        super(parent, pathSegment);
    }

    public static PartialProductVariantQueryModel of() {
        return new PartialProductVariantQueryModelImpl(null, null);
    }
}
