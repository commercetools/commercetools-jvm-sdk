package io.sphere.sdk.queries;

public interface StringQueryModel<T> {
    Predicate<T> is(String s);

    Predicate<T> isNot(String s);

    Predicate<T> isOneOf(String arg0, String... args);

    Predicate<T> isOneOf(Iterable<String> args);
}
