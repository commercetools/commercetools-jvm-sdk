package io.sphere.sdk.products.queries;

public interface PartialProductVariantQueryModel extends ProductVariantQueryModel<PartialProductVariantQueryModel> {

    static <T> PartialProductVariantQueryModel of() {
        return new PartialProductVariantQueryModelImpl(null, null);
    }
}
