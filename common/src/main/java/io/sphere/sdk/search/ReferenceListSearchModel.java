package io.sphere.sdk.search;

import io.sphere.sdk.models.Referenceable;

import java.util.List;
import java.util.Optional;

import static io.sphere.sdk.utils.IterableUtils.toStream;
import static io.sphere.sdk.utils.ListUtils.listOf;
import static java.util.stream.Collectors.toList;

public class ReferenceListSearchModel<T, R> extends SearchModelImpl<T> {
    public ReferenceListSearchModel(Optional<? extends SearchModel<T>> parent, String pathSegment) {
        super(parent, pathSegment);
    }

    public final FacetExpression<T> isIn(final Iterable<? extends Referenceable<R>> references) {
        final List<String> ids = toStream(references).map(r -> r.toReference().getId()).collect(toList());
        return new StringSearchModel<>(Optional.of(this), "id").isOneOf(ids);
    }

    @SafeVarargs
    @SuppressWarnings("varargs")
    public final FacetExpression<T> isIn(final Referenceable<R> reference, final Referenceable<R> ... moreReferences) {
        return isIn(listOf(reference, moreReferences));
    }
}
