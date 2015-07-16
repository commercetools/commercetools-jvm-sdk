package io.sphere.sdk.queries;

import io.sphere.sdk.models.Base;

abstract class QueryPredicateBase<T> extends Base implements QueryPredicate<T> {
    public final QueryPredicate<T> or(final QueryPredicate<T> other) {
        return new QueryPredicateConnector<>("or", this, other);
    }

    public final QueryPredicate<T> and(final QueryPredicate<T> other) {
        return new QueryPredicateConnector<>("and", this, other);
    }

    @Override
    public QueryPredicate<T> negate() {
        return new NegatedQueryPredicate<>(this);
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
        return "Predicate[" + toSphereQuery() + "]";
    }

    @SuppressWarnings("rawtypes")
    @Override
    public final boolean equals(final Object o) {
        return o != null && (o instanceof QueryPredicate) && toSphereQuery().equals(((QueryPredicate)o).toSphereQuery());
    }

    @Override
    public final int hashCode() {
        return toSphereQuery().hashCode();
    }
}