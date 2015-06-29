package io.sphere.sdk.productdiscounts.queries;

import io.sphere.sdk.expansion.ExpansionPath;
import io.sphere.sdk.models.Identifiable;
import io.sphere.sdk.productdiscounts.ProductDiscount;
import io.sphere.sdk.productdiscounts.expansion.ProductDiscountExpansionModel;
import io.sphere.sdk.queries.MetaModelFetchDsl;

import java.util.List;
import java.util.function.Function;

public interface ProductDiscountByIdFetch extends MetaModelFetchDsl<ProductDiscount, ProductDiscountByIdFetch, ProductDiscountExpansionModel<ProductDiscount>> {
    static ProductDiscountByIdFetch of(final Identifiable<ProductDiscount> cartDiscount) {
        return of(cartDiscount.getId());
    }

    static ProductDiscountByIdFetch of(final String id) {
        return new ProductDiscountByIdFetchImpl(id);
    }

    @Override
    ProductDiscountByIdFetch plusExpansionPaths(final Function<ProductDiscountExpansionModel<ProductDiscount>, ExpansionPath<ProductDiscount>> m);

    @Override
    ProductDiscountByIdFetch withExpansionPaths(final Function<ProductDiscountExpansionModel<ProductDiscount>, ExpansionPath<ProductDiscount>> m);

    @Override
    List<ExpansionPath<ProductDiscount>> expansionPaths();

    @Override
    ProductDiscountByIdFetch plusExpansionPaths(final ExpansionPath<ProductDiscount> expansionPath);

    @Override
    ProductDiscountByIdFetch withExpansionPaths(final ExpansionPath<ProductDiscount> expansionPath);

    @Override
    ProductDiscountByIdFetch withExpansionPaths(final List<ExpansionPath<ProductDiscount>> expansionPaths);
}

