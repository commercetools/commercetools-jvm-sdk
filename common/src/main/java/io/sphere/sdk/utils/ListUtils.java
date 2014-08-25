package io.sphere.sdk.utils;

import java.util.Optional;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;
import java.util.function.Predicate;

import static com.google.common.collect.Lists.newArrayList;
import static java.util.stream.Collectors.joining;

public final class ListUtils {
    private ListUtils() {
    }

    /**
     * Partitions <code>list</code> in two lists according to <code>predicate</code>.
     * @param list the list which should be divided
     * @param predicate returns true if the element of <code>list</code> should belong to the first result list
     * @param <T> generic type of the list
     * @return the first list satisfies <code>predicate</code>, the second one not.
     */
    public static <T> Pair<List<T>, List<T>> partition(final List<T> list, final Predicate<T> predicate) {
        final List<T> matchingPredicate = newArrayList();
        final List<T> notMatchingPredicate = newArrayList();
        for (final T element : list) {
            if (predicate.test(element)) {
                matchingPredicate.add(element);
            } else {
                notMatchingPredicate.add(element);
            }
        }
        return Pair.of(matchingPredicate, notMatchingPredicate);
    }

    public static <T> Optional<T> headOption(final List<T> list) {
        return IterableUtils.headOption(list);
    }

    public static <T> String join(final List<T> list) {
        return list.stream().map(i -> i.toString()).collect(joining(", "));
    }
}
