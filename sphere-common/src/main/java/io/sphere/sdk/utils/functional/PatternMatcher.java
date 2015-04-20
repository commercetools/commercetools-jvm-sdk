package io.sphere.sdk.utils.functional;

import io.sphere.sdk.models.Base;

import java.util.Optional;
import java.util.function.Function;

public class PatternMatcher<T> extends Base {
    private final Object thing;
    private final Optional<T> result;

    PatternMatcher(final Object thing, final Optional<T> result) {
        this.thing = thing;
        this.result = result;
    }

    @SuppressWarnings("unchecked")
    public <C> PatternMatcher<T> when(final Class<C> type, final Function<C, T> f) {
        final boolean noActionRequired = result.isPresent() || !type.isAssignableFrom(thing.getClass());
        return noActionRequired ? this : new PatternMatcher<>(thing, Optional.of(f.apply((C) thing)));
    }

    public Optional<T> toOption() {
        return result;
    }
}
