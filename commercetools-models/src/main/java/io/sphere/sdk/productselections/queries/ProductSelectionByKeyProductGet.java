package io.sphere.sdk.productselections.queries;

import io.sphere.sdk.expansion.ExpansionPath;
import io.sphere.sdk.products.Product;
import io.sphere.sdk.products.expansion.ProductExpansionModel;
import io.sphere.sdk.productselections.ProductSelection;
import io.sphere.sdk.productselections.expansion.ProductSelectionExpansionModel;
import io.sphere.sdk.queries.MetaModelGetDsl;

import java.util.List;

public interface ProductSelectionByKeyProductGet extends MetaModelGetDsl<ProductSelection, ProductSelection, ProductSelectionByKeyProductGet, ProductSelectionExpansionModel<ProductSelection>> {

    static ProductSelectionByKeyProductGet of(final String key) {
        return new ProductSelectionByKeyProductGetImpl(key);
    }

    @Override
    List<ExpansionPath<ProductSelection>> expansionPaths();

    @Override
    ProductSelectionByKeyProductGet plusExpansionPaths(final ExpansionPath<ProductSelection> expansionPath);

    @Override
    ProductSelectionByKeyProductGet withExpansionPaths(final ExpansionPath<ProductSelection> expansionPath);

    @Override
    ProductSelectionByKeyProductGet withExpansionPaths(final List<ExpansionPath<ProductSelection>> expansionPaths);
}

