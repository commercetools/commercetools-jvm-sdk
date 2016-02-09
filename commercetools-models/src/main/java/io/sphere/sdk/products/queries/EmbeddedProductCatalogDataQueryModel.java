package io.sphere.sdk.products.queries;

public interface EmbeddedProductCatalogDataQueryModel extends ProductCatalogDataQueryModel<EmbeddedProductCatalogDataQueryModel> {
    static EmbeddedProductCatalogDataQueryModel of() {
        return new EmbeddedProductCatalogDataQueryModelImpl(null, null);
    }
}
