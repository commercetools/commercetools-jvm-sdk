package io.sphere.sdk.queries;

public abstract class PredicateBase<T> implements Predicate<T> {
    public final Predicate<T> or(final Predicate<T> other) {
        return new PredicateConnector<>("or", this, other);
    }

    public final Predicate<T> and(final Predicate<T> other) {
        return new PredicateConnector<>("and", this, other);
    }

    protected String buildQuery(final QueryModel<T> model, final String definition) {
        final String current = (model.getPathSegment().isPresent() ? model.getPathSegment().get() : "") + definition;

        if (model.getParent().isPresent()) {
            QueryModel<T> parent = model.getParent().get();
            return buildQuery(parent, parent.getPathSegment().isPresent() ? "(" + current + ")" : current);
        } else {
            return current;
        }
    }

    @Override
    public String toString() {
        return toSphereQuery();
    }
}