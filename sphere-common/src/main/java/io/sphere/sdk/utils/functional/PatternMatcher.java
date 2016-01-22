package io.sphere.sdk.utils.functional;

import io.sphere.sdk.models.Base;

import javax.annotation.Nullable;
import java.util.function.Function;

/**
 * @deprecated This is an internal class.
 */
@Deprecated
public class PatternMatcher<T> extends Base {
    private final Object thing;
    @Nullable
    private final T result;

    PatternMatcher(final Object thing, @Nullable final T result) {
        this.thing = thing;
        this.result = result;
    }

    @SuppressWarnings("unchecked")
    public <C> PatternMatcher<T> when(final Class<? super C> type, final Function<C, ? extends T> f) {
        final boolean noActionRequired = result != null || !type.isAssignableFrom(thing.getClass());
        return noActionRequired ? this : new PatternMatcher<>(this.thing, f.apply((C) this.thing));
    }
}
