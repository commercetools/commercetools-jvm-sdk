package io.sphere.sdk.queries;

import io.sphere.sdk.models.Referenceable;

import javax.annotation.Nullable;
import java.util.List;

import static io.sphere.sdk.utils.IterableUtils.toStream;
import static io.sphere.sdk.utils.ListUtils.listOf;
import static java.util.stream.Collectors.toList;

public class ReferenceCollectionQueryModel<T, R> extends QueryModelImpl<T> {
    public ReferenceCollectionQueryModel(@Nullable final QueryModel<T> parent, @Nullable final String pathSegment) {
        super(parent, pathSegment);
    }

    public final QueryPredicate<T> isIn(final Iterable<? extends Referenceable<R>> references) {
        final List<String> ids = toStream(references).map(r -> r.toReference().getId()).collect(toList());
        return stringModel("id").isIn(ids);
    }

    @SafeVarargs
    @SuppressWarnings("varargs")
    public final QueryPredicate<T> isIn(final Referenceable<R> reference, final Referenceable<R> ... moreReferences) {
        return isIn(listOf(reference, moreReferences));
    }
}
