package io.sphere.sdk.expansion;

/**
 * @param <T> unit of the command result
 * @param <C> type of the class implementing this class
 * @param <E> type of the expansion model
 */
public interface MetaModelExpansionDslExpansionModelRead<T, C, E> extends MetaModelReferenceExpansionDsl<T, C, E> {
    E expansionModel();
}
