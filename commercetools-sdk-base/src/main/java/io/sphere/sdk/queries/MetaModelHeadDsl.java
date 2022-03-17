package io.sphere.sdk.queries;

import io.sphere.sdk.expansion.MetaModelReferenceExpansionDsl;

/**
 *
 * @param <R> type of the query result
 * @param <T> unit of the query result
 * @param <C> type of the class implementing this class
 * @param <E> type of the expansion model
 */
public interface MetaModelHeadDsl<R, T, C extends MetaModelHeadDsl<R, T, C, E>, E> extends HeadDsl<R, T, C>, MetaModelReferenceExpansionDsl<T, C, E> {

}
