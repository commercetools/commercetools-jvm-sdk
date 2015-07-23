package io.sphere.sdk.search;

import io.sphere.sdk.utils.StringUtils;

import javax.annotation.Nullable;
import java.util.Optional;

import static io.sphere.sdk.utils.IterableUtils.toStream;
import static java.util.stream.Collectors.joining;

abstract class TermExpression<T, V> extends SearchModelExpression<T, V> {
    private final Iterable<V> terms;

    TermExpression(final SearchModel<T> searchModel, final TypeSerializer<V> typeSerializer, final Iterable<V> terms, @Nullable final String alias) {
        super(searchModel, typeSerializer, alias);
        this.terms = terms;
    }

    @Override
    protected String serializedValue() {
        return Optional.ofNullable(toTermExpression()).map(e -> ":" + e).orElse("");
    }

    /**
     * Turns a group of terms into an expression of the form "term1,term2,..."
     * @return the generated term expression.
     */
    private String toTermExpression() {
        return toStream(terms).map(t -> serializer().apply(t))
                .filter(t -> !t.isEmpty()).collect(joining(","));
    }
}
