package io.sphere.sdk.queries;

import io.sphere.sdk.models.Referenceable;

import java.util.List;
import java.util.Optional;

import static io.sphere.sdk.utils.IterableUtils.toStream;
import static java.util.stream.Collectors.toList;

public class ReferenceListQueryModel<T, R> extends QueryModelImpl<T> {
    public ReferenceListQueryModel(Optional<? extends QueryModel<T>> parent, String pathSegment) {
        super(parent, pathSegment);
    }

    public Predicate<T> isIn(final Iterable<? extends Referenceable<R>> references) {
        final List<String> ids = toStream(references).map(r -> r.toReference().getId()).collect(toList());
        return new StringQuerySortingModel<>(Optional.of(this), "id").isOneOf(ids);
    }
}
