package io.sphere.sdk.productselections.queries;


import io.sphere.sdk.expansion.ExpansionPath;
import io.sphere.sdk.models.Identifiable;
import io.sphere.sdk.productselections.ProductSelection;
import io.sphere.sdk.productselections.expansion.ProductSelectionExpansionModel;
import io.sphere.sdk.queries.MetaModelGetDsl;

import java.util.List;


public interface ProductSelectionByIdProductGet extends MetaModelGetDsl<ProductSelection, ProductSelection, ProductSelectionByIdProductGet, ProductSelectionExpansionModel<ProductSelection>> {

    static ProductSelectionByIdProductGet of(final Identifiable<ProductSelection> productSelection) {
        return of(productSelection.getId());
    }

    static ProductSelectionByIdProductGet of(final String id) {
        return new ProductSelectionByIdProductGetImpl(id);
    }

    @Override
    List<ExpansionPath<ProductSelection>> expansionPaths();

    @Override
    ProductSelectionByIdProductGet plusExpansionPaths(final ExpansionPath<ProductSelection> expansionPath);

    @Override
    ProductSelectionByIdProductGet withExpansionPaths(final ExpansionPath<ProductSelection> expansionPath);

    @Override
    ProductSelectionByIdProductGet withExpansionPaths(final List<ExpansionPath<ProductSelection>> expansionPaths);

}

