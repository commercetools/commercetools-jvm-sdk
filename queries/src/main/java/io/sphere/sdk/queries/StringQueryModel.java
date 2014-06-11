package io.sphere.sdk.queries;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;

public class StringQueryModel<T> extends QueryModelImpl<T> {
    StringQueryModel(final Optional<? extends QueryModel<T>> parent, final String pathSegment) {
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

    public Predicate<T> is(final String s) {
        return new EqPredicate<>(this, escape(s));
    }

    public Predicate<T> isNot(final String s) {
        return new NotEqPredicate<>(this, escape(s));
    }

    public Predicate<T> isOneOf(final String arg0, final String ... args) {
        final ImmutableList<String> list = ImmutableList.<String>builder().add(arg0).add(args).build();
        return new IsInPredicate<>(this, Iterables.transform(list, new Function<String, String>() {
            @Override
            public String apply(final String s) {
                return escape(s);
            }
        }));
    }
}