package io.sphere.sdk.products.queries;

import io.sphere.sdk.queries.QueryModel;

import javax.annotation.Nullable;

final class EmbeddedProductVariantQueryModelImpl extends ProductVariantQueryModelImpl<EmbeddedProductVariantQueryModel> implements EmbeddedProductVariantQueryModel {
    EmbeddedProductVariantQueryModelImpl(@Nullable final QueryModel<EmbeddedProductVariantQueryModel> parent, @Nullable final String pathSegment) {
        super(parent, pathSegment);
    }

    public static EmbeddedProductVariantQueryModel of() {
        return new EmbeddedProductVariantQueryModelImpl(null, null);
    }
}
