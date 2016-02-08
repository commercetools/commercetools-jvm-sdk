package io.sphere.sdk.queries;

import io.sphere.sdk.models.Referenceable;

import javax.annotation.Nullable;
import java.util.List;

import static java.util.stream.Collectors.toList;

final class AnyReferenceQueryModelImpl<T> extends QueryModelImpl<T> implements AnyReferenceQueryModel<T> {
    public AnyReferenceQueryModelImpl(@Nullable final QueryModel<T> parent, final @Nullable String pathSegment) {
        super(parent, pathSegment);
    }

    @Override
    public <R> QueryPredicate<T> is(final Referenceable<R> reference) {
        return id().is(reference.toReference().getId());
    }

    @Override
    public <R> QueryPredicate<T> isIn(final List<? extends Referenceable<R>> elements) {
        final List<String> ids = elements.stream().map(elem -> elem.toReference().getId()).collect(toList());
        return isInIds(ids);
    }

    @Override
    public QueryPredicate<T> isInIds(final List<String> ids) {
        return new IsInQueryPredicate<>(idSegment(), ids);
    }

    @Override
    public StringQueryModel<T> id() {
        return stringModel("id");
    }

    @Override
    public StringQueryModel<T> typeId() {
        return stringModel("typeId");
    }

    private QueryModelImpl<T> idSegment() {
        return new QueryModelImpl<>(this, "id");
    }
}
