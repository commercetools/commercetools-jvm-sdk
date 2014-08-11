package io.sphere.sdk.queries;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;

import java.util.Optional;

public class StringQuerySortingModel<T> extends QueryModelImpl<T> implements SortingModel<T>, StringQueryModel<T> {
    public StringQuerySortingModel(Optional<? extends QueryModel<T>> parent, String pathSegment) {
        super(parent, pathSegment);
    }

    /**
     * Escapes Strings like that (Scala notation) """query by name " test name"""
     * @param s the unescaped String
     * @return the escaped string
     */
    public static String escape(final String s) {
        return s.replace("\"", "\\\"");
    }

    @Override
    public Predicate<T> is(final String s) {
        return new EqPredicate<>(this, escape(s));
    }

    @Override
    public Predicate<T> isNot(final String s) {
        return new NotEqPredicate<>(this, escape(s));
    }

    @Override
    public Predicate<T> isOneOf(final String arg0, final String... args) {
        final ImmutableList<String> list = ImmutableList.<String>builder().add(arg0).add(args).build();
        return isOneOf(list);
    }

    @Override
    public Predicate<T> isOneOf(final Iterable<String> args) {
        return new IsInPredicate<>(this, Iterables.transform(args, s -> escape(s)));
    }

    @Override
    public Sort<T> sort(SortDirection sortDirection) {
        return new SphereSort<>(this, sortDirection);
    }
}