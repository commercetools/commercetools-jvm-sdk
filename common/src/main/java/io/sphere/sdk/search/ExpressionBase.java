package io.sphere.sdk.search;

import io.sphere.sdk.models.Base;

import static io.sphere.sdk.utils.IterableUtils.toStream;
import static java.util.stream.Collectors.joining;

abstract class ExpressionBase<T> extends Base {

    protected String buildQuery(final SearchModel<T> model, final String definition) {
        return toStream(model.buildPath()).collect(joining(".")) + definition;
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
