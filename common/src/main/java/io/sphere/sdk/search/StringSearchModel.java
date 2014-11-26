package io.sphere.sdk.search;

import java.util.List;
import java.util.Optional;

import static io.sphere.sdk.utils.IterableUtils.toStream;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;

public class StringSearchModel<T> extends SearchModelImpl<T> {

    public StringSearchModel(Optional<? extends SearchModel<T>> parent, String pathSegment) {
        super(parent, pathSegment);
    }

    public FacetExpression<T> any() {
        return new TermFacetExpression<>(this, asList());
    }

    public FacetExpression<T> is(final String s) {
        return isIn(asList(s));
    }

    public FacetExpression<T> isIn(final Iterable<String> args) {
        List<Term<String>> terms = toStream(args).map(s -> escape(s)).map(s -> StringTerm.of(s)).collect(toList());
        return new TermFacetExpression<>(this, terms);
    }

    /**
     * Escapes Strings like that (Scala notation) """query by name " test name"""
     * @param s the unescaped String
     * @return the escaped string
     */
    public static String escape(final String s) {
        return s.replace("\"", "\\\"");
    }
}