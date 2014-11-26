package io.sphere.sdk.search;

import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import static io.sphere.sdk.utils.IterableUtils.toStream;

class TermFacetExpression<T, V> extends SearchModelFacetExpression<T>  {
    private final Iterable<Term<V>> terms;

    public TermFacetExpression(SearchModel<T> searchModel, Iterable<Term<V>> terms) {
        super(searchModel);
        this.terms = terms;
    }

    @Override
    protected String render() {
        if (terms.iterator().hasNext()) {
            return ":" + renderTerms(terms);
        } else {
            return "";
        }
    }

    private String renderTerms(Iterable<Term<V>> terms) {
        return toStream(terms).map(t -> t.render()).filter(termIsValid()).collect(rangeJoiner());
    }

    private Predicate<String> termIsValid() {
        return term -> !term.isEmpty();
    }

    private Collector<CharSequence, ?, String> rangeJoiner() {
        return Collectors.joining(",");
    }
}
