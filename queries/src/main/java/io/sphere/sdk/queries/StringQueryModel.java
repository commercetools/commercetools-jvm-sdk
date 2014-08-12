package io.sphere.sdk.queries;

public interface StringQueryModel<T> {
    Predicate<T> is(String s);

    Predicate<T> isNot(String s);

    Predicate<T> isOneOf(String arg0, String... args);

    Predicate<T> isOneOf(Iterable<String> args);

    Predicate<T> isGreaterThan(final String value);

    Predicate<T> isLessThan(final String value);

    Predicate<T> isLessThanOrEquals(final String value);

    Predicate<T> isGreaterThanOrEquals(final String value);

    Predicate<T> isNotIn(final Iterable<String> args);

    Predicate<T> isNotIn(final String arg0, final String... args);

    Predicate<T> isPresent();

    Predicate<T> isNotPresent();
}
