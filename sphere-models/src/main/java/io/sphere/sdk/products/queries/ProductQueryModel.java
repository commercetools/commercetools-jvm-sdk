package io.sphere.sdk.products.queries;

import io.sphere.sdk.products.Product;
import io.sphere.sdk.producttypes.ProductType;
import io.sphere.sdk.queries.QueryModel;
import io.sphere.sdk.queries.ReferenceOptionalQueryModel;
import io.sphere.sdk.queries.ReferenceQueryModel;
import io.sphere.sdk.queries.ResourceQueryModelImpl;
import io.sphere.sdk.states.State;

import javax.annotation.Nullable;

public class ProductQueryModel extends ResourceQueryModelImpl<Product> {

    public static ProductQueryModel of() {
        return new ProductQueryModel(null, null);
    }

    private ProductQueryModel(@Nullable final QueryModel<Product> parent, @Nullable final String pathSegment) {
        super(parent, pathSegment);
    }

    public ProductCatalogDataQueryModel<Product> masterData() {
        return new ProductCatalogDataQueryModel<>(this, "masterData");
    }

    public ReferenceQueryModel<Product, ProductType> productType() {
        return referenceModel("productType");
    }


    public ReferenceOptionalQueryModel<Product, State> state() {
        return referenceOptionalModel("state");
    }
}