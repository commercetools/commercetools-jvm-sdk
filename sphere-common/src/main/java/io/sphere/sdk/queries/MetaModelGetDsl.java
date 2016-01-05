package io.sphere.sdk.queries;

import io.sphere.sdk.expansion.MetaModelExpansionDsl;

/**
 *
 * @param <R> type of the query result
 * @param <T> unit of the query result
 * @param <C> type of the class implementing this class
 * @param <E> type of the expansion model
 */
public interface MetaModelGetDsl<R, T, C extends MetaModelGetDsl<R, T, C, E>, E> extends GetDsl<R, T, C>, MetaModelExpansionDsl<T, C, E> {

}
