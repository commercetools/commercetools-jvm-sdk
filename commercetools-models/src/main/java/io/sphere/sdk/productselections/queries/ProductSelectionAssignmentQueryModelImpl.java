package io.sphere.sdk.productselections.queries;

import io.sphere.sdk.products.Product;
import io.sphere.sdk.productselections.ProductSelection;
import io.sphere.sdk.queries.*;

final class ProductSelectionAssignmentQueryModelImpl<T> extends QueryModelImpl<T> implements ProductSelectionAssignmentQueryModel<T> {
    public ProductSelectionAssignmentQueryModelImpl(final QueryModel<T> parent, final String pathSegment) {
        super(parent, pathSegment);


    }

    @Override
    public ReferenceQueryModel<T, Product> product() {
        return referenceModel("product");
    }

    @Override
    public ReferenceQueryModel<T, ProductSelection> productSelection() {
        return referenceModel("product-selection");
    }
}
