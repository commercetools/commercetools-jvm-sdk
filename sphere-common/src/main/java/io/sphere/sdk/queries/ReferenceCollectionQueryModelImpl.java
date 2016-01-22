package io.sphere.sdk.queries;

import io.sphere.sdk.models.Referenceable;

import javax.annotation.Nullable;
import java.util.List;

import static io.sphere.sdk.utils.SphereInternalUtils.toStream;
import static java.util.stream.Collectors.toList;

final class ReferenceCollectionQueryModelImpl<T, R> extends QueryModelImpl<T> implements ReferenceCollectionQueryModel<T,R> {
    public ReferenceCollectionQueryModelImpl(@Nullable final QueryModel<T> parent, @Nullable final String pathSegment) {
        super(parent, pathSegment);
    }

    @Override
    public final QueryPredicate<T> isIn(final Iterable<? extends Referenceable<R>> references) {
        final List<String> ids = toStream(references).map(r -> r.toReference().getId()).collect(toList());
        return id().isIn(ids);
    }

    @Override
    public StringQueryModel<T> id() {
        return stringModel("id");
    }

    @Override
    public QueryPredicate<T> isEmpty() {
        return isEmptyCollectionQueryPredicate();
    }

    @Override
    public QueryPredicate<T> isNotEmpty() {
        return isNotEmptyCollectionQueryPredicate();
    }
}
