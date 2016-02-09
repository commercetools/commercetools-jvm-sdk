package io.sphere.sdk.products.queries;

public interface EmbeddedProductCatalogDataQueryModel extends SharedProductCatalogDataQueryModel<EmbeddedProductCatalogDataQueryModel> {
    static EmbeddedProductCatalogDataQueryModel of() {
        return new EmbeddedProductCatalogDataQueryModelImpl(null, null);
    }
}
