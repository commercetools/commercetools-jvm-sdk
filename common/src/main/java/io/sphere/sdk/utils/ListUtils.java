package io.sphere.sdk.utils;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.Lists;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

public final class ListUtils {
    private ListUtils() {
    }

    /**
     * Partitions <code>list</code> in two lists according to <code>predicate</code>.
     * @param list
     * @param predicate
     * @param <T>
     * @return the first list satisfies <code>predicate</code>, the second one not.
     */
    public static <T> Pair<List<T>, List<T>> partition(final List<T> list, final Predicate<T> predicate) {
        final List<T> matchingPredicate = newArrayList();
        final List<T> notMatchingPredicate = newArrayList();
        for (final T element : list) {
            if (predicate.apply(element)) {
                matchingPredicate.add(element);
            } else {
                notMatchingPredicate.add(element);
            }
        }
        return new Pair<>(matchingPredicate, notMatchingPredicate);
    }
}
