package io.sphere.sdk.search;

import io.sphere.sdk.models.Referenceable;

import java.util.List;
import java.util.Optional;

import static io.sphere.sdk.utils.IterableUtils.toStream;
import static java.util.stream.Collectors.toList;

public class ReferenceListSearchModel<T, R> extends SearchModelImpl<T> {
    public ReferenceListSearchModel(Optional<? extends SearchModel<T>> parent, String pathSegment) {
        super(parent, pathSegment);
    }

    public final FacetExpression<T> any() {
        return new StringSearchModel<>(Optional.of(this), "id").any();
    }

    public final FacetExpression<T> is(final Referenceable<R> reference) {
        return new StringSearchModel<>(Optional.of(this), "id").is(referenceToString(reference));
    }

    public final FacetExpression<T> isIn(final Iterable<? extends Referenceable<R>> references) {
        final List<String> ids = toStream(references).map(r -> referenceToString(r)).collect(toList());
        return new StringSearchModel<>(Optional.of(this), "id").isIn(ids);
    }

    private String referenceToString(Referenceable<R> referenceable) {
        return referenceable.toReference().getId();
    }
}
