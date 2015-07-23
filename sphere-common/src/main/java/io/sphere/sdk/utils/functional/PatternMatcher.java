package io.sphere.sdk.utils.functional;

import io.sphere.sdk.models.Base;

import javax.annotation.Nullable;
import java.util.function.Function;

public class PatternMatcher<T> extends Base {
    private final Object thing;
    private @Nullable final T result;

    PatternMatcher(final Object thing, @Nullable final T result) {
        this.thing = thing;
        this.result = result;
    }

    @SuppressWarnings("unchecked")
    public <C> PatternMatcher<T> when(final Class<C> type, final Function<C, T> f) {
        final boolean noActionRequired = result != null || !type.isAssignableFrom(thing.getClass());
        return noActionRequired ? this : new PatternMatcher<>(thing, f.apply((C) thing));
    }
}
