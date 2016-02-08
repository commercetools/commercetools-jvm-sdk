package io.sphere.sdk.types.queries;

import io.sphere.sdk.expansion.ExpansionPath;
import io.sphere.sdk.models.Identifiable;
import io.sphere.sdk.queries.MetaModelGetDsl;
import io.sphere.sdk.types.Type;
import io.sphere.sdk.types.expansion.TypeExpansionModel;

import java.util.List;
import java.util.function.Function;

public interface TypeByIdGet extends MetaModelGetDsl<Type, Type, TypeByIdGet, TypeExpansionModel<Type>> {
    static TypeByIdGet of(final Identifiable<Type> cartDiscount) {
        return of(cartDiscount.getId());
    }

    static TypeByIdGet of(final String id) {
        return new TypeByIdGetImpl(id);
    }

    @Override
    TypeByIdGet plusExpansionPaths(final ExpansionPath<Type> expansionPath);

    @Override
    List<ExpansionPath<Type>> expansionPaths();

    @Override
    TypeByIdGet withExpansionPaths(final ExpansionPath<Type> expansionPath);

    @Override
    TypeByIdGet withExpansionPaths(final List<ExpansionPath<Type>> expansionPaths);
}

