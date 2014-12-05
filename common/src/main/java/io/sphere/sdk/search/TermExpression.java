package io.sphere.sdk.search;

import java.util.Optional;

import static io.sphere.sdk.utils.IterableUtils.toStream;
import static java.util.stream.Collectors.joining;

abstract class TermExpression<T> extends SearchModelExpression<T> {
    private final Iterable<String> terms;

    public TermExpression(final SearchModel<T> searchModel, final Iterable<String> terms) {
        super(searchModel);
        this.terms = terms;
    }

    @Override
    protected String render() {
        return toTermExpression().map(e -> ":" + e).orElse("");
    }

    /**
     * Turns a group of terms into an expression of the form "term1,term2,..."
     * @return the generated term expression.
     */
    private Optional<String> toTermExpression() {
        String termExpression = toStream(terms).filter(t -> !t.isEmpty()).collect(joining(","));
        if (termExpression.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(termExpression);
        }
    }
}
