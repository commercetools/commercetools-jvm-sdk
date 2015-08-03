package io.sphere.sdk.queries;

import io.sphere.sdk.models.Referenceable;

import javax.annotation.Nullable;
import java.util.List;

import static java.util.stream.Collectors.toList;

class ReferenceQueryModelImpl<T, R> extends QueryModelImpl<T> implements ReferenceQueryModel<T,R> {
    public ReferenceQueryModelImpl(@Nullable final QueryModel<T> parent, @Nullable final String pathSegment) {
        super(parent, pathSegment);
    }

    @Override
    public QueryPredicate<T> is(final Referenceable<R> reference) {
        final String id = reference.toReference().getId();
        return id().is(id);
    }

    @Override
    public QueryPredicate<T> isInIds(final List<String> ids) {
        return new IsInQueryPredicate<>(idSegment(),
                ids.stream()
                .map(StringQuerySortingModel::normalize).collect(toList()));
    }

    @Override
    public QueryPredicate<T> isIn(final List<? extends Referenceable<R>> elements) {
        final List<String> ids = elements.stream().map(elem -> elem.toReference().getId()).collect(toList());
        return isInIds(ids);
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
