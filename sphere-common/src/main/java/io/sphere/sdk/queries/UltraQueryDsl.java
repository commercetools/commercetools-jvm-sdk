package io.sphere.sdk.queries;

import io.sphere.sdk.http.HttpQueryParameter;

import java.util.List;
import java.util.function.Function;

import static java.util.Arrays.asList;

/**
 *
 * @param <T> type of the query result
 * @param <C> type of the class implementing this class
 * @param <Q> type of the query model
 * @param <E> type of the expansion model
 */
public interface UltraQueryDsl<T, C extends UltraQueryDsl<T, C, Q, E>, Q, E> extends EntityQuery<T>, ReferenceExpandeableDsl<T, C> {

    C withPredicate(final QueryPredicate<T> predicate);

    C withPredicate(final Function<Q, QueryPredicate<T>> m);

    C withSort(final List<QuerySort<T>> sort);

    default C withSort(final QuerySort<T> sort) {
        return withSort(asList(sort));
    }

    C withSort(final Function<Q, QuerySort<T>> m);

    C withSortMulti(final Function<Q, List<QuerySort<T>>> m);

    C withLimit(final long limit);

    C withOffset(final long offset);

    C withAdditionalQueryParameters(final List<HttpQueryParameter> additionalQueryParameters);

    C plusExpansionPath(final Function<E, ExpansionPath<T>> m);

    C withExpansionPath(final Function<E, ExpansionPath<T>> m);
}
