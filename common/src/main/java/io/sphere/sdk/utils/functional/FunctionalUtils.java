package io.sphere.sdk.utils.functional;

import io.sphere.sdk.models.Base;

import java.util.Optional;

public final class FunctionalUtils extends Base {
    private FunctionalUtils() {
        //utility class
    }

    public static <T> PatternMatcher<T> patternMatching(final Object objectToMatch) {
        return new PatternMatcher<>(objectToMatch, Optional.empty());
    }
}
