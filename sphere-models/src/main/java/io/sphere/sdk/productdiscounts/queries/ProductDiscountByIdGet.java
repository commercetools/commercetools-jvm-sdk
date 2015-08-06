package io.sphere.sdk.productdiscounts.queries;

import io.sphere.sdk.expansion.ExpansionPath;
import io.sphere.sdk.models.Identifiable;
import io.sphere.sdk.productdiscounts.ProductDiscount;
import io.sphere.sdk.productdiscounts.expansion.ProductDiscountExpansionModel;
import io.sphere.sdk.queries.MetaModelGetDsl;

import java.util.List;
import java.util.function.Function;

public interface ProductDiscountByIdGet extends MetaModelGetDsl<ProductDiscount, ProductDiscount, ProductDiscountByIdGet, ProductDiscountExpansionModel<ProductDiscount>> {
    static ProductDiscountByIdGet of(final Identifiable<ProductDiscount> cartDiscount) {
        return of(cartDiscount.getId());
    }

    static ProductDiscountByIdGet of(final String id) {
        return new ProductDiscountByIdGetImpl(id);
    }

    @Override
    ProductDiscountByIdGet plusExpansionPaths(final Function<ProductDiscountExpansionModel<ProductDiscount>, ExpansionPath<ProductDiscount>> m);

    @Override
    ProductDiscountByIdGet withExpansionPaths(final Function<ProductDiscountExpansionModel<ProductDiscount>, ExpansionPath<ProductDiscount>> m);

    @Override
    List<ExpansionPath<ProductDiscount>> expansionPaths();

    @Override
    ProductDiscountByIdGet plusExpansionPaths(final ExpansionPath<ProductDiscount> expansionPath);

    @Override
    ProductDiscountByIdGet withExpansionPaths(final ExpansionPath<ProductDiscount> expansionPath);

    @Override
    ProductDiscountByIdGet withExpansionPaths(final List<ExpansionPath<ProductDiscount>> expansionPaths);
}

