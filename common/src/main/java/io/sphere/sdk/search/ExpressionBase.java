package io.sphere.sdk.search;

import io.sphere.sdk.models.Base;

abstract class ExpressionBase<T> extends Base {

    protected String buildQuery(final SearchModel<T> model, final String definition) {
        final String current = (model.getPathSegment().isPresent() ? model.getPathSegment().get() : "") + definition;

        if (model.getParent().isPresent()) {
            SearchModel<T> parent = model.getParent().get();
            boolean printSeparator = parent.getPathSegment().isPresent() && model.getPathSegment().isPresent();
            return buildQuery(parent, printSeparator ? "." + current : current);
        } else {
            return current;
        }
    }

    protected abstract String toSphereSearchExpression();

    @Override
    public String toString() {
        return toSphereSearchExpression();
    }

    @Override
    public final int hashCode() {
        return toSphereSearchExpression().hashCode();
    }
}
