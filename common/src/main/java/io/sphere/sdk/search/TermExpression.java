package io.sphere.sdk.search;

import java.util.Optional;

import static io.sphere.sdk.utils.IterableUtils.toStream;
import static java.util.stream.Collectors.joining;

abstract class TermExpression<T, V> extends SearchModelExpression<T, V> {
    private final Iterable<V> terms;

    TermExpression(final SearchModel<T> searchModel, final TypeSerializer<V> typeSerializer, final Iterable<V> terms, final Optional<String> alias) {
        super(searchModel, typeSerializer, alias);
        this.terms = terms;
    }

    @Override
    protected String serializedValue() {
        return toTermExpression().map(e -> ":" + e).orElse("");
    }

    /**
     * Turns a group of terms into an expression of the form "term1,term2,..."
     * @return the generated term expression.
     */
    private Optional<String> toTermExpression() {
        String termExpression = toStream(terms).map(t -> serializer().apply(t))
                .filter(t -> !t.isEmpty()).collect(joining(","));
        if (termExpression.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(termExpression);
        }
    }
}
