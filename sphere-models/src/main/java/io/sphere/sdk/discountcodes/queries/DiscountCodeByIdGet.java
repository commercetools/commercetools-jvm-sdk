package io.sphere.sdk.discountcodes.queries;

import io.sphere.sdk.discountcodes.DiscountCode;
import io.sphere.sdk.discountcodes.expansion.DiscountCodeExpansionModel;
import io.sphere.sdk.models.Identifiable;
import io.sphere.sdk.expansion.ExpansionPath;
import io.sphere.sdk.queries.MetaModelGetDsl;

import java.util.List;
import java.util.function.Function;

public interface DiscountCodeByIdGet extends MetaModelGetDsl<DiscountCode, DiscountCode, DiscountCodeByIdGet, DiscountCodeExpansionModel<DiscountCode>> {
    static DiscountCodeByIdGet of(final Identifiable<DiscountCode> cartDiscount) {
        return of(cartDiscount.getId());
    }

    static DiscountCodeByIdGet of(final String id) {
        return new DiscountCodeByIdGetImpl(id);
    }

    @Override
    List<ExpansionPath<DiscountCode>> expansionPaths();

    @Override
    DiscountCodeByIdGet plusExpansionPaths(final ExpansionPath<DiscountCode> expansionPath);

    @Override
    DiscountCodeByIdGet withExpansionPaths(final ExpansionPath<DiscountCode> expansionPath);

    @Override
    DiscountCodeByIdGet withExpansionPaths(final List<ExpansionPath<DiscountCode>> expansionPaths);
}

