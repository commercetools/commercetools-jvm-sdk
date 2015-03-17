package io.sphere.sdk.queries;

import java.util.List;
import java.util.Optional;

import static io.sphere.sdk.utils.IterableUtils.toStream;
import static io.sphere.sdk.utils.ListUtils.*;
import static java.util.stream.Collectors.toList;

public class StringQuerySortingModel<T> extends QueryModelImpl<T> implements SortingModel<T>, StringQueryModel<T> {
    public StringQuerySortingModel(Optional<? extends QueryModel<T>> parent, String pathSegment) {
        super(parent, pathSegment);
    }

    @Override
    public Sort<T> sort(SortDirection sortDirection) {
        return new SphereSort<>(this, sortDirection);
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
        return EqPredicate.of(this, escape(s));
    }

    @Override
    public Predicate<T> isNot(final String s) {
        return NotEqPredicate.of(this, escape(s));
    }

    @Override
    public Predicate<T> isOneOf(final String arg0, final String ... args) {
        return isOneOf(listOf(arg0, args));
    }

    @Override
    public Predicate<T> isOneOf(final Iterable<String> args) {
        return new IsInPredicate<>(this, escape(args));
    }

    @Override
    public Predicate<T> isIn(final Iterable<String> args) {
        return isOneOf(args);
    }

    @Override
    public Predicate<T> isGreaterThan(final String value) {
        return new IsGreaterThanPredicate<>(this, value);
    }

    @Override
    public Predicate<T> isLessThan(final String value) {
        return new IsLessThanPredicate<>(this, value);
    }

    @Override
    public Predicate<T> isLessThanOrEquals(final String value) {
        return new IsLessThanOrEqualsPredicate<>(this, value);
    }

    @Override
    public Predicate<T> isGreaterThanOrEquals(final String value) {
        return new IsGreaterThanOrEqualsPredicate<>(this, value);
    }

    @Override
    public Predicate<T> isNotIn(final Iterable<String> args) {
        return new IsNotInPredicate<>(this, escape(args));
    }

    @Override
    public Predicate<T> isNotIn(final String arg0, final String ... args) {
        return isNotIn(listOf(arg0, args));
    }

    @Override
    public Predicate<T> isPresent() {
        return new OptionalPredicate<>(this, true);
    }

    @Override
    public Predicate<T> isNotPresent() {
        return new OptionalPredicate<>(this, false);
    }

    private static List<String> escape(final Iterable<String> args) {
        return toStream(args).map(x -> escape(x)).collect(toList());
    }
}