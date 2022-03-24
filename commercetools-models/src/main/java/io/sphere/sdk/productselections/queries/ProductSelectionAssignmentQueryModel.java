package io.sphere.sdk.productselections.queries;

import io.sphere.sdk.products.Product;
import io.sphere.sdk.productselections.ProductSelection;
import io.sphere.sdk.queries.*;

public interface ProductSelectionAssignmentQueryModel<T> {

    ReferenceQueryModel<T, Product> product();

    ReferenceQueryModel<T, ProductSelection> productSelection();
}
