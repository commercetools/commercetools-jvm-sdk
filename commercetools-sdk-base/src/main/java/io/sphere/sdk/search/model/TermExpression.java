package io.sphere.sdk.search.model;

import javax.annotation.Nullable;
import java.util.function.Function;

import static io.sphere.sdk.utils.SphereInternalUtils.toStream;
import static java.util.stream.Collectors.joining;

abstract class TermExpression<T, V> extends SearchModelExpression<T, V> {
    private final Iterable<V> terms;

    TermExpression(final SearchModel<T> searchModel, final Function<V, String> typeSerializer, final Iterable<V> terms, @Nullable final String alias, final Boolean isCountingProducts) {
        super(searchModel, typeSerializer, alias, isCountingProducts);
        this.terms = terms;
    }

    /**
     * Turns a group of terms into an expression of the form ":term1,term2,..."
     * @return the generated term expression.
     */
    @Override
    public String value() {
        return ":" + toStream(terms)
                .map(t -> serializer().apply(t))
                .filter(t -> !t.isEmpty())
                .collect(joining(","));
    }
}
