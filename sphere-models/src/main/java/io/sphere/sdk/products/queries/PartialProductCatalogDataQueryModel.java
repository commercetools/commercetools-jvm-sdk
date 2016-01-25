package io.sphere.sdk.products.queries;

public interface PartialProductCatalogDataQueryModel extends ProductCatalogDataQueryModel<PartialProductCatalogDataQueryModel> {
    static PartialProductCatalogDataQueryModel of() {
        return new PartialProductCatalogDataQueryModelImpl(null, null);
    }
}
