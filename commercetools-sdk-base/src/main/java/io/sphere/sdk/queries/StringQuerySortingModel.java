package io.sphere.sdk.queries;

public interface StringQuerySortingModel<T> extends QuerySortingModel<T>, StringQueryModel<T> {

    @Override
    DirectionlessQuerySort<T> sort();

    @Override
    QueryPredicate<T> is(String s);

    @Override
    QueryPredicate<T> isNot(String s);

    @Override
    QueryPredicate<T> isIn(Iterable<String> args);

    @Override
    QueryPredicate<T> isGreaterThan(String s);

    @Override
    QueryPredicate<T> isLessThan(String s);

    @Override
    QueryPredicate<T> isLessThanOrEqualTo(String s);

    @Override
    QueryPredicate<T> isGreaterThanOrEqualTo(String s);

    @Override
    QueryPredicate<T> isNotIn(Iterable<String> args);

    @Override
    QueryPredicate<T> isPresent();

    @Override
    QueryPredicate<T> isNotPresent();


    /**
     * Internal: Escapes Strings like that (Scala notation) """query by name " test name"""
     * @param s the unescaped String
     * @return the escaped string
     */
    static String escape(final String s) {
        return s.replace("\"", "\\\"");
    }

    /**
     * Internal method to normalize Strings for queries.
     * @param s the unescaped String
     * @return the escaped String
     */
    static String normalize(final String s) {
        return '"' + escape(s) + '"';
    }
}
