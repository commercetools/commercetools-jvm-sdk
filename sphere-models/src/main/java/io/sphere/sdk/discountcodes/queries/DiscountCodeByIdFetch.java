package io.sphere.sdk.discountcodes.queries;

import io.sphere.sdk.discountcodes.DiscountCode;
import io.sphere.sdk.discountcodes.expansion.DiscountCodeExpansionModel;
import io.sphere.sdk.models.Identifiable;
import io.sphere.sdk.expansion.ExpansionPath;
import io.sphere.sdk.queries.MetaModelFetchDsl;

import java.util.List;
import java.util.function.Function;

public interface DiscountCodeByIdFetch extends MetaModelFetchDsl<DiscountCode, DiscountCodeByIdFetch, DiscountCodeExpansionModel<DiscountCode>> {
    static DiscountCodeByIdFetch of(final Identifiable<DiscountCode> cartDiscount) {
        return of(cartDiscount.getId());
    }

    static DiscountCodeByIdFetch of(final String id) {
        return new DiscountCodeByIdFetchImpl(id);
    }

    @Override
    DiscountCodeByIdFetch plusExpansionPaths(final Function<DiscountCodeExpansionModel<DiscountCode>, ExpansionPath<DiscountCode>> m);

    @Override
    DiscountCodeByIdFetch withExpansionPaths(final Function<DiscountCodeExpansionModel<DiscountCode>, ExpansionPath<DiscountCode>> m);

    @Override
    List<ExpansionPath<DiscountCode>> expansionPaths();

    @Override
    DiscountCodeByIdFetch plusExpansionPaths(final ExpansionPath<DiscountCode> expansionPath);

    @Override
    DiscountCodeByIdFetch withExpansionPaths(final ExpansionPath<DiscountCode> expansionPath);

    @Override
    DiscountCodeByIdFetch withExpansionPaths(final List<ExpansionPath<DiscountCode>> expansionPaths);
}

