package io.sphere.sdk.search;

import io.sphere.sdk.models.Referenceable;

import java.util.Optional;

public class ReferenceSearchModel<T, R> extends SearchModelImpl<T> {

    public ReferenceSearchModel(final Optional<? extends SearchModel<T>> parent, final String pathSegment) {
        super(parent, pathSegment);
    }

    public TermFilterSearchModel<T, Referenceable<R>> filter() {
        return new TermFilterSearchModel<>(Optional.of(this), Optional.of("id"), this::render);
    }

    public TermFacetSearchModel<T, Referenceable<R>> facet() {
        return new TermFacetSearchModel<>(Optional.of(this), Optional.of("id"), this::render);
    }

    private String render(final Referenceable<R> referenceable) {
        return "\"" + referenceToString(referenceable) + "\"";
    }

    /**
     * Converts the given reference to string format.
     * @param referenceable the reference instance.
     * @return the reference as string.
     */
    private String referenceToString(final Referenceable<R> referenceable) {
        return referenceable.toReference().getId();
    }
}
