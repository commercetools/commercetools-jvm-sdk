package io.sphere.sdk.products.queries;

public interface EmbeddedProductDataQueryModel extends SharedProductProjectionProductDataQueryModel<EmbeddedProductDataQueryModel> {

    static EmbeddedProductDataQueryModel of() {
        return new EmbeddedProductDataQueryModelImpl(null, null);
    }
}
